#!/bin/bash

set -ueo pipefail

printf " * Odstranění .class souborů -> "
rm -f cz/cuni/mff/java/graphs/*.class
echo "Hotovo"

printf " * Kompilace -> "
javac cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

printf " * Vytvoření dokumentace -> "
mkdir -p docs/
javadoc cz/cuni/mff/java/graphs/*.java -d docs -quiet
echo "Hotovo"

printf " * Spuštění -> "
java cz.cuni.mff.java.graphs.Main > ./testing/OUTPUT
echo "Konec"
