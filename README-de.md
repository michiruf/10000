# Einführung
Ziel ist es einen Bot zu programmieren, der die anderen Bots in diesem Spiel schlägt.
Die [offiziellen Regeln des Spiels](https://de.wikipedia.org/wiki/Zehntausend_(Spiel)) haben wir in vereinfachter
Form übernommmen (bspw. keine Bestätigungen).


# Bot einreichen
1. Repo forken
2. Eigenen Code hinzufügen (nur in Modul "game-bots" im Package "com.github.tenthousand.bots.EIGENER_NAME" und als
   Klasse com.github.tenthousand.EigenerNameBot)
3. Pull Request erstellen


# Spielregeln
Gespielt wird mit sechs 6-seitigen Würfeln.
Es wird Reihum gewürfelt bis der Zug eines Spielers vorbei ist.
Ein Spieler hat gewonnen, wenn er 10000 Puntke erreicht hat.

## <a name="turn">Ablauf eines Zugs
1. Kann der spieler [Punkte übernehmen](#adopt)?
2. Der Spieler [würfelt](#roll).
   Sind alle Punkte mit Werten belegt, kommen die [Würfel zuvor wieder rein](#alle-wieder-nei)
3. Je nachdem was gewürfelt wurde:
    * Hat der Spieler keinen neuen [Wert](#dice-values) gewürfelt
        1. Hat der Spieler [Punkte übernommen](#adopt), verliert er diese
        2. Weiter bei 4.
    * Hat der Spieler etwas neues mit [Wert](#dice-values) gewürfelt
        1. Der Spieler entscheidet welche Werte (mindestens 1 Würfel) beiseite gelegt werden
        2. Der Spieler kann aufhören: [Punkte notieren](#get-points) & weiter bei 4.
        3. Der Spieler macht weiter: Weiter bei 2
4. Der nächste Spieler ist am Zug

Siehe auch die [kompletten Beispiele](#samples).

## <a name="dice-values">Werte der Würfel
Die Punkte eines Wurfs berechnen sich durch:

* Eine 1 zählt 100 Punkte.
* Eine 5 zählt 50 Punkte.
* 2, 3, 4, 6 sind keine Punkte Wert.

### Ausnahme: Pasch
* 3 Würfel mit gleicher Augenzahl entsprechen der Augenzahl*100  
  2 2 2 entspricht 200 Punkten, 3 3 3 entspricht 300 Punkten etc.  
  Drei Einsen entsprechen nicht 100 sondern 1000 Punkten

* 4 Würfel mit gleicher Augenzahl entsprechen dem doppelten Wert eines Paschs mit 3 Würfeln mit dieser Augenzahl  
  2 2 2 2 entspricht 400 Punkten  
  3 3 3 3 entspricht 600 Punkten  
  1 1 1 1 entspricht 2000 Punkten

* 5 Würfel mit gleicher Augenzahl entsprechen dem doppelten Wert eines Paschs mit 4 Würfeln etc. etc.  
  3 3 3 3 3 entspricht 1200 Punkten (2 * 2 * 3 * 100)  
  6 6 6 6 6 6 entspricht 4800 Punkten (2 * 2 * 2 * 6 * 100) 

Werden 3 oder mehr Einsen bzw. Fünfen als Pasch gewertet sind sie nicht zusätzlich ihre 100 bzw. 50 Punkte Wert.

> **Ein Pasch wird nur als Pasch gewertet wenn alle Würfel in einem Wurf gewürfelt wurden**  
> Der Spieler würfelt 1 1 2 2 3 3 und wertet die beiden Einsen.  
> Er würfelt mit den 4 verbleibenden Würfeln erneut und würfelt 1 2 3 4  
> Er wertet die Eins und hat nun 200+100=300 Punkte anstatt eines Paschs zu 1000 Punkten.

## <a name="adopt">Punkte übernehmen
Hat der vorausgegangene Spieler positive [Punkte notiert](#get-points), so darf der Spieler, welcher nun an der Reihe
ist die Punkte und die Würfel übernehmen. Bedingung um Punkte zu übernehmen ist, dass der übernehmende Spieler
mindestens so viele Punkte hat wie er übernehmen möchte.  
Wurde übernommen startet der Spieler die Runde mit der verbleibenden Anzahl der Würfel (also die Anzahl der Würfel,
die nicht in die Wertung mit einflossen) bei seinem ersten Wurf. Der Spieler kann nun seinen [Zug wie gewohnt](#turn)
fortsetzen.  
Hört der Spieler rechtzeitig auf, so bekommt er seine erspielten Punkte, sowie die Punkte des Spielers zuvor (der
Spieler zuvor verliert dabei nichts!) notiert.  
Hat der Spieler in einem Wurf keinen Wert erzielt, bekommt er die übernommenen Punkte des vorherigen
Spielers abgezogen.

## <a name="roll">Würfeln
Sofern nicht übernommen wurde, hat ein Spieler am Anfang eines Zuges 6 Würfel zur Verfügung. Bei jedem Wurf muss ein 
neuer [Wert](#dice-values) erzielt werden, ansonsten ist die Runde des Spielers vorbei.    
Wird mindestens ein neuer Wert erzielt, muss der Spieler entscheiden welche wertigen Würfel er beiseite legen
möchte und damit gleichzeitig auch wertet. Der Spieler kann nun entweder erneut würfeln, oder die
[Punkte notieren](#get-points).  
Sollten alle Würfel einen Wert haben, siehe [Alle-wieder-nei](#alle-wieder-nei)

> **Mehr als 1 mal würfeln**  
> Der Spieler würfelt 1 1 2 3 5 6.  
> Er wertet die beiden Einsen (zu je 100 Punkten) und die 5 (zu 50 Punkten) für insgesamt 250 Punkte.  
> Mit den anderen 3 Würfeln (2 3 6) wird erneut gewürfelt.  
> Der neue Wurf mit den 3 Würfeln ergibt 1 3 4.  
> Der Spieler wertet die Eins für 100 Punkte welche zu den vorherigen Punkten addiert werden.  
> Der Spieler hat nun also 250 + 100 = 350 Punkte.  
> Mit den verbleibenden 2 Würfeln (3 4) wird erneut gewürfelt etc...

> **Ein Spieler muss nicht alle Würfel werten lassen, jedoch mindestens einen Würfel pro Zug**  
> Der Spieler würfelt 1 1 2 3 5 6.  
> Anstatt die Würfel 1 1 5 zu werten entscheidet er sich nur eine Eins zu werten und mit den 5 anderen Würfeln erneut zu würfeln.

> **Macht ein Spieler mit einem Wurf keinen einzigen Punkt (z.B. 2 2 3 4 6 6) ist sein Zug beendet und er verliert alle Punkte die er in diesem Zug verdient hat**  
> Der Spieler würfelt 1 2 3 3 4 4.  
> Er wertet die Eins zu 100 Punkten und würfelt mit den verbleibenden 5 Würfeln erneut.  
> Er würfelt 2 2 3 4 4 und kann somit keinen Würfel werten.  
> Er verliert die 100 Punkte aus dem ersten Wurf und sein Zug ist beendet.

## <a name="alle-wieder-nei">Alle Würfel wieder nei
Hat der Spieler mit einem Wurf alle (verbliebenen) Würfel mit Wertigkeiten belegt, so kann der Spieler diese Werten
und mit allen Würfeln erneut würfeln. ALLE WIEDER NEI!

> Der Spieler würfelt 1 1 1 1 5 6  
> Er wertet die 4 Einsen zu 2000 Punkten und die Fünf zu 50 Punkten.  
> Mit dem verbleibenden Würfel (6) würfelt er erneut.  
> Er würfelt eine 5 und hat nun insgesamt 2000+50+50 = 2100 Punkte  
> Er würfelt nun alle 6 Würfel erneut und würfelt 1 1 3 4 4 6  
> Er wertet die beiden Einsen zu 200 Punkten und hat nun insgesamt 2100+200 = 2300 Punkte.  
> Mit den verbleibenden 4 Würfeln würfelt er erneut etc....

## <a name="get-points">Punkte notieren
Hat der Spieler bisher noch keine Punkte notiert, so kann er lediglich notieren lassen, wenn er mehr als 1000 Punkte
erreicht hat.  
Hat der Spieler bereits Punkte notiert gehabt, so kann er ab 250 Punkten notieren.  
Hierbei zählt nicht der Punktestand, sondern nur der Fakt ob schon Punkte notiert wurden. Fällt der Spieler durch
Übernehmen unterhalb die 1000er-Grenze, so darf er weiterhin 250 Punkte notieren lassen.

## <a name="samples">Beispiele

> **Vollständiges Beispiel 1**  
> Der Spieler würfelt 1 1 2 3 5 6  
> Er wertet beide Einsen (200 Punkte) und würfelt erneut.  
> Er würfelt 4 4 4 4 und erhält zusätzlich 800 Punkte.  
> Er hat nun insgesamt 200+800 Punkte und darf mit allen Würfeln erneut würfeln.  
> Er entscheidet sich zu würfeln und würfelt 1 2 3 5 5 5.  
> Er wertet die Einsen und die 3 Fünfen zu 100+500=600 Punkten.  
> Er hat nun insgesamt 1000+600=1600 Punkte.  
> Er entscheidet sich erneut zu würfeln und würfelt 6 6.  
> Da er keine Würfel werten kann verliert alle Punkte die er in diesem Zug verdient hat und sein Zug ist beendet.

> **Vollständiges Beispiel 2**  
> Der Spieler würfelt 1 2 6 6 6 6 und wertet 100+1200=1300 Punkte.  
> Anstatt mit einem Würfel erneut zu würfeln entschließt er sich aufzuhören.  
> Im werden 1300 Punkte angerechnet und sein Zug ist beendet
