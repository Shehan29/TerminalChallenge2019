import gamelib
import random
from gamelib.util import debug_write
import sys
import utility

"""
Most of the algo code you write will be in this file unless you create new
modules yourself. Start by modifying the 'on_turn' function.

Advanced strategy tips: 

Additional functions are made available by importing the AdvancedGameState 
class from gamelib/advanced.py as a replcement for the regular GameState class 
in game.py.

You can analyze action frames by modifying algocore.py.

The GameState.map object can be manually manipulated to create hypothetical 
board states. Though, we recommended making a copy of the map to preserve 
the actual current map state.
"""

class WallStrategy(gamelib.AlgoCore):
    def __init__(self):
        super().__init__()
        random.seed()

        # self.destructor_locations = {(1, 12), (26, 12), (6, 12), (21, 12), (11, 12), (16, 12)}
        # self.primary_filter_locations = {(1,13), (26,13), (6,13), (21,13), (11,13), (16,13)}

        self.destructor_locations = [(0,13), (2, 12), (3, 12), (10, 12), (17, 12), (24, 12), (25, 12), (27,13)]
        self.primary_filter_locations = [(2, 13), (3, 13), (10, 13), (17, 13), (24, 13), (25, 13)]
        self.wall_y = 12
        self.opening = 12

        self.left_deployment_locations = [[x,13-x] for x in range(14)]
        self.right_deployment_locations = [[27-x,y] for x,y in self.left_deployment_locations]
        self.center_deployment_locations = [[x, 13] for x in range(28)]

    def isHealthy(self, game_state):
        totalSpaces = 0
        count = 0
        for i in range(0, 27):
            if game_state.game_map.in_arena_bounds((i, self.wall_y)):
                totalSpaces += 1
            if game_state.contains_stationary_unit((i, self.wall_y)):
                count += 1

        if (count / totalSpaces) <= 0.2:
            return False
        else:
            return True


    def on_game_start(self, config):
        """
        Read in config and perform any initial setup here
        """
        gamelib.debug_write('Configuring your custom algo strategy...')
        self.config = config
        global FILTER, ENCRYPTOR, DESTRUCTOR, PING, EMP, SCRAMBLER
        FILTER = config["unitInformation"][0]["shorthand"]
        ENCRYPTOR = config["unitInformation"][1]["shorthand"]
        DESTRUCTOR = config["unitInformation"][2]["shorthand"]
        PING = config["unitInformation"][3]["shorthand"]
        EMP = config["unitInformation"][4]["shorthand"]
        SCRAMBLER = config["unitInformation"][5]["shorthand"]


    def on_turn(self, turn_state):
        """
        This function is called every turn with the game state wrapper as
        an argument. The wrapper stores the state of the arena and has methods
        for querying its state, allocating your current resources as planned
        unit deployments, and transmitting your intended deployments to the
        game engine.
        """
        game_state = gamelib.AdvancedGameState(self.config, turn_state)
        gamelib.debug_write('Performing turn {} of your custom algo strategy'.format(game_state.turn_number))
        game_state.suppress_warnings(True)  # Uncomment this line to suppress warnings.

        self.strategy(game_state)

        game_state.submit_turn()

    def strategy(self, game_state):
        """
        Build the wall.
        """
        self.build_great_wall(game_state)

        self.placeEncryptors(game_state)

        """
        Reinforce the defenses.
        """
        self.reinforce_defences(game_state)

        """
        Attack!
        """
        self.deploy_attackers(game_state)
        # self.deploy_attackers_from_middle(game_state)

    def build_great_wall(self, game_state):
        for location in self.destructor_locations:
            if game_state.can_spawn(DESTRUCTOR, location):
                game_state.attempt_spawn(DESTRUCTOR, location)

        for location in self.primary_filter_locations:
            if game_state.can_spawn(FILTER, location):
                game_state.attempt_spawn(FILTER, location)

        gaps = []
        for i in range(1,27):
            if i != self.opening and not game_state.contains_stationary_unit((i, self.wall_y)):
                gaps.append((i, self.wall_y))
        random.shuffle(gaps)
        for location in gaps:
            if game_state.can_spawn(FILTER, location):
                game_state.attempt_spawn(FILTER, location)

    def reinforce_destructor(self, game_state, location):
        """
        Attempt to support a destructor with another firewall unit
        """
        # new_locations = [(1,0), (-1,0), (0,-1), (0,1)] if location[0] <= 13 else [(-1,0), (1,0), (0,-1), (0,1)]
        destructor_locations = [(-1,0), (0,-1)] if location[0] <= 13 else [(1,0), (0,-1)]
        filter_locations = [(-1,1), (1,1)]

        FIREWALL = DESTRUCTOR if game_state.get_resource(game_state.CORES) >= game_state.type_cost(DESTRUCTOR) else FILTER
        new_locations = destructor_locations if FIREWALL == DESTRUCTOR else filter_locations

        for offset in new_locations:
            new_firewall_location = (location[0] + offset[0], location[1] + offset[1])
            if game_state.can_spawn(FIREWALL, new_firewall_location):
                game_state.attempt_spawn(FIREWALL, new_firewall_location)
                return FIREWALL, new_firewall_location

        return None, None

    def reinforce_defences(self, game_state):
        """
        Exit if reinforcement is not possible
        """
        if game_state.get_resource(game_state.CORES) < 1:
            return

        """
        Reinforce damaged destructors in the WALL
        """
        # new_destructors = set()
        random.shuffle(self.destructor_locations)
        for location in self.destructor_locations:
            destructor = game_state.game_map[location][0]
            if destructor.stability < destructor.max_stability:
                firewall, new_location = self.reinforce_destructor(game_state, location)
                # if firewall is DESTRUCTOR:
                    # new_destructors.add(new_location)
            if game_state.get_resource(game_state.CORES) < 1:
                break
        # self.destructor_locations |= new_destructors

    def get_damage_on_path(self, game_state, path):
        total_damage = 0
        for location in path:
            attackers = game_state.get_attackers(location, 0)
            for attacker in attackers:
                total_damage += attacker.damage
        return total_damage

    def deploy_attackers(self, game_state):
        """
        Wait until we accumulate enough bits
        """
        if game_state.get_resource(game_state.BITS) < 10:
            return

        min_damage_deploy_location = self.left_deployment_locations[0]
        min_damage = sys.maxsize

        for location in self.left_deployment_locations:
            if game_state.can_spawn(PING, location):
                path = game_state.find_path_to_edge(location, game_state.game_map.TOP_RIGHT)
                damage_on_path = self.get_damage_on_path(game_state, path)
                if damage_on_path < min_damage:
                    min_damage = damage_on_path
                    min_damage_deploy_location = location

        for location in self.right_deployment_locations:
            if game_state.can_spawn(PING, location):
                path = game_state.find_path_to_edge(location, game_state.game_map.TOP_LEFT)
                if len(path) > 5:
                    damage_on_path = self.get_damage_on_path(game_state, path)
                    if damage_on_path < min_damage:
                        min_damage = damage_on_path
                        min_damage_deploy_location = location

        bits = int(game_state.get_resource(game_state.BITS))
        ping_duplication = bits
        emp_duplication = bits//3
        # debug_write("-----------")
        # debug_write(min_damage)
        # debug_write(ping_duplication)
        # debug_write("-----------")
        if min_damage > (15*ping_duplication - 50):
            # not worth sending pings (will get destroyed)
            # send EMPs to hopefully clear
            if game_state.can_spawn(EMP, (24,10), emp_duplication):
                game_state.attempt_spawn(EMP, (24,10), emp_duplication)
            return
        else:
            if game_state.can_spawn(PING, min_damage_deploy_location, ping_duplication):
                game_state.attempt_spawn(PING, min_damage_deploy_location, ping_duplication)


        """
        Send Scrambler from far end, so that it can destroy enemy units
        that have passed the WALL
        """
        if game_state.can_spawn(SCRAMBLER, min_damage_deploy_location):
            game_state.attempt_spawn(SCRAMBLER, min_damage_deploy_location)

    def deploy_attackers_from_middle(self, game_state):
        if game_state.get_resource(game_state.BITS) < 10:
            return

        min_damage_deploy_location = self.center_deployment_locations[0]
        left_start = True
        min_damage = sys.maxsize
        path = []

        for location in self.center_deployment_locations:
            debug_write("-----------")
            debug_write("(%d, %d)" % (location[0], location[1]))
            if game_state.can_place(PING, location):
                debug_write("can_place")
                path = game_state.find_path_to_edge(location, game_state.game_map.TOP_RIGHT)
                damage_on_path = self.get_damage_on_path(game_state, path)
                if damage_on_path < min_damage:
                    debug_write("new_damage")
                    debug_write(damage_on_path)
                    min_damage = damage_on_path
                    min_damage_deploy_location = location
            debug_write("-----------")

        for location in self.right_deployment_locations:
            debug_write("-----------")
            debug_write("(%d, %d)" % (location[0], location[1]))
            if game_state.can_place(PING, location):
                debug_write("can_place")
                path = game_state.find_path_to_edge(location, game_state.game_map.TOP_LEFT)
                if len(path) > 5:
                    damage_on_path = self.get_damage_on_path(game_state, path)
                    if damage_on_path < min_damage:
                        debug_write("new_damage")
                        debug_write(damage_on_path)
                        min_damage = damage_on_path
                        min_damage_deploy_location = location
                        left_start = False
            debug_write("-----------")

        if left_start:
            for location in self.left_deployment_locations:
                if game_state.can_spawn(PING, location):
                    path = game_state.find_path_to_point(location, min_damage_deploy_location) + path
                    min_damage = self.get_damage_on_path(game_state, path)
                    break
        else:
            for location in self.right_deployment_locations:
                if game_state.can_spawn(PING, location):
                    path = game_state.find_path_to_point(location, min_damage_deploy_location) + path
                    min_damage = self.get_damage_on_path(game_state, path)
                    break


        bits = int(game_state.get_resource(game_state.BITS))
        opponentWall = utility.detectWall()
        if opponentWall != 0:
            if game_state.can_spawn(EMP, (2, 11), 1):
                game_state.attempt_spawn(EMP, (2, 11), 1)
        bits -= 3

        ping_duplication = bits
        emp_duplication = bits//3
        # debug_write("-----------")
        # debug_write(min_damage)
        # debug_write(ping_duplication)
        # debug_write("-----------")
        if min_damage > (15*ping_duplication - 50):
            # not worth sending pings (will get destroyed)
            # send EMPs to hopefully clear
            if game_state.can_spawn(EMP, (24,10), emp_duplication):
                game_state.attempt_spawn(EMP, (24,10), emp_duplication)
            return
        else:
            if game_state.can_spawn(PING, min_damage_deploy_location, ping_duplication):
                game_state.attempt_spawn(PING, min_damage_deploy_location, ping_duplication)

    def placeEncryptors(self, game_state):
        if not game_state.contains_stationary_unit((6, 7)):
            game_state.attempt_spawn(ENCRYPTOR, [6,7])
        if not game_state.contains_stationary_unit((8, 5)):
            game_state.attempt_spawn(ENCRYPTOR, [8,5])

if __name__ == "__main__":
    algo = AlgoStrategy()
    algo.start()