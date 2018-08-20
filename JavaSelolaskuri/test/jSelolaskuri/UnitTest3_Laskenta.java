package jSelolaskuri;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ismo
 */
public class UnitTest3_Laskenta {
    
    private final UnitTest u = new UnitTest();  
    private Testitulokset t;
    
    /**
     * Laskennan testauksia erilaisin syöttein(oma selo, vastustajat, ottelutulokset, ...)
     */
    @Test
    public void UudenPelaajanOttelutYksittain() {
            // Testataan uuden pelaajan vahvuusluvun muutokset ottelu kerrallaan.

        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, "1525", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
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
        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1441", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(2,      t.Item2.getUusiPelimaara());              // uusi pelimäärä 1+1 = 2
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1441,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(84,     t.Item2.getOdotustulos());               // 0,84*100

        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1973", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1713,   t.Item2.getUusiSelo());
        assertEquals(3,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1973,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(16,     t.Item2.getOdotustulos());               // 0,16*100

        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1718", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1764,   t.Item2.getUusiSelo());
        assertEquals(4,      t.Item2.getUusiPelimaara());
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1718,   t.Item2.getTurnauksenKeskivahvuus());

        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1784", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1728,   t.Item2.getUusiSelo());
        assertEquals(5,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1784,   t.Item2.getTurnauksenKeskivahvuus());

        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1660", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(6,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1660,   t.Item2.getTurnauksenKeskivahvuus());

        t = u.Testaa(Integer.toString(t.Item2.getUusiSelo()), Integer.toString(t.Item2.getUusiPelimaara()), "1966", Vakiot.OttelunTulos_enum.TULOS_TAPPIO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(0,      t.Item2.getTurnauksenTulos());
        assertEquals(1966,   t.Item2.getTurnauksenKeskivahvuus());    }
	
    // Calculation from the format ""+1525 +1441 -1973 +1718..." takes more time than from "3 1525 1441 1973 1718..."
    @Test
    public void UudenPelaajanOttelutKerralla1()
    {
        t = u.Testaa("1525", "0", "+1525 +1441 -1973 +1718 -1784 -1660 -1966");
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
        t = u.Testaa("1525", "0", "3 1525 1441 1973 1718 1784 1660 1966");
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
        t = u.Testaa("1525", "+1525 +1441 -1973 +1718 -1784 -1660 -1966");
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
        t = u.Testaa("1525", "3 1525 1441 1973 1718 1784 1660 1966");
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
        t = u.Testaa("1800", "1900", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    @Test
    public void TulosSelossa()
    {
        t = u.Testaa("1800", "+1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    // Seuraavan kahden laskennan tarkistus: http://shakki.kivij.info/performance_calculator.shtml
    @Test
    public void TulosNumeronaEnnenSeloaDesimPiste()
    {
        t = u.Testaa("1800", "1.0 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    @Test
    public void TulosNumeronaEnnenSeloaPilkku()
    {
        t = u.Testaa("1800", "1,0 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1823,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    // Seuraavan kahden laskennan tarkistus: http://shakki.kivij.info/performance_calculator.shtml
    @Test
    public void TulosNumeronaEnnenSeloaDesimPiste2()
    {
        t = u.Testaa("1800", "0.5 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1805,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }

    @Test
    public void TulosNumeronaEnnenSeloaPilkku2()
    {
        t = u.Testaa("1800", "0,5 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1805,   t.Item2.getUusiSelo());
        assertEquals(36,     t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }    
    
    @Test
    public void TulosNumeronaEnnenSeloaPuolikas()
    {
        t = u.Testaa("1800", "½ 1900");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1805, t.Item2.getUusiSelo());
        assertEquals(36, t.Item2.getOdotustulos());   // odotustulos 0,36*100
    }
    
    // Merkkijonoissa ylimääräisiä välilyöntejä
    @Test
    public void UudenPelaajanOttelutValilyonteja()
    {
        t = u.Testaa("    1525  ", "0  ", "     +1525  +1441           -1973 +1718    -1784 -1660     -1966   ");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1695,   t.Item2.getUusiSelo());
        assertEquals(7,      t.Item2.getUusiPelimaara());
        assertEquals(3 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1724,   t.Item2.getTurnauksenKeskivahvuus());
    }

    @Test
    public void PikashakinVahvuuslukuTurnauksestaDesimPiste()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2034,   t.Item2.getUusiSelo());  // tarkista, oliko 2033 (yhden pisteen virhe laskennassa mahdollinen)
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    @Test
    public void PikashakinVahvuuslukuTurnauksestaPilkku()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "10,5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2034,   t.Item2.getUusiSelo());  // tarkista, oliko 2033 (yhden pisteen virhe laskennassa mahdollinen)
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    @Test
    public void PikashakinVahvuuslukuTurnauksestaPuolikas()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "10½ 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2034,   t.Item2.getUusiSelo());  // tarkista, oliko 2033 (yhden pisteen virhe laskennassa mahdollinen)
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }    

    // Esimerkki Joukkuepikashakin SM 2018 alkukilpailut, alkukilpailuryhmä C  4.8.2018, LauttSSK 1 pöytä 1
    // Kilpailuryhmä C: http://www.shakki.net/cgi-bin/selo?do=turnaus&turnaus_id=5068
    // Kaikki täsmää
    @Test
    public void PikashakinVahvuuslukuTurnauksesta1()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "2180", "2054", "14.5 1914 2020 1869 2003 2019 1979 2131 2161 2179 2392 1590 1656 1732 1944 1767 1903 1984 2038 2083 2594 2324 1466 1758");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2148, t.Item2.getUusiSelo());       // Saadaan sama tulos kuin shakkiliiton sivulla
        assertEquals(2077, t.Item2.getUusiPelimaara());  // pelimäärä
        assertEquals((int)(14.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1979, t.Item2.getTurnauksenKeskivahvuus());  // Summa 45506 / 23 = 1978,521739 pyöristys 1979
        assertEquals(23, t.Item2.getVastustajienLkm());           // 23 vastustajaa eli ottelua
        assertEquals(1624, t.Item2.getOdotustulos());              // odotustulos 16,24*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Esimerkki Joukkuepikashakin SM 2018 alkukilpailut, alkukilpailuryhmä C  4.8.2018, LauttSSK 1 pöytä 4
    // Kilpailuryhmä C: http://www.shakki.net/cgi-bin/selo?do=turnaus&turnaus_id=5068
    // Kaikki täsmää
    @Test
    public void PikashakinVahvuuslukuTurnauksesta2()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "2045", "1225", "19.5 1548 1560 1699 1737 1735 1880 1856 2019 2102 2177 1539 1531 1672 1592 1775 1842 1847 1905 1970 2308 1988 1454 1481");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2083, t.Item2.getUusiSelo());
        assertEquals(1248, t.Item2.getUusiPelimaara());  // pelimäärä
        assertEquals((int)(19.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1792, t.Item2.getTurnauksenKeskivahvuus());  // Summa 41217 / 23 = 1792.043
        assertEquals(23, t.Item2.getVastustajienLkm());           // 23 vastustajaa eli ottelua
        assertEquals(1740, t.Item2.getOdotustulos());              // odotustulos 17,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    // Esimerkki Joukkuepikashakin SM 2018 sijoituskilpailut 5.8.2018, sijoitusryhmä C, LauttSSK 4 pöytä 4
    // Sijoitusryhmä 5: http://www.shakki.net/cgi-bin/selo?do=turnaus&turnaus_id=5068
    // Kaikki täsmää
    @Test
    public void PikashakinVahvuuslukuTurnauksesta3()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1262", "11 1623 1591 1318 1560 1493 1417 1343 1493 1524 1227 1716 1490 1454 1479 1329 1429 1444 1289 1576 1445 1280");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1345, t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärä
        assertEquals((int)(11F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1453, t.Item2.getTurnauksenKeskivahvuus());  // Summa 30520 / 21 = 1453.333
        assertEquals(21, t.Item2.getVastustajienLkm());           // 21 vastustajaa eli ottelua
        assertEquals(564, t.Item2.getOdotustulos());              // odotustulos 5,64*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    
    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void PikashakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = u.Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN, "1996", "75", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2034,   t.Item2.getUusiSelo());  // tarkista, oliko 2033 (yhden pisteen virhe laskennassa mahdollinen)
        assertEquals(87,     t.Item2.getUusiPelimaara());             // 75 + 12 ottelua = 87
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(12,     t.Item2.getVastustajienLkm());
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
    }

    @Test
    public void ShakinVahvuuslukuTurnauksesta()
    {
        t = u.Testaa("1996", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
    }

    // Testataan eri pelimäärillä, ettei tulos riipu pelimäärästä silloin kun ei ole uuden pelaajan laskenta
    @Test
    public void ShakinVahvuuslukuTurnauksestaPelimaaralla()
    {
        t = u.Testaa("1996", "150", "10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(162,    t.Item2.getUusiPelimaara());        // 150 + 12 ottelua  = 162
    }    
    
    // Testataan, että sallittu minimiselo (1000) käy syötteessä
    // Testataan, että tulos 0 on OK turnauksen tuloksena (pienin sallittu tulos).
    // Testataan, entä jos tulos menee alle minimiselon. Ei ongelmaa.
    @Test
    public void LaskettuVahvuuslukuAlleMinimin1()
    {
        t = u.Testaa("1000", "0 1100");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(984, t.Item2.getUusiSelo());
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    @Test
    public void LaskettuVahvuuslukuAlleMinimin2()
    {
        t = u.Testaa("1000", "-1100 +1005 -1002");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(985, t.Item2.getUusiSelo());
        assertEquals(984, t.Item2.getMinSelo());      // laskennan aikainen minimi
        assertEquals(1007, t.Item2.getMaxSelo());     // laskennan aikainen maksimi
    }
    // Testataan, että sallittu maksimiselo (2999) käy syötteessä
    // Testataan, entä jos tulos menee yli maksimiselon. Ei ongelmaa.
    @Test
    public void LaskettuVahvuuslukuYliMaksimin1()
    {
        t = u.Testaa("2999", "1 2700");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(3002, t.Item2.getUusiSelo());
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }

    @Test
    public void LaskettuVahvuuslukuYliMaksimin2()
    {
        t = u.Testaa("2999", "+2700 -2991 +2988");
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(3002, t.Item2.getUusiSelo());
        assertEquals(2992, t.Item2.getMinSelo());     // laskennan aikainen minimi
        assertEquals(3002, t.Item2.getMaxSelo());     // laskennan aikainen maksimi
    }    
}
