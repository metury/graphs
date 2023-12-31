# Knihovna pro práci s informatickými grafy v Javě

Celý kód obsahuje dokumentační komentáře pomocí **javadoc**. Mimo podrobnější popis zde také je abstraktní dokumentace (*tento dokument*). Postupně si probereme různé části knihovny a do podrobněji i všechny třídy. Také je třeba zmínit, že knihovna není jen pro grafy, ale také i pro multigrafy, jinak řečeno smyčky a několikanásobné hrany lze přidávat.

# Výjimky

Předem je nutné zmínit, že v knihovně se vyskytují dvě dodefinované výjimky: `NonexistingVertex` a `NonexistingEdge` a jak už popis napovídá, tak dané výjimky jsou vyhozené, pokud se uživatel snaží použít hranu, respektive vrchol, který neexistuje.

Také kvůli algoritmu Dijkstra byla přidána výjimka `NegativeCycle`, která je vyhozena, pokud v grafu na kterém byl puštěn Dijkstrův algoritmus má negativní cyklus.

# Grafy

Hlavní třídou je `Graph`, která implementuje všechny operace, které se pro práci s grafy hodí. Hrany jsou uložené v seznamu `ArrayList<Vertex>` a stejně tak i hrany v seznamu `ArrayList<Edge>`. Jako další věc, tak graf má informaci, zda-li je orientovaný, nebo ne.

Buď je zde základní konstruktor jen s boolovskou proměnnou pro to, jestli je orientovaný nebo ne a pak druhý konstruktor, který dostává řetězec s cestou k souboru a pak se používá přímo import. Hrany a vrcholy se přidávají pomocí separátních metod, kterých je vícero druhů. S tím, že pokaždé se vrací reference na nově vytvořený objekt.

Hrany i vrcholy lze odstraňovat buď přes jejich `id` anebo přímo přes jejich referenci. Ve všech případech se po odstranění vrací reference na odebraný objekt. Obdobnou metodou je kontrakce hrany. Ta odstraní danou hranu a oba incidentní vrcholy a vytvoří se nový vrchol a všechny hrany obsahující aspoň jeden vrchol se přesunou na nově vytvořený vrchol. **Tyto operace mohou měnit identifikační čísla, protože se odstraňují objekty.**

Další metodou je odstranění všech smyček `removeLoops()`. Následují také možné importy a exporty (viz dále).

Graf lze také vytvářet pomocí slepování více grafů dohromady a to pomocí metody `merge()`.

Posledním důležitým prvkem je implementace `Iterable<Vertex>` přes všechny vrcholy grafu.

## Hrany

Každá hrana má jednak svoji hodnotu (`double`)a taky svoje `id`. Mimo metody typu `get` a `set` je zde přepsaný tisk (přesněji metoda `toString()`), která tiskne ve tvaru `[vertexFrom;value;vertexTo]`. Jako další metodu, zde také máme pro kontrolu, zda-li je hrana smyčka `isLoop()`.

## Vrcholy

Každý vrchol má svoji hodnotu (`double`) a taky svoje `id`. Opět je zde spoustu `get` a `set` metod, které nebudu blíže vypisovat. Třída implementuje `Iterable<Edge>` a lze iterovat přes incidentní hrany, které každý vrchol zná. Opět je přepsaná funkce `toString()` ve tvaru `(id;value)` a další metody, které upravují list incidentních hran.

## Export a Import

Celý graf lze exportovat a také i importovat. Daný soubor musí splňovat pár základních vlastností. Na prvním řádku je jen zda-li je graf orientovaný (použitím `true`) nebo neorientovaný (`false`). Na dalších řádcích jsou pak už jen vrcholy a hrany. Doporučený postup je nejdříve definovat vrcholy a pak hrany, protože zde pak bude méně vedlejších efektů. Vrchol je ve formátu `(id;value)`, kde `id` je identifikační číslo vrcholu a `value` je hodnota. Také lze jen napsat `(value)`, kde je `id` bráno automaticky jako nové. Hrana je napsaná pomocí formátu `[idFrom;value;idTo]`, kde `idFrom` je identifikační číslo výstupního vrcholu a `idTo` je id vstupního vrcholu (v případě neorientovaného grafu je to symetrické) také `value` je hodnota hrany. Obdobně jako u vrcholu lze mít jen `[idFrom;idTo]` a alternativně stejně je i `[idFrom;NaN;idTo]`. Obecně lze používat v hranách vrcholy, které ještě neexistují a automaticky se vytvoří nové. Obdobně při přidávání nových vrcholů.

Dále také lze exportovat graf v mermaid diagram přímo do `BufferedWriter` pokud už je otevřen. Následně lze specifikovat, že je soubor v markdownu pro přidání znaků na zzačátku a na konci. Jako posledním parametrem může být (je i verze bez) seznamu `boolean` proměnných, která určuje, jaké hrany mají být čárkované.

# Grafové algoritmy

Knihovna obsahuje i soubor implementující pár grafových algoritmů. Hlavními jsou pravděpodobnostní algoritmus pro *MinCut* problém. Dále jestli graf je souvislý anebo ne. Všechny metody jsou statické.

## Souvislost

Třída obsahuje metodu pro souvislost (`isConnected(Graph G)`) a taky u orientovaných grafů ostrou souvislost (u neorientovaného to vrací stejný výsledek jako u normální souvislosti) a je to v `isStronglyConnected(Graph G)`.

## MinCut

Všechny algoritmy dané grafy kopíruje a nijak je nepřepisuje. Jedním je algoritmus, který používá hrubou sílu. Poté je zde pravděpodobnostní algoritmus, který kontrahuje náhodné hrany a když má jen dva vrcholy, tak vrátí počet multi hran. Tento proces opakuje celkem tolikrát, kolik je vrcholů pro co nejlepší odpověď. Posledním algoritmem je Karger Steinův algoritmus, který využívá kontrakce hran a po překročení limitu počtu vrcholů se využije hrubá síla.

Jako další je zde metoda, která vytvoří dokument ve formátu markdown, který ukazuje, jak algoritmus pro hledání minimálního řezu pomocí kontrakce funguje a také dodává i výsledky ostatních algoritmů.

## Dijkstra

V rámci balíčku zde také je Dijkstrův algoritmus hledající nejkratší cesty z daného vrcholu. V případě negativního cyklu v grafu se samotný algoritmus nezacyklý, nicméně vrátí "špatný výsledek" (protože pak všechny hodnoty můžou být nekonečněkrát malé). V případě vizualizace v tomto případě je vyhozena výjimka `NegativeCycle`.

Primárně metody vrací seznam délek pro každý vrchol relativní vůči identifikačnímu číslu daného vrcholu. Tedy na pozici `id` vrcholu je délka nejkratší cesty mezi kořenem do daného vrcholu. Také lze použít druhou metodu, do které je nutné přidat seznam hran pro každý vrchol, jakožto hrana reprezentující předchozí hraně po které se do vrcholu lze dostat. A tak tedy lze zpětně získat celou cestu.

V případě orientovaného grafu se bere ohled na orientaci hran.

## MST

Dalším algoritmem je implementace nalezení minimální kostry grafu. Opět je zde na výběr buď `findMST(Graph G)`, která vrací seznam hran v kostře a nebo vizualizace pomocí `visualizeMST(Graph G, String filePath)`, která graf a jeho kostru ihned i vykreslí.

Pokud graf není souvislý, tak získáme kostru v rámci jejich komponent souvislosti.

Pokud graf je orientovaný, tak se na to nebere ohled a hledá se minimální kostra neorientovanému grafu.

# Main

Knihovna také obsahuje jeden soubor `Main.java`, který ukazuje, jak dané metody fungují.
