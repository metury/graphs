#!/bin/bash

set -ueo pipefail

printf " * Odstranění .class souborů -> "
rm -f cz/cuni/mff/java/graphs/*.class
echo "Hotovo"

printf " * Generace jar suboru -> "
jar cf ../graphs.jar cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

printf " * Kompilace -> "
javac cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

printf " * Vytvoření dokumentace -> "
mkdir -p docs/
javadoc cz/cuni/mff/java/graphs/*.java -d docs -quiet
echo "Hotovo"

printf " * Generace pro testy -> "
mkdir -p testing/
mkdir -p testing/showcase/
mkdir -p testing/export/
mkdir -p testing/import/
mkdir -p testing/graphs/
echo "true
[0;1][1;2][2;3][3;4][2;4][2;0][3;1][0;4][5,6][6,4][5,4]" > testing/import/import
echo "false
[0;1][1;2][2;3][3;4][4;0][0;5][1;6][2;7][3;8][4;9][5;7][5;8][6;8][6;9][7;9]" > testing/import/petersen
echo "Hotovo"


printf " * Spuštění -> "
java cz.cuni.mff.java.graphs.Main > ./testing/OUTPUT
echo "Konec"
