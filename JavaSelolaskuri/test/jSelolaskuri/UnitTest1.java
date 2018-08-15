/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

//import org.junit.AfterClass;
//import org.junit.BeforeClass;
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
     * Laskennan testauksia erilaisin syöttein (oma selo, vastustajat, ottelutulokset, ...)
     * Virhestatuksien testauksia erilaisin virhein
     */
    @Test
    public void UudenPelaajanOttelutYksittain() {
            // Testataan uuden pelaajan vahvuusluvun muutokset ottelu kerrallaan.

        t = Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        // Jos pitkä peli, niin jatkossa käytetään lyhyempää muotoa
        // var t = Testaa("1525", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        // jossa miettimisaika on oletuksena MIETTIMISAIKA_VAH_90MIN
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1725,   t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(1,      t.Item2.getUusiPelimaara());              // uusi pelimäärä 0+1 = 1
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());        // tulos voitto 
        assertEquals(1525,   t.Item2.getTurnauksenKeskivahvuus());  // keskivahvuus
        assertEquals(1,      t.Item2.getVastustajienLkm());            // yksi vastustaja
        assertEquals(50,     t.Item2.getOdotustulos());               // 0,50*100  odotustulos palautuu 100-kertaisena
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // yksi ottelu, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // yksi ottelu, sama kuin UusiSelo

        // Ja tästä eteenpäin käytetään edellisestä laskennasta saatuja UusiSelo ja UusiPelimaara
        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1441", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(2,      t.Item2.getUusiPelimaara());              // uusi pelimäärä 1+1 = 2
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1441,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(84,     t.Item2.getOdotustulos());               // 0,84*100

        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1973", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1713,   t.Item2.getUusiSelo());
        assertEquals(3,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1973,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(16,     t.Item2.getOdotustulos());               // 0,16*100

        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1718", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1764,   t.Item2.getUusiSelo());
        assertEquals(4,      t.Item2.getUusiPelimaara());
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1718,   t.Item2.getTurnauksenKeskivahvuus());

        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1784", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1728,   t.Item2.getUusiSelo());
        assertEquals(5,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1784,   t.Item2.getTurnauksenKeskivahvuus());

        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1660", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(6,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1660,   t.Item2.getTurnauksenKeskivahvuus());

        t = Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1966", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1966,   t.Item2.getTurnauksenKeskivahvuus());    }
	
    // Calculation from the format ""+1525 +1441 -1973 +1718..." takes more time than from "3 1525 1441 1973 1718..."
    @Test
    public void UudenPelaajanOttelutKerralla1()
    {
        t = Testaa("1525", "0", "+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        // Jos pitkä peli ja tulos määrittelematon, niin jatkossa käytetään lyhyempää muotoa, jossa 
        // var t = Testaa("1525", "0", "+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        // jossa miettimisaika on oletuksena MIETTIMISAIKA_VAH_90MIN ja tulos TULOS_MAARITTELEMATON
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());
        assertEquals(199,    t.Item2.getOdotustulos());          // odotustulos 1,99*100
        assertEquals(1683,   t.Item2.getMinSelo());             // laskennan aikainen minimi
        assertEquals(1764,   t.Item2.getMaxSelo());             // laskennan aikainen maksimi
    }

    @Test
    public void UudenPelaajanOttelutKerralla2()
    {
        t = Testaa("1525", "0", "3 1525 1441 1973 1718 1784 1660 1966");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Tässä lasketaan samat ottelut kuin uudelle pelaajalle, mutta vanhan pelaajan kaavalla (pelimäärä "")
    @Test
    public void SamatOttelutKuinUudella1() // Turnauksen tulos lasketaan otteluista
    {
        t = Testaa("1525", "+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1571,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());        // seitsemän vastustajaa
        assertEquals(199,    t.Item2.getOdotustulos());          // odotustulos 1,99*100
        assertEquals(1548,   t.Item2.getMinSelo());             // laskennan aikainen minimi
        assertEquals(1596,   t.Item2.getMaxSelo());             // laskennan aikainen maksimi
    }

    @Test
    public void SamatOttelutKuinUudella2() // Turnauksen tulos annettu numerona
    {
        t = Testaa("1525", "3 1525 1441 1973 1718 1784 1660 1966");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1571,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());        // seitsemän vastustajaa
        assertEquals(199,    t.Item2.getOdotustulos());          // odotustulos 1,99*100
    }

    // Kolme tapaa syöttää ottelun tulos
    @Test
    public void TulosPainikkeilla()
    {
        t = Testaa("1800", "1900", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    @Test
    public void TulosSelossa()
    {
        t = Testaa("1800", "+1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    @Test
    public void TulosNumeronaEnnenSeloa()
    {
        t = Testaa("1800", "1.0 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    // Merkkijonoissa ylimääräisiä välilyöntejä
    @Test
    public void UudenPelaajanOttelutValilyonteja()
    {
        t = Testaa("    1525  ", "0  ", "     +1525  +1441           -1973 +1718    -1784 -1660     -1966   ");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
    }

    @Test
    public void PikashakinVahvuuslukuTurnauksesta()
    {
        t = Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2033,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void PikashakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "75", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2033,   t.Item2.getUusiSelo());
        assertEquals(87,     t.Item2.getUusiPelimaara());             // 75 + 12 ottelua = 87
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(12,     t.Item2.getVastustajienLkm());
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
    }

    @Test
    public void ShakinVahvuuslukuTurnauksesta()
    {
        t = Testaa("1996", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
    }

    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void ShakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = Testaa("1996", "150", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(162,    t.Item2.getUusiPelimaara());        // 150 + 12 ottelua  = 162
    }


    // --------------------------------------------------------------------------------
    // Testataan virheellisiä syötteitä, joista pitää saada virheen mukainen virhestatus
    // --------------------------------------------------------------------------------

    // Testataan virheellinen syöte, tässä virheellinen oma vahvuusluku
    @Test
    public void VirheellinenSyoteOmaSELO()
    {
        t = Testaa("15zz5", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }

    @Test
    public void VirheellinenSyoteOmaSELOtyhja()
    {
        t = Testaa("", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }
    
    // Testataan virheellinen syöte, tässä virheellinen vastustajan vahvuusluku
    @Test
    public void VirheellinenSyoteVastustajanSELO()
    {
        t = Testaa("1525", "0", "c5sdffew25", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    @Test
    public void VirheellinenSyoteVastustajanSELOtyhja()
    {
        t = Testaa("1525", "0", "", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }
    
    // Pelimäärä virheellinen, annettu liian suureksi
    @Test
    public void VirheellinenSyoteOmaPelimaara()
    {
        t = Testaa("1525", "123456", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_PELIMAARA, t.Item1);
    }

    // Ei ole annettu ottelun tulosta valintanapeilla tappio, tasapeli tai voitto
    @Test
    public void VirheellinenSyoteEiTulosta()
    {
        t = Testaa("1525", "0", "1600", Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        assertEquals(Vakiot.SYOTE_VIRHE_BUTTON_TULOS, t.Item1);
    }

    // Virheellinen yksittäinen tulos turnauksen tuloksissa. Oltava + (voitto), - (tappio) tai = (tasan).
    @Test
    public void VirheellinenSyoteTurnauksessaVirheellinenTulos()
    {
        t = Testaa("1525", "0", "+1525 +1441 -1973 +1718 /1784 -1660 -1966", Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        assertEquals(Vakiot.SYOTE_VIRHE_YKSITTAINEN_TULOS, t.Item1);
    }

    // Annettu isompi pistemäärä (20) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos1()  
    {
        t = Testaa("1996", "20 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS, t.Item1);
    }

    // Annettu isompi pistemäärä (99) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos2()
    {
        t = Testaa("1996", "99 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS, t.Item1);
    }

    // Annettu negatiivinen pistemäärä.
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos3()
    {
        t = Testaa("1996", "-6 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    // Annettu isompi pistemäärä (150) kuin mitä on otteluita (12 kpl)
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos4()
    {
        t = Testaa("1996", "150 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    @Test
    public void CSV_UudenPelaajanOttelutYksittain1()
    {
        t = Testaa("90,1525,0,1525,1");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1725,   t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(1,      t.Item2.getUusiPelimaara());              // uusi pelimäärä 0+1 = 1
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());        // tulos voitto 
        assertEquals(1525,   t.Item2.getTurnauksenKeskivahvuus());  // keskivahvuus
        assertEquals(1,      t.Item2.getVastustajienLkm());            // yksi vastustaja
        assertEquals(50,     t.Item2.getOdotustulos());               // 0,50*100  odotustulos palautuu 100-kertaisena
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // yksi ottelu, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // yksi ottelu, sama kuin UusiSelo           
    }
    @Test
    public void CSV_UudenPelaajanOttelutKerralla1()
    {
        t = Testaa("90,1525,0,+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());
        assertEquals(199,    t.Item2.getOdotustulos());          // odotustulos 1,99*100
        assertEquals(1683,   t.Item2.getMinSelo());             // laskennan aikainen minimi
        assertEquals(1764,   t.Item2.getMaxSelo());             // laskennan aikainen maksimi
    }

    @Test
    public void CSV_UudenPelaajanOttelutKerralla2()
    {
        t = Testaa("90,1525,0,3 1525 1441 1973 1718 1784 1660 1966");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(7,      t.Item2.getVastustajienLkm());
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // yksi ottelu, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // yksi ottelu, sama kuin UusiSelo           
    }

    
    @Test
    public void CSV_PikashakinVahvuuslukuTurnauksesta()
    {
        t = Testaa("5,1996,,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2033,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }
    
    // --------------------------------------------------------------------------------
    // Testauksen apurutiinit
    //
    // Näissä kaikissa on string selo ja string vastustajat
    // Muiden parametrien puuttumisen varalta on useita versioita
    // --------------------------------------------------------------------------------
    private Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos)
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
    
    // Jos aikaa ei annettu, oletus 90 minuuttia eli pitkä peli
    private Testitulokset Testaa(String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, pelimaara, vastustajat, tulos);
    }

    // Jos aikaa, pelimaaraa ei annettu, oletus 90 minuuttia ja ""
    private Testitulokset Testaa(String selo, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, "", vastustajat, tulos);
    }

    // Jos pelimäärää ja tulosta ei annettu, oletus "" ja TULOS_MAARITTELEMATON
    private Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String vastustajat)
    {
        return Testaa(aika, selo, "", vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }

    // Jos tulosta ei annettu, oletus TULOS_MAARITTELEMATON
    private Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat)
    {
        return Testaa(aika, selo, pelimaara, vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }
    
    // Jos aikaa ja yksittäistä tulosta ei annettu, oletus 90 minuuttia ja TULOS_MAARITTELEMATON
    private Testitulokset Testaa(String selo, String pelimaara, String vastustajat)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, pelimaara, vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }

    // Jos aikaa, pelimäärää ja yksittäistä tulosta ei annettu, oletus 90 minuuttia, "" ja TULOS_MAARITTELEMATON
    private Testitulokset Testaa(String selo, String vastustajat)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, "", vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
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
            return Testaa(so.SelvitaMiettimisaika(data[0]), data[1], data[2], data[3], Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        } else if (data.length == 3) {
            return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, data[0], data[1], data[2], Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        } else if (data.length == 2) {
            return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, data[0], "", data[1], Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        } else {
            assertEquals(5, data.length); // -> Illegal CSV
            return null;
        }
    }
}
