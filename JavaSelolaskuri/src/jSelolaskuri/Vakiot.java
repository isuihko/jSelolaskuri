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
    static final int SYOTE_VIRHE_OMA_SELO           = -1;
    static final int SYOTE_VIRHE_VASTUSTAJAN_SELO   = -2;
    static final int SYOTE_VIRHE_PELIMAARA          = -3;
    static final int SYOTE_VIRHE_BUTTON_TULOS       = -4;
    static final int SYOTE_VIRHE_YKSITTAINEN_TULOS  = -5;
    static final int SYOTE_VIRHE_TURNAUKSEN_TULOS   = -6;
    
    static final int PELIMAARA_TYHJA         = -1; // OK, muilla kuin uusilla pelaajilla voi olla tyhjä
        
    // Tallenna tulokset kokonaislukuina. Laskennassa käytetään 0, 1/2 ja 1.
    // voisi käyttää myös enum
    static final int TULOS_MAARITTELEMATON  = -1;
    static final int TULOS_TAPPIO           = 0;
    static final int TULOS_TASAPELI         = 1;
    static final int TULOS_VOITTO           = 2;

    // Miettimisajat (miettimisajan pituuden mukaan nousevassa järjestyksessä)
    // voisi käyttää myös enum
    static final int MIETTIMISAIKA_ENINT_10MIN = 10; 
    static final int MIETTIMISAIKA_11_59MIN    = 11;
    static final int MIETTIMISAIKA_60_89MIN    = 60;
    static final int MIETTIMISAIKA_VAH_90MIN   = 90;     
    
    // Miettimisaikojen vaihdon takia vaihdetaan kenttien tekstejä. SELO on pitkä peli ja PELO on pikashakki.
    // Esim. "Oma SELO" ja "Oma PELO".
    public enum VaihdaMiettimisaika_enum { VAIHDA_SELOKSI, VAIHDA_PELOKSI };
}
