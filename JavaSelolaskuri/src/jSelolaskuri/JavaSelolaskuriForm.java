/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;


import javax.swing.JOptionPane; // JOptionPane.showMessageDialog
import java.awt.Color; // setForeground(Color.RED) or (Color.BLACK)
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.awt.event.KeyEvent;  // KeyEvent.VK_UP   .VK_DOWN
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
 * 
 * 31.7.2018    - Tehty uusi versio, jossa käytetty pohjana C#-versiota ja saatu siihen tehnyt
 *                laskentakaavakorjaukset mukaan
 *              - Versiopvm 31.7.2018 -> GitHub jSelolaskuri
 * 
 * 1.8.2018     - Luokkien Syotetiedot, Ottelu ja Ottelulista kenttiä public -> private
 *                ja lisätty niille tarvittavat Getter/Setter-rutiinit
 *              - Järjestetty näytön paneeleja (Design), jotta kentät tulisivat paremmille paikoille
 *              - Versiopvm 1.8.2018 -> GitHub jSelolaskuri
 * 
 * TODO:  koodi ei ole vielä Java-koodaustyylin mukaista kaikin puolin.
 *        Tämä on ensimmäinen Java-ohjelmani ja muutan tätä vielä paljonkin
 *        aina kun keksin, miten asioita kannattaa tehdä. Mm. radiobuttonien
 *        käsittely ei vielä ole kunnossa.
 *      - ADD: Unit Testing
 * 
 */


// Esimerkkitapauksia:
//
// Pikashakin laskenta turnauksesta
//      Miettimiaika     : enint. 10 min
//      Oma PELO         : 1996
//      Oma pelimäärä    : tyhjä
//      Vastustajan PELO : 10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684
// Tulos
//      Uusi PELO        : 2033 +37 (jos lasketaan miettimisajalla väh. 90 min, saadaan tulos 2050 +54)
//
// Uuden pelaajan vahvuusluvun laskenta turnauksesta
//      Miettimisaika    : väh. 90 min.
//      Oma SELO         : 1525
//      Oma pelimäärä    : 0
//      Vastustajan SELO : +1525 +1441 -1973 +1718 -1784 -1660 -1966
// Tulos
//      Uusi SELO        : 1695 +170 vaihteluväli 1683 - 1764
//
// Samaan tulokseen päästään jälkimmäisessä, jos annetaan tulokset formaatissa
//      Vastustajan SELO : 3 1525 1441 1973 1718 1784 1660 1966
// Tai lähtemällä tilanteesta 1525 ja 0 ja sitten syöttämällä kukin ottelu erikseen
// (vastustajan selo ja valintanapeista tulos, esim. 1525 (x) 1 = voitto)
// ja klikkaamalla Käytä uutta SELOa jatkolaskennassa (seuraava 1441 (x) 1 = voitto jne.).
// 


public class JavaSelolaskuriForm extends javax.swing.JFrame {
     
    SelolaskuriOperations so = new SelolaskuriOperations();
       
    /**
     * Creates new form JavaSelolaskuri
     */
    public JavaSelolaskuriForm() {
        initComponents();

        // Meillä on oma formWindowClosing-käsittely
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // aloita oma selo-kentästä!
        selo_in.requestFocus();
    }
    
      
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
        selo_in = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        pelimaara_in = new javax.swing.JTextField();
        VastustajanVahvuusluku_teksti = new javax.swing.JLabel();
        vastustajanSelo_in = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TuloksetPistemaaranKanssa_teksti = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        laskeUusiSelo_btn = new javax.swing.JButton();
        kaytaUutta_btn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        tulos_out = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        keskivahvuus_out = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        miettimisaika_vah90_btn = new javax.swing.JRadioButton();
        miettimisaika_60_89_btn = new javax.swing.JRadioButton();
        miettimisaika_11_59_btn = new javax.swing.JRadioButton();
        miettimisaika_enint10_btn = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        tulosTappio_btn = new javax.swing.JRadioButton();
        tulosVoitto_btn = new javax.swing.JRadioButton();
        tulosTasapeli_btn = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        odotustulos_out = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pisteEro_out = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        UusiSELO_teksti = new javax.swing.JLabel();
        uusiSelo_out = new javax.swing.JTextField();
        selomuutos_out = new javax.swing.JTextField();
        vaihteluvali_out = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        uusi_pelimaara_out = new javax.swing.JTextField();
        UudenPelaajanLaskenta_txt = new javax.swing.JLabel();
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

        OmaVahvuusluku_teksti.setText("Nykyinen SELO");

        jLabel3.setText("Oma pelimäärä (tyhjä, numero, uudella pelaajalla 0-10)");

        VastustajanVahvuusluku_teksti.setText("Vastustajat:  SELO / SELOt tuloksineen / turnauksen pistemäärä ja SELOt");

        vastustajanSelo_in.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                vastustajanSelo_inKeyPressed(evt);
            }
        });

        jLabel13.setText("Esim. +1725 -1910 =1812 (tai 1812), jossa + voitto, = tai tyhjä tasapeli, - tappio");

        TuloksetPistemaaranKanssa_teksti.setText("Tai pistemäärä ja vastustajien SELOt: 1.5 1725 1910 1812");

        jLabel5.setText("Jos annettu yksi vahvuusluku numerona (esim. 1720), niin tuloksen valinta:");

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
                                .addComponent(selo_in, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(pelimaara_in, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)))
                    .addComponent(VastustajanVahvuusluku_teksti)
                    .addComponent(vastustajanSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(TuloksetPistemaaranKanssa_teksti)
                    .addComponent(jLabel13))
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
                    .addComponent(selo_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pelimaara_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(VastustajanVahvuusluku_teksti, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vastustajanSelo_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TuloksetPistemaaranKanssa_teksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(6, 6, 6))
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

        jLabel12.setText("Java 1.8.2018 github.com/isuihko/jSelolaskuri");

        jLabel15.setText("Vastustajien keskivahvuus");

        tulos_out.setEditable(false);
        tulos_out.setFocusable(false);

        jLabel16.setText("Ottelun/turnauksen tulos");

        keskivahvuus_out.setEditable(false);
        keskivahvuus_out.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tulos_out, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(keskivahvuus_out))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(keskivahvuus_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        tulosTappio_btn.setText("0 = tappio");
        tulosTappio_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tulosTappio_btnActionPerformed(evt);
            }
        });
        tulosTappio_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tulosTappio_btnKeyPressed(evt);
            }
        });

        tulosVoitto_btn.setText("1 = voitto");
        tulosVoitto_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tulosVoitto_btnActionPerformed(evt);
            }
        });
        tulosVoitto_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tulosVoitto_btnKeyPressed(evt);
            }
        });

        tulosTasapeli_btn.setText("1/2 = tasapeli");
        tulosTasapeli_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tulosTasapeli_btnActionPerformed(evt);
            }
        });
        tulosTasapeli_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tulosTasapeli_btnKeyPressed(evt);
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
                    .addComponent(tulosTappio_btn)
                    .addComponent(tulosTasapeli_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tulosVoitto_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tulosVoitto_btn)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tulosTasapeli_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tulosTappio_btn))
        );

        jLabel10.setText("Odotustulos");

        odotustulos_out.setEditable(false);
        odotustulos_out.setFocusable(false);

        jLabel9.setText("Piste-ero");

        pisteEro_out.setEditable(false);
        pisteEro_out.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pisteEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(pisteEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        UusiSELO_teksti.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        UusiSELO_teksti.setText("Uusi SELO");

        uusiSelo_out.setEditable(false);
        uusiSelo_out.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        uusiSelo_out.setFocusable(false);
        uusiSelo_out.setRequestFocusEnabled(false);

        selomuutos_out.setEditable(false);
        selomuutos_out.setFocusable(false);

        vaihteluvali_out.setEditable(false);
        vaihteluvali_out.setFocusable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(UusiSELO_teksti)
                .addGap(64, 64, 64)
                .addComponent(uusiSelo_out, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(selomuutos_out, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vaihteluvali_out, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(UusiSELO_teksti)
                    .addComponent(uusiSelo_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selomuutos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vaihteluvali_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel8.setText("Uusi pelimäärä");

        uusi_pelimaara_out.setEditable(false);
        uusi_pelimaara_out.setFocusable(false);

        UudenPelaajanLaskenta_txt.setText("Uuden pelaajan laskenta");
        UudenPelaajanLaskenta_txt.setFocusable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(76, 76, 76)
                .addComponent(uusi_pelimaara_out, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(UudenPelaajanLaskenta_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uusi_pelimaara_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(UudenPelaajanLaskenta_txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(3, 3, 3))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel1))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(laskeUusiSelo_btn))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(kaytaUutta_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(kaytaUutta_btn)
                .addGap(4, 4, 4)
                .addComponent(jLabel12))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

           
    // --------------------------------------------------------------------------------
    // LOMAKKEEN KENTTIEN ARVOJEN HAKEMINEN
    // --------------------------------------------------------------------------------
    // Tietoja ei tarkisteta tässä
    // Miettimisaika on aina kelvollinen, mutta merkkijonot eivät välttämättä
    // Myös ottelun tulos voi/saa olla antamatta, joten silloin se on määrittelemätön
    private Syotetiedot HaeSyotteetLomakkeelta()
    {
        // Remove all leading and trailing white spaces from the form
        selo_in.setText(selo_in.getText().trim());
        pelimaara_in.setText(pelimaara_in.getText().trim());
        // poista sanojen väleistä ylimääräiset välilyönnit!
        vastustajanSelo_in.setText(vastustajanSelo_in.getText().trim().replaceAll("\\s+", " "));
        
        return new Syotetiedot(HaeMiettimisaika(), selo_in.getText(), pelimaara_in.getText(), vastustajanSelo_in.getText(), HaeOttelunTulos());
    }

    // Nämä miettimisajan valintapainikkeet ovat omana ryhmänään paneelissa
    // Aina on joku valittuna, joten ei voi olla virhetilannetta.
    private int HaeMiettimisaika()
    {
        int valinta;
        
        if (miettimisaika_vah90_btn.isSelected())
            valinta = Vakiot.MIETTIMISAIKA_VAH_90MIN;
        else if (miettimisaika_60_89_btn.isSelected())
            valinta = Vakiot.MIETTIMISAIKA_60_89MIN;
        else if (miettimisaika_11_59_btn.isSelected())
            valinta = Vakiot.MIETTIMISAIKA_11_59MIN;
        else
            valinta = Vakiot.MIETTIMISAIKA_ENINT_10MIN;
        return valinta;
    }    

    // Ottelun tulos voi olla valittu radiobuttoneilla tai valitsematta (MAARITTELEMATON)
    private int HaeOttelunTulos()
    {
        int valinta;
        if (tulosVoitto_btn.isSelected())
            valinta = Vakiot.TULOS_VOITTO;
        else if (tulosTasapeli_btn.isSelected())
            valinta = Vakiot.TULOS_TASAPELI;
        else if (tulosTappio_btn.isSelected())
            valinta = Vakiot.TULOS_TAPPIO;
        else 
            valinta = Vakiot.TULOS_MAARITTELEMATON;
        
        return valinta;
    }
    
     
    // --------------------------------------------------------------------------------
    // Painikkeiden toiminta
    // --------------------------------------------------------------------------------
    //    Laske uusi SELO  (pikashakissa Laske uusi PELO)
    //    Käytä uutta SELOa jatkolaskennassa

    // Suoritetaan laskenta -button
    private void laskeUusiSelo_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laskeUusiSelo_btnActionPerformed
        LaskeOttelunTulosLomakkeelta();
        // XXX: TODO: tallenna kelvolliset syötteet comboboxiin, kuten C#-versiossa       
    }//GEN-LAST:event_laskeUusiSelo_btnActionPerformed
   
    
    // Kopioi lasketun uuden selon ja mahdollisen pelimäärän käytettäväksi, jotta
    // laskentaa voidaan jatkaa helposti syöttämällä uusi vastustajan selo ja ottelun tulos.
    // Ja sitten siirrytään valmiiksi vastustajan SELO-kenttään.
    //
    // Jos painiketta oli painettu ennen ensimmäistäkään laskentaa, niin
    // saadaan pelaajaa luodessa käytetyt alkuarvot SELO 1525 ja pelimääärä 0.
    private void kaytaUutta_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kaytaUutta_btnActionPerformed
        int selo1 = so.HaeViimeksiLaskettuSelo();
        int pelimaara1 = so.HaeViimeksiLaskettuPelimaara();
                   
        selo_in.setText(Integer.toString(selo1));
        
        if (pelimaara1 != Vakiot.PELIMAARA_TYHJA) {
            // vain, jos pelimaara oli annettu (muutoin on jo valmiiksi tyhjä)
            pelimaara_in.setText(Integer.toString(pelimaara1));
        }
        vastustajanSelo_in.requestFocus();
    }//GEN-LAST:event_kaytaUutta_btnActionPerformed

        
    // Näyttää virheen mukaisen ilmoituksen sekä siirtää kursorin kenttään, jossa virhe
    // Virheellisen kentän arvo näytetään punaisella kunnes ilmoitusikkuna kuitataan
    private void NaytaVirheilmoitus(int virhestatus)
    {
        // String message;
        
        switch (virhestatus) {
            case Vakiot.SYOTE_STATUS_OK:
                break;
  
            case Vakiot.SYOTE_VIRHE_OMA_SELO:
                selo_in.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                        "VIRHE: Nykyisen SELOn oltava numero " + Vakiot.MIN_SELO + " - " + Vakiot.MAX_SELO + ".",
                        "VIRHE",
                        JOptionPane.WARNING_MESSAGE);
                selo_in.setForeground(Color.BLACK);

                if (selo_in.getText().length() > Vakiot.MAX_PITUUS)
                    selo_in.setText("");
                selo_in.requestFocus();   // siirry takaisin virheelliseen kenttään                
                break;
                
            case Vakiot.SYOTE_VIRHE_VASTUSTAJAN_SELO:
                vastustajanSelo_in.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Vastustajan vahvuusluvun on oltava numero " + Vakiot.MIN_SELO + " - " + Vakiot.MAX_SELO,
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_in.setForeground(Color.BLACK);   // palauta väri                
                vastustajanSelo_in.requestFocus();
                break;
                
            case Vakiot.SYOTE_VIRHE_PELIMAARA:
                pelimaara_in.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                        "pelimäärän oltava numero " + Vakiot.MIN_PELIMAARA + " - " + Vakiot.MAX_PELIMAARA + " tai tyhjä",
                        "VIRHE",
                        JOptionPane.WARNING_MESSAGE);
                pelimaara_in.setForeground(Color.BLACK); 
                pelimaara_in.requestFocus();
                break;
                
            // tulos puuttuu painonapeista, siirry ensimmäiseen valintanapeista
            case Vakiot.SYOTE_VIRHE_BUTTON_TULOS:  
                JOptionPane.showMessageDialog(null,
                        "Ottelun tulosta ei valittu!",
                        "VIRHE",
                        JOptionPane.WARNING_MESSAGE);                
                tulosVoitto_btn.requestFocus();  // ensimmäinen tulos-painikkeista
                break;      
                
            case Vakiot.SYOTE_VIRHE_YKSITTAINEN_TULOS:   
                vastustajanSelo_in.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Yksittäisen ottelun tulos voidaan antaa merkeillä +(voitto), =(tasapeli) tai -(tappio), esim. +1720. Tasapeli voidaan antaa muodossa =1720 ja 1720.",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_in.setForeground(Color.BLACK);
                vastustajanSelo_in.requestFocus();
                break;
                
            case Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS:
                vastustajanSelo_in.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Turnauksen pistemäärä voi olla enintään sama kuin vastustajien lukumäärä.",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_in.setForeground(Color.BLACK);
                vastustajanSelo_in.requestFocus();                
                break;
        } 
    }

    
    // --------------------------------------------------------------------------------
    // Lomakkeen tietojen käsittely, laskenta ja tuloksien näyttäminen
    // --------------------------------------------------------------------------------
    // Haetaan tiedot lomakkeelta
    // Tarkistataan ne
    // Lasketaan uusi vahvuusluku ja ottelumäärä sekä muut tulokset
    // Näytetään tulokset
    //
    private boolean LaskeOttelunTulosLomakkeelta()
    {
        boolean status = true;
        int tulos;
        
        // hakee syötetyt tekstit ja tehdyt valinnat, ei virhetarkastusta
        Syotetiedot syotteet = HaeSyotteetLomakkeelta();

        // Virhetarkastus ja laskenta erillisessä luokassa SelolaskuriOperations,
        // jotta niitä voidaan kutsua myös yksikkötestauksesta
        if ((tulos = so.TarkistaSyote(syotteet)) != Vakiot.SYOTE_STATUS_OK) {
            NaytaVirheilmoitus(tulos);
            status = false;
        } else {
            Selopelaaja tulokset = so.SuoritaLaskenta(syotteet);
                       
            // Tuloksissa näytetään myös selo-muutos
            NaytaTulokset(tulokset);
        }
        
        return status;
    }

    
    // Lasketut tulokset lomakkeelle
    //   Uusi vahvuusluku ja sen muutos +/- NN pistettä
    //   uusi pelimäärä tai tyhjä
    //   piste-ero
    private void NaytaTulokset(Selopelaaja tulokset)
    {
        uusiSelo_out.setText(Integer.toString(tulokset.UusiSelo));
        
        int i = tulokset.UusiSelo - tulokset.AlkuperainenSelo();
        selomuutos_out.setText(((i > 0) ? "+" : "") + Integer.toString(i));  // jos > 0, lisää '+'   sama kuin C# .ToString("+#;-#;0")
       
        //   uusi pelimäärä tai tyhjä
        if (tulokset.UusiPelimaara >= 0)
            uusi_pelimaara_out.setText(Integer.toString(tulokset.UusiPelimaara));
        else
            uusi_pelimaara_out.setText("");
        
        // piste-ero turnauksen keskivahvuuteen nähden
        String tempstr = Integer.toString(Math.abs(tulokset.AlkuperainenSelo() - tulokset.TurnauksenKeskivahvuus));
        pisteEro_out.setText(tempstr);
        
        // Vastustajien vahvuuslukujen keskiarvo
        keskivahvuus_out.setText(Integer.toString(tulokset.TurnauksenKeskivahvuus));
        
        // Turnauksen loppupisteet yhdellä desimaalilla / ottelujen lkm, esim.  2.5 / 6 tai 2.0 / 6
        tulos_out.setText(
                Float.toString(tulokset.TurnauksenTulos / 2F) + " / " + Integer.toString(tulokset.VastustajienLkm));
               
        // Vahvuusluku on voinut vaihdella laskennan edetessä, jos vastustajat ovat olleet formaatissa "+1622 -1880 =1633"
        // Vaihteluväliä ei ole, jos laskenta on tehty yhdellä lausekkeella tai on ollut vain yksi vastustaja
        if (tulokset.MinSelo < tulokset.MaxSelo)
            vaihteluvali_out.setText(Integer.toString(tulokset.MinSelo) + " - " + Integer.toString(tulokset.MaxSelo));
        else
            vaihteluvali_out.setText("");
       
        // Odotustulosta tai sen summaa ei näytetä uudelle pelaajalle, koska vahvuusluku on vielä provisional
        // Uuden pelaajan laskennasta annetaan ilmoitusteksti
        if (tulokset.UudenPelaajanLaskenta()) {
            odotustulos_out.setText("");
            UudenPelaajanLaskenta_txt.setVisible(true);
        } else {
            odotustulos_out.setText(Float.toString(tulokset.Odotustulos / 100F)); // not Double.toString
            UudenPelaajanLaskenta_txt.setVisible(false);
        }

        
        // kerroin on laskettu alkuperäisestä omasta selosta (laskennan aputieto)
        //XXX: Poistettu lomakkeelta kerroin_out.Text = tulokset.Kerroin.ToString();

        // Jos ei käytetty tulospainikkeita, niin tuloksen valintanapit varmuuden vuoksi pois päältä
        // Tulospainikkeita käytettäessä yksi vastustajan selo, eikä tulosta annettu muodossa "1.0 1434" tai "+1434"
        if (!tulokset.KaytettiinkoTulospainikkeita()) {
            tulosTappio_btn.setSelected(false);
            tulosTasapeli_btn.setSelected(false);
            tulosVoitto_btn.setSelected(false);
        }
    }
    
    
    // --------------------------------------------------------------------------------
    // Miettimisajan valinnan mukaan tekstit: SELO (pidempi peli) vai PELO (pikashakki)
    // --------------------------------------------------------------------------------
    private void vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum suunta)
    {
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

        OmaVahvuusluku_teksti.setText(OmaVahvuusluku_teksti.getText().replaceAll(alkup, uusi));
        VastustajanVahvuusluku_teksti.setText(VastustajanVahvuusluku_teksti.getText().replaceAll(alkup, uusi));
        TuloksetPistemaaranKanssa_teksti.setText(TuloksetPistemaaranKanssa_teksti.getText().replaceAll(alkup, uusi));
        UusiSELO_teksti.setText(UusiSELO_teksti.getText().replaceAll(alkup, uusi));
        laskeUusiSelo_btn.setText(laskeUusiSelo_btn.getText().replaceAll(alkup, uusi));
        kaytaUutta_btn.setText(kaytaUutta_btn.getText().replaceAll(alkup, uusi));
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

    
    // --------------------------------------------------------------------------------
    // Ottelun tulos-buttonit
    // --------------------------------------------------------------------------------
    // Suorita laskenta aina kun siirrytään tulos-painikkeeseen.
    // Ennen laskentaa asetetaan nykyinen painike valituksi, koska sitä ei
    // muutoin vielä oltu valittu kenttään siirryttäessä.
    //
    // Jos tässä vaiheessa ei ole vielä annettu SELOja, tulee virheilmoitus
    // sekä siirrytään SELO-kenttään.
    //    
    // XXX: Seuraavassa pitäisi noiden radiobuttonien valintojen onnistua automaattisemminkin
    //
    private void tulosTappio_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tulosTappio_btnActionPerformed
        tulosVoitto_btn.setSelected(false);
        tulosTasapeli_btn.setSelected(false);
        tulosTappio_btn.setSelected(true);
        LaskeOttelunTulosLomakkeelta();
    }//GEN-LAST:event_tulosTappio_btnActionPerformed
    
    // Suorita laskenta aina kun siirrytään tulos-painikkeeseen.
    //
    private void tulosTappio_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tulosTappio_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tulosTasapeli_btn.requestFocus();
            tulosVoitto_btn.setSelected(false);
            tulosTasapeli_btn.setSelected(true);
            tulosTappio_btn.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tulosVoitto_btn.requestFocus();
            tulosVoitto_btn.setSelected(true);
            tulosTasapeli_btn.setSelected(false);
            tulosTappio_btn.setSelected(false);
        }
        LaskeOttelunTulosLomakkeelta();
    }//GEN-LAST:event_tulosTappio_btnKeyPressed

    private void tulosVoitto_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tulosVoitto_btnActionPerformed
        tulosVoitto_btn.setSelected(true);
        tulosTasapeli_btn.setSelected(false);
        tulosTappio_btn.setSelected(false);
        LaskeOttelunTulosLomakkeelta();

    }//GEN-LAST:event_tulosVoitto_btnActionPerformed

    private void tulosVoitto_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tulosVoitto_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tulosTappio_btn.requestFocus();
            tulosVoitto_btn.setSelected(true);
            tulosTasapeli_btn.setSelected(false);
            tulosTappio_btn.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tulosTasapeli_btn.requestFocus();
            tulosVoitto_btn.setSelected(false);
            tulosTasapeli_btn.setSelected(true);
            tulosTappio_btn.setSelected(false);
        }
        LaskeOttelunTulosLomakkeelta();
    }//GEN-LAST:event_tulosVoitto_btnKeyPressed

    private void tulosTasapeli_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tulosTasapeli_btnActionPerformed
        tulosVoitto_btn.setSelected(false);
        tulosTasapeli_btn.setSelected(true);
        tulosTappio_btn.setSelected(false);
        LaskeOttelunTulosLomakkeelta();

    }//GEN-LAST:event_tulosTasapeli_btnActionPerformed

    private void tulosTasapeli_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tulosTasapeli_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tulosVoitto_btn.requestFocus();
            tulosVoitto_btn.setSelected(true);
            tulosTasapeli_btn.setSelected(false);
            tulosTappio_btn.setSelected(false);
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tulosTappio_btn.requestFocus();
            tulosVoitto_btn.setSelected(false);
            tulosTasapeli_btn.setSelected(false);
            tulosTappio_btn.setSelected(true);
        }
        LaskeOttelunTulosLomakkeelta();
    }//GEN-LAST:event_tulosTasapeli_btnKeyPressed

    // --------------------------------------------------------------------------------
    // Kun painettu Enter vastustajan SELO-kentässä, suoritetaan laskenta
    // --------------------------------------------------------------------------------
    private void vastustajanSelo_inKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_vastustajanSelo_inKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LaskeOttelunTulosLomakkeelta();
            // XXX: TODO: tallenna kelvolliset syötteet comboboxiin, kuten C#-versiossa
        }
    }//GEN-LAST:event_vastustajanSelo_inKeyPressed


    // --------------------------------------------------------------------------------
    // File-Menu
    // --------------------------------------------------------------------------------
    //    Ohjeita
    //    Laskentakaavat
    //    Tietoa ohjelmasta
    //    Sulje ohjelma
    
    // MenuItem: Ohjeita    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here
        String infoMessage = "Shakin vahvuusluvun laskenta SELO ja PELO"
                + "\r\n"
                + "\r\n" + "Annettavat tiedot:"
                + "\r\n"
                + "\r\n" + "-Miettimisaika. Pitkä peli (väh. 90 minuuttia) on oletuksena. Jos valitset enint. 10 minuuttia, lasketaan pikashakin vahvuuslukua (PELO)"
                + "\r\n" + "-Oma vahvuusluku"
                + "\r\n" + "-Oma pelimäärä, joka tarvitaan vain jos olet pelannut enintään 10 peliä. Tällöin käytetään uuden pelaajan laskentakaavaa."
                + "\r\n" + "-Vastustajien vahvuusluvut ja tulokset jollakin kolmesta tavasta:"
                + "\r\n" + "   1) Yhden vastustajan vahvuusluku (esim. 1922) ja lisäksi ottelun tulos 1/0,5/0 nuolinäppäimillä tai hiirellä. Laskennan tulos päivittyy valinnan mukaan."
                + "\r\n" + "   2) Vahvuusluvut tuloksineen, esim. +1525 =1600 -1611 +1558, jossa + voitto, = tasan ja - tappio"
                + "\r\n" + "   3) Turnauksen pistemäärä ja vastustajien vahvuusluvut, esim. 2.5 1525 1600 1611 1558"
                + "\r\n"
                + "\r\n" + "Laskenta suoritetaan klikkaamalla laskenta-painiketta tai painamalla Enter vastustajan SELO-kentässä sekä (jos yksi vastustaja) tuloksen valinta -painikkeilla."
                + "\r\n"
                + "\r\n" + "Jos haluat jatkaa laskentaa uudella vahvuusluvulla, klikkaa Käytä uutta SELOa jatkolaskennassa. Jos ei ole vielä ollut laskentaa, saadaan uuden pelaajan oletusarvot SELO 1525 ja pelimäärä 0.";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Ohjeita", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed
                                

    // MenuItem: Laskentakaavat
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        String infoMessage = "Shakin vahvuusluvun laskentakaavat: http://skore.users.paivola.fi/selo.html"
                + " \r\n"
                + "Lisätietoa: http://www.shakkiliitto.fi/ ja http://www.shakki.net/cgi-bin/selo";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Laskentakaavat", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    // MenuItem: Tietoja ohjelmasta
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        String infoMessage = "Shakin vahvuusluvun laskenta"
                + " \r\n"
                + "Ohjelmointikieli Java: https://github.com/isuihko/jSelolaskuri"
                + " \r\n"
                + "Myös C#: https://github.com/isuihko/selolaskuri";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Tietoa ohjelmasta", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    // Lopetuksen varmistaminen
    //      Valittu File->Sulje ohjelma -> Application.Exit()
    // Sama toiminta kuin formWindowClosing(java.awt.event.WindowEvent evt)
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        int vastaus = JOptionPane.showConfirmDialog(null, 
                "Haluatko poistua ohjelmasta?"); // Yes, No, Cancel
        if (vastaus == JOptionPane.YES_OPTION)
            JavaSelolaskuriForm.this.dispose();        
    }                                                

    // Lopetuksen varmistaminen
    //      Suljettu ikkuna   
    // Aiemmin on asetettu: setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int vastaus = JOptionPane.showConfirmDialog(null, 
                "Haluatko poistua ohjelmasta?"); // Yes, No, Cancel
        if (vastaus == JOptionPane.YES_OPTION)
            JavaSelolaskuriForm.this.dispose();
    }//GEN-LAST:event_formWindowClosing
          
 

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
            java.util.logging.Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JavaSelolaskuriForm().setVisible(true);
            }
        });
    }

    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel OmaVahvuusluku_teksti;
    private javax.swing.JLabel TuloksetPistemaaranKanssa_teksti;
    private javax.swing.JLabel UudenPelaajanLaskenta_txt;
    private javax.swing.JLabel UusiSELO_teksti;
    private javax.swing.JLabel VastustajanVahvuusluku_teksti;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JButton kaytaUutta_btn;
    private javax.swing.JTextField keskivahvuus_out;
    private javax.swing.JButton laskeUusiSelo_btn;
    private javax.swing.JRadioButton miettimisaika_11_59_btn;
    private javax.swing.JRadioButton miettimisaika_60_89_btn;
    private javax.swing.JRadioButton miettimisaika_enint10_btn;
    private javax.swing.JRadioButton miettimisaika_vah90_btn;
    private javax.swing.JTextField odotustulos_out;
    private javax.swing.JTextField pelimaara_in;
    private javax.swing.JTextField pisteEro_out;
    private javax.swing.JTextField selo_in;
    private javax.swing.JTextField selomuutos_out;
    private javax.swing.JRadioButton tulosTappio_btn;
    private javax.swing.JRadioButton tulosTasapeli_btn;
    private javax.swing.JRadioButton tulosVoitto_btn;
    private javax.swing.JTextField tulos_out;
    private javax.swing.JTextField uusiSelo_out;
    private javax.swing.JTextField uusi_pelimaara_out;
    private javax.swing.JTextField vaihteluvali_out;
    private javax.swing.JTextField vastustajanSelo_in;
    // End of variables declaration//GEN-END:variables

}


