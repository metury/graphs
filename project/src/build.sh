#!/bin/bash

set -ueo pipefail

printf " * Kompilace -> "
javac cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

echo " * Vytvoření dokumentace"
javadoc cz/cuni/mff/java/graphs/*.java -d docs
echo "    Hotovo"

echo " * Spuštění"
java cz.cuni.mff.java.graphs.Main
