#!/bin/bash

echo "				                                              "
echo "				          00                      00          "
echo "				         0000                    0000         "
echo "				        00  00                  00  00        "
echo "				         0000                    0000         "
echo "				          00                      00          "
echo "				          ll                      ll          "
echo "				    =========================================="
echo "				    =========================================="
echo "				    =========================================="
echo "				          ll                      ll          "
echo "				          ll                      ll          "
echo "				          ll                      ll          "
echo "				    =========================================="
echo "				    =========================================="
echo "				    =========================================="
echo "				          ll                      ll          "
echo "				          ll                      ll          "
echo "				         /  \                    /  \         "
echo "				        ------                  ------        "


echo "-----------------------------------------------------------------------------------------------------------------------"
echo "░▒▓███████▓▒░  ░▒▓██████▓▒░ ░▒▓███████▓▒░ ░▒▓███████▓▒░ ░▒▓█▓▒░ ░▒▓██████▓▒░  ░▒▓██████▓▒░ ░▒▓███████▓▒░ ░▒▓████████▓▒░"
echo "░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       "
echo "░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       "
echo "░▒▓███████▓▒░ ░▒▓████████▓▒░░▒▓███████▓▒░ ░▒▓███████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░       ░▒▓████████▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓██████▓▒░  "
echo "░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       "
echo "░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░       "
echo "░▒▓███████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░░▒▓█▓▒░ ░▒▓██████▓▒░ ░▒▓█▓▒░░▒▓█▓▒░░▒▓███████▓▒░ ░▒▓████████▓▒░"
echo "-----------------------------------------------------------------------------------------------------------------------"

echo "Starting the game Barricade Game"
echo "What would you like to call the game?"
read name

javac ./Players/*.java
javac ./Board/*.java



java Board/Board $name


