import gamelib
def detectWall(game_state):
    min_enemy_wall_y = 14
    minConsecutiveWall = 5
    expectedDensity = 0.6

    for i in range(5):
        consecutiveCount = 0
        maxConsecutiveCount = 0
        count = 0
        for j in range(5, 23):
            if game_state.contains_stationary_unit((j, min_enemy_wall_y+i)):
                count += 1
                consecutiveCount += 1
            else:
                maxConsecutiveCount = max(consecutiveCount, maxConsecutiveCount)
                consecutiveCount = 0
        gamelib.debug_write("COUNT :")
        gamelib.debug_write(count)
        gamelib.debug_write("MAX CONSEC")
        gamelib.debug_write(maxConsecutiveCount)
        if count > 18*expectedDensity or maxConsecutiveCount > minConsecutiveWall:
            gamelib.debug_write("DEPLOYING EMP")
            return min_enemy_wall_y + i

    # zero indicates no wall found
    return 0

