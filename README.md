# jSelolaskuri
Shakin vahvuuslukulaskenta, Java-versio

27.5.2018 Started refactoring and documenting

Refactoring. Still much to do to catch C#/.NET version of this same program.
So far:  New class Vakiot.java for all constants. Naming of classes to better follow Java naming conventions (not ready yet). Menu. Verify closing of the window (are you sure). Some fixes to calculation which affected to rounding. New error statuses when checking the input and so can have better error messages.

TODO: New classes. More fixes to checking of input and add more calculation.

------

Shakin vahvuusluvun laskenta

30.11.2017 Java, NetBeans IDE 8.2

Java-kielisestä versiosta:
- Lähdekoodit ovat vielä ns. työn alla. Laskentaan on tulossa pieniä muutoksia sekä lisätään vastustajan SELO-kenttään lista.
- C#/.NET -versio on vähän pidemmällä kuin tämä.

Lasketaan shakinpelaajalle uusi vahvuusluku SELO tai PELO, ks. http://www.shakki.net/cgi-bin/selo
- SELO on Suomen kansallinen shakin vahvuusluku, esim. https://fi.wikipedia.org/wiki/Elo-luku#Suomen_Elo
- PELO on vastaavasti pikashakin vahvuusluku, jota käytetään kun miettimisaika on alle 15 minuuttia. Eri miettimisajoille on omat kaavansa.

Enemmän tietoa C#/.NET-version README.md:ssä -> github.com/isuihko/selolaskuri 
