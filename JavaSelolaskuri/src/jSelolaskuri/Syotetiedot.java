/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

/**
 *
 * @author Ismo
 */
public class Syotetiedot {
    // Alkuperäiset syötteet (sama järjestys kuin näytöllä)
    public int  Miettimisaika;
    public String AlkuperainenSelo_str;
    public String AlkuperainenPelimaara_str;
    public String VastustajienSelot_str;  // vastustajan/vastustajien tiedot ja tulokset
    public int OttelunTulos;

    // Tarkastuksessa merkkijonot muutettu numeroiksi
    public int AlkuperainenSelo;
    public int AlkuperainenPelimaara;
    public int YksiVastustajaTulosnapit;

    public Ottelulista Ottelut;   // sis. vastustajien selot ja ottelutulokset


    // Oikeastaan kaikkea ei tarvitsisi alustaa, koska tiedot täytetään lomakkeelta
    // ks. SelolaskuriForm.cs/HaeSyotteetLomakkeelta()
    //
    // Ottelulista pitää kuitenkin luoda ja tehdään nyt kaikki muukin alustus
    //public Syotetiedot() : this(Vakiot.MIETTIMISAIKA_VAH_90MIN, null, null, null, Vakiot.TULOS_MAARITTELEMATON)
    //{          
    //}

    // KÄYTETÄÄN TESTATTAESSA (UnitTest)
    // esim. Syotetiedot ottelu =
    //   new Syotetiedot(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, "1725", "1", "1441", Vakiot.OttelunTulos_enum.TULOS_VOITTO);
    public Syotetiedot(int aika, String selo, String pelimaara, String vastustajat, int tulos)
    {
        Miettimisaika              = aika;
        AlkuperainenSelo_str       = selo;
        AlkuperainenPelimaara_str  = pelimaara;
        VastustajienSelot_str      = vastustajat;
        OttelunTulos               = tulos;

        // Clear these too although not actually needed
        AlkuperainenSelo            = 0;
        AlkuperainenPelimaara       = 0;
        YksiVastustajaTulosnapit    = 0;

        // Create en empty list for matches (opponent's selo, match result)
        Ottelut = new Ottelulista();
    }

    // Uuden pelaajan laskennassa ja tulostuksissa joitain erikoistapauksia
    //
    // Tarkistetaan alkuperäisestä pelimäärästä, koska turnauksen laskenta tehdään
    // uuden pelaajan kaavalla vaikka pelimäärä laskennan aikana ylittäisikin rajan
    public boolean UudenPelaajanLaskenta()
    {
        return (AlkuperainenPelimaara >= Vakiot.MIN_PELIMAARA &&
                AlkuperainenPelimaara <= Vakiot.MAX_PELIMAARA_UUSI_PELAAJA);
    }
}
