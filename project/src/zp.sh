#!/bin/bash

set -ueo pipefail

printf "Kompilace -> "
javac cz/cuni/mff/java/graphs/*.java
echo "Hotovo"

echo "Spuštění"
java cz.cuni.mff.java.zp.graphs.Main
