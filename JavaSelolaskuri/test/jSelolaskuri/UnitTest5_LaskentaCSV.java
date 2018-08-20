package jSelolaskuri;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public class UnitTest5_LaskentaCSV {
    
    private final UnitTest u = new UnitTest();  
    private Testitulokset t;    
    
    @Test
    public void CSV_UudenPelaajanOttelutYksittain1()
    {
        t = u.Testaa("90,1525,0,1525,1");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1725,   t.Item2.getUusiSelo());                       // uusi vahvuusluku
        assertEquals(1,      t.Item2.getUusiPelimaara());                  // uusi pelimäärä 0+1 = 1
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());                // tulos voitto 
        assertEquals(1525,   t.Item2.getTurnauksenKeskivahvuus());         // keskivahvuus
        assertEquals(1,      t.Item2.getVastustajienLkm());                // yksi vastustaja
        assertEquals(50,     t.Item2.getOdotustulos());                    // 0,50*100  odotustulos palautuu 100-kertaisena
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());         // yksi ottelu, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());         // yksi ottelu, sama kuin UusiSelo           
    }

    @Test
    public void CSV_UudenPelaajanOttelutYksittain2()
    {
        t = u.Testaa("90,1725,1,1441,1");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(2,      t.Item2.getUusiPelimaara());           // uusi pelimäärä 1+1 = 2
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1441,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(84,     t.Item2.getOdotustulos());               // 0,84*100
    }

    @Test
    public void CSV_TasapeliOttelustaUusiPelaaja()
    {
        t = u.Testaa("90,1525,0,1812,0.5");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1812,   t.Item2.getUusiSelo());
        assertEquals(1,      t.Item2.getUusiPelimaara());           // uusi pelimäärä 0+1 = 1
        assertEquals((int)(0.5 * 2),  t.Item2.getTurnauksenTulos());
        assertEquals(1812,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(16,     t.Item2.getOdotustulos());  
    }
    @Test
    public void CSV_TasapeliOttelustaUusiPelaaja2()
    {
        t = u.Testaa("90,1525,0,1812,½");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1812,   t.Item2.getUusiSelo());
        assertEquals(1,      t.Item2.getUusiPelimaara());           // uusi pelimäärä 0+1 = 1
        assertEquals((int)(0.5 * 2),  t.Item2.getTurnauksenTulos());
        assertEquals(1812,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(16,     t.Item2.getOdotustulos());  
    }
    
    // Tarkistettu http://shakki.kivij.info/performance_calculator.shtml    
    @Test
    public void CSV_TurnauksenLaskenta()
    {
        t = u.Testaa("90,1525,20,2.5 1505 1600 1611 1558");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());  // 1568,5 -> Round 1569
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena
    }
    
    @Test
    public void CSV_TurnauksenLaskentaPuolikas()
    {
        t = u.Testaa("90,1525,20,2½ 1505 1600 1611 1558");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());  // 1568,5 -> Round 1569
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena
    }

    @Test
    public void CSV_TurnauksenLaskentaValilyonnit1()
    {
        t = u.Testaa("    90   ,   1525,    20,    2.5    1505    1600 1611 1558   ");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena
    }

    @Test
    public void CSV_TurnauksenLaskentaValilyonnit1Puolikas()
    {
        t = u.Testaa("    90   ,   1525,    20,    2½    1505    1600 1611 1558   ");    
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena
    }        
    
    @Test
    public void CSV_TurnauksenLaskentaValilyonnit2()
    {
        t = u.Testaa("90 , 1525 ,  20  ,   2.5 1505 1600 1611 1558");
        assertNotNull(t);        
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena

    }

    @Test
    public void CSV_TurnauksenLaskentaValilyonnit3()
    {
        t = u.Testaa("  90   ,1525    ,20       ,2.5 1505 1600    1611    1558  ");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1559, t.Item2.getUusiSelo());                // uusi vahvuusluku
        assertEquals(24, t.Item2.getUusiPelimaara());             // uusi pelimäärä 0+1 = 1
        assertEquals((int)(2.5F * 2), t.Item2.getTurnauksenTulos());     // tulos voitto kaksinkertaisena
        // assertEquals(1568, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(1569, t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(4, t.Item2.getVastustajienLkm());
        assertEquals(176, t.Item2.getOdotustulos());              // 1,76*100  odotustulos palautuu 100-kertaisena
    }    
    
    @Test
    public void CSV_UudenPelaajanOttelutKerralla1()
    {
        t = u.Testaa("90,1525,0,+1525 +1441 -1973 +1718 -1784 -1660 -1966");
        assertNotNull(t);
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
        t = u.Testaa("90,1525,0,3 1525 1441 1973 1718 1784 1660 1966");
        assertNotNull(t);
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
        t = u.Testaa("5,1996,,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertNotNull(t);
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
    
    // Kuten edellä CSV_PikashakinVahvuuslukuTurnauksesta(), mutta ei anneta miettimisaikaa 5 vaan käytetään oletusta 90 min -> eri tulos
    // Tässä voidaan jättää myös oma pelimäärä antamatta kokonaan. Edellisessä testitapauksessa annettu tyhjä.
    @Test
    public void CSV_VahvuuslukuTurnauksesta()
    {
        t = u.Testaa("1996,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }  
    
    // Kuten edellä CSV_PikashakinVahvuuslukuTurnauksesta(), mutta ei anneta miettimisaikaa 5 vaan käytetään oletusta 90 min -> eri tulos
    // Tässä voidaan jättää myös oma pelimäärä antamatta kokonaan. Edellisessä testitapauksessa annettu tyhjä.
    @Test
    public void CSV_VahvuuslukuTurnauksestaPuolikas()
    {
        t = u.Testaa("1996,10½ 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertNotNull(t);
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(2050,   t.Item2.getUusiSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, t.Item2.getUusiPelimaara());  // pelimäärää ei laskettu
        assertEquals((int)(10.5F * 2), t.Item2.getTurnauksenTulos());
        assertEquals(1827,   t.Item2.getTurnauksenKeskivahvuus());  // 
        assertEquals(12,     t.Item2.getVastustajienLkm());           // 12 vastustajaa eli ottelua
        assertEquals(840,    t.Item2.getOdotustulos());              // odotustulos 8,40*100
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMinSelo());     // selo laskettu kerralla, sama kuin UusiSelo
        assertEquals(t.Item2.getUusiSelo(), t.Item2.getMaxSelo());     // selo laskettu kerralla, sama kuin UusiSelo
    }           
}
