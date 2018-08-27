### jSelolaskuri
Shakin vahvuuslukulaskenta

Java Swing desktop application, NetBeans IDE 8.2.

Lasketaan shakinpelaajalle uusi vahvuusluku SELO tai PELO, ks. http://www.shakki.net/cgi-bin/selo
- SELO on Suomen kansallinen shakin vahvuusluku, esim. https://fi.wikipedia.org/wiki/Elo-luku#Suomen_Elo
- PELO on vastaavasti pikashakin vahvuusluku, jota käytetään kun miettimisaika on enintään 10 minuuttia. Eri miettimisajoille on omat laskentakaavansa.

Enemmän ohjeita ohjelman C#-versiossa: https://github.com/isuihko/selolaskuri

------

### Lataaminen ja käyttäminen

**Ohjelman asennus (vain Windows):**
Lataa jar-tiedosto JavaSelolaskuri/dist-hakemistosta ja suorita se Windowsissa komentorivipromptissa komennolla **java -jar JavaSelolaskuri.jar**
Suora latauslinkki: https://github.com/isuihko/jSelolaskuri/blob/master/JavaSelolaskuri/dist/JavaSelolaskuri.jar josta valitse Download.
*Java Runtime Environment* pitää olla asennettuna, jotta ohjelma toimisi (Java SE 8 tai uusi Java SE 10): http://www.oracle.com/technetwork/java/javase/downloads/index.html

Voit ladata lähdekoodit suoraan netistä GitHub.com:sta selaimella tai komentorivillä (git asennettuna) *git clone https://github.com/isuihko/jSelolaskuri* ja kääntää ohjelman itsekin.

------

You can find more documents about usage and comments from C# version https://github.com/isuihko/selolaskuri. Java and C# versions have same functionality.

-31.7. - 1.8.2018 Refactoring continued: cleaning and reorganizing code, taking new features from C# version. Added unit tests from C# version.
-2.8.2018 Added jComboBox for storing opponent+result input. Added executable dist/JavaSelolaskuri.jar which can be downloaded and then executed (only in Windows) with command line java -jar JavaSelolaskuri.jar
-4.8.2018 Added support for CSV format with 2-5 values. E.g. with two values can give own selo,matches 1712,2.5 1525 1600 1611 1558 (and thinking time taken from form selection)
-12.8.2018 Edit menu. Can copy to and paste from clipboard opponents/results (Vastustajat field). Version date 12.8.2018.
-19.-20.8.2018 Updates to CSV format checking and adds new unit tests mostly related to CSV format, usage of half character "1/2" and more unit tests.
