# Zápočtový program do Javy v zimním semestru

## Tomáš Turek

## Cvičící: Jakub Galgonek

Tento zápočtový program bude knihovna pro informatické grafy napsané v javě.

Část dohodnutá z emailu:

> Pokud tam budou metody pro import a export, mohlo by to být myslím jako téma dostatečné. Jen by bylo dobré aby:
> 1) to umožňovalo export i v nějakém jazyce pro kreslení grafů (například v DOT Language),
> 2) při exportu to mohlo zároveň nějak pěkně prezentovat i výsledky implementovaného algoritmu/implementovaných algoritmů
> 3) a ke knihovně byla k dispozici i jednoduchá aplikace, která bude demonstrovat její funkčnost.

## Dokumantace

V daném projektu je hlavní dokumentace pomocí javadoc. Mimo to je zde i abstraktnější a kratší dokumentace ručně sepsaná v [dokumentace](project/src/dokumentace.md).

Poté zde také jsou ukázky algoritmů:

- Minimální řez na jednom [grafu](project/src/testing/import/import) a to je pak souboru [markdown](project/src/testing/showcase/minCut.md).
- Další je minimální řez [Petersenova grafu](project/src/testing/import/petersen) a pak [markdown](project/src/testing/showcase/minCutPetersen.md) *(popřípadě [Petersen graph](https://en.wikipedia.org/wiki/Petersen_graph))*.
- Dalšími vygenerovanými soubory jsou pro Dujkstrův algoritmus na neorientovaném [grafu](project/src/testing/showcase/dijkstra.md) a taky na orientovaném [graf](project/src/testing/showcase/didijkstra.md).
- Také je zde ukázka algoritmu minimální kostry souvislém [grafu](project/src/testing/showcase/mst.md) a taky na nesouvislém [graf](project/src/testing/showcase/sepmst.md).
- Poslední je tady teké výsledek [testu](project/src/testing/hardTest.md).
- Také je zde uložený [výstup](project/src/testing/OUTPUT).

## Balíček

- Také je zde předem připravený `.jar` [balíček](project/graphs.jar).
