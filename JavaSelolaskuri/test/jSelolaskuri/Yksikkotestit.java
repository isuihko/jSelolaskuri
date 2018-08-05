/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ismo
 */
public class Yksikkotestit {
    
    public Yksikkotestit() {
    }

    SelolaskuriOperations so = new SelolaskuriOperations();
    
    /* TESTS WERE CONVERTED FROM C# version UnitTests
     *  e.g. Here we use class TestiTulokset instead of Tuple<int><Selopelaaja>
     *  Use a(got, expected) instead of assertEquals(expected, got);
     *
     * To execute the tests:  Right click either on file name Test Packages/jSelolaskuri/Yksikkotestit.java or over this file, and choose Run File
     */
    private class Testitulokset {
        int Item1;
        Selopelaaja Item2;
        Testitulokset(int status, Selopelaaja tulokset) {
            this.Item1 = status;
            this.Item2 = tulokset;
        }
    }
    
    Testitulokset t;
    
    /**
     * Test of getMiettimisaika method, of class Syotetiedot.
     */
    @Test
    public void UudenPelaajanOttelutYksittain() {
        // System.out.println("Testaa laskentaa");
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "1525", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1725);
        a(t.Item2.getUusiPelimaara(), 1);
        a(t.Item2.getTurnauksenTulos(), 1 * 2);        // tulos voitto (tulee kokonaislukuna 2)
        a(t.Item2.getTurnauksenKeskivahvuus(), 1525);  // keskivahvuus
        a(t.Item2.getVastustajienLkm(), 1);            // yksi vastustaja
        a(t.Item2.getOdotustulos(), 50);               // 0,50*100  odotustulos palautuu 100-kertaisena
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // yksi ottelu, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // yksi ottelu, sama kuin UusiSelo                
        
        // Ja tästä eteenpäin käytetään edellisestä laskennasta saatuja UusiSelo ja UusiPelimaara
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1441", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1683);
        a(t.Item2.getUusiPelimaara(), 2);              // uusi pelimäärä 1+1 = 2
        a(t.Item2.getTurnauksenTulos(), 1 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1441);
        a(t.Item2.getOdotustulos(), 84);               // 0,84*100
        
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1973", Vakiot.TULOS_TAPPIO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1713);
        a(t.Item2.getUusiPelimaara(), 3);
        a(t.Item2.getTurnauksenTulos(), 0);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1973);
        a(t.Item2.getOdotustulos(), 16);               // 0,16*100

        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1718", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1764);
        a(t.Item2.getUusiPelimaara(), 4);
        a(t.Item2.getTurnauksenTulos(), 1 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1718);

        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1784", Vakiot.TULOS_TAPPIO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1728);
        a(t.Item2.getUusiPelimaara(), 5);
        a(t.Item2.getTurnauksenTulos(), 0);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1784);

        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()),  "1660", Vakiot.TULOS_TAPPIO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1683);
        a(t.Item2.getUusiPelimaara(), 6);
        a(t.Item2.getTurnauksenTulos(), 0);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1660);

        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1966", Vakiot.TULOS_TAPPIO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 0);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1966);
    }
	
    // Calculation from the format ""+1525 +1441 -1973 +1718..." takes more time than from "3 1525 1441 1973 1718..."
    @Test
    public void UudenPelaajanOttelutKerralla1()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "+1525 +1441 -1973 +1718 -1784 -1660 -1966", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getVastustajienLkm(), 7);
        a(t.Item2.getOdotustulos(), 199);          // odotustulos 1,99*100
        a(t.Item2.getMinSelo(), 1683);             // laskennan aikainen minimi
        a(t.Item2.getMaxSelo(), 1764);             // laskennan aikainen maksimi
    }

    @Test
    public void UudenPelaajanOttelutKerralla2()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "3 1525 1441 1973 1718 1784 1660 1966", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Tässä lasketaan samat ottelut kuin uudelle pelaajalle, mutta vanhan pelaajan kaavalla (pelimäärä "")
    @Test
    public void SamatOttelutKuinUudella1() // Turnauksen tulos lasketaan otteluista
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "", "+1525 +1441 -1973 +1718 -1784 -1660 -1966", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1571);
        a(t.Item2.getUusiPelimaara(), Vakiot.PELIMAARA_TYHJA);  // pelimäärää ei laskettu
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getVastustajienLkm(), 7);        // seitsemän vastustajaa
        a(t.Item2.getOdotustulos(), 199);          // odotustulos 1,99*100
        a(t.Item2.getMinSelo(), 1548);             // laskennan aikainen minimi
        a(t.Item2.getMaxSelo(), 1596);             // laskennan aikainen maksimi
    }

    @Test
    public void SamatOttelutKuinUudella2() // Turnauksen tulos annettu numerona
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "", "3 1525 1441 1973 1718 1784 1660 1966", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1571);
        a(t.Item2.getUusiPelimaara(), Vakiot.PELIMAARA_TYHJA);  // pelimäärää ei laskettu
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getVastustajienLkm(), 7);        // seitsemän vastustajaa
        a(t.Item2.getOdotustulos(), 199);          // odotustulos 1,99*100
    }

    // Kolme tapaa syöttää ottelun tulos
    @Test
    public void TulosPainikkeilla()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1800", "", "1900", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1823);
        a(t.Item2.getOdotustulos(), 36);   // odotustulos 0,36*100
    }

    @Test
    public void TulosSelossa()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1800", "", "+1900", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1823);
        a(t.Item2.getOdotustulos(), 36);   // odotustulos 0,36*100
    }

    @Test
    public void TulosNumeronaEnnenSeloa()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1800", "", "1.0 1900", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1823);
        a(t.Item2.getOdotustulos(), 36);   // odotustulos 0,36*100
    }

    // Merkkijonoissa ylimääräisiä välilyöntejä
    @Test
    public void UudenPelaajanOttelutValilyonteja()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "    1525  ", "0  ", "     +1525  +1441           -1973 +1718    -1784 -1660     -1966   ", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
    }

    @Test
    public void PikashakinVahvuuslukuTurnauksesta()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 2033);
        a(t.Item2.getUusiPelimaara(), Vakiot.PELIMAARA_TYHJA);  // pelimäärää ei laskettu
        a(t.Item2.getTurnauksenTulos(), (int)(10.5F * 2));
        a(t.Item2.getTurnauksenKeskivahvuus(), 1827);  // (1977+2013+1923+1728+1638+1684+1977+2013+1923+1728+1638+1684)/12 = 1827,167
        a(t.Item2.getVastustajienLkm(), 12);           // 12 vastustajaa eli ottelua
        a(t.Item2.getOdotustulos(), 840);              // odotustulos 8,40*100
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void PikashakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "75", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 2033);
        a(t.Item2.getUusiPelimaara(), 87);             // 75 + 12 ottelua = 87
        a(t.Item2.getTurnauksenTulos(), (int)(10.5F * 2));
        a(t.Item2.getTurnauksenKeskivahvuus(), 1827);
        a(t.Item2.getVastustajienLkm(), 12);
        a(t.Item2.getOdotustulos(), 840);              // odotustulos 8,40*100
    }

    @Test
    public void ShakinVahvuuslukuTurnauksesta()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1996", "", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 2050);
        a(t.Item2.getUusiPelimaara(), Vakiot.PELIMAARA_TYHJA);  // pelimäärää ei laskettu
    }

    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void ShakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1996", "150", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 2050);
        a(t.Item2.getUusiPelimaara(), 162);        // 150 + 12 ottelua  = 162
    }


    // --------------------------------------------------------------------------------
    // Testataan virheellisiä syötteitä, joista pitää saada virheen mukainen virhestatus
    // --------------------------------------------------------------------------------

    // Testataan virheellinen syöte, tässä virheellinen oma vahvuusluku
    @Test
    public void VirheellinenSyoteOmaSELO()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "15zz5", "0", "1525", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_VIRHE_OMA_SELO);
    }

    @Test
    public void VirheellinenSyoteOmaSELOtyhja()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "", "0", "1525", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_VIRHE_OMA_SELO);
    }
    
    // Testataan virheellinen syöte, tässä virheellinen vastustajan vahvuusluku
    @Test
    public void VirheellinenSyoteVastustajanSELO()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "c5sdffew25", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO);
    }

    @Test
    public void VirheellinenSyoteVastustajanSELOtyhja()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO);
    }
    
    // Pelimäärä virheellinen, annettu liian suureksi
    @Test
    public void VirheellinenSyoteOmaPelimaara()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "123456", "1525", Vakiot.TULOS_VOITTO);
        a(t.Item1, Vakiot.SYOTE_VIRHE_PELIMAARA);
    }

    // Ei ole annettu ottelun tulosta valintanapeilla tappio, tasapeli tai voitto
    @Test
    public void VirheellinenSyoteEiTulosta()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "1600", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_BUTTON_TULOS);
    }

    // Virheellinen yksittäinen tulos turnauksen tuloksissa. Oltava + (voitto), - (tappio) tai = (tasan).
    @Test
    public void VirheellinenSyoteTurnauksessaVirheellinenTulos()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "+1525 +1441 -1973 +1718 /1784 -1660 -1966", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_YKSITTAINEN_TULOS);
    }

    // Annettu isompi pistemäärä (20) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos1()  
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "", "20 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS);
    }

    // Annettu isompi pistemäärä (99) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos2()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "", "99 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS);
    }

    // Annettu negatiivinen pistemäärä.
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos3()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "", "-6 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO);
    }

    // Annettu isompi pistemäärä (150) kuin mitä on otteluita (12 kpl)
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos4()
    {
        t = Testaa(Vakiot.MIETTIMISAIKA_ENINT_10MIN, "1996", "", "150 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684", Vakiot.TULOS_MAARITTELEMATON);
        a(t.Item1, Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO);
    }

    @Test
    public void CSV_UudenPelaajanOttelutYksittain1()
    {
        t = Testaa("90,1525,0,1525,1");
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1725);
        a(t.Item2.getUusiPelimaara(), 1);
        a(t.Item2.getTurnauksenTulos(), 1 * 2);        // tulos voitto (tulee kokonaislukuna 2)
        a(t.Item2.getTurnauksenKeskivahvuus(), 1525);  // keskivahvuus
        a(t.Item2.getVastustajienLkm(), 1);            // yksi vastustaja
        a(t.Item2.getOdotustulos(), 50);               // 0,50*100  odotustulos palautuu 100-kertaisena
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // yksi ottelu, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // yksi ottelu, sama kuin UusiSelo              
    }
    @Test
    public void CSV_UudenPelaajanOttelutKerralla1()
    {
        t = Testaa("90,1525,0,+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getVastustajienLkm(), 7);
        a(t.Item2.getOdotustulos(), 199);          // odotustulos 1,99*100
        a(t.Item2.getMinSelo(), 1683);             // laskennan aikainen minimi
        a(t.Item2.getMaxSelo(), 1764);             // laskennan aikainen maksimi
    }

    @Test
    public void CSV_UudenPelaajanOttelutKerralla2()
    {
        t = Testaa("90,1525,0,3 1525 1441 1973 1718 1784 1660 1966");
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 1695);
        a(t.Item2.getUusiPelimaara(), 7);
        a(t.Item2.getTurnauksenTulos(), 3 * 2);
        a(t.Item2.getTurnauksenKeskivahvuus(), 1724);
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    
    @Test
    public void CSV_PikashakinVahvuuslukuTurnauksesta()
    {
        t = Testaa("5,1996,,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        a(t.Item1, Vakiot.SYOTE_STATUS_OK);
        a(t.Item2.getUusiSelo(), 2033);
        a(t.Item2.getUusiPelimaara(), Vakiot.PELIMAARA_TYHJA);  // pelimäärää ei laskettu
        a(t.Item2.getTurnauksenTulos(), (int)(10.5F * 2));
        a(t.Item2.getTurnauksenKeskivahvuus(), 1827);  // (1977+2013+1923+1728+1638+1684+1977+2013+1923+1728+1638+1684)/12 = 1827,167
        a(t.Item2.getVastustajienLkm(), 12);           // 12 vastustajaa eli ottelua
        a(t.Item2.getOdotustulos(), 840);              // odotustulos 8,40*100
        a(t.Item2.getMinSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        a(t.Item2.getMaxSelo(), t.Item2.getUusiSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }
    
    // --------------------------------------------------------------------------------
    // Testauksen apurutiini
    // --------------------------------------------------------------------------------
    private Testitulokset Testaa(int aika, String selo, String pelimaara, String vastustajat, int tulos)
    {
        //SelolaskuriOperations so = new SelolaskuriOperations();
        Syotetiedot syotetiedot = new Syotetiedot(aika, selo, pelimaara, vastustajat, tulos, /*doTrim*/true);
        int status;
        Selopelaaja tulokset = null;
        
        if ((status = so.TarkistaSyote(syotetiedot)) == Vakiot.SYOTE_STATUS_OK) {
            tulokset = so.SuoritaLaskenta(syotetiedot);
        }
        
        return new Testitulokset(status, tulokset);
    }
    
        
    // CSV 
    // 90,1525,0,1725,1
    // Jos 5 merkkijonoa:  minuutit,selo,pelimäärä,vastustajat,jos_yksi_selo_niin_tulos
    // Jos 4: ottelun tulosta ei anneta, käytetään TULOS_MAARITTELEMATON
    // Jos 3: Myös miettimisaika on antamatta, käytetään oletuksena MIETTIMISAIKA_VAH_90MIN
    // Jos 2: Myös pelimäärä on antamatta, käytetään oletuksena tyhjää ""
    private Testitulokset Testaa(String csv)
    {
        String[] data = csv.split(",");
        if (data.length == 5) {
            return Testaa(so.SelvitaMiettimisaika(data[0]), data[1], data[2], data[3], so.SelvitaTulos(data[4]));
        } else if (data.length == 4) {
            return Testaa(so.SelvitaMiettimisaika(data[0]), data[1], data[2], data[3], Vakiot.TULOS_MAARITTELEMATON);
        } else if (data.length == 3) {
            return Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, data[0], data[1], data[2], Vakiot.TULOS_MAARITTELEMATON);
        } else if (data.length == 2) {
            return Testaa(Vakiot.MIETTIMISAIKA_VAH_90MIN, data[0], "", data[1], Vakiot.TULOS_MAARITTELEMATON);
        } else {
            assertEquals(5, data.length); // -> Illegal CSV
            return null;
        }
    }
    
    /* IN C# PARAMETERS WERE IN DIFFERENT ORDER */
    private void a(int got, int expected) {
        assertEquals(expected, got);
    }   
}
