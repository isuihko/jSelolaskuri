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
 * 
 * NOTE! More general comments in C# version of this file.
 * 
 *  This has general Testaa() routine (with a few overloads) which are used by other UnitTest modules.
 */
public class UnitTest {
    
    public UnitTest() {
    }

    private SelolaskuriOperations so = new SelolaskuriOperations();
    
    /* TESTS WERE CONVERTED FROM C# version UnitTests
     *  e.g. Here we use class TestiTulokset instead of Tuple<int><Selopelaaja>
     *
     * To execute the tests:  Right click either on file name or over the unit test file, and choose Run File
     */
    
    // --------------------------------------------------------------------------------
    // Parametrien tarkistuksen ja laskennan testauksen apurutiinit
    //
    // Näissä kaikissa on string selo ja string vastustajat
    // Muiden parametrien puuttumisen varalta on useita versioita
    // --------------------------------------------------------------------------------

    public Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        //SelolaskuriOperations so = new SelolaskuriOperations();
        Syotetiedot syotetiedot = new Syotetiedot(aika, selo, pelimaara, vastustajat, tulos, /*doTrim*/true);
        int status;
        Selopelaaja tulokset = null;
        
        if ((status = so.TarkistaSyote(syotetiedot)) == Vakiot.SYOTE_STATUS_OK) {
            // If the input was OK, continue and calculate
            // If wasn't, then tulokset is left null and error status will be returned
            tulokset = so.SuoritaLaskenta(syotetiedot);
        }        
        return new Testitulokset(status, tulokset);
    }
    
    // Jos aikaa ei annettu, oletus 90 minuuttia eli pitkä peli
    public Testitulokset Testaa(String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, pelimaara, vastustajat, tulos);
    }

    // Jos aikaa, pelimaaraa ei annettu, oletus 90 minuuttia ja ""
    public Testitulokset Testaa(String selo, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, "", vastustajat, tulos);
    }

    // Jos pelimäärää ja tulosta ei annettu, oletus "" ja TULOS_MAARITTELEMATON
    public Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String vastustajat)
    {
        return Testaa(aika, selo, "", vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }

    // Jos tulosta ei annettu, oletus TULOS_MAARITTELEMATON
    public Testitulokset Testaa(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat)
    {
        return Testaa(aika, selo, pelimaara, vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }
    
    // Jos aikaa ja yksittäistä tulosta ei annettu, oletus 90 minuuttia ja TULOS_MAARITTELEMATON
    public Testitulokset Testaa(String selo, String pelimaara, String vastustajat)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, pelimaara, vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }

    // Jos aikaa, pelimäärää ja yksittäistä tulosta ei annettu, oletus 90 minuuttia, "" ja TULOS_MAARITTELEMATON
    public Testitulokset Testaa(String selo, String vastustajat)
    {
        return Testaa(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, selo, "", vastustajat, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON);
    }
        
    public Testitulokset Testaa(String csv)
    {
        Syotetiedot syotetiedot;
        int status;
        Selopelaaja tulokset = null;

        // This will store input in text format or numbers into class Syotetiedot. Does not check numbers.
        // Note! In unit test the default thinking time is 90 minutes because it can't be taken from the form
        syotetiedot = so.SelvitaCSV(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, csv);

        if (syotetiedot == null) {
            // Possible errors:
            //   too many commas, e.g. 
            //   one comma which tells the tournamet result or single match result, so it can't be CSV format
            status = Vakiot.SYOTE_VIRHE_CSV_FORMAT;
        } else if ((status = so.TarkistaSyote(syotetiedot)) == Vakiot.SYOTE_STATUS_OK) {

            // If the input was OK, continue and calculate
            // If wasn't, then tulokset is left null and error status will be returned
            tulokset = so.SuoritaLaskenta(syotetiedot);
        }
        return new Testitulokset(status, tulokset);
    }
}
