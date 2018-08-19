# jSelolaskuri
Shakin vahvuuslukulaskenta, Java-ohjelmointikieli (Java Swing eli desktop application)

You can find more documents and comments from C# version https://github.com/isuihko/selolaskuri

27.5.2018 Started refactoring and documenting

31.7. - 1.8.2018 Refactoring continued finally, cleaning and reorganizing code, taking new features from C# version. Data made hidden and used getters and setters. Added the exactly same unit tests that C# version has (modified to make them work).

2.8.2018 Added jComboBox for storing opponent+result input (which can have a lot of number) for later use so it can be chosen again, modified and used in new calculations. Added also executable file dist/JavaSelolaskuri.jar which can be downloaded and executed with command line java -jar JavaSelolaskuri.jar

4.8.2018 Added support for CSV format with 2-5 values. E.g. with two values can give own selo,matches e.g. 1712,2.5 1525 1600 1611 1558

8.8.2018 Small modifications to form, e.g. instruction that Enter calculates in vastustajanSelo field. Version date 8.8.2018.

12.8.2018 Edit menu with cut/copy/paste. Can copy to and paste from clipboard opponents/results field. Version date 12.8.2018.

14.8.2018 Testing modifications, simplifications e.g. using default values if some data was not defined. Now also uses enums for thinking time and match result. No changes to operation so not changed version date.

19.8.2018 Updates to CSV format checking and adds new unit tests mostly related to CSV format.

------

Shakin vahvuusluvun laskenta

30.11.2017 - 19.8.2018 Java, NetBeans IDE 8.2

- 31.7. - 4.8. muutettu ja siivottu koodia merkittävästi. Pohjana käytetty C#-version koodia, joka oli paljon pidemmällä ja sisälsi  enemmän laskentaakin
- Nyt C#- ja Java-versiot ovat ominaisuuksiltaan samat

Lataaminen: Suoraan netistä GitHub.com:sta selaimella tai komentorivillä (git asennettuna) git clone https://github.com/isuihko/jSelolaskuri

Ohjelman suoritus (ainakin Windows):
Lataa jar-tiedosto JavaSelolaskuri/dist-hakemistosta ja suorita se Windowsissa komentorivipromptissa komennolla java -jar JavaSelolaskuri.jar
Suora latauslinkki: https://github.com/isuihko/jSelolaskuri/blob/master/JavaSelolaskuri/dist/JavaSelolaskuri.jar valitse Download.
Java Runtime Environment pitää olla asennettuna, jotta tämä toimisi.

Lasketaan shakinpelaajalle uusi vahvuusluku SELO tai PELO, ks. http://www.shakki.net/cgi-bin/selo
- SELO on Suomen kansallinen shakin vahvuusluku, esim. https://fi.wikipedia.org/wiki/Elo-luku#Suomen_Elo
- PELO on vastaavasti pikashakin vahvuusluku, jota käytetään kun miettimisaika on enintään 10 minuuttia. Eri miettimisajoille on omat kaavansa.

Enemmän tietoa C#/.NET-version README.md:ssä -> https://github.com/isuihko/selolaskuri
