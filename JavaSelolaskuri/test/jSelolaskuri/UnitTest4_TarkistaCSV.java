/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Ismo
 */
public class UnitTest4_TarkistaCSV {
    
    private final UnitTest u = new UnitTest();  
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
        //assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);  // C#
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki2()
    {
        t = u.Testaa(",,,");
        // three commas expected format: thinking time,own selo,own game count,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_MIETTIMISAIKA, t.Item1);  // C#
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki3()
    {
        t = u.Testaa(",,");
        // two commas expected format: own selo,own game count,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);  // C#
    }

    @Test
    public void CSV_ArvotPuuttuvatKaikki4()
    {
        t = u.Testaa(",");
        // one comma expected format: own selo,opponents
        assertEquals(Vakiot.SYOTE_VIRHE_CSV_FORMAT, t.Item1);
        //assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);  // C#
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

    // Turnauksen tulos 2½ syötetty ½2, jolloin se tarkastuksessa tulkitaan vastustajan seloksi
    @Test
    public void CSV_VirheellinenTurnauksenTulos()
    {
        t = u.Testaa("90,1525,20,½2 1505 1600 1611 1558");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }
}
