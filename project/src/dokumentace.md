# Knihovna pro práci s informatickými grafy v Javě

Celý kód obsahuje dokumentční komentáře pomocí **javadoc**. Mimo podrobnější popis zde také je abstraktní dokumentace (*tento dokument*). Postupně si probereme různé části knihovny a do podrobněji i všechny třídy. Také je třeba zmínit, že knihovna není jen pro grafy, ale také i pro multigrafy, jinak řečeno smyčky a několikanásobné hrany lze přidávat.

# Vyjímky

Předem je nutné zmínit, že v knihovně se vyskytují dvě dodefinované vyjímky: `NonexistingVertex` a `NonexistingEdge` a jak už popis napovídá, tak dané vyjímky jsou vyhozené, pokud se uživatel snaží použít hranu, repsektive vrchol, který neexistuje.

# Grafy

Hlavní třídou je `Graph`, která implementuje všechny operace, které se pro práci s grafy hodí.

## Hrany

## Vrcholy

## Export a Import

Celý graf lze exportovat a také i importovat. Daný soubor musí splňovat pár základních vlastností. Na prvním řádku je jen zda-li je graf orientovaný (použitím `true`) nebo neorientovaný (`false`). Na dalších řádcích jsou pak už jen vrcholy a hrany. Doporučený postup je nejdříve definovat vrcholy a pak hrany, protože zde pak bude méně vedlejších efektů. Vrchol je ve formátu `(id;value)`, kde `id` je identifikační číslo vrcholu a `value` je hodnota. Také lze jen napsat `(id)`, pokud vrchol nemá mít hodnotu a alternativně lze i pomocí `(id;NaN)`. Hrana je napsaná pomocí  formátu `[idFrom;value;idTo]`, kde `idFrom` je identifikační číslo výstupního vrcholu a `idTo` je id vstupního vrcholu (v případě neorientovaného grafu je to symetrické) také `value` je hodnota hrany. Obdobně jako u vrcholu lze mít jen `[idFrom;idTo]` a alternativně stejně je i `[idFrom;NaN;idTo]`.

# Grafové algoritmy

Knihovna obsahuje i soubor implementující pár grafových algoritmů. Hlavními jsou pravděpodobnostní algoritmus pro *MinCut* problém. Dále jestli graf je spojený anebo ne. Také zde je základní algoritmus pro hledání nejkratší cesty.

## MinCut

Pravděpodobnostní algoritmus na MinCut se nebere graf s jeho ohodnocením.
