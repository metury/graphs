#!/bin/bash

set -ueo pipefail

printf " * Kompilace -> "
javac cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

printf " * Vytvoření dokumentace -> "
mkdir -p docs/
javadoc cz/cuni/mff/java/graphs/*.java -d docs -quiet
echo "Hotovo"

echo "========"
echo "Spuštění"
echo "========"
echo ""
java cz.cuni.mff.java.graphs.Main
echo ""
echo "========"
echo "Konec"
echo "========"

for var in $(ls *.dot); do
	printf " * Dot test '$var'-> "
	dot -Tsvg $var > $var.svg
	echo "Hotovo '$var'.svg"
done
