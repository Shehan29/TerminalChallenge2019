import com.c1games.terminal.algo.Config;
import com.c1games.terminal.algo.FrameData;
import com.c1games.terminal.algo.map.SpawnCommand;
import com.c1games.terminal.algo.units.UnitTypeAtlas;
import com.google.gson.Gson;
import org.junit.Test;

public class FrameDeserializeTest {
    @Test
    public void test() {
        String configData = "{\n" +
                "  \"debug\": {\n" +
                "    \"printMapString\": false,\n" +
                "    \"printTStrings\": false,\n" +
                "    \"printActStrings\": false,\n" +
                "    \"printHitStrings\": false,\n" +
                "    \"printPlayerInputStrings\": false,\n" +
                "    \"printBotErrors\": false,\n" +
                "    \"printPlayerGetHitStrings\": false\n" +
                "  },\n" +
                "  \"unitInformation\": [\n" +
                "    {\n" +
                "      \"damage\": 0.0,\n" +
                "      \"cost\": 1.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"display\": \"Filter\",\n" +
                "      \"range\": 3.0,\n" +
                "      \"shorthand\": \"FF\",\n" +
                "      \"stability\": 60.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"damage\": 0.0,\n" +
                "      \"cost\": 4.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"shieldAmount\": 10.0,\n" +
                "      \"display\": \"Encryptor\",\n" +
                "      \"range\": 3.0,\n" +
                "      \"shorthand\": \"EF\",\n" +
                "      \"stability\": 30.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"damage\": 4.0,\n" +
                "      \"cost\": 3.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"display\": \"Destructor\",\n" +
                "      \"range\": 3.0,\n" +
                "      \"shorthand\": \"DF\",\n" +
                "      \"stability\": 75.0\n" +
                "    },\n" +
                "    {\n" +
                "      \"damageI\": 1.0,\n" +
                "      \"damageToPlayer\": 1.0,\n" +
                "      \"cost\": 1.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"damageF\": 1.0,\n" +
                "      \"display\": \"Ping\",\n" +
                "      \"range\": 3.0,\n" +
                "      \"shorthand\": \"PI\",\n" +
                "      \"stability\": 15.0,\n" +
                "      \"speed\": 0.5\n" +
                "    },\n" +
                "    {\n" +
                "      \"damageI\": 3.0,\n" +
                "      \"damageToPlayer\": 1.0,\n" +
                "      \"cost\": 3.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"damageF\": 3.0,\n" +
                "      \"display\": \"EMP\",\n" +
                "      \"range\": 5.0,\n" +
                "      \"shorthand\": \"EI\",\n" +
                "      \"stability\": 5.0,\n" +
                "      \"speed\": 0.25\n" +
                "    },\n" +
                "    {\n" +
                "      \"damageI\": 10.0,\n" +
                "      \"damageToPlayer\": 1.0,\n" +
                "      \"cost\": 1.0,\n" +
                "      \"getHitRadius\": 0.51,\n" +
                "      \"damageF\": 0.0,\n" +
                "      \"display\": \"Scrambler\",\n" +
                "      \"range\": 3.0,\n" +
                "      \"shorthand\": \"SI\",\n" +
                "      \"stability\": 40.0,\n" +
                "      \"speed\": 0.25\n" +
                "    },\n" +
                "    {\n" +
                "      \"display\": \"Remove\",\n" +
                "      \"shorthand\": \"RM\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"timingAndReplay\": {\n" +
                "    \"waitTimeBotMax\": 70000,\n" +
                "    \"waitTimeManual\": 1820000,\n" +
                "    \"waitForever\": false,\n" +
                "    \"waitTimeBotSoft\": 40000,\n" +
                "    \"replaySave\": 0,\n" +
                "    \"storeBotTimes\": true\n" +
                "  },\n" +
                "  \"resources\": {\n" +
                "    \"turnIntervalForBitCapSchedule\": 10,\n" +
                "    \"turnIntervalForBitSchedule\": 10,\n" +
                "    \"bitRampBitCapGrowthRate\": 5.0,\n" +
                "    \"roundStartBitRamp\": 10,\n" +
                "    \"bitGrowthRate\": 1.0,\n" +
                "    \"startingHP\": 30.0,\n" +
                "    \"maxBits\": 999999.0,\n" +
                "    \"bitsPerRound\": 5.0,\n" +
                "    \"coresPerRound\": 4.0,\n" +
                "    \"coresForPlayerDamage\": 1.0,\n" +
                "    \"startingBits\": 5.0,\n" +
                "    \"bitDecayPerRound\": 0.33333,\n" +
                "    \"startingCores\": 25.0\n" +
                "  },\n" +
                "  \"mechanics\": {\n" +
                "    \"basePlayerHealthDamage\": 1.0,\n" +
                "    \"damageGrowthBasedOnY\": 0.0,\n" +
                "    \"bitsCanStackOnDeployment\": true,\n" +
                "    \"destroyOwnUnitRefund\": 0.5,\n" +
                "    \"destroyOwnUnitsEnabled\": true,\n" +
                "    \"stepsRequiredSelfDestruct\": 5,\n" +
                "    \"selfDestructRadius\": 1.5,\n" +
                "    \"shieldDecayPerFrame\": 0.15,\n" +
                "    \"meleeMultiplier\": 0,\n" +
                "    \"destroyOwnUnitDelay\": 1,\n" +
                "    \"rerouteMidRound\": true,\n" +
                "    \"firewallBuildTime\": 0\n" +
                "  }\n" +
                "}\n";
        String frameData = "{\n" +
                "  \"p2Units\": [\n" +
                "    [\n" +
                "      [\n" +
                "        19,\n" +
                "        16,\n" +
                "        60.0,\n" +
                "        \"38\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        16,\n" +
                "        60.0,\n" +
                "        \"40\"\n" +
                "      ],\n" +
                "      [\n" +
                "        20,\n" +
                "        17,\n" +
                "        60.0,\n" +
                "        \"42\"\n" +
                "      ],\n" +
                "      [\n" +
                "        20,\n" +
                "        18,\n" +
                "        60.0,\n" +
                "        \"44\"\n" +
                "      ],\n" +
                "      [\n" +
                "        20,\n" +
                "        19,\n" +
                "        60.0,\n" +
                "        \"46\"\n" +
                "      ],\n" +
                "      [\n" +
                "        19,\n" +
                "        20,\n" +
                "        60.0,\n" +
                "        \"48\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        20,\n" +
                "        60.0,\n" +
                "        \"50\"\n" +
                "      ],\n" +
                "      [\n" +
                "        10,\n" +
                "        16,\n" +
                "        60.0,\n" +
                "        \"52\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        16,\n" +
                "        60.0,\n" +
                "        \"54\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        17,\n" +
                "        60.0,\n" +
                "        \"56\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        18,\n" +
                "        60.0,\n" +
                "        \"58\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        19,\n" +
                "        60.0,\n" +
                "        \"60\"\n" +
                "      ],\n" +
                "      [\n" +
                "        10,\n" +
                "        20,\n" +
                "        60.0,\n" +
                "        \"62\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        20,\n" +
                "        60.0,\n" +
                "        \"64\"\n" +
                "      ],\n" +
                "      [\n" +
                "        8,\n" +
                "        20,\n" +
                "        60.0,\n" +
                "        \"66\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [],\n" +
                "    [\n" +
                "      [\n" +
                "        16,\n" +
                "        20,\n" +
                "        75.0,\n" +
                "        \"68\"\n" +
                "      ],\n" +
                "      [\n" +
                "        14,\n" +
                "        18,\n" +
                "        75.0,\n" +
                "        \"70\"\n" +
                "      ],\n" +
                "      [\n" +
                "        12,\n" +
                "        16,\n" +
                "        75.0,\n" +
                "        \"72\"\n" +
                "      ],\n" +
                "      [\n" +
                "        27,\n" +
                "        14,\n" +
                "        75.0,\n" +
                "        \"76\"\n" +
                "      ],\n" +
                "      [\n" +
                "        0,\n" +
                "        14,\n" +
                "        42.0,\n" +
                "        \"80\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [\n" +
                "      [\n" +
                "        18,\n" +
                "        22,\n" +
                "        15.0,\n" +
                "        \"89\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        22,\n" +
                "        15.0,\n" +
                "        \"90\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        22,\n" +
                "        15.0,\n" +
                "        \"91\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [],\n" +
                "    [\n" +
                "      [\n" +
                "        13,\n" +
                "        22,\n" +
                "        40.0,\n" +
                "        \"92\"\n" +
                "      ],\n" +
                "      [\n" +
                "        6,\n" +
                "        15,\n" +
                "        13.0,\n" +
                "        \"93\"\n" +
                "      ],\n" +
                "      [\n" +
                "        22,\n" +
                "        14,\n" +
                "        40.0,\n" +
                "        \"94\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    []\n" +
                "  ],\n" +
                "  \"turnInfo\": [\n" +
                "    1,\n" +
                "    2,\n" +
                "    19\n" +
                "  ],\n" +
                "  \"p1Stats\": [\n" +
                "    30.0,\n" +
                "    3.0,\n" +
                "    1.5,\n" +
                "    10\n" +
                "  ],\n" +
                "  \"p1Units\": [\n" +
                "    [\n" +
                "      [\n" +
                "        8,\n" +
                "        11,\n" +
                "        60.0,\n" +
                "        \"2\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        11,\n" +
                "        60.0,\n" +
                "        \"4\"\n" +
                "      ],\n" +
                "      [\n" +
                "        7,\n" +
                "        10,\n" +
                "        60.0,\n" +
                "        \"6\"\n" +
                "      ],\n" +
                "      [\n" +
                "        7,\n" +
                "        9,\n" +
                "        60.0,\n" +
                "        \"8\"\n" +
                "      ],\n" +
                "      [\n" +
                "        7,\n" +
                "        8,\n" +
                "        60.0,\n" +
                "        \"10\"\n" +
                "      ],\n" +
                "      [\n" +
                "        8,\n" +
                "        7,\n" +
                "        60.0,\n" +
                "        \"12\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        7,\n" +
                "        60.0,\n" +
                "        \"14\"\n" +
                "      ],\n" +
                "      [\n" +
                "        17,\n" +
                "        11,\n" +
                "        60.0,\n" +
                "        \"16\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        11,\n" +
                "        60.0,\n" +
                "        \"18\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        10,\n" +
                "        60.0,\n" +
                "        \"20\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        9,\n" +
                "        60.0,\n" +
                "        \"22\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        8,\n" +
                "        60.0,\n" +
                "        \"24\"\n" +
                "      ],\n" +
                "      [\n" +
                "        17,\n" +
                "        7,\n" +
                "        60.0,\n" +
                "        \"26\"\n" +
                "      ],\n" +
                "      [\n" +
                "        18,\n" +
                "        7,\n" +
                "        60.0,\n" +
                "        \"28\"\n" +
                "      ],\n" +
                "      [\n" +
                "        19,\n" +
                "        7,\n" +
                "        60.0,\n" +
                "        \"30\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [],\n" +
                "    [\n" +
                "      [\n" +
                "        11,\n" +
                "        7,\n" +
                "        75.0,\n" +
                "        \"32\"\n" +
                "      ],\n" +
                "      [\n" +
                "        13,\n" +
                "        9,\n" +
                "        75.0,\n" +
                "        \"34\"\n" +
                "      ],\n" +
                "      [\n" +
                "        15,\n" +
                "        11,\n" +
                "        75.0,\n" +
                "        \"36\"\n" +
                "      ],\n" +
                "      [\n" +
                "        0,\n" +
                "        13,\n" +
                "        75.0,\n" +
                "        \"74\"\n" +
                "      ],\n" +
                "      [\n" +
                "        27,\n" +
                "        13,\n" +
                "        42.0,\n" +
                "        \"78\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [\n" +
                "      [\n" +
                "        9,\n" +
                "        5,\n" +
                "        15.0,\n" +
                "        \"82\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        5,\n" +
                "        15.0,\n" +
                "        \"83\"\n" +
                "      ],\n" +
                "      [\n" +
                "        9,\n" +
                "        5,\n" +
                "        15.0,\n" +
                "        \"84\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    [],\n" +
                "    [\n" +
                "      [\n" +
                "        10,\n" +
                "        8,\n" +
                "        40.0,\n" +
                "        \"85\"\n" +
                "      ],\n" +
                "      [\n" +
                "        20,\n" +
                "        11,\n" +
                "        40.0,\n" +
                "        \"86\"\n" +
                "      ],\n" +
                "      [\n" +
                "        21,\n" +
                "        12,\n" +
                "        3.0,\n" +
                "        \"87\"\n" +
                "      ]\n" +
                "    ],\n" +
                "    []\n" +
                "  ],\n" +
                "  \"p2Stats\": [\n" +
                "    30.0,\n" +
                "    3.0,\n" +
                "    1.5,\n" +
                "    10\n" +
                "  ],\n" +
                "  \"events\": {\n" +
                "    \"selfDestruct\": [],\n" +
                "    \"breach\": [],\n" +
                "    \"damage\": [\n" +
                "      [\n" +
                "        [\n" +
                "          6,\n" +
                "          15\n" +
                "        ],\n" +
                "        3.0,\n" +
                "        5,\n" +
                "        \"93\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        4,\n" +
                "        \"88\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        3.0,\n" +
                "        5,\n" +
                "        \"87\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          5,\n" +
                "          13\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        4,\n" +
                "        \"81\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        5,\n" +
                "        \"87\",\n" +
                "        1\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"shield\": [],\n" +
                "    \"move\": [\n" +
                "      [\n" +
                "        [\n" +
                "          5,\n" +
                "          12\n" +
                "        ],\n" +
                "        [\n" +
                "          5,\n" +
                "          13\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        4,\n" +
                "        \"81\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          10,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          9,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"82\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          10,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          9,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"83\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          10,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          9,\n" +
                "          5\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"84\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          10,\n" +
                "          7\n" +
                "        ],\n" +
                "        [\n" +
                "          10,\n" +
                "          8\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"85\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          20,\n" +
                "          10\n" +
                "        ],\n" +
                "        [\n" +
                "          20,\n" +
                "          11\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"86\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          21,\n" +
                "          11\n" +
                "        ],\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"87\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          15\n" +
                "        ],\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        4,\n" +
                "        \"88\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          17,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          18,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"89\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          17,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          18,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"90\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          17,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          18,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        3,\n" +
                "        \"91\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          13,\n" +
                "          23\n" +
                "        ],\n" +
                "        [\n" +
                "          13,\n" +
                "          22\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"92\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          6,\n" +
                "          16\n" +
                "        ],\n" +
                "        [\n" +
                "          6,\n" +
                "          15\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"93\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          15\n" +
                "        ],\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        [\n" +
                "          0,\n" +
                "          0\n" +
                "        ],\n" +
                "        5,\n" +
                "        \"94\",\n" +
                "        2\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"spawn\": [],\n" +
                "    \"death\": [\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        4,\n" +
                "        \"88\",\n" +
                "        2,\n" +
                "        false\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          5,\n" +
                "          13\n" +
                "        ],\n" +
                "        4,\n" +
                "        \"81\",\n" +
                "        1,\n" +
                "        false\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"attack\": [\n" +
                "      [\n" +
                "        [\n" +
                "          5,\n" +
                "          13\n" +
                "        ],\n" +
                "        [\n" +
                "          6,\n" +
                "          15\n" +
                "        ],\n" +
                "        3.0,\n" +
                "        4,\n" +
                "        \"81\",\n" +
                "        \"93\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        5,\n" +
                "        \"87\",\n" +
                "        \"88\",\n" +
                "        1\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        3.0,\n" +
                "        4,\n" +
                "        \"88\",\n" +
                "        \"87\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          6,\n" +
                "          15\n" +
                "        ],\n" +
                "        [\n" +
                "          5,\n" +
                "          13\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        5,\n" +
                "        \"93\",\n" +
                "        \"81\",\n" +
                "        2\n" +
                "      ],\n" +
                "      [\n" +
                "        [\n" +
                "          22,\n" +
                "          14\n" +
                "        ],\n" +
                "        [\n" +
                "          21,\n" +
                "          12\n" +
                "        ],\n" +
                "        10.0,\n" +
                "        5,\n" +
                "        \"94\",\n" +
                "        \"87\",\n" +
                "        2\n" +
                "      ]\n" +
                "    ],\n" +
                "    \"melee\": []\n" +
                "  }\n" +
                "}";
        Config config = Config.GSON.fromJson(configData, Config.class);
        UnitTypeAtlas atlas = new UnitTypeAtlas(config);
        Gson gson = FrameData.gson(atlas);
        FrameData data = gson.fromJson(frameData, FrameData.class);
    }
}
