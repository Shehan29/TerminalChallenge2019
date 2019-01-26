from WallStrategy import WallStrategy
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
        self.algo_strategy = WallStrategy()

    def on_game_start(self, config):
        """ 
        Read in config and perform any initial setup here 
        """
        self.algo_strategy.on_game_start(config)


    def on_turn(self, turn_state):
        """
        This function is called every turn with the game state wrapper as
        an argument. The wrapper stores the state of the arena and has methods
        for querying its state, allocating your current resources as planned
        unit deployments, and transmitting your intended deployments to the
        game engine.
        """
        self.algo_strategy.on_turn(turn_state)

    def strategy(self, game_state):
        self.algo_strategy.strategy(game_state)

if __name__ == "__main__":
    algo = AlgoStrategy()
    algo.start()
