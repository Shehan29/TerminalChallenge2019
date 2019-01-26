from WallStrategy import WallStrategy
from FunnelStrategy import FunnelStrategy
import gamelib

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

class AlgoStrategy(gamelib.AlgoCore):
    def __init__(self):
        super().__init__()
        self.wall_strategy = WallStrategy()
        self.funnel_strategy = FunnelStrategy()

        self.algo_strategy = self.wall_strategy
        self.curr_strategy = 'wall'

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

        self.funnel_strategy.on_game_start(config)
        self.wall_strategy.on_game_start(config)


    def on_turn(self, turn_state):
        """
        This function is called every turn with the game state wrapper as
        an argument. The wrapper stores the state of the arena and has methods
        for querying its state, allocating your current resources as planned
        unit deployments, and transmitting your intended deployments to the
        game engine.
        """
        game_state = gamelib.AdvancedGameState(self.config, turn_state)
        if game_state.turn_number % 3 == 0:
            if not self.algo_strategy.isHealthy(game_state):
                self.algo_strategy = self.funnel_strategy if self.curr_strategy == 'wall' else self.wall_strategy

        self.algo_strategy.on_turn(turn_state)

    def strategy(self, game_state):
        self.algo_strategy.strategy(game_state)

if __name__ == "__main__":
    algo = AlgoStrategy()
    algo.start()
