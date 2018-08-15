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
public class Vakiot {   
    // YLEISET VAKIOT, joilla määrätään syötteen rajat
    static final int MIN_SELO          = 1000;
    static final int MAX_SELO          = 2999;
    static final int MIN_PELIMAARA     = 0;
    static final int MAX_PELIMAARA     = 9999;

    // Vahvuuslukuarvon maksimipituus
    // Pituus 5  Sisältää ottelun tuloksen kertovan +, - tai =
    static final int MAX_PITUUS  = 5;
    static final int SELO_PITUUS = 4;

    // Kun ottelumäärä on 0-10, käytetään uuden pelaajan laskentakaavaa
    static final int MAX_PELIMAARA_UUSI_PELAAJA  = 10;
    static final int UUDEN_PELAAJAN_ALKUSELO     = 1525;
    
    // syötteen tarkastuksessa käytetyt virhestatukset eivät ole syötteiden arvoarvoalueella
    static final int SYOTE_STATUS_OK                = 0;
    static final int SYOTE_VIRHE_MIETTIMISAIKA      = -1; // Tälle ei virheilmoitusta, koska toistaiseksi ei mahdollinen
    static final int SYOTE_VIRHE_OMA_SELO           = -2;
    static final int SYOTE_VIRHE_VASTUSTAJAN_SELO   = -3;
    static final int SYOTE_VIRHE_PELIMAARA          = -4;
    static final int SYOTE_VIRHE_BUTTON_TULOS       = -5;
    static final int SYOTE_VIRHE_YKSITTAINEN_TULOS  = -6;
    static final int SYOTE_VIRHE_TURNAUKSEN_TULOS   = -7;
    static final int SYOTE_VIRHE_CSV_FORMAT         = -8;
    
    static final int PELIMAARA_TYHJA         = -1; // OK, muilla kuin uusilla pelaajilla voi olla tyhjä
        
    static final int LEIKEKIRJA_MAX_RIVINPITUUS    = 1000;
    static final int LEIKEKIRJA_MAX_RIVIMAARA      = 100;
    
    static final int TURNAUKSEN_TULOS_ANTAMATTA     = -1;


    // Tallenna kunkin ottelun tulos kokonaislukuna kahdella kerrottuna lukujen 0, 1/2 ja 1 sijaan.
    //
    // TULOS_EI_ANNETTU: kun turnauksen tuloksissa ei ole vahvuusluvun kanssa tulosta +-=
    //                   Esim. "+1624 -1700 1685 +1400" tai "1.5 1525 1441 1973 1718 1784 1660 1966"
    //
    // TULOS_MAARITTELEMATON: Kun tulosta ei ole vielä saatu tietoon. Myös lopetusehto ottelulistaa tutkittaessa.
    //
            
    // Tallenna tulokset kokonaislukuina. Laskennassa käytetään 0, 1/2 ja 1.
    enum OttelunTulos_enum {
        TULOS_EI_ANNETTU(-2),
        TULOS_MAARITTELEMATON(-1),
        TULOS_TAPPIO(0),
        TULOS_TASAPELI(1),
        TULOS_VOITTO(2);
        
        private int value;
        private OttelunTulos_enum(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    // Miettimisajat (miettimisajan pituuden mukaan nousevassa järjestyksessä)
    enum Miettimisaika_enum {
        MIETTIMISAIKA_MAARITTELEMATON(-1),
        MIETTIMISAIKA_ENINT_10MIN(10),
        MIETTIMISAIKA_11_59MIN(59),
        MIETTIMISAIKA_60_89MIN(89),
        MIETTIMISAIKA_VAH_90MIN(90);
        
        private int value;
        private Miettimisaika_enum(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }        
    
    // Miettimisaikojen vaihdon takia vaihdetaan kenttien tekstejä. SELO on pitkä peli ja PELO on pikashakki.
    // Esim. "Oma SELO" ja "Oma PELO".
    public enum VaihdaMiettimisaika_enum { VAIHDA_SELOKSI, VAIHDA_PELOKSI };
}
