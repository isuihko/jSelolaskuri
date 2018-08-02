/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Ismo
 */

//
// Calls the routines in Selopelaaja to check the input fields and calculate results
// The business logic of Selolaskuri
//
// Originally the routines of this class were in SelolaskuriForm.cs
// Created a separate module so that routines can be called from the unit testing too
//
// Public:
//      HaeViimeksiLasketutTulokset  - get the latest calculated selo and game count
//      TarkistaSyote                - check the input data like SELO and number of games, opponents and results
//      SuoritaLaskenta              - calculate the results
//
// 31.7.2018 Ismo Suihko
//

public class SelolaskuriOperations {
    Selopelaaja selopelaaja;   // store and return the results, calculations
    
    // Initialize calculation, clear selopelaaja etc
    public SelolaskuriOperations()
    {
        selopelaaja = new Selopelaaja();
    }
    
    // Palautetaan laskennasta saadut arvot, selo ja pelimäärä
    public int HaeViimeksiLaskettuSelo()
    {
        int i = selopelaaja.getUusiSelo();
        if (i == 0)
            i = Vakiot.UUDEN_PELAAJAN_ALKUSELO;
        return i;
    }

    public int HaeViimeksiLaskettuPelimaara()
    {
        return selopelaaja.getUusiPelimaara();
    }
        
    // TarkistaSyote
    //
    // Kutsuttu: 
    //      -SelolaskuriForm.cs
    //      -Selolaskuri.Tests/UnitTest1.cs
    //
    // Tarkistaa
    //      -miettimisaika-valintanapit (ei voi olla virhettä)
    //      -oma SELO eli nykyinen vahvuusluku (onko kelvollinen numero)
    //      -oma pelimäärä (kelvollinen numero tai tyhjä)
    //      -vastustajan SELO (onko numero) tai vastustajien SELOT (onko turnauksen tulos+selot tai selot tuloksineen)
    //      -yhtä ottelua syötettäessä tuloksen valintanapit (jos yksi vastustaja, niin tulos pitää valita)
    //
    // Syotetiedot syotteet  = oma nykyinen selo ja pelimäärä, vastustajan selo, ottelun tulos sekä merkkijono
    //
    // Tuloksena
    //    syotteet.ottelut sisältää listan vastustajista tuloksineen: ottelu(selo, tulos) 
    //    syotteet.VastustajanSeloYksittainen on joko yhden vastustajan selo tai 0 (jos monta ottelua)
    //
    // Virhetilanteet:
    //    Kenttiä tarkistetaan yo. järjestyksessä ja lopetetaan, kun kohdataan ensimmäinen virhe.
    //    Palautetaan tarkka virhestatus ja virheilmoitukset näytetään ylemmällä tasolla.
    //
    public int TarkistaSyote(Syotetiedot syotteet)
    {
        int tulos = Vakiot.SYOTE_STATUS_OK;
        
        // ************ TARKISTA SYÖTE ************

        // ENSIN TARKISTA MIETTIMISAIKA.
        // Miettimisaika on jo haettu lomakkeelta eikä siinä voi olla virhettä, joten OK
        
        do {
            // Hae ensin oma nykyinen vahvuusluku ja pelimäärä
            if ((tulos = TarkistaOmaSelo(syotteet.getAlkuperainenSelo_str())) == Vakiot.SYOTE_VIRHE_OMA_SELO)
                break;
            syotteet.setAlkuperainenSelo(tulos);

            if ((tulos = TarkistaPelimaara(syotteet.getAlkuperainenPelimaara_str())) == Vakiot.SYOTE_VIRHE_PELIMAARA)
                break;
            syotteet.setAlkuperainenPelimaara(tulos);  // Voi olla PELIMAARA_TYHJA tai numero >= 0


            //    JOS YKSI OTTELU,   saadaan sen yhden vastustajan vahvuusluku, eikä otteluja ole listassa
            //    JOS MONTA OTTELUA, palautuu 0 ja ottelut on tallennettu tuloksineen listaan
            if ((tulos = TarkistaVastustajanSelo(syotteet.getOttelut(), syotteet.getVastustajienSelot_str())) < Vakiot.SYOTE_STATUS_OK)
                break;

            // tässä siis voi olla vahvuusluku tai 0
            syotteet.setYksiVastustajaTulosnapit(tulos);

            // vain jos otteluita ei jo ole listalla (ja TarkistaVastustajanSelo palautti kelvollisen vahvuusluvun),
            // niin tarkista ottelutuloksen valintanapit -> TarkistaOttelunTulos()
            // ja sitten lisää tämä yksi ottelu listaan!
            if (syotteet.getOttelut().getLukumaara() == 0) {
                //
                // Vastustajan vahvuusluku on nyt vastustajanSeloYksittainen-kentässä
                // Haetaan vielä ottelunTulos -kenttään tulospisteet tuplana (0=tappio,1=tasapeli,2=voitto)

                // Tarvitaan tulos (voitto, tasapeli tai tappio)
                if ((tulos = TarkistaOttelunTulos(syotteet.getOttelunTulos())) == Vakiot.SYOTE_VIRHE_BUTTON_TULOS)
                    break;

                // Nyt voidaan tallentaa ainoan ottelun tiedot listaan (vastustajanSelo, ottelunTulos), josta
                // ne on helppo hakea laskennassa.
                // Myös vastustajanSeloYksittainen jää alustetuksi, koska siitä nähdään että vahvuusluku oli
                // annettu erikseen, jolloin myös ottelun tuloksen on oltava annettuna valintapainikkeilla.
                syotteet.getOttelut().LisaaOttelunTulos(syotteet.getYksiVastustajaTulosnapit(), syotteet.getOttelunTulos());
            }

            tulos = Vakiot.SYOTE_STATUS_OK; // syötekentät OK, jos päästy tänne asti ja ottelu/ottelut ovat listassa

        } while (false);

        // Virheen käsittelyt ja virheilmoitus ovat kutsuvissa rutiineissa
        
        return tulos;
    }
    

    // ************ TARKISTA MIETTIMISAIKA ************
    //
    // Nämä miettimisajan valintapainikkeet ovat omana ryhmänään paneelissa
    // Aina on joku valittuna, joten ei voi olla virhetilannetta.


    // ************ TARKISTA NYKYINEN SELO ************
    //
    // Tarkista Oma SELO -kenttä, oltava numero ja rajojen sisällä
    // Paluuarvo joko kelvollinen SELO (MIN_SELO .. MAX_SELO) tai negatiivinen virhestatus
    private int TarkistaOmaSelo(String syote)
    {
        int tulos;

        // onko numero ja jos on, niin onko sallittu numero
        try {
            tulos = Integer.parseInt(syote);
            if (tulos < Vakiot.MIN_SELO || tulos > Vakiot.MAX_SELO)
                tulos = Vakiot.SYOTE_VIRHE_OMA_SELO;               
        }
        catch (NumberFormatException e) {
            tulos = Vakiot.SYOTE_VIRHE_OMA_SELO;
        }
       
        return tulos;
    }
    
    // ************ TARKISTA PELIMÄÄRÄ ************
    //
    // Saa olla tyhjä, mutta jos annettu, oltava numero, joka on 0-9999.
    // Jos pelimäärä on 0-10, tullaan käyttämään uuden pelaajan laskentakaavaa.
    // Paluuarvo joko kelvollinen pelimäärä, PELIMAARA_TYHJA tai VIRHE_PELIMAARA.
    private int TarkistaPelimaara(String syote)
    {
        int tulos;

        //
        // tarkista Pelimäärä -kenttä
        // Saa olla tyhjä, mutta jos annettu, oltava numero, joka on 0-9999.
        //        
        if (syote.isEmpty()) {
            tulos = Vakiot.PELIMAARA_TYHJA; // OK
        } else {
            // onko numero ja jos on, niin onko sallittu numero
            try {
                tulos = Integer.parseInt(syote);
                if (tulos < Vakiot.MIN_PELIMAARA || tulos > Vakiot.MAX_PELIMAARA)
                    tulos = Vakiot.SYOTE_VIRHE_PELIMAARA;
            }
            catch (NumberFormatException e) {
                tulos = Vakiot.SYOTE_VIRHE_PELIMAARA;
            }           
        }
        return tulos;
    }
    
    
    // ************ TARKISTA VASTUSTAJAN SELO-KENTTÄ ************
    //
    // Ottelut (selot ja tulokset) tallennetaan listaan
    //
    // Syöte voi olla annettu kolmella eri formaatilla:
    //  1)  1720   -> ja sitten tulos valintanapeilla
    //  2)  2,5 1624 1700 1685 1400    Eli aloitetaan kokonaispistemäärällä.
    //                                 SELOt ilman erillisiä tuloksia.
    //  3)  +1624 -1700 =1685 +1400    jossa  '+' voitto, '=' tasapeli ja '-' tappio.
    //                                 Tasapeli voidaan myös antaa ilman '='-merkkiä.
    //
    // Yhden ottelun tulos voidaan antaa kolmella tavalla:
    //   1)  1720      ja tulos erikseen valintanapeilla, esim. 1=voitto, 1/2=tasapeli tai 0=tappio
    //   2)  -1720  (tappio), =1720    (tasapeli) tai +1720  (voitto)
    //   3)  0 1720 (tappio), 0.5 1720 (tasapeli) tai 1 1720 (voitto)
    //
    // Kahden tai useamman ottelun tulos voidaan syöttää kahdella eri tavalla
    //   1) 2,5 1624 1700 1685 1400
    //   2) +1624 -1700 =1685 +1400  (Huom! myös -1624 +1700 +1685 1400 laskee saman vahvuusluvun)
    // HUOM! Jos tuloksessa on desimaalit väärin, esim. 2.37 tai 0,9,
    //       niin ylimääräiset desimaalit "pyöristyvät" alas -> 2,0 tai 0,5.
    //
    // Paluuarvo joko kelvollinen seloluku (vain yksi vastustaja annettu), nolla (jos ottelut ovat listassa) tai virhestatus.
    private int TarkistaVastustajanSelo(Ottelulista ottelut, String syote)
    {
        boolean status = true;
        int vastustajanSelo = 0;   // palautettava vastustajan selo tai nolla tai virhestatus
        int virhekoodi = 0; 

        boolean onko_turnauksen_tulos = false;  // oliko tulos ensimmäisenä?
        float syotetty_tulos = 0F;           // tähän sitten sen tulos desimaalilukuna (esim. 2,5)        

        // kentässä voidaan antaa alussa turnauksen tulos, esim. 0.5, 2.0, 2.5, 7.5 eli saadut pisteet
        selopelaaja.setAnnettuTurnauksenTulos(-1.0F);  // oletus: ei annettu turnauksen tulosta        
        
        if (syote.isEmpty()) {
            status = false;
            virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
        } else if (syote.length() == Vakiot.SELO_PITUUS) {
            
            try {
                vastustajanSelo = Integer.parseInt(syote);
                if (vastustajanSelo < Vakiot.MIN_SELO || vastustajanSelo > Vakiot.MAX_SELO) {
                    // 3) Numeron on oltava sallitulla lukualueella
                    //    Mutta jos oli OK, niin vastustajanSelo sisältää nyt sallitun vahvuusluvun eikä tulla tähän
                    status = false;
                    virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
                }
            }
            catch (NumberFormatException e) {
                // 2) Jos on annettu neljä merkkiä (esim. 1728), niin sen on oltava numero
                status = false;
                virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
            }       
        } else {
            // Jäljellä vielä hankalammat tapaukset:
            // 4) turnauksen tulos+vahvuusluvut, esim. 2,5 1624 1700 1685 1400
            // 5) vahvuusluvut, joissa kussakin tulos  +1624 -1700 =1685 +1400
            
            // Nyt voidaan jakaa syöte merkkijonoihin!
            String [] selostr = syote.split(" ");
            List<String> listaOtteluista = Arrays.asList(selostr);
            
            // Apumuuttujat
            int selo1; // = Vakiot.MIN_SELO;
            boolean ensimmainen = true;  // ensimmäinen syötekentän numero tai merkkijono
            
            // Tutki vastustajanSelo_in -kenttä välilyönnein erotettu merkkijono kerrallaan
            for (int i = 0; i < listaOtteluista.size(); i++) {
                String vastustaja = listaOtteluista.get(i);
                if (ensimmainen) {
                    // need to use temporary variable because can't modify foreach iteration variable
                    String tempString = vastustaja;

                    // 4) Onko annettu kokonaispistemäärä? (eli useamman ottelun yhteistulos)
                    ensimmainen = false;

                    // Laita molemmat 1.5 ja 1,5 toimimaan, InvariantCulture
                    if (tempString.indexOf(',') >= 0)  // korvaa pilkku pisteellä...
                        tempString = tempString.replace(",", ".");
                    
                    try {
                        syotetty_tulos = Float.parseFloat(tempString);
                        if (syotetty_tulos >= 0.0F && syotetty_tulos <= 99.5F) {
                            // HUOM! Jos tuloksessa on desimaalit väärin, esim. 2.37 tai 0,9,
                            //       niin ylimääräiset desimaalit "pyöristyvät" alas -> 2,0 tai 0,5.
                            onko_turnauksen_tulos = true;
                            selopelaaja.setAnnettuTurnauksenTulos(syotetty_tulos);

                            // alussa oli annettu turnauksen lopputulos, jatka SELOjen tarkistamista
                            // Nyt selojen on oltava ilman tulosmerkintää!
                            continue;
                        }
                    }
                    catch (NumberFormatException e) {
                        // Jos ei saatu kelvollista lukua, joka käy tuloksena, niin jatketaan
                        // ja katsotaan, saadaanko vahvuusluku sen sijaan (jossa voi olla +/=/-)
                    }                   
                }    
                
                // Tarkista yksittäiset vastustajien vahvuusluvut

                // merkkijono voi alkaa merkillä '+', '=' tai '-'
                // Mutta tasapeli voidaan antaa myös ilman '='-merkkiä
                // Jos oli annettu turnauksen tulos, niin selot on syötettävä näin ilman tulosta
                if (vastustaja.length() == Vakiot.SELO_PITUUS) {  // numero (4 merkkiä)
                    try {
                        selo1 = Integer.parseInt(vastustaja);
                        if (selo1 < Vakiot.MIN_SELO || selo1 > Vakiot.MAX_SELO) {
                            virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
                            status = false;
                            break;
                        }
                    }
                    catch (NumberFormatException e) {
                        virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
                        status = false;
                        break;
                    }
                    
                    // XXX: (if status)
                    // Tallennetaan ottelu tasapelinä, ei ollut +:aa tai -:sta
                    ottelut.LisaaOttelunTulos(selo1, Vakiot.TULOS_TASAPELI);
                } else if (onko_turnauksen_tulos == false && vastustaja.length() == Vakiot.MAX_PITUUS) {
                    // 5)
                    // Erillisten tulosten antaminen hyväksytään vain, jos turnauksen
                    // lopputulosta ei oltu jo annettu (onko_turnauksen_tulos false)
                    
                    int tulos1 = Vakiot.TULOS_MAARITTELEMATON;
                    
                    if (vastustaja.charAt(0) >= '0' && vastustaja.charAt(0) <= '9') {
                        // tarkistetaan, voidaan olla annettu viisinumeroinen luku
                        // 10000 - 99999... joten anna virheilmoitus vahvuusluvusta
                        virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
                        status = false;
                    } else {                    
                        char c = vastustaja.charAt(0);
                        // Ensimmäinen merkki kertoo tuloksen
                        switch (c)
                        {
                            case '+':   // voitto 1 piste, tallentetaan 2
                                tulos1 = Vakiot.TULOS_VOITTO;
                                break;
                            case '=':   // tasapeli 1/2 pistettä, tallentaan 1
                                tulos1 = Vakiot.TULOS_TASAPELI;
                                break;
                            case '-':   // tappio, tallennetaan 0
                                tulos1 = Vakiot.TULOS_TAPPIO;
                                break;
                            default: // ei sallittu tuloksen kertova merkki
                                virhekoodi = Vakiot.SYOTE_VIRHE_YKSITTAINEN_TULOS;
                                status = false;
                                break;
                        }                              
                    }

                    // jos virhe, pois foreach-loopista
                    if (!status)
                        break;
                    
                    // Selvitä vielä tuloksen perässä oleva numero
                    // tarkista sitten, että on sallitulla alueella
                    try {
                        selo1 = Integer.parseInt(vastustaja.substring(1));
                        if (selo1 < Vakiot.MIN_SELO || selo1 > Vakiot.MAX_SELO) {
                            virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;  // -> virheilmoitus, ei ollut numero
                            status = false;
                            break;
                        }
                    }
                    catch (NumberFormatException e) {
                        virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;  // -> virheilmoitus, ei ollut numero
                        status = false;
                        break;                    
                    }
                    // XXX: if (status)
                    ottelut.LisaaOttelunTulos(selo1, tulos1);
                } else {
                    // pituus ei ollut
                    //   - SELO_PITUUS (esim. 1234) 
                    //   - MAX_PITUUS (esim. +1234) silloin kun tulos voidaan antaa
                    // Tähän tullaan myös, jos turnauksen kokonaistulos oli annettu ennen vahvuuslukuja,
                    // koska silloin annetaan vain pelkät vahvuusluvut ilman yksittäisiä tuloksia.
                    // Ei ole sallittu  2,5 +1624 =1700 -1685 +1400 (oikein on 2,5 1624 1700 1685 1400)
                    virhekoodi = Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO;
                    status = false;
                    break;
                }

                // Oliko asetettu virhe, mutta ei vielä poistuttu foreach-loopista?
                if (!status)
                    break; 
                
            } // for (käydään läpi syötteen numerot)

            // Lisää tarkastuksia
            // 6) Annettu turnauksen tulos ei saa olla suurempi kuin pelaajien lukumäärä
            //    Jos tulos on sama kuin pelaajien lkm, on voitettu kaikki ottelut.
            if (status && onko_turnauksen_tulos) {
                // Vertailu kokonaislukuina, esim. syötetty tulos 3.5 ja pelaajia 4, vertailu 7 > 8.
                if ((int)(2 * syotetty_tulos + 0.01F) > 2 * ottelut.getLukumaara()) {
                    virhekoodi = Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS;  // tästä oma virheilmoitus
                    // status = false; // no need to clear status any more
                }
            }            
        }
        
        // Palauta virhekoodi tai selvitetty yksittäisen vastustajan selo (joka on 0, jos ottelut listassa)
        return virhekoodi < 0 ? virhekoodi : vastustajanSelo;       
    }
    
    
    // Tarkista valitun ottelun tulos -painikkeen kelvollisuus
    // Virhestatus palautetaan, jos oli valittu TULOS_MAARITTELEMATON
    //
    private int TarkistaOttelunTulos(int ottelunTulos)
    {
        int tulos = Vakiot.SYOTE_STATUS_OK;

        if (ottelunTulos == Vakiot.TULOS_MAARITTELEMATON) {
            tulos = Vakiot.SYOTE_VIRHE_BUTTON_TULOS;
        }
        return tulos;
    }    
    
    
    // Laske tulokset, syöte on jo tarkistettu tätä ennen
    //
    // Kutsuttu: 
    //      -SelolaskuriForm.cs
    //      -Selolaskuri.Tests/UnitTest1.cs
    //
    // Lisäksi kopioi lasketut tulokset tietorakenteeseen Tulokset, josta ne myöhemmin
    // näytetään (SelolaskuriForm.cs) tai ) yksikkötestauksessa tarkistetaan (Selolaskuri.Tests/UnitTest1.cs)
    //
    public Selopelaaja SuoritaLaskenta(Syotetiedot syotteet)
    {          
        //  *** NYT LASKETAAN ***
        //
        selopelaaja.PelaaKaikkiOttelut(syotteet);   // pelaa kaikki ottelut listalta

        //  *** PALAUTA TULOKSET ***

        return selopelaaja;
     }   
}
