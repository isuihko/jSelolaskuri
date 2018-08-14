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
    private final Vakiot.Miettimisaika_enum Miettimisaika;
    private final String AlkuperainenSelo_str;
    private final String AlkuperainenPelimaara_str;
    private final String VastustajienSelot_str;  // vastustajan/vastustajien tiedot ja tulokset
    private final Vakiot.OttelunTulos_enum OttelunTulos;

    // Tarkastuksessa merkkijonot muutettu numeroiksi, näille kolmelle getter ja setter
    private int AlkuperainenSelo;
    private int AlkuperainenPelimaara;
    private int YksiVastustajaTulosnapit;

    private static Ottelulista Ottelut;   // sis. vastustajien selot ja ottelutulokset

    /* GETTERS AND SETTERS */
    
    public Vakiot.Miettimisaika_enum getMiettimisaika() {
        return this.Miettimisaika;
    }
    public String getAlkuperainenSelo_str() {
        return this.AlkuperainenSelo_str;
    }
    public String getAlkuperainenPelimaara_str() {
        return this.AlkuperainenPelimaara_str;
    }
    public String getVastustajienSelot_str() {
        return this.VastustajienSelot_str;
    }
    public Vakiot.OttelunTulos_enum getOttelunTulos() {
        return this.OttelunTulos;
    }
    public int getAlkuperainenSelo() {
        return this.AlkuperainenSelo;
    }
    public void setAlkuperainenSelo(int value) {
        this.AlkuperainenSelo = value;
    }
    public int getAlkuperainenPelimaara() {
        return this.AlkuperainenPelimaara;
    }
    public void setAlkuperainenPelimaara(int value) {
        this.AlkuperainenPelimaara = value;
    }    
    public int getYksiVastustajaTulosnapit() {
        return this.YksiVastustajaTulosnapit;
    }
    public void setYksiVastustajaTulosnapit(int value) {
        this.YksiVastustajaTulosnapit = value;
    }
    public Ottelulista getOttelut() {
        return this.Ottelut;
    }

    // Oikeastaan kaikkea ei tarvitsisi alustaa, koska tiedot täytetään lomakkeelta
    // ks. SelolaskuriForm.cs/HaeSyotteetLomakkeelta()
    //
    // Ottelulista pitää kuitenkin luoda ja tehdään nyt kaikki muukin alustus
    //public Syotetiedot() : this(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, null, null, null, Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON)
    //{          
    //}
    public Syotetiedot(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos)
    {
        this(aika, selo, pelimaara, vastustajat, tulos, /*doTrim*/false);
    }
       
    // KÄYTETÄÄN TESTATTAESSA (UnitTest)
    // esim. Syotetiedot ottelu =
    //   new Syotetiedot(Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN, "1725", "1", "1441", Vakiot.OttelunTulos_enum.TULOS_VOITTO, /*doTrim*/true);
    //
    // Tarkistus: vastustajat voi olla null, koska vastustajanSelo_jComboBox.getSelectedItem() voi olla null
    public Syotetiedot(Vakiot.Miettimisaika_enum aika, String selo, String pelimaara, String vastustajat, Vakiot.OttelunTulos_enum tulos, boolean doTrim)
    {
        this.Miettimisaika              = aika;
        // if doTrim -> remove leading and trailing white spaces       
        this.AlkuperainenSelo_str       = doTrim ? selo.trim() : selo;
        this.AlkuperainenPelimaara_str  = doTrim ? pelimaara.trim() : pelimaara;
        if (vastustajat == null) {  // jComboBox.getSelectedItem() could had been null
            vastustajat = "";
        } else if (doTrim) {
            // poista sanojen väleistä ylimääräiset välilyönnit            
            vastustajat = vastustajat.trim().replaceAll("\\s+", " ");
        }
        this.VastustajienSelot_str      = vastustajat;
        this.OttelunTulos               = tulos;

        // Clear these too although not actually needed
        this.AlkuperainenSelo            = 0;
        this.AlkuperainenPelimaara       = 0;
        this.YksiVastustajaTulosnapit    = 0;

        // Create en empty list for matches (opponent's selo, match result)
        this.Ottelut = new Ottelulista();
    }
   
    // Uuden pelaajan laskennassa ja tulostuksissa joitain erikoistapauksia
    //
    // Tarkistetaan alkuperäisestä pelimäärästä, koska turnauksen laskenta tehdään
    // uuden pelaajan kaavalla vaikka pelimäärä laskennan aikana ylittäisikin rajan
    public boolean UudenPelaajanLaskenta()
    {
        return (this.AlkuperainenPelimaara >= Vakiot.MIN_PELIMAARA &&
                this.AlkuperainenPelimaara <= Vakiot.MAX_PELIMAARA_UUSI_PELAAJA);
    }
}
