/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;


import javax.swing.JOptionPane;
/* Käytössä:    JOptionPane.showMessageDialog(null, 
                        "ALERT MESSAGE", 
                        "TITLE", 
                        JOptionPane.WARNING_MESSAGE);
*/
import java.awt.Color; // Käytössä: setForeground(Color.RED) or (Color.BLACK)
/* font = font.deriveFont(
                Collections.singletonMap(
                    TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR));
*/
import java.awt.Image; // JOptionPane.showConfirmDialog
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Collections;

import java.awt.event.KeyEvent;  // KeyEvent.VK_UP   .VK_DOWN
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;  // Arrays.asList
import javax.swing.JFrame;


/**
 *
 * @author Ismo
 * 
 * NetBeans IDE 8.2
 * 
 *  21.11.2017   Aloitettu. Tavoitteena samanlainen ohjelma, jonka tein C#:lla aiemmin.
 *               Lomakkeet, tarkistukset ja laskenta. -> Toimii!
 *  22.11.2017   Koodin siistimistä ja optimointia, kommenttien lisäämistä,
 *               Virhetilanteiden käsittely: näytä virheellisen kentän sisältö
 *               punaisena virheilmoituksen ajan. Jos siihen oli syötetty
 *               yli 4 merkkiä, niin tyhjennä kenttä.
 * 
 *  29.11.2017   Lomakkeen muotoilua vastaamaan C#-versiota.
 *               Laita samaan ryhmään kuuluvat kentät samaan paneeliin, niin
 *               niitä on helppo järjestellä. Painonapit omaansa.
 *               Jos kenttä on tuloskenttä, niin Focusable ja Editable pois päältä.
 *               Miettimisaika väh. 90 min   Selected päälle.
 *               Kenttien Auto resizing pois päältä.
 * 
 * Aloitettu uudestaan luomalla uusi projekti ja paketin nimi jSelolaskuri.
 * Design ja koodi oli mahdollista kopioida vanhasta, joten nyt uudella projektin
 * nimellä paremmin alkuun.
 * 
 * Luotu tiedosto "seloPelaaja.java", jossa on luokka seloPelaaja.
 * Sinne siirretään kaikki pelaajaan ja laskentaan liittyvät muuttujat ja rutiinit.
 * Päämoduuliin jää vain käyttöliittymän käsittely, kenttien tarkastus ja
 * tuloksien näyttäminen.
 * 
 * Toimii!
 * 
 * 
 *  30.11.2017    Pikashakin laskennan ajaksi vaihda tekstit SELO -> PELO
 *                Myös korostetaan pikashakin tulosten syöttöohjetta
 *                  -> TextAttribute.WEIGHT_BOLD
 *                Täydennetty virheilmoituksiin puuttuneita rajoja, esim. MIN_SELO - MAX_SELO.
 *                Laskenta Enter-napilla, kun ollaan vastustajan SELO-kentässä.
 * 
 * 
 * 
 * 
 * **** ALOITETTU KOODIN JÄRJESTÄMINEN JA REFAKTOROINTI
 * **** TARKOITUS TEHDÄ SAMAT MUUTOKSET KUIN C#-VERSIOSSA
 * **** Versiopvm (ikkunan alareuna) muutettu 29.11.2017 -> 27.5.2018
 * 
 * 27.5.2018    - Vakioita varten luokka: Vakiot.java, esim. Vakiot.MIETTIMISAIKA_11_59MIN
 *              - rutiinien ym. nimiä alettu muuttaa Java-nimeämisstandardin mukaisiksi (kesken)
 *              - Ikkunan sulkeminen varmistetaan
 *              - File-menu, jonka alla Ohjeita / Laskentakaavat / Tietoja ohjelmasta / Sulje ohjelma
 *              - Oikeast virhestatukset negatiivisten numeroiden sijaan, myös tarkempia virheilmoituksia
 *              - Korjauksia laskentakaavoihin (pyöristykset, liukulukujen käyttö)
 *              - 
 *              -
 * 
 * 
 * TODO:  koodi ei ole vielä Java-koodaustyylin mukaista kaikin puolin.
 *        Aliohjelmiakin pitäisi jakaa pienempiin osiin.
 *        Tämä on ensimmäinen Java-ohjelmani ja muutan tätä vielä paljonkin
 *        aina kun keksin, miten asioita kannattaa tehdä. Mm. radiobuttonien
 *        käsittely ei vielä ole kunnossa.
 * 
 * 
 */


// Kuvia ohjelman toiminnasta:  (Esimerkkitapaukset samat kuin C#-versiossa)
//
// Ensimmäinen kuva "Selolaskuri PELOn laskenta turnauksesta.png" - pikashakin laskenta
// https://github.com/isuihko/jSelolaskuri/blob/master/JavaSelolaskuri%20PELOn%20laskenta%20turnauksesta.png
//      Miettimiaika     : enint. 10 min
//      Oma PELO         : 1996
//      Oma pelimäärä    : tyhjä
//      Vastustajan PELO : 10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684
// Tulos
//      Uusi PELO        : 2033 +37 (jos lasketaan miettimisajalla väh. 90 min, saadaan tulos 2048 +52)
//
// Toinen kuva "Selolaskuri uuden pelaajan SELO.png" - uuden pelaajan vahvuusluvun laskenta
// https://github.com/isuihko/jSelolaskuri/blob/master/JavaSelolaskuri%20uuden%20pelaajan%20SELO.png
//      Miettimisaika    : väh. 90 min.
//      Oma SELO         : 1525
//      Oma pelimäärä    : 0
//      Vastustajan SELO : +1525 +1441 -1973 +1718 -1784 -1660 -1966
// Tulos
//      Uusi SELO        : 1695 +170 (1683 - 1764)
//
// Samaan tulokseen päästään jälkimmäisessä, jos annetaan tulokset formaatissa
//      Vastustajan SELO : 3 1525 1441 1973 1718 1784 1660 1966
// Tai lähtemällä tilanteesta 1525 ja 0 ja sitten syöttämällä kukin ottelu erikseen
// (vastustajan selo ja valintanapeista tulos, esim. 1525 (x) 1 = voitto)
// ja klikkaamalla Käytä uutta SELOa jatkolaskennassa (seuraava 1441 (x) 1 = voitto jne.).



public class JavaSelolaskuri extends javax.swing.JFrame {
  
    // Luodaan shakinpelaaja, jolla tietoina mm. SELO ja pelimäärä.
    // Uudella pelaajalla SELO 1525, pelimäärä 0
    // Nämä arvot kopioidaan käyttöön, jos valitaan "Käytä uutta SELOa jatkolaskennassa"
    // ilman, että on suoritettu laskentaa sitä ennen.
    SeloPelaaja shakinpelaaja = new SeloPelaaja(1525, 0);
    
    /**
     * Creates new form JavaSelolaskuri
     */
    public JavaSelolaskuri() {
        initComponents();

        // Oma formWindowClosing-tarkistus
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // aloita oma selo-kentästä!
        nykyinenSelo_in.requestFocus();
    }
    
   
    //private boolean pelimaara_tyhja = true;
    //private int talteenUusiSelo;
    //private int talteenUusiPelimaara;

    private int nykyinenSelo;
    private int pelimaara;
    private int vastustajanSelo;
    private int pisteet;    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        OmaVahvuusluku_teksti = new javax.swing.JLabel();
        nykyinenSelo_in = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pelimaara_in = new javax.swing.JTextField();
        VastustajanVahvuusluku_teksti = new javax.swing.JLabel();
        vastustajanSelo_in = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TuloksetPistemaaranKanssa_teksti = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        laskeUusiSelo_btn = new javax.swing.JButton();
        kaytaUutta_btn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        UusiSELO_teksti = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        uusiSelo_out = new javax.swing.JTextField();
        uusi_pelimaara_out = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        seloEro_out = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        odotustulos_out = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        kerroin_out = new javax.swing.JTextField();
        uusiSelo_diff_out = new javax.swing.JTextField();
        vaihteluvali_out = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        keskivahvuus_out = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tulos_out = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        miettimisaika_vah90_btn = new javax.swing.JRadioButton();
        miettimisaika_60_89_btn = new javax.swing.JRadioButton();
        miettimisaika_11_59_btn = new javax.swing.JRadioButton();
        miettimisaika_enint10_btn = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        voittoRadioButton = new javax.swing.JRadioButton();
        tappioRadioButton = new javax.swing.JRadioButton();
        tasapeliRadioButton = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuBar = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        OmaVahvuusluku_teksti.setText("Nykyinen SELO (1000-2999)");

        jLabel3.setText("Pelimäärä (numero tai tyhjä, 0-10, jos uusi pelaaja)");

        VastustajanVahvuusluku_teksti.setText("Vastustajan SELO. Tai monta tuloksineen: +1725 -1810 =1612 (tai 1612)");

        vastustajanSelo_in.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vastustajanSelo_inKeyPressed(evt);
            }
        });

        jLabel13.setText("Montaa vahvuuslukua syötettäessä voitto +  tasapeli = tai tyhjä  ja tappio -");

        TuloksetPistemaaranKanssa_teksti.setText("Tai pistemäärä ja vastustajien SELOt: 1.5 1725 1810 1612");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(OmaVahvuusluku_teksti)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(nykyinenSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(pelimaara_in, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)))
                    .addComponent(VastustajanVahvuusluku_teksti)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(TuloksetPistemaaranKanssa_teksti)))
                    .addComponent(vastustajanSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(OmaVahvuusluku_teksti)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nykyinenSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pelimaara_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VastustajanVahvuusluku_teksti, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vastustajanSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TuloksetPistemaaranKanssa_teksti)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Shakin vahvuusluvun laskenta");

        laskeUusiSelo_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        laskeUusiSelo_btn.setText("Laske uusi SELO");
        laskeUusiSelo_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laskeUusiSelo_btnActionPerformed(evt);
            }
        });

        kaytaUutta_btn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        kaytaUutta_btn.setText("Käytä uutta SELOa jatkolaskennassa");
        kaytaUutta_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kaytaUutta_btnActionPerformed(evt);
            }
        });

        jLabel12.setText("Java 27.5.2018 Ismo Suihko github/isuihko");

        UusiSELO_teksti.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UusiSELO_teksti.setText("Uusi SELO");

        jLabel8.setText("Uusi pelimäärä");

        uusiSelo_out.setEditable(false);
        uusiSelo_out.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        uusiSelo_out.setFocusable(false);
        uusiSelo_out.setRequestFocusEnabled(false);

        uusi_pelimaara_out.setEditable(false);
        uusi_pelimaara_out.setFocusable(false);

        jLabel9.setText("Piste-ero");

        seloEro_out.setEditable(false);
        seloEro_out.setFocusable(false);

        jLabel10.setText("Odotustulos");

        odotustulos_out.setEditable(false);
        odotustulos_out.setFocusable(false);

        jLabel11.setText("Kerroin");

        kerroin_out.setEditable(false);
        kerroin_out.setFocusable(false);

        uusiSelo_diff_out.setEditable(false);
        uusiSelo_diff_out.setFocusable(false);

        vaihteluvali_out.setEditable(false);
        vaihteluvali_out.setFocusable(false);

        jLabel15.setText("Vastustajien keskivahvuus");

        keskivahvuus_out.setEditable(false);
        keskivahvuus_out.setFocusable(false);

        jLabel16.setText("Ottelun/turnauksen tulos");

        tulos_out.setEditable(false);
        tulos_out.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UusiSELO_teksti)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel8))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(uusiSelo_out, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(uusiSelo_diff_out, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(vaihteluvali_out, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 64, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(uusi_pelimaara_out, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                            .addComponent(keskivahvuus_out)
                            .addComponent(tulos_out))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(kerroin_out, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(seloEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(UusiSELO_teksti)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(vaihteluvali_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(uusiSelo_diff_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(uusiSelo_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(seloEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uusi_pelimaara_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(keskivahvuus_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(kerroin_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(tulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel4.setText("Miettimisaika:");

        miettimisaika_vah90_btn.setSelected(true);
        miettimisaika_vah90_btn.setText("väh. 90 min");
        miettimisaika_vah90_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miettimisaika_vah90_btnActionPerformed(evt);
            }
        });

        miettimisaika_60_89_btn.setText("60-89 min");
        miettimisaika_60_89_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miettimisaika_60_89_btnActionPerformed(evt);
            }
        });

        miettimisaika_11_59_btn.setText("11-59 min");
        miettimisaika_11_59_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miettimisaika_11_59_btnActionPerformed(evt);
            }
        });

        miettimisaika_enint10_btn.setText("enint. 10 min");
        miettimisaika_enint10_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miettimisaika_enint10_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miettimisaika_vah90_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miettimisaika_60_89_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miettimisaika_11_59_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(miettimisaika_enint10_btn)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(miettimisaika_vah90_btn)
                    .addComponent(miettimisaika_60_89_btn)
                    .addComponent(miettimisaika_11_59_btn)
                    .addComponent(miettimisaika_enint10_btn))
                .addContainerGap())
        );

        jLabel6.setText("Ottelun tulos:");

        voittoRadioButton.setText("1=voitto");
        voittoRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voittoRadioButtonActionPerformed(evt);
            }
        });
        voittoRadioButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                voittoRadioButtonKeyPressed(evt);
            }
        });

        tappioRadioButton.setText("0=tappio");
        tappioRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tappioRadioButtonActionPerformed(evt);
            }
        });
        tappioRadioButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tappioRadioButtonKeyPressed(evt);
            }
        });

        tasapeliRadioButton.setText("1/2=tasapeli");
        tasapeliRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tasapeliRadioButtonActionPerformed(evt);
            }
        });
        tasapeliRadioButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tasapeliRadioButtonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(voittoRadioButton)
                    .addComponent(tasapeliRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tappioRadioButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tappioRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasapeliRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(voittoRadioButton))
        );

        jMenuBar.setText("File");

        jMenuItem1.setText("Ohjeita");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuBar.add(jMenuItem1);

        jMenuItem2.setText("Laskentakaavat");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuBar.add(jMenuItem2);

        jMenuItem3.setText("Tietoja ohjelmasta");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenuBar.add(jMenuItem3);
        jMenuBar.add(jSeparator1);

        jMenuItem4.setText("Sulje ohjelma");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenuBar.add(jMenuItem4);

        jMenuBar1.add(jMenuBar);

        jMenu2.setText("Edit");

        jMenuItem5.setText("Cut, Copy & Paste not implemented yet");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel1))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(laskeUusiSelo_btn))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(kaytaUutta_btn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(3, 3, 3))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(laskeUusiSelo_btn)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(kaytaUutta_btn)
                .addGap(4, 4, 4)
                .addComponent(jLabel12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    //  KENTTIEN TARKASTUKSET

    // Nämä valintapainikkeet ovat omana ryhmänään paneelissa
    // Käynnistyksessä valitaan oletukseksi vähintään 90 min.
    // Magic numbers:   90, 60, 15, 5
    private void TarkistaMiettimisaika()
    {
        if (miettimisaika_vah90_btn.isSelected())
            shakinpelaaja.set_miettimisaika(Vakiot.MIETTIMISAIKA_VAH_90MIN);
        else if (miettimisaika_60_89_btn.isSelected())
            shakinpelaaja.set_miettimisaika(Vakiot.MIETTIMISAIKA_60_89MIN);
        else if (miettimisaika_11_59_btn.isSelected())
            shakinpelaaja.set_miettimisaika(Vakiot.MIETTIMISAIKA_11_59MIN);
        else
            shakinpelaaja.set_miettimisaika(Vakiot.MIETTIMISAIKA_ENINT_10MIN);
    }    

    // Tarkista Oma SELO -kenttä, oltava numero ja rajojen sisällä
    private int TarkistaNykyinenSelo()
    {
        int nykyinenSelo;
    
        // remove leading and trailing white spaces
        String tmpstr = nykyinenSelo_in.getText().trim();
        nykyinenSelo_in.setText(tmpstr);
        
        // onko kelvollinen numero?
        try {
            // voitaisiin käsitellä kaikki numerokentät tässä, mutta tehdään
            //  yksitellen, niin saadaan yksilöidyt virhekäsittelyt
            nykyinenSelo = Integer.parseInt(nykyinenSelo_in.getText());
            // nykyinenPelimaara = Integer.parseInt(nykyinenPelimaara_input.getText());
            // vastustajanSelo = Integer.parseInt(vastustajanSelo_input.getText());
        }
        catch (NumberFormatException e) {
            nykyinenSelo = Vakiot.VIRHE_SELO;
        }
        
        // onko kelvollinen numero?
        if (nykyinenSelo < Vakiot.MIN_SELO || nykyinenSelo > Vakiot.MAX_SELO)
        {
            nykyinenSelo_in.setForeground(Color.RED);
            JOptionPane.showMessageDialog(null,
                    "Nykyisen SELOn oltava numero " + Vakiot.MIN_SELO + " - " + Vakiot.MAX_SELO,
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);
            nykyinenSelo_in.setForeground(Color.BLACK);
            
            if (nykyinenSelo_in.getText().length() > Vakiot.MAX_PITUUS)
                nykyinenSelo_in.setText("");
            nykyinenSelo_in.requestFocus();   // siirry takaisin virheelliseen kenttään
            return Vakiot.VIRHE_SELO;
        }        
        return nykyinenSelo;
    }
            

    //
    // tarkista pelimäärä
    // Saa olla tyhjä, mutta jos annettu, oltava numero, joka on 0-9999.
    // Käytetään uuden pelaajan laskentakaavaa, jos pelimäärä on 0-10.
    //
    private int TarkistaPelimaara()
    {
        int pelimaara;
        
        // remove leading and trailing white spaces
        String tmpstr = pelimaara_in.getText().trim();
        pelimaara_in.setText(tmpstr);
        //
        // tarkista Pelimäärä -kenttä
        // Saa olla tyhjä, mutta jos annettu, oltava numero, joka on 0-9999.
        //        
        if (pelimaara_in.getText().isEmpty()) {
           return Vakiot.PELIMAARA_TYHJA; // OK;
        } else {
            try {
                pelimaara = Integer.parseInt(pelimaara_in.getText());
            }
            catch (NumberFormatException e) {
                pelimaara = Vakiot.VIRHE_PELIMAARA;  // -> virheilmoitus
            }
        }

        if (pelimaara < Vakiot.MIN_PELIMAARA || pelimaara > Vakiot.MAX_PELIMAARA) {
            pelimaara_in.setForeground(Color.RED);
            JOptionPane.showMessageDialog(null,
                    "pelimäärän oltava numero " + Vakiot.MIN_PELIMAARA + " - " + Vakiot.MAX_PELIMAARA + " tai tyhjä",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);
            pelimaara_in.setForeground(Color.BLACK);
            
            if (pelimaara_in.getText().length() > Vakiot.MAX_PITUUS)
                pelimaara_in.setText("");
            pelimaara_in.requestFocus();   // siirry takaisin virheelliseen kenttään
            return Vakiot.VIRHE_PELIMAARA;
        }
        return pelimaara;
    }
    
    // Tarkista Vastustajan SELO -kenttä, oltava numero ja rajojen sisällä
    //
    // Syöte voi olla annettu kolmella eri formaatilla:
    //  1)  1720   -> ja sitten tulos valintanapeilla
    //  2)  +1624 -1700 =1685 +1400    jossa  '+' voitto, '=' tasapeli ja '-' tappio.
    //                                 Tasapeli voidaan myös antaa ilman '='-merkkiä.
    //  3)  2,5 1624 1700 1685 1400    Eli aloitetaan kokonaispistemäärällä.
    //                                 SELOt ilman erillisiä tuloksia.
    //
    // Yhden ottelun tulos voidaan antaa kolmella tavalla:
    //   1)  1720      ja tulos erikseen valintanapeilla, esim. 1/2 tasapeli
    //   2)  =1720     tasapeli myös näin
    //   3)  0.5 1720  tai näin
    //
    
    // XXX:   tarkista_vastustajanSelo() ON LIIAN PITKÄ. JAA OSIIN!
    //
    private int TarkistaVastustajanSelo()
    {
        int vastustajanSelo = 0;
        float syotetty_tulos = 0F;
        
        // remove leading and trailing white spaces
        String tmpstr = vastustajanSelo_in.getText().trim();
        vastustajanSelo_in.setText(tmpstr);            

        if (vastustajanSelo_in.getText().isEmpty()) {
           vastustajanSelo = Vakiot.VIRHE_SELO;   // ei saa olla tyhjä -> virheilmoitus
        } else if (vastustajanSelo_in.getText().length() == Vakiot.SELO_PITUUS) {
            try {
                vastustajanSelo = Integer.parseInt(vastustajanSelo_in.getText());
            }
            catch (NumberFormatException e) {
                vastustajanSelo = Vakiot.VIRHE_SELO;  // -> virheilmoitus
            }            
        }
        else
        {         
            // kentässä voidaan antaa alussa turnauksen tulos, esim. 0.5, 2.0, 2.5, 7.5 eli saadut pisteet
            shakinpelaaja.set_syotetty_turnauksen_tulos(-1.0F);  // oletus: ei annettu

            // poista sanojen väleistä ylimääräiset välilyönnit!
            String result = vastustajanSelo_in.getText().replaceAll("\\s+", " ");
            vastustajanSelo_in.setText(result);
            
            // Nyt voidaan jakaa syöte merkkijonoihin!
            String [] selostr = vastustajanSelo_in.getText().split(" ");
            List<String> selo_lista = Arrays.asList(selostr);
            
            int selo1 = Vakiot.MIN_SELO;
            int tulos1 = 0;
            Boolean ensimmainen = true;
            Boolean turnauksen_tulos = false;

            // Tutki vastustajanSelo_in-kenttä
            // Tallenna listaan selo_lista vastustajien SELO:t ja tulokset merkkijonona            
            for (int i = 0; i < selo_lista.size(); i++) {
                String tulos = selo_lista.get(i);
                if (ensimmainen)
                {                    
                    // Tarkista, onko alussa annettu turnauksen lopputulos eli kokonaispistemäärä?
                    ensimmainen = false;
                    try {
                        syotetty_tulos = Float.parseFloat(tulos);
                        if (syotetty_tulos >= 0.0F && syotetty_tulos <= 99.5F)
                        {
                            turnauksen_tulos = true;
                            shakinpelaaja.set_syotetty_turnauksen_tulos(syotetty_tulos);

                            // alussa oli annettu turnauksen lopputulos, jatka SELOjen tarkistamista
                            // Nyt selojen on oltava ilman tulosmerkintää!
                            continue;
                        }
                    }
                    catch (NumberFormatException e) {
                        // OK, anna jatkaa ja jää kiinni myöhemmin
                    }
                }      
                
                
                // merkkijono voi alkaa merkillä '+', '=' tai '-'
                // Mutta tasapeli voidaan antaa myös ilman '='-merkkiä
                // Jos oli annettu turnauksen tulos, niin selot on syötettävä näin ilman tulosta
                if (tulos.length() == Vakiot.SELO_PITUUS)  // numero(4 merkkiä)
                {
                    try {
                        selo1 = Integer.parseInt(tulos);
                    }
                    catch (NumberFormatException e) {
                        selo1 = Vakiot.VIRHE_SELO;  // -> virheilmoitus, ei ollut numero
                        break;                    
                    }
                    tulos1 = 1;  // 1=tasapeli  HUOM! Jos tulos oli jo annettu, niin tätä ei huomioida laskuissa
                    shakinpelaaja.lista_lisaa_ottelun_tulos(selo1, tulos1);
                }
                else if (tulos.length() == Vakiot.MAX_PITUUS && turnauksen_tulos == false)  // tulos(1 merkki)+numero(4 merkkiä)
                {
                    // Erillisten tulosten antaminen hyväksytään vain, jos turnauksen
                    // lopputulosta ei oltu jo annettu
                    char c = tulos.charAt(0);
                    
                    switch (c)
                    {
                        case '+':   // voitto 1 piste, tallentetaan 2
                            tulos1 = Vakiot.TULOS_VOITTOx2;
                            break;
                        case '=':   // tasapeli 1/2 pistettä, tallentaan 1
                            tulos1 = Vakiot.TULOS_TASAPELIx2;
                            break;
                        case '-':   // tappio, tallennetaan 0
                            tulos1 = Vakiot.TULOS_TAPPIOx2;
                            break;
                        default:
                            selo1 = Vakiot.VIRHE_YKSITTAINEN_TULOS;
                            break;
                    }           

                    if (selo1 >= Vakiot.MIN_SELO)  // Vielä OK?  Selvitä sitten numero
                    {
                        // parse: ohita +=- eli aloita numerosta, oli esim. =1612
                        try {
                            selo1 = Integer.parseInt(tulos.substring(1));
                        }
                        catch (NumberFormatException e) {
                            selo1 = Vakiot.VIRHE_SELO;  // -> virheilmoitus, ei ollut numero
                            break;                    
                        }
                        shakinpelaaja.lista_lisaa_ottelun_tulos(selo1, tulos1);
                    }
                }
                else
                {
                    // pituus ei ollut SELO_PITUUS (4 esim. 1234) eikä MAX_PITUUS (5 esim. +1234)
                    selo1 = Vakiot.VIRHE_SELO; // -> virheellistä dataa
                    break;
                }

                // Oliko asetettu virhe, mutta ei vielä poistuttu foreach-loopista?
                if (selo1 < Vakiot.MIN_SELO)
                    break;

            } // for

            if (turnauksen_tulos)
            {
                // Syötteen annettu turnauksen tulos ei saa olla suurempi kuin pelaajien lukumäärä
                // Vertailu kokonaislukuina, syötetty tulos 3.5 vs 4, vertailu 7 vs 8.
                if ((int)(2*syotetty_tulos + 0.01F) > 2*shakinpelaaja.get_vastustajien_lkm_listassa())
                {
                    selo1 = Vakiot.VIRHE_TURNAUKSEN_TULOS;  // tästä oma virheilmoitus
                }
            }

            // vain virhetarkastusta varten
            vastustajanSelo = selo1;
        }

        // XXX: Fiksaa tätä virhestatuksien käsittelyä
        // VIRHEILMOITUKSET, JOS EI OLLA ARVOALUEELLA
        if (vastustajanSelo < Vakiot.MIN_SELO || vastustajanSelo > Vakiot.MAX_SELO)
        {        
            vastustajanSelo_in.setForeground(Color.RED);  // korosta punaisella

            if (vastustajanSelo == Vakiot.VIRHE_TURNAUKSEN_TULOS) {
                JOptionPane.showMessageDialog(null,
                    "Turnauksen pistemäärä (" + syotetty_tulos + ") voi olla enintään sama kuin vastustajien lukumäärä (" + shakinpelaaja.get_vastustajien_lkm_listassa() + ").",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);                           
            } else if (vastustajanSelo == Vakiot.VIRHE_YKSITTAINEN_TULOS) {
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Yksittäisen ottelun tulos voidaan antaa merkeillä +(voitto), =(tasapeli) tai -(tappio), esim. +1720. Tasapeli voidaan antaa muodossa =1720 ja 1720.",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE); 
            } else {
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Vahvuusluvun on oltava numero " + Vakiot.MIN_SELO + " - " + Vakiot.MAX_SELO,
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
            }
            
            vastustajanSelo_in.setForeground(Color.BLACK);   // palauta väri

            // Ei tyhjennetä kenttää, jotta sitä on helpompi korjata
            //              if (vastustajanSelo_in.Text.Length > MAX_PITUUS)
            //                  vastustajanSelo_in.Text = "";
            // Kentästä on kuitenkin jo poistettu ylimääräiset välilyönnit
            vastustajanSelo_in.requestFocus();
            return Vakiot.VIRHE_SELO;
        }
      
        return vastustajanSelo;
    }

    private int TarkistaOttelunTulos()
    {
        int pisteet;
    
        // Tarkista ottelun tulos -painikkeet ja tallenna niiden vaikutus
        // pisteet: tappiosta 0, tasapelistä puoli ja voitosta yksi
        //          tallentetaan kokonaisulukuna 0, 1 ja 2
        if (tappioRadioButton.isSelected()) {
            pisteet = Vakiot.TULOS_TAPPIOx2;
        } else if (tasapeliRadioButton.isSelected()) {
            pisteet = Vakiot.TULOS_TASAPELIx2;
        } else if (voittoRadioButton.isSelected()) {
            pisteet = Vakiot.TULOS_VOITTOx2;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Ottelun tulosta ei ole annettu!",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);
            tappioRadioButton.requestFocus();  // siirry ensimmäiseen radiobuttoneista
            return Vakiot.TULOS_MAAARITTELEMATON;    // -1
        }
        return pisteet;
    }
    
    // FUNKTIO: tarkistaInput
    //
    // Tarkistaa
    //      -miettimisaika-valintanapit
    //      -nykyinen SELO eli oma alkuselo
    //      -nykyinen oma pelimäärä
    //      -vastustajan SELO tai vastustajien SELOT
    //      -yhtä ottelua syötettäessä tuloksen valintanapit
    //
    // Tuloksena
    //    palautetaan parametreissa oma selo ja pelimäärä
    //    1) Jos syötetty yksi ottelu, niin palautetaan vastustajan selo ja ottelun pisteet
    //    2) Jos syötetty monen ottelun tulokset, niin selo_lista sisältää selot ja tulokset
    //
    // VIrhetilanteet:
    //    Jos jokin syötekenttä on virheellinen, annetaan virheilmoitus, siirrytään ko kenttään ja keskeytetään.
    //    Kenttiä tarkistetaan yo. järjestyksessä ja vain ensimmäisestä virheestä annetaan virheilmoitus.
    //
    private Boolean TarkistaInput()
    {    
        // ************ TARKISTA SYÖTE ************
        // XXX: laita oikea virhestatukset (vrt. C#-versio)
      
        // ENSIN TARKISTA MIETTIMISAIKA. Tässä ei voi olla virheellista tietoa.
        TarkistaMiettimisaika();

        nykyinenSelo = 0; pelimaara = 0; vastustajanSelo = 0; pisteet = 0;  // alkuarvot

        nykyinenSelo = TarkistaNykyinenSelo();
        if (nykyinenSelo == Vakiot.VIRHE_SELO)
            return false;

        pelimaara = TarkistaPelimaara();
        if (pelimaara == Vakiot.VIRHE_PELIMAARA)
            return false;

        vastustajanSelo = TarkistaVastustajanSelo();
        if (vastustajanSelo == Vakiot.VIRHE_SELO)
            return false;

        // jos ottelut ovat listassa, niin ottelutuloksen valintanapeilla ei ole merkitystä
        if (shakinpelaaja.get_vastustajien_lkm_listassa() > 0)
            return true;

        pisteet = TarkistaOttelunTulos();
        if (pisteet == Vakiot.TULOS_MAAARITTELEMATON)
            return false;        
       
        return true;
    }
    
    private void laskeUusiSelo_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laskeUusiSelo_btnActionPerformed
        if (SuoritaLaskenta())
            NaytaTulokset();
    }//GEN-LAST:event_laskeUusiSelo_btnActionPerformed

    
    
    private void kaytaUutta_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kaytaUutta_btnActionPerformed
        int selo = shakinpelaaja.get_uusiselo();
        int pelimaara = shakinpelaaja.get_uusipelimaara();

        if (selo == 0) {
            selo = shakinpelaaja.get_selo();
            pelimaara = shakinpelaaja.get_pelimaara();
        }
            
        nykyinenSelo_in.setText(Integer.toString(selo));
        if (pelimaara != Vakiot.PELIMAARA_TYHJA) {
            // vain, jos pelimaara oli annettu (muutoin on jo valmiiksi tyhjä)
            pelimaara_in.setText(Integer.toString(pelimaara));
        }
        vastustajanSelo_in.requestFocus();
    }//GEN-LAST:event_kaytaUutta_btnActionPerformed

    

    private void vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum suunta)
    {
        String tmpstr;
        String alkup, uusi;

        Font font = TuloksetPistemaaranKanssa_teksti.getFont();
        
        if (suunta == Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI)
        {
            alkup = "PELO";
            uusi = "SELO";
            
            font = font.deriveFont(
                Collections.singletonMap(
                    TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR));

            TuloksetPistemaaranKanssa_teksti.setFont(font);                        
        }
        else
        {
            alkup = "SELO";
            uusi = "PELO";

            // korosta PELO-ohje
            font = font.deriveFont(
                Collections.singletonMap(
                    TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD));

            TuloksetPistemaaranKanssa_teksti.setFont(font);
        }

        tmpstr = OmaVahvuusluku_teksti.getText().replaceAll(alkup, uusi);
        OmaVahvuusluku_teksti.setText(tmpstr);

        tmpstr = VastustajanVahvuusluku_teksti.getText().replaceAll(alkup, uusi);
        VastustajanVahvuusluku_teksti.setText(tmpstr);

        tmpstr = TuloksetPistemaaranKanssa_teksti.getText().replaceAll(alkup, uusi);
        TuloksetPistemaaranKanssa_teksti.setText(tmpstr);

        tmpstr = UusiSELO_teksti.getText().replaceAll(alkup, uusi);
        UusiSELO_teksti.setText(tmpstr);

        tmpstr = laskeUusiSelo_btn.getText().replaceAll(alkup, uusi);
        laskeUusiSelo_btn.setText(tmpstr);

        tmpstr = kaytaUutta_btn.getText().replaceAll(alkup, uusi);
        kaytaUutta_btn.setText(tmpstr);   
    }
    
    // Vaihda aktiiviseksi vähintään 90 minuuttia
    // XXX: Tämän pitäisi onnistua automaattisemminkin    
    private void miettimisaika_vah90_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miettimisaika_vah90_btnActionPerformed
        miettimisaika_vah90_btn.setSelected(true);
        miettimisaika_60_89_btn.setSelected(false);
        miettimisaika_11_59_btn.setSelected(false);
        miettimisaika_enint10_btn.setSelected(false);
        vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI);
    }//GEN-LAST:event_miettimisaika_vah90_btnActionPerformed

    // Vaihda aktiiviseksi 60-89 minuuttia
    // XXX: Tämän pitäisi onnistua automaattisemminkin
    private void miettimisaika_60_89_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miettimisaika_60_89_btnActionPerformed
        miettimisaika_vah90_btn.setSelected(false);
        miettimisaika_60_89_btn.setSelected(true);
        miettimisaika_11_59_btn.setSelected(false);
        miettimisaika_enint10_btn.setSelected(false);
        vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI);
    }//GEN-LAST:event_miettimisaika_60_89_btnActionPerformed

    // Vaihda aktiiviseksi 11-59 minuuttia
    // XXX: Tämän pitäisi onnistua automaattisemminkin
    private void miettimisaika_11_59_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miettimisaika_11_59_btnActionPerformed
        miettimisaika_vah90_btn.setSelected(false);
        miettimisaika_60_89_btn.setSelected(false);
        miettimisaika_11_59_btn.setSelected(true);
        miettimisaika_enint10_btn.setSelected(false);
        vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI);
    }//GEN-LAST:event_miettimisaika_11_59_btnActionPerformed

    // Vaihda aktiiviseksi enintään 10 minuuttia
    // XXX: Tämän pitäisi onnistua automaattisemminkin
    private void miettimisaika_enint10_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miettimisaika_enint10_btnActionPerformed
        miettimisaika_vah90_btn.setSelected(false);
        miettimisaika_60_89_btn.setSelected(false);
        miettimisaika_11_59_btn.setSelected(false);
        miettimisaika_enint10_btn.setSelected(true);
        vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_PELOKSI);
    }//GEN-LAST:event_miettimisaika_enint10_btnActionPerformed

    // FUNKTIO: suorita_laskenta()
    //
    // Laske tulokset
    // Näytä tulokset
    private Boolean SuoritaLaskenta()
    {
        // tyhjennä ottelulista!
        shakinpelaaja.lista_tyhjenna();

        // jos annettu yksi ottelu, niin vastustajan tiedot:
        //     vastustajanSelo ja pisteet
        // jos annettu useampi ottelu, niin ottelut ovat listassa ja get_vastustajien_lkm_listassa > 0
        if (TarkistaInput() == false)
            return false;

        // asettaa omat tiedot, nollaa tilastotiedot ym.
        shakinpelaaja.AloitaLaskenta(nykyinenSelo, pelimaara);

        //  *** LASKETAAN ***

        // Lasketaanko yhtä ottelua vai turnausta?
        if (shakinpelaaja.get_vastustajien_lkm_listassa() == 0) // tyhjä lista
            shakinpelaaja.PelaaOttelu(vastustajanSelo, pisteet); // pelaa yksi tietty ottelu
        else
            shakinpelaaja.PelaaOttelu();       // pelaa kaikki (turnauksen) ottelut

        return true;
    }

    void NaytaTulokset()
    {
        //  *** NÄYTÄ TULOKSIA ***

        // Laskettiinko yhtä ottelua vai turnausta?
        if (shakinpelaaja.get_vastustajien_lkm_listassa() == 0)
        {
            // tyhjä lista, joten yksi ottelu -> näytä uusi vahvuusluku, pelimäärä ym. tiedot
            String tempstr = Integer.toString(Math.abs(shakinpelaaja.get_selo_alkuperainen() - shakinpelaaja.get_viimeisin_vastustaja()));
            seloEro_out.setText(tempstr);

            odotustulos_out.setText(Float.toString(shakinpelaaja.get_odotustulos() / 100F));
            kerroin_out.setText(Integer.toString(shakinpelaaja.get_kerroin()));
            vaihteluvali_out.setText("");  // ei vaihteluväliä, koska vain yksi luku laskettu
        }
        else
        {
            // tyhjennä yksittäisen ottelun tuloskentät
            seloEro_out.setText("");
            // odotustulos näytetään, jos ei ollut uuden pelaajan laskenta
            if (shakinpelaaja.get_pelimaara() < 0 || shakinpelaaja.get_pelimaara() > 10)
                odotustulos_out.setText(Float.toString(shakinpelaaja.get_odotustuloksien_summa() / 100F));
            else
                odotustulos_out.setText("");

            // kerroin on laskettu alkuperäisestä omasta selosta
            kerroin_out.setText(Integer.toString(shakinpelaaja.get_kerroin()));

            // Valintanapeilla ei merkitystä, kun käsitellään turnausta eli valinnat pois
            tappioRadioButton.setSelected(false);
            tasapeliRadioButton.setSelected(false);
            voittoRadioButton.setSelected(false);

            // Näytä laskennan aikainen vahvuusluvun vaihteluväli
            // Jos oli annettu turnauksen tulos, niin laskenta tehdään yhdellä lauseella eikä vaihteluväliä ole
            // Vaihteluväliä ei ole myöskään, jos oli laskettu yhden ottelun tulosta
            // On vain, jos tulokset formaatissa "+1622 -1880 =1633"
            if (shakinpelaaja.get_syotetty_turnauksen_tulos() < 0 && shakinpelaaja.get_turnauksen_ottelumaara() > 1)
            {
                vaihteluvali_out.setText(
                    Integer.toString(shakinpelaaja.get_min_selo()) + " - " + Integer.toString(shakinpelaaja.get_max_selo()));
            }
            else
                vaihteluvali_out.setText("");  // muutoin siis tyhjä
        }

        // Näytä uusi vahvuusluku ja pelimäärä. Näytä myös vahvuusluvun muutos +/-NN pistettä,
        // sekä vastustajien keskivahvuus ja omat pisteet.
        uusiSelo_out.setText(Integer.toString(shakinpelaaja.get_uusiselo()));
        // How to format as with C#  .ToString("+#;-#;0")
        int i = shakinpelaaja.get_uusiselo() - shakinpelaaja.get_selo_alkuperainen();
        uusiSelo_diff_out.setText(((i > 0) ? "+" : "") + Integer.toString(i));  // jos > 0, lisää '+'
        if (shakinpelaaja.get_uusipelimaara() >= 0)
            uusi_pelimaara_out.setText(Integer.toString(shakinpelaaja.get_uusipelimaara()));
        else
            uusi_pelimaara_out.setText("");
        keskivahvuus_out.setText(Integer.toString(shakinpelaaja.get_turnauksen_keskivahvuus()));
        // Turnauksen loppupisteet / ottelujen lkm, esim.  2.5 / 6
        // C#  tulos formatoitu, näyttää esim. 2.0 / 3
        tulos_out.setText(
            Float.toString(shakinpelaaja.get_turnauksen_tulos() / 2F) + " / " +
            Integer.toString(shakinpelaaja.get_turnauksen_ottelumaara()));
    }    
    
    
    
   
    // XXX: Seuraavassa pitäisi noiden radiobuttonien valintoijen onnistua automaattisemminkin
    
    private void voittoRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voittoRadioButtonActionPerformed
        tappioRadioButton.setSelected(false);
        tasapeliRadioButton.setSelected(false);
        voittoRadioButton.setSelected(true);
        if (SuoritaLaskenta())
            NaytaTulokset();
    }//GEN-LAST:event_voittoRadioButtonActionPerformed
    
    // Suorita laskenta aina kun siirrytään tulos-painikkeeseen.
    //
    private void voittoRadioButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_voittoRadioButtonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tasapeliRadioButton.requestFocus();
            tappioRadioButton.setSelected(false);
            tasapeliRadioButton.setSelected(true);
            voittoRadioButton.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tappioRadioButton.requestFocus();
            tappioRadioButton.setSelected(true);
            tasapeliRadioButton.setSelected(false);
            voittoRadioButton.setSelected(false);
        }
        if (SuoritaLaskenta())
            NaytaTulokset();

        return;
    }//GEN-LAST:event_voittoRadioButtonKeyPressed

    private void tappioRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tappioRadioButtonActionPerformed
        tappioRadioButton.setSelected(true);
        tasapeliRadioButton.setSelected(false);
        voittoRadioButton.setSelected(false);
        if (SuoritaLaskenta())
            NaytaTulokset();

    }//GEN-LAST:event_tappioRadioButtonActionPerformed

    private void tappioRadioButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tappioRadioButtonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            voittoRadioButton.requestFocus();
            tappioRadioButton.setSelected(true);
            tasapeliRadioButton.setSelected(false);
            voittoRadioButton.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tasapeliRadioButton.requestFocus();
            tappioRadioButton.setSelected(false);
            tasapeliRadioButton.setSelected(true);
            voittoRadioButton.setSelected(false);
        }
        if (SuoritaLaskenta())
            NaytaTulokset();
    }//GEN-LAST:event_tappioRadioButtonKeyPressed

    private void tasapeliRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tasapeliRadioButtonActionPerformed
        tappioRadioButton.setSelected(false);
        tasapeliRadioButton.setSelected(true);
        voittoRadioButton.setSelected(false);
        if (SuoritaLaskenta())
            NaytaTulokset();

    }//GEN-LAST:event_tasapeliRadioButtonActionPerformed

    private void tasapeliRadioButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tasapeliRadioButtonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tappioRadioButton.requestFocus();
            tappioRadioButton.setSelected(true);
            tasapeliRadioButton.setSelected(false);
            voittoRadioButton.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            voittoRadioButton.requestFocus();
            tappioRadioButton.setSelected(false);
            tasapeliRadioButton.setSelected(false);
            voittoRadioButton.setSelected(true);
        }
        if (SuoritaLaskenta())
            NaytaTulokset();
    }//GEN-LAST:event_tasapeliRadioButtonKeyPressed

    // Suorita laskenta Enter-napilla, kun ollaan vastustajanSelo_input-kentässä
    private void vastustajanSelo_inKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vastustajanSelo_inKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (SuoritaLaskenta())
                NaytaTulokset();
        }
    }//GEN-LAST:event_vastustajanSelo_inKeyPressed

    //
    // Ikkunan sulkunappia painettu, varmista poistuminen
    // Aiemmin on kutsuttu: setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    //
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int vastaus = JOptionPane.showConfirmDialog(null, 
                "Haluatko poistua ohjelmasta?"); // Yes, No, Cancel
        if (vastaus == JOptionPane.YES_OPTION)
            JavaSelolaskuri.this.dispose();
    }//GEN-LAST:event_formWindowClosing


    // File-menu
    //   - Ohjeita
    //   - Laskentakaavat
    //   - Tietoja ohjelmasta
    //   - Sulje ohjelma
    
    // MenuItem: Ohjeita    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here
        String infoMessage = "(kesken)Ohjeita: "
                + "\r\n"
                + "Syötä oma vahvuusluku. Jos olet uusi pelaaja, niin anna oma pelimäärä 0-10, jolloin käytetään uuden pelaajan laskentakaavaa."
                + "\r\n"
                + "Syötä lisäksi joko "
                + "\r\n"
                + "  1) Vastustajan vahvuusluku ja valitse ottelun tulos 0/0,5/1nuolinäppäimillä tai "
                + "\r\n"
                + "   2) Vahvuusluvut tuloksineen, esim. +1525 =1600 -1611 +1558 tai "
                + "\r\n"
                + "  3) Turnauksen pistemäärä ja vastustajien vahvuusluvut, esim. 2.5 1525 1600 1611 1558"
                + "\r\n"
                + "Lisäksi voidaan valita miettimisaika yläreunan valintapainikkeilla."
                + "\r\n"
                + " Ohjelma sisältää sekä SELO:n (pitkä peli) että PELO:n (pikashakki) laskennat.";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Ohjeita", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    // MenuItem: Laskentakaavat
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        String infoMessage = "(kesken)Shakin vahvuusluvun laskentakaavat: http://skore.users.paivola.fi/selo.html"
                + " \r\n"
                + "Lisätietoa: http://www.shakkiliitto.fi/ ja http://www.shakki.net/cgi-bin/selo";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Laskentakaavat", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // MenuItem: Tietoja ohjelmasta
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        String infoMessage = "(kesken)Shakin vahvuusluvun laskenta, ohjelmointikieli: Java sekä myös C#/.NET/WinForms"
                + " \r\n"
                + "https://github.com/isuihko/selolaskuri";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Tietoa ohjelmasta", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // MenuItem: Sulje ohjelma
    // Sama toiminta kuin formWindowClosing(java.awt.event.WindowEvent evt)
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        int vastaus = JOptionPane.showConfirmDialog(null, 
                "Haluatko poistua ohjelmasta?"); // Yes, No, Cancel
        if (vastaus == JOptionPane.YES_OPTION)
            JavaSelolaskuri.this.dispose();        
    }//GEN-LAST:event_jMenuItem4ActionPerformed
   
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaSelolaskuri().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OmaVahvuusluku_teksti;
    private javax.swing.JLabel TuloksetPistemaaranKanssa_teksti;
    private javax.swing.JLabel UusiSELO_teksti;
    private javax.swing.JLabel VastustajanVahvuusluku_teksti;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenuBar;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton kaytaUutta_btn;
    private javax.swing.JTextField kerroin_out;
    private javax.swing.JTextField keskivahvuus_out;
    private javax.swing.JButton laskeUusiSelo_btn;
    private javax.swing.JRadioButton miettimisaika_11_59_btn;
    private javax.swing.JRadioButton miettimisaika_60_89_btn;
    private javax.swing.JRadioButton miettimisaika_enint10_btn;
    private javax.swing.JRadioButton miettimisaika_vah90_btn;
    private javax.swing.JTextField nykyinenSelo_in;
    private javax.swing.JTextField odotustulos_out;
    private javax.swing.JTextField pelimaara_in;
    private javax.swing.JTextField seloEro_out;
    private javax.swing.JRadioButton tappioRadioButton;
    private javax.swing.JRadioButton tasapeliRadioButton;
    private javax.swing.JTextField tulos_out;
    private javax.swing.JTextField uusiSelo_diff_out;
    private javax.swing.JTextField uusiSelo_out;
    private javax.swing.JTextField uusi_pelimaara_out;
    private javax.swing.JTextField vaihteluvali_out;
    private javax.swing.JTextField vastustajanSelo_in;
    private javax.swing.JRadioButton voittoRadioButton;
    // End of variables declaration//GEN-END:variables

}


