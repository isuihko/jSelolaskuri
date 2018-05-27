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
    // Käytetään pääasiassa tarkista_input() -rutiinissa
    static final int MIN_SELO          = 1000;
    static final int MAX_SELO          = 2999;
    static final int MIN_PELIMAARA     = 0;
    static final int MAX_PELIMAARA     = 9999;
    static final int MAX_UUSI_PELAAJA  = 10;
        
    // syötteen tarkastuksessa käytetyt statukset, eivät ole syötteiden arvoarvoalueella
    static final int VIRHE_SELO              = -1;
    static final int VIRHE_PELIMAARA         = -2;
    static final int VIRHE_TULOS             = -3;
    static final int VIRHE_YKSITTAINEN_TULOS = -4;
    static final int VIRHE_TURNAUKSEN_TULOS  = -5;
    
    static final int PELIMAARA_TYHJA         = -1; // OK, ei pakko antaa
    
    // input-kentän syötteen maksimipituus. Tarkistetaan virhetilanteissa
    // ja jos merkkejä yli tuon, niin tyhjennetään kenttä
    static final int MAX_PITUUS  = 5; 
    static final int SELO_PITUUS = 4;
    
    // Tallenna tulokset kokonaislukuina, oikeasti 0, 1/2 ja 1
    // voisi käyttää myös enum
    static final int TULOS_MAAARITTELEMATON = -1;
    static final int TULOS_TAPPIOx2         = 0;
    static final int TULOS_TASAPELIx2       = 1;
    static final int TULOS_VOITTOx2         = 2;

    // Miettimisajat (numeroilla ei merkitystä, kunhan menevät miettimisajan mukaan suuruusjärjestyksessä)
    // voisi käyttää myös enum
    static final int MIETTIMISAIKA_ENINT_10MIN = 10; 
    static final int MIETTIMISAIKA_11_59MIN    = 11;
    static final int MIETTIMISAIKA_60_89MIN    = 60;
    static final int MIETTIMISAIKA_VAH_90MIN   = 90;     
    
    // Miettimisaikojen vaihdon takia voidaan joutua vaihtamaan tekstejä, esim. Laske SELO / Laske PELO
    public enum VaihdaMiettimisaika_enum { VAIHDA_SELOKSI, VAIHDA_PELOKSI };
}

