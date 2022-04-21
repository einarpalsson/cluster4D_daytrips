# cluster4D_daytrips

### How to generate daytrips.db

Make sure to be in daytrips folder and that you have the sqlite-jdbc-3.36.0.3.jar there.

`javac Init.java`

`java -cp .:sqlite-jdbc-3.36.0.3.jar Init.java`

### How to run Init

From the root folder:
MacOs:
`javac -cp .:sqlite-jdbc-3.36.0.3.jar:junit-4.13.2.jar:javax.mail.jar:activation.jar daytrips/*.java`
`java -cp .:sqlite-jdbc-3.36.0.3.jar:junit-4.13.2.jar:javax.mail.jar:activation.jar daytrips/Init`

Windows:
Hægt að gera ` -encoding utf-8` á eftir þessum skipunum til að fá UTF-8 kóðun.

Einnig hægt að gera:
`chcp 65001` og keyra svo þessar skipanir óbreyttum
`javac -cp .;sqlite-jdbc-3.36.0.3.jar;junit-4.13.2.jar;javax.mail.jar;activation.jar daytrips/*.java`
`java -cp .;sqlite-jdbc-3.36.0.3.jar;junit-4.13.2.jar;javax.mail.jar;activation.jar daytrips/Init`
