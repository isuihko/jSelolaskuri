# jSelolaskuri
Shakin vahvuuslukulaskenta, Java-ohjelmointikieli

27.5.2018 Started refactoring and documenting

31.7. - 1.8.2018 Refactoring continued finally, cleaning and reorganizing code, taking new features from C# version. Data made hidden and used getters and setters. Added the exactly same unit tests that C# version has (modified to make them work).

2.8.2018 Added jComboBox for storing opponent+result input (which can have a lot of number) for later use so it can be chosen again, modified and used in new calculations.

------

Shakin vahvuusluvun laskenta

30.11.2017 - 2.8.2018 Java, NetBeans IDE 8.2

- 31.7. - 2.8. muutettu ja siivottu koodia merkittävästi. Pohjana käytetty C#-version koodia, joka oli paljon pidemmällä ja sisälsi  enemmän laskentaakin
- Nyt C#- ja Java-versiot ovat ominaisuuksiltaan samat.

Lasketaan shakinpelaajalle uusi vahvuusluku SELO tai PELO, ks. http://www.shakki.net/cgi-bin/selo
- SELO on Suomen kansallinen shakin vahvuusluku, esim. https://fi.wikipedia.org/wiki/Elo-luku#Suomen_Elo
- PELO on vastaavasti pikashakin vahvuusluku, jota käytetään kun miettimisaika on alle 15 minuuttia. Eri miettimisajoille on omat kaavansa.

Enemmän tietoa C#/.NET-version README.md:ssä -> https://github.com/isuihko/selolaskuri
