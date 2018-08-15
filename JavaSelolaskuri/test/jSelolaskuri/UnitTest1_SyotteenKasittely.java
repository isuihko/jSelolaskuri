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
public class UnitTest1_SyotteenKasittely {
    
    SelolaskuriOperations so = new SelolaskuriOperations();    

    // Apurutiini, muodostaa seloluvusta ja tuloksesta merkkijonon,
    // Esim. selo 1441, tulos voitto -> "+1441"
    private String MuodostaOttelu(int selo, Vakiot.OttelunTulos_enum tulos)
    {
        String ottelu;
        
        switch (tulos) {
            case TULOS_VOITTO:
                ottelu = "+";
                break;
            case TULOS_TASAPELI:
                ottelu = "=";
                break;
            case TULOS_TAPPIO:
                ottelu = "-";
                break;
            case TULOS_EI_ANNETTU:
                ottelu = "";
                break;
            default:
                ottelu = "*";  // VIRHE
                break;
        }
        
        ottelu += Integer.toString(selo);
        return ottelu;
    }
    
    //
    // Tämä tarkistaa, ovat syötteet ja ottelulista oikein, kun tulokset annettu formaatissa
    // "+1525 +1441 -1973 +1718 -1784 -1660 -1966",
    // jossa kunkin vastustajan vahvuusluvun kanssa on annettu ottelun tulos
    //
    @Test
    public void TarkistaOttelulistaErillisinTuloksin()
    {
        // input
        Vakiot.Miettimisaika_enum aika = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN;
        String selo = "1525";
        String pelimaara = "";
        String vastustajat = "+1525 +1441 -1973 +1718 -1784 -1660 -1966 =1234 1555";
        Vakiot.OttelunTulos_enum tulos = Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON;


        Syotetiedot s = new Syotetiedot(aika, selo, pelimaara, vastustajat, tulos);

        // 
        // Check that the input was stored correctly!
        // 
        assertEquals(aika, s.getMiettimisaika());
        assertEquals(selo, s.getAlkuperainenSelo_str());
        assertEquals(pelimaara, s.getAlkuperainenPelimaara_str());
        assertEquals(vastustajat, s.getVastustajienSelot_str());
        assertEquals(tulos, s.getOttelunTulos());

        //
        // parse strings into numbers, create an array of opponents
        //
        int status = so.TarkistaSyote(s);
        assertEquals(Vakiot.SYOTE_STATUS_OK, status);
        

        int seloInt = 0;
        
        try {
            seloInt = Integer.parseInt(selo);
        }
        catch (NumberFormatException e) {
            assertEquals("Illegal test data (number)", selo);
        }      

        assertEquals(seloInt, s.getAlkuperainenSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, s.getAlkuperainenPelimaara());

        //
        // Tarkista ottelulista, ensin lukumäärä
        //
        String[] vastustajatStr = vastustajat.split(" ");
        assertEquals(vastustajatStr.length, s.getOttelut().getLukumaara());

        // Käy sitten tallennetut ottelut läpi

        // Huom! Koska vastustajatStr sisältää myös turnauksen tuloksen, aloitetaan indeksistä 1
        int i = 0;
        Ottelulista lista = s.getOttelut();

        Ottelu ottelu = lista.HaeEnsimmainen(); // vastustajanSelo, ottelunTulos            
        while (ottelu.getOttelunTulos() != Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON) {
            // Koska käytetty turnauksen tulosta, niin listaan tallennettuihin otteluihin ei ole annettu tulosta
            assertEquals(MuodostaOttelu(ottelu.getVastustajanSelo(), ottelu.getOttelunTulos()), vastustajatStr[i]);
            ottelu = lista.HaeSeuraava();
            i++;
        }

    }


    //
    // Tämä tarkistaa, ovat syötteet ja ottelulista oikein, kun tulokset annettu
    // formaatissa "1.5 1525 1441 1973 1718 1784 1660 1966",
    // jossa on turnauksen tulos ensin ja vastustajien vahvuusluvut ovat ilman erillistä tulosta
    //
    @Test
    public void TarkistaOttelulistaTurnauksenTuloksella()
    {
        // input
        Vakiot.Miettimisaika_enum aika = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN;
        String selo = "1525";
        String pelimaara = "";
        String vastustajat = "1.5 1525 1441 1973 1718 1784 1660 1966";
        Vakiot.OttelunTulos_enum tulos = Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON;

        Syotetiedot s = new Syotetiedot(aika, selo, pelimaara, vastustajat, tulos);

        // 
        // Check that the input was stored correctly!
        // 
        assertEquals(aika, s.getMiettimisaika());
        assertEquals(selo, s.getAlkuperainenSelo_str());
        assertEquals(pelimaara, s.getAlkuperainenPelimaara_str());
        assertEquals(vastustajat, s.getVastustajienSelot_str());
        assertEquals(tulos, s.getOttelunTulos());

        //
        // parse strings into numbers, create an array of opponents
        //
        int status = so.TarkistaSyote(s);
        assertEquals(Vakiot.SYOTE_STATUS_OK, status);

        int seloInt = 0;
        
        try {
            seloInt = Integer.parseInt(selo);
        }
        catch (NumberFormatException e) {
            assertEquals("Illegal test data (number)", selo);
        }      


        assertEquals(seloInt, s.getAlkuperainenSelo());
        assertEquals(Vakiot.PELIMAARA_TYHJA, s.getAlkuperainenPelimaara());

        //
        // Tarkista ottelulista, ensin lukumäärä
        //
        // Huom! Nyt vastustajatStr sisältää myös turnauksen tuloksen, joten vähennettävä lukumäärää yhdellä
        //
        String[] vastustajatStr = vastustajat.split(" ");
        assertEquals(vastustajatStr.length - 1, s.getOttelut().getLukumaara());

        // Käy sitten tallennetut ottelut läpi

        // Huom! Koska vastustajatStr sisältää myös turnauksen tuloksen, aloitetaan indeksistä 1
        int i = 1;
        Ottelulista lista = s.getOttelut();

        Ottelu ottelu = lista.HaeEnsimmainen(); // vastustajanSelo, ottelunTulos            
        while (ottelu.getOttelunTulos() != Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON) {
            // Koska käytetty turnauksen tulosta, niin listaan tallennettuihin otteluihin ei ole annettu tulosta
            assertEquals(MuodostaOttelu(ottelu.getVastustajanSelo(), ottelu.getOttelunTulos()), vastustajatStr[i]);
            ottelu = lista.HaeSeuraava();
            i++;
        }
    }    
    
}
