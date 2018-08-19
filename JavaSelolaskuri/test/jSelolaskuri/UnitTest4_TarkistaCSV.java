/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 *
 * @author Ismo
 */
public class UnitTest4_LaskentaCSV {
    
    private final UnitTest1 u = new UnitTest1();  
    private Testitulokset t;    
            
    // --------------------------------------------------------------------------------
    // Laskennan testauksia erilaisin syöttein CSV-formaatista (comma separated values)
    //
    // Tässä testataan virhetilanteet (liian monta arvoa, arvot puuttuvat) ja 
    // sitten muutamalla tapauksella laskentaa, että saadaanko oikea syöte erotettua merkkijonosta.
    // Sen jälkeen laskenta menee tavalliseksi laskennaksi, vrt. UnitTest3.cs
    // --------------------------------------------------------------------------------

    @Test
    public void CSV_LiianMontaArvoa1()
    {
        t = u.Testaa("90,1525,0,1525,1,123");
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
    }

    @Test
    public void CSV_LiianMontaArvoa2()
    {
        t = u.Testaa(",,,,,,");
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki1()
    {
        t = u.Testaa(",,,,");
        
        // four commas expected format: thinking time,own selo,own game count,opponents,single match result
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki2()
    {
        t = u.Testaa(",,,");
        // three commas expected format: thinking time,own selo,own game count,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki3()
    {
        t = u.Testaa(",,");
        // two commas expected format: own selo,own game count,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki4()
    {
        t = u.Testaa(",");
        // one comma expected format: own selo,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki5()
    {
        t = u.Testaa("");
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
    }

    // Jos on neljä pilkkua, niin pitää olla myös miettimisaika
    @Test
    public void CSV_ArvotPuuttuvatMiettimisaika()
    {
        t = u.Testaa(",1525,0,1525,1");
        assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);
    }

    // Kolme pilkkua, neljä arvoa ja pitää olla miettimisaika ensimmäisenä
    @Test
    public void CSV_ArvotPuuttuvatMiettimisaika2()
    {
        t = u.Testaa(",0,1525,1");
        assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);
    }

    // Neljä pilkkua ja viisi arvoa, mutta oma selo tyhjä
    @Test
    public void CSV_ArvotPuuttuvatOmaSelo()
    {
        t = u.Testaa("90,,0,1525,1");
        assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }

    @Test
    public void CSV_ArvotPuuttuvatVastustajat()
    {
        t = u.Testaa("90,1525,0,,1");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    // Yksittäisen ottelun tulos tarvitaan, jos on yksi vastustaja eikä tulosta ole kerrottu esim. "+1425"
    @Test
    public void CSV_ArvotPuuttuvatYksittainenTulos()
    {
        t = u.Testaa("90,1525,0,1525");
        assertEquals(Vakiot.SYOTE_VIRHE_BUTTON_TULOS, t.Item1);
    }    
    
    @Test
    public void CSV_UudenPelaajanOttelutYksittain1()
    {
        t = u.Testaa("90,1525,0,1525,1");
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
        assertEquals(Vakiot.SYOTE_STATUS_OK, t.Item1);
        assertEquals(1683,   t.Item2.getUusiSelo());
        assertEquals(2,      t.Item2.getUusiPelimaara());           // uusi pelimäärä 1+1 = 2
        assertEquals(1 * 2,  t.Item2.getTurnauksenTulos());
        assertEquals(1441,   t.Item2.getTurnauksenKeskivahvuus());
        assertEquals(84,     t.Item2.getOdotustulos());               // 0,84*100
    }
    
    @Test
    public void CSV_UudenPelaajanOttelutKerralla1()
    {
        t = u.Testaa("90,1525,0,+1525 +1441 -1973 +1718 -1784 -1660 -1966");
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
    
    // Kuten edellä CSV_PikashakinVahvuuslukuTurnauksesta(), mutta ei anneta miettimisaikaa 5 vaan käytetään oletusta 90 min -> eri tulos
    // Tässä voidaan jättää myös oma pelimäärä antamatta kokonaan. Edellisessä testitapauksessa annettu tyhjä.
    @Test
    public void CSV_VahvuuslukuTurnauksesta()
    {
        t = u.Testaa("1996,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
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
