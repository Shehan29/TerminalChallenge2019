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

def isWallHealthy(game_state, wallRow):
    totalSpaces = 0
    count = 0
    for i in range (0, 27):
        if game_state.game_map.in_arena_bounds((i, wallRow)):
            totalSpaces += 1
            if game_state.contains_stationary_unit(i, wallRow):
                count += 1

    if (count/totalSpaces) <= 0.2:
        return False
    else:
        return True
