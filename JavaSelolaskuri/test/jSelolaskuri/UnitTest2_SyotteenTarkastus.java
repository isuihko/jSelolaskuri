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
public class UnitTest2_SyotteenTarkastus {
    
    private final UnitTest1 u = new UnitTest1();  
    private Testitulokset t;
    

    // --------------------------------------------------------------------------------
    // Testataan virheellisiä syötteitä, joista pitää saada virheen mukainen virhestatus
    // --------------------------------------------------------------------------------

    // Testataan virheellinen syöte, tässä virheellinen oma vahvuusluku
    @Test
    public void VirheellinenSyoteOmaSELO()
    {
        t = u.Testaa("15zz5", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }

    @Test
    public void VirheellinenSyoteOmaSELOtyhja()
    {
        t = u.Testaa("", "0", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_OMA_SELO, t.Item1);
    }
    
    // Testataan virheellinen syöte, tässä virheellinen vastustajan vahvuusluku
    @Test
    public void VirheellinenSyoteVastustajanSELO()
    {
        t = u.Testaa("1525", "0", "c5sdffew25", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    @Test
    public void VirheellinenSyoteVastustajanSELOtyhja()
    {
        t = u.Testaa("1525", "0", "", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }
    
    // Pelimäärä virheellinen, annettu liian suureksi (asetettu rajoitus 9999)
    @Test
    public void VirheellinenSyoteOmaPelimaara()
    {
        t = u.Testaa("1525", "123456", "1525", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
        assertEquals(Vakiot.SYOTE_VIRHE_PELIMAARA, t.Item1);
    }

    // Ei ole annettu ottelun tulosta valintanapeilla tappio, tasapeli tai voitto
    @Test
    public void VirheellinenSyoteEiTulosta()
    {
        t = u.Testaa("1525", "0", "1600", Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        assertEquals(Vakiot.SYOTE_VIRHE_BUTTON_TULOS, t.Item1);
    }

    // Virheellinen yksittäinen tulos turnauksen tuloksissa. Oltava + (voitto), - (tappio) tai = (tasan).
    @Test
    public void VirheellinenSyoteTurnauksessaVirheellinenTulos()
    {
        t = u.Testaa("1525", "0", "+1525 +1441 -1973 +1718 /1784 -1660 -1966", Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
        assertEquals(Vakiot.SYOTE_VIRHE_YKSITTAINEN_TULOS, t.Item1);
    }

    // Annettu isompi pistemäärä (20) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos1()  
    {
        t = u.Testaa("1996", "20 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS, t.Item1);
    }

    // Annettu isompi pistemäärä (99) kuin mitä on otteluita (12 kpl)
    @Test
    public void VirheellinenSyoteTurnauksenTulos2()
    {
        t = u.Testaa("1996", "99 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS, t.Item1);
    }

    // Annettu negatiivinen pistemäärä.
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos3()
    {
        t = u.Testaa("1996", "-6 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }

    // Annettu isompi pistemäärä (150) kuin mitä on otteluita (12 kpl)
    // Palautuu ilmoituksena virheellisestä vastustajan selosta, kun ensimmäinen 
    // luku käsitellään numerona eikä tarkistuksessa voida tietää, kumpaa on tarkoitettu.
    @Test
    public void VirheellinenSyoteTurnauksenTulos4()
    {
        t = u.Testaa("1996", "150 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        assertEquals(Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO, t.Item1);
    }    
}