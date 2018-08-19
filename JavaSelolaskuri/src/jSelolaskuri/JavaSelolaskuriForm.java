/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;


import javax.swing.JOptionPane; // JOptionPane.showMessageDialog
import java.awt.Color; // setForeground(Color.RED) or (Color.BLACK)
import java.awt.event.KeyAdapter;   // KeyAdapter()
import java.awt.event.KeyEvent;  // KeyEvent.VK_UP   .VK_DOWN
import javax.swing.JFrame;

// Leikekirjan käsittely:
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

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
 * 27.5.2018    - Vakioita varten luokka: Vakiot.java, esim. Vakiot.Miettimisaika_enum.MIETTIMISAIKA_11_59MIN
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
 *              - Unit Testing Yksikkotestit.java
 *                Lisätty täsmälleen samat testitapaukset (22 kpl), jotka ovat C#-versiossakin.
 *              - Versiopvm 1.8.2018 -> GitHub jSelolaskuri
 * 
 * 2.8.2018     - Käyttöön vastustajanSelo_jComboBox (aiemmin vastustajanSelo_in -tekstikenttä).
 *                jComboBoxiin tallenetaan kaikki siihen annetut vastustajien tiedot, jotta voidaan
 *                tarvittaessa hakea aiemmat vastustajat/ottelut, muuttaa niitä ja laskea helposti uudestaan.
 *                Tämä kenttä on ollut ohjelman C#-versiossa jo pidempään.
 *                Tässä ei oteta talteen omaa vahvuuslukua tai omaa pelimäärää vaan vastustajat, joka
 *                voi sisältää enemmänkin tietoa.
 *                Tarvittiin KeyListener, jotta voidaan saada napattua Enter, jonka jälkeen suoritetaan laskenta.
 *               -TarkistaVastustajanSelo() ei antanut virhestatusta, jos kenttä oli tyhjä.
 *              - Estetty ikkunan koon muuttaminen: Design->Properties->Resizable-ruksi pois.
 *              - Versiopvm 2.8.2018 -> GitHub jSelolaskuri
 * 
 * 4.-5.8.2018  - Syöte voidaan antaa CSV (comma-separated values) eli pilkulla erotettuna listana
 *                vastustajat-kenttään. Listassa 2, 3, 4 tai 5 merkkijonoa, ks. HaeSyotteetLomakkeelta()
 *                Myös uusi virheilmoitus, jos CSV-formaatissa liikaa pilkkuja.
 *              - Muutama yksikkötesti CSV-formaatin testaamiseen
 *              - Tarkistetaan syötteen haussa, onko vastustajanSelo_jComboBox.getSelectedItem() null.
 *
 * 8.8.2018     - vastustajanSelo_jComboBox:n alustukset ikkunakaappauksien ottoa varten (oletuksena ei käännetä)
 *              - lomakkeen teksteihin muutoksia ja vastustajanSelo-kenttään ohje Enter=laskenta
 * 
 * 12.8.2018    - vastustajanSelo-kentässä voidaan antaa komentoja. Tarkistus enterin painalluksen jälkeen.
 *                  clear - nollaa kaiken syötteen ja tulosteen
 *                  test  - tallentaa vastustajanSelo_comboBox:iin testidataa ikkunakaappauksien ottoa varten
 *              - Valikko Edit, jossa cut tyhjentää vastustaja-historian, copy kopioi vastustajat leikekirjaan
 *                ja paste kopioi tekstin leikekirjasta vastustaja-historiaan.
 *                Pastessa vain tarkistukset, että pituus on vähintään seloluvun pituus (eli 4) eikä ole yli 1000 merkkiä.
 *                rivin pitää alkaa sallitulla merkillä (numero tai tulos +-=).  Ei saa olla kahta samaa riviä.
 *                Rajoitettu lisättävien rivin lkm (vakio, jonka arvo nyt 100).
 *              - Lisätty virheilmoitus virheelliselle miettimisajalla, joka on mahdollinen (vain) CSV-formaatissa,
 *                kun voidaan antaa miettimisaika (minuutit) numerona
 *              - vastustajanSelo: Horizontal Size = 475, Auto Rezising ja Horizontal Resizable pois päältä,
 *                koska kentästä saattoi tulla Pastea käytettäessä leveämpi kuin ikkuna
 *              - Muutettu menun otsikko: File -> Menu
 * 
 * 15.8.2018        - Yksikkötestausta muutettu, testitapaukset jaettu moduuleihin testattavan asian mukaan
 *                  - CSV-formaatin testaukseen muutos: merkkijonon alkukäsittely tehdään uudella yleisellä rutiinilla,
 *                    joka on SelolaskuriOperations-luokassa ja jota nyt myös käytetään tietoja haettaessa lomakkeelta.
 *                    Nyt merkkijonon jakaminen osiin saadaan testaukseen.
 *
 * 19.8.2018        - CSV-formaatin testit jaettu kahteen moduuliin:
 *                       UnitTest4_TarkistaCSV:  virheellinen data
 *                       UnitTest5_Laskenta:     kelvollinen data, josta lasketaan
 *                  - CSV-formaatin tarkistuksia lisätty. Poistetaan ylimääräiset välilyönnit alusta ja lopusta, sekä pilkkujen ympäriltä:
 *                        "   90 , 1525 ,  20  ,    2.5 1505 1600    1611 1558   "  -> "90,1525,20,2.5 1505 1600 1611 1558"
 *                    Siistitty versio tallennetaan vastustajat-historiaan. Tällöin samasisältöinen, mutta eri määrät välilyöntejä, ei tallennu
 *                    historiaan kuin kerran.
 *                    Aiemmin laskenta epäonnistui, jos formaatissa oli vastustajia edeltävän pilkun jälkeen välilyönti.
 *                  - Välilyöntien poisto (using Regex) luokassa SelolaskuriOperation (aiemmin oli lomake ja unit test)
 *                  - Paste: Tehdään ylimääräisten välilyöntien poisto ennen tallennusta.
 *                  - Lisätty testitapauksia laskentaan em. korjauksien varmistamiseen ja
 *                    nyt yhteensä 65 testiä.
 * 
 * TODO:  koodi ei ole vielä Java-koodaustyylin mukaista kaikin puolin.
 *        Tämä on ensimmäinen Java-ohjelmani ja muutan tätä vielä paljonkin
 *        aina kun keksin, miten asioita kannattaa tehdä.
 *        Mm. radiobuttonien käsittely ei vielä ole kunnossa.
 * 
 */


// Esimerkkitapauksia (ks. laskentaesimerkkejä myös Yksikkotestit.java)
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
        
        // jComboBox-kentässä tarvitaan KeyListener, jotta voidaan suorittaa laskenta Enteriä painamalla
        //
        // Aiemmin oli käytössä tavallinen vastustajanSelo_in -tekstikenttä, jossa riitti Event-handler       
        //    private void vastustajanSelo_inKeyPressed(java.awt.event.KeyEvent evt)
        //          if (evt.getKeyCode() == KeyEvent.VK_ENTER) ...
        //
        vastustajanSelo_jComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    vastustajanSelo_jComboBox_KasitteleEnter();
                }
            }             
        });
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
        jLabel13 = new javax.swing.JLabel();
        TuloksetPistemaaranKanssa_teksti = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        vastustajanSelo_jComboBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        laskeUusiSelo_btn = new javax.swing.JButton();
        kaytaUutta_btn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        turnauksenTulos_out = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        keskivahvuus_out = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        odotustulos_out = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pisteEro_out = new javax.swing.JTextField();
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
        jPanel6 = new javax.swing.JPanel();
        UusiSELO_teksti = new javax.swing.JLabel();
        uusiSelo_out = new javax.swing.JTextField();
        selomuutos_out = new javax.swing.JTextField();
        vaihteluvali_out = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        uusiPelimaara_out = new javax.swing.JTextField();
        UudenPelaajanLaskenta_txt = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuBar = new javax.swing.JMenu();
        ohjeita_jMenuItem = new javax.swing.JMenuItem();
        laskentakaavat_jMenuItem = new javax.swing.JMenuItem();
        tietoaOhjelmasta_jMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        suljeOhjelma_jMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        cutVastustajat_jMenuItem = new javax.swing.JMenuItem();
        copyVastustajat_jMenuItem = new javax.swing.JMenuItem();
        pasteVastustajat_jMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        OmaVahvuusluku_teksti.setText("Oma SELO");

        jLabel3.setText("Oma pelimäärä (tyhjä, numero, uudella pelaajalla 0-10)");

        VastustajanVahvuusluku_teksti.setText("Vastustajat:  SELO / SELOt tuloksineen / turnauksen pistemäärä ja SELOt / CSV    Enter=laskenta");

        jLabel13.setText("Esim. +1725 -1910 =1812 (tai 1812), jossa + voitto, = tai tyhjä tasapeli, - tappio");

        TuloksetPistemaaranKanssa_teksti.setText("Tai pistemäärä ja vastustajien vahvuusluvut: 1.5 1725 1910 1812");

        jLabel5.setText("Jos annettu yksi vahvuusluku numerona (esim. 1720), niin tuloksen valinta:");

        vastustajanSelo_jComboBox.setEditable(true);
        vastustajanSelo_jComboBox.setMaximumRowCount(10);

        jLabel2.setText("CSV: min,selo,pelimaara,vastustajat[,tulos] tai selo,vastustajat. Esim. 1650,+1725 -1910 1812");

        jLabel7.setText("Huom! CSV:ssä annetut arvot ohittavat muut (miettimisaika,vahvuusluku,pelimäärä)");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vastustajanSelo_jComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(VastustajanVahvuusluku_teksti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
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
                            .addComponent(jLabel5)
                            .addComponent(TuloksetPistemaaranKanssa_teksti)
                            .addComponent(jLabel13)
                            .addComponent(jLabel7))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addComponent(vastustajanSelo_jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TuloksetPistemaaranKanssa_teksti)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
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

        jLabel12.setText("Java 12.8.2018 github.com/isuihko/jSelolaskuri");

        turnauksenTulos_out.setEditable(false);
        turnauksenTulos_out.setFocusable(false);

        jLabel16.setText("Ottelun/turnauksen tulos");

        keskivahvuus_out.setEditable(false);
        keskivahvuus_out.setFocusable(false);

        jLabel15.setText("Vastustajien keskivahvuus");

        jLabel10.setText("Odotustulos");

        odotustulos_out.setEditable(false);
        odotustulos_out.setFocusable(false);

        jLabel9.setText("Piste-ero");

        pisteEro_out.setEditable(false);
        pisteEro_out.setFocusable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(keskivahvuus_out, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                    .addComponent(turnauksenTulos_out))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pisteEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(turnauksenTulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(odotustulos_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(keskivahvuus_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pisteEro_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel9))
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

        uusiPelimaara_out.setEditable(false);
        uusiPelimaara_out.setFocusable(false);

        UudenPelaajanLaskenta_txt.setText("Uuden pelaajan laskenta");
        UudenPelaajanLaskenta_txt.setFocusable(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(uusiPelimaara_out, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(UudenPelaajanLaskenta_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uusiPelimaara_out, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UudenPelaajanLaskenta_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 3, Short.MAX_VALUE))
        );

        jMenuBar1.setMaximumSize(new java.awt.Dimension(56, 21));

        jMenuBar.setText("Menu");

        ohjeita_jMenuItem.setText("Ohjeita");
        ohjeita_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ohjeita_jMenuItemActionPerformed(evt);
            }
        });
        jMenuBar.add(ohjeita_jMenuItem);

        laskentakaavat_jMenuItem.setText("Laskentakaavat");
        laskentakaavat_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laskentakaavat_jMenuItemActionPerformed(evt);
            }
        });
        jMenuBar.add(laskentakaavat_jMenuItem);

        tietoaOhjelmasta_jMenuItem.setText("Tietoa ohjelmasta");
        tietoaOhjelmasta_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tietoaOhjelmasta_jMenuItemActionPerformed(evt);
            }
        });
        jMenuBar.add(tietoaOhjelmasta_jMenuItem);
        jMenuBar.add(jSeparator1);

        suljeOhjelma_jMenuItem.setText("Sulje ohjelma");
        suljeOhjelma_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                suljeOhjelma_jMenuItemActionPerformed(evt);
            }
        });
        jMenuBar.add(suljeOhjelma_jMenuItem);

        jMenuBar1.add(jMenuBar);

        jMenu2.setText("Edit");

        cutVastustajat_jMenuItem.setText("Cut (kopioi ja tyhjentää Vastustajat-historian)");
        cutVastustajat_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutVastustajat_jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(cutVastustajat_jMenuItem);

        copyVastustajat_jMenuItem.setText("Copy (kopioi Vastustajat-historian)");
        copyVastustajat_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyVastustajat_jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(copyVastustajat_jMenuItem);

        pasteVastustajat_jMenuItem.setText("Paste (täyttää Vastustajat-historian, ei tarkistusta)");
        pasteVastustajat_jMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteVastustajat_jMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(pasteVastustajat_jMenuItem);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(kaytaUutta_btn))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(59, 59, 59)
                                .addComponent(jLabel1))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(laskeUusiSelo_btn))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel12)))
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
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
    //
    // Jos CSV-formaatti ja on liian monta arvoa, palauttaa null
    private Syotetiedot HaeSyotteetLomakkeelta()
    {
        // Remove all leading and trailing white spaces from the form
        selo_in.setText(selo_in.getText().trim());
        pelimaara_in.setText(pelimaara_in.getText().trim());

        String s = (String)vastustajanSelo_jComboBox.getSelectedItem();
        // jComboBox.getSelectedItem() could had been null
        if (s != null) {
            s = s.trim();
            vastustajanSelo_jComboBox.setSelectedItem(s);  // XXX: Update selection
        }

        // process opponents field and check if CSV format was used                
        
        if (s != null && !s.isEmpty()) {
            // poista ylimääräiset välilyönnit, korvaa yhdellä
            // poista myös välilyönnit pilkun molemmilta puolilta, jos on CSV-formaatti           
            // Update selection with cleaner version of the field
            vastustajanSelo_jComboBox.setSelectedItem(so.SiistiVastustajatKentta(s)); // .Trim jo tehty

            // Tarkista, onko csv ja jos on, niin unohda muut syötteet
            // Paitsi jos on väärässä formaatissa, palautetaan null ja kutsuvalla tasolla virheilmoitus
            //
            // CSV 
            // 90,1525,0,1725,1
            // Jos 5 merkkijonoa:  minuutit,selo,pelimäärä,vastustajat,jos_yksi_selo_niin_tulos
            // Jos 4: ottelun tulosta ei anneta, käytetään TULOS_MAARITTELEMATON
            // Jos 3: miettimisaikaa ei anneta, käytetään lomakkeelta valittua miettimisaikaa
            // Jos 2: pelimäärää ei anneta, käytetään oletuksena tyhjää ""
            //
            //
            // Note that string in the following format is not CSV (comma can be used in tournament result)
            //    "tournamentResult selo1 selo2 ..." e.g. "2,5 1505 1600 1611 1558" or "100,5 1505 1600 1611 1558 ... "
            //
            // But that checking should not affect CSV format "thinking time,selo,..." e.g. "5,1525,0,1505 1600 ..."
            //      or "own selo,opponent selo with result" e.g. "1525,+1505"
            //      or "own selo,opponent selo,single match result" e.g. "1525,1505,0.5" <- Here must use decimal point!!!
            //
            // So check that if there is just one comma, so two values, the length of the first value must be at least 4 (length of selo)

            String csv = (String)vastustajanSelo_jComboBox.getSelectedItem(); // GET THE UPDATED VALUE
            if (csv.contains(",")) {
                String[] tmp = csv.split(",");
                if (tmp.length != 2 || (tmp.length == 2 && tmp[0].trim().length() >= 4)) {
                    // The thinking time might be needed from the form if there are 2 or 3 values in CSV format
                    return so.SelvitaCSV(HaeMiettimisaika(), csv);                    
                }
            }
        }
        
        return new Syotetiedot(HaeMiettimisaika(), selo_in.getText(), pelimaara_in.getText(), (String)vastustajanSelo_jComboBox.getSelectedItem(), HaeOttelunTulos());
    }

    // Nämä miettimisajan valintapainikkeet ovat omana ryhmänään paneelissa
    // Aina on joku valittuna, joten ei voi olla virhetilannetta.
    private Vakiot.Miettimisaika_enum HaeMiettimisaika()
    {
        Vakiot.Miettimisaika_enum valinta;
        
        if (miettimisaika_vah90_btn.isSelected())
            valinta = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_VAH_90MIN;
        else if (miettimisaika_60_89_btn.isSelected())
            valinta = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_60_89MIN;
        else if (miettimisaika_11_59_btn.isSelected())
            valinta = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_11_59MIN;
        else
            valinta = Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN;
        return valinta;
    }    

    // Ottelun tulos voi olla valittu radiobuttoneilla tai valitsematta (MAARITTELEMATON)
    private Vakiot.OttelunTulos_enum HaeOttelunTulos()
    {
        Vakiot.OttelunTulos_enum valinta;
        if (tulosVoitto_btn.isSelected())
            valinta = Vakiot.OttelunTulos_enum.TULOS_VOITTO;
        else if (tulosTasapeli_btn.isSelected())
            valinta = Vakiot.OttelunTulos_enum.TULOS_TASAPELI;
        else if (tulosTappio_btn.isSelected())
            valinta = Vakiot.OttelunTulos_enum.TULOS_TAPPIO;
        else 
            valinta = Vakiot.OttelunTulos_enum.TULOS_MAARITTELEMATON; // ei mahdollinen
        
        return valinta;
    }
    
     
    // --------------------------------------------------------------------------------
    // Painikkeiden toiminta
    // --------------------------------------------------------------------------------
    //    Laske uusi SELO  (pikashakissa Laske uusi PELO)
    //    Käytä uutta SELOa jatkolaskennassa

    // Suoritetaan laskenta -button
    private void laskeUusiSelo_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laskeUusiSelo_btnActionPerformed
        if (LaskeOttelunTulosLomakkeelta()) {
            // Annettu teksti talteen (jos ei ennestään ollut) -> Drop-down Combo box
            // Tallennus kun klikattu Laske SELO tai painettu enter vastustajan selo-kentässä
            //
            // Tekstistä on poistettu ylimääräiset välilyönnit ennen tallennusta            
            // HUOM! Tämä sama koodi on myös kentän KeyListener():ssä, joka nappaa Enterin
            String s = (String)vastustajanSelo_jComboBox.getSelectedItem();
            vastustajanSelo_jComboBox.setSelectedIndex(-1);
            vastustajanSelo_jComboBox.setSelectedItem(s);
            if (vastustajanSelo_jComboBox.getSelectedIndex() < 0) {
                vastustajanSelo_jComboBox.addItem(s);
            }
        }
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
        vastustajanSelo_jComboBox.requestFocus();
    }//GEN-LAST:event_kaytaUutta_btnActionPerformed

        
    // Näyttää virheen mukaisen ilmoituksen sekä siirtää kursorin kenttään, jossa virhe
    // Virheellisen kentän arvo näytetään punaisella kunnes ilmoitusikkuna kuitataan
    private void NaytaVirheilmoitus(int virhestatus)
    {
        // String message;
        
        switch (virhestatus) {
            case Vakiot.SYOTE_STATUS_OK:
                break;
  
            case Vakiot.SYOTE_VIRHE_MIETTIMISAIKA:
                JOptionPane.showMessageDialog(null,
                        "VIRHE: CSV-formaatissa annettu virheellinen miettimisaika. Annettava minuutit. Ks. Menu->Ohjeita",
                        "VIRHE",
                        JOptionPane.WARNING_MESSAGE);
                vastustajanSelo_jComboBox.requestFocus();
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
                vastustajanSelo_jComboBox.setForeground(Color.RED); // XXX: DOES NOT CHANGE COLOR
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Vastustajan vahvuusluvun on oltava numero " + Vakiot.MIN_SELO + " - " + Vakiot.MAX_SELO,
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_jComboBox.setForeground(Color.BLACK);   // palauta väri                
                vastustajanSelo_jComboBox.requestFocus();
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
                vastustajanSelo_jComboBox.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Yksittäisen ottelun tulos voidaan antaa merkeillä +(voitto), =(tasapeli) tai -(tappio), esim. +1720. Tasapeli voidaan antaa muodossa =1720 ja 1720.",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_jComboBox.setForeground(Color.BLACK);
                vastustajanSelo_jComboBox.requestFocus();
                break;
                
            case Vakiot.SYOTE_VIRHE_TURNAUKSEN_TULOS:
                vastustajanSelo_jComboBox.setForeground(Color.RED);
                JOptionPane.showMessageDialog(null,
                    "VIRHE: Turnauksen pistemäärä voi olla enintään sama kuin vastustajien lukumäärä.",
                    "VIRHE",
                    JOptionPane.WARNING_MESSAGE);          
                vastustajanSelo_jComboBox.setForeground(Color.BLACK);
                vastustajanSelo_jComboBox.requestFocus();                
                break;
                
            case Vakiot.SYOTE_VIRHE_CSV_FORMAT:
                JOptionPane.showMessageDialog(null,
                        "CSV-formaattivirhe, ks. Menu->Ohjeita",
                        "VIRHE",
                        JOptionPane.WARNING_MESSAGE);                
                vastustajanSelo_jComboBox.requestFocus();
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
    // Virhetarkastus ja laskenta erillisessä luokassa SelolaskuriOperations,
    // jotta niitä voidaan kutsua myös yksikkötestauksesta
    //
    private boolean LaskeOttelunTulosLomakkeelta()
    {
        boolean status = true;
        int tulos;
        
        // hakee syötetyt tekstit ja tehdyt valinnat, ei virhetarkastusta
        Syotetiedot syotteet = HaeSyotteetLomakkeelta();

        if (syotteet == null) {
            NaytaVirheilmoitus(Vakiot.SYOTE_VIRHE_CSV_FORMAT);
            status = false;                  
        } else if ((tulos = so.TarkistaSyote(syotteet)) != Vakiot.SYOTE_STATUS_OK) {
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
        uusiSelo_out.setText(Integer.toString(tulokset.getUusiSelo()));
        
        int i = tulokset.getUusiSelo() - tulokset.AlkuperainenSelo();
        selomuutos_out.setText(((i > 0) ? "+" : "") + Integer.toString(i));  // jos > 0, lisää '+'   sama kuin C# .ToString("+#;-#;0")
       
        //   uusi pelimäärä tai tyhjä
        if (tulokset.getUusiPelimaara() >= 0)
            uusiPelimaara_out.setText(Integer.toString(tulokset.getUusiPelimaara()));
        else
            uusiPelimaara_out.setText("");
        
        // piste-ero turnauksen keskivahvuuteen nähden
        String tempstr = Integer.toString(Math.abs(tulokset.AlkuperainenSelo() - tulokset.getTurnauksenKeskivahvuus()));
        pisteEro_out.setText(tempstr);
        
        // Vastustajien vahvuuslukujen keskiarvo
        keskivahvuus_out.setText(Integer.toString(tulokset.getTurnauksenKeskivahvuus()));
        
        // Turnauksen loppupisteet yhdellä desimaalilla / ottelujen lkm, esim.  2.5 / 6 tai 2.0 / 6
        turnauksenTulos_out.setText(
                Float.toString(tulokset.getTurnauksenTulos() / 2F) + " / " + Integer.toString(tulokset.getVastustajienLkm()));
               
        // Vahvuusluku on voinut vaihdella laskennan edetessä, jos vastustajat ovat olleet formaatissa "+1622 -1880 =1633"
        // Vaihteluväliä ei ole, jos laskenta on tehty yhdellä lausekkeella tai on ollut vain yksi vastustaja
        if (tulokset.getMinSelo() < tulokset.getMaxSelo())
            vaihteluvali_out.setText(Integer.toString(tulokset.getMinSelo()) + " - " + Integer.toString(tulokset.getMaxSelo()));
        else
            vaihteluvali_out.setText("");
       
        // Odotustulosta tai sen summaa ei näytetä uudelle pelaajalle, koska vahvuusluku on vielä provisional
        // Uuden pelaajan laskennasta annetaan ilmoitusteksti
        if (tulokset.UudenPelaajanLaskenta()) {
            odotustulos_out.setText("");
            UudenPelaajanLaskenta_txt.setVisible(true);
        } else {
            odotustulos_out.setText(Float.toString(tulokset.getOdotustulos() / 100F)); // not Double.toString
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
        
        // Jos käytetty CSV-formaattia, on voitu antaa eri miettimisaika kuin mitä valittu buttoneilla,
        // joten varmuuden vuoksi päivitetään SELO- ja PELO-tekstit (vaikka voivat jo olla oikein)
        // Turhan päivittämisen voisi estää lisäämällä flag syötetietoihin kertomaan, oliko csv:ssä miettimisaika.
        //
        // Ei riitä tarkistaa, onko valittu eri kuin näytöllä, koska tekstit on voitu vaihtaa välillä
        //if (tulokset.getMiettimisaika() != HaeMiettimisaika()) {
            if (tulokset.getMiettimisaika() == Vakiot.Miettimisaika_enum.MIETTIMISAIKA_ENINT_10MIN)
                vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_PELOKSI);
            else
                vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI);
        //}              
    }
    
    
    // --------------------------------------------------------------------------------
    // Miettimisajan valinnan mukaan tekstit: SELO (pidempi peli) vai PELO (pikashakki)
    //
    // XXX: Hm... onko liian monta vaihdettavaa otsikkokenttää? Esim. Laske uusi SELO -> Laske uusi vahvuusluku
    // --------------------------------------------------------------------------------
    private void vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum suunta)
    {
        String alkup, uusi;
        //Font font = TuloksetPistemaaranKanssa_teksti.getFont();
        
        if (suunta == Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI)
        {
            alkup = "PELO";
            uusi = "SELO";
            
            //font = font.deriveFont(
            //    Collections.singletonMap(
            //        TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR));
            //TuloksetPistemaaranKanssa_teksti.setFont(font);                        
        }
        else
        {
            alkup = "SELO";
            uusi = "PELO";

            // korosta PELO-ohje
            //font = font.deriveFont(
            //    Collections.singletonMap(
            //        TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD));
            //TuloksetPistemaaranKanssa_teksti.setFont(font);
        }

        OmaVahvuusluku_teksti.setText(OmaVahvuusluku_teksti.getText().replaceAll(alkup, uusi));
        VastustajanVahvuusluku_teksti.setText(VastustajanVahvuusluku_teksti.getText().replaceAll(alkup, uusi));
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

    
    private void TyhjennaVastustajat()
    {
        vastustajanSelo_jComboBox.removeAllItems();      
    }
    
    // vastustajanSelo-kentässä clear ja Enter, niin tyhjennetään syötteet ja tuloskentät
    //
    // tyhjentää lomakkeen kentät ja palauttaa alkuarvot, miettimisaika vähintään 90 min, ei tulospainikkeita valittuna
    private void TyhjennaSyotteet()
    {
        selo_in.setText("");
        pelimaara_in.setText("");
        
        miettimisaika_vah90_btn.setSelected(true);
        miettimisaika_60_89_btn.setSelected(false);
        miettimisaika_11_59_btn.setSelected(false);
        miettimisaika_enint10_btn.setSelected(false);
        
        tulosVoitto_btn.setSelected(false);
        tulosTasapeli_btn.setSelected(false);
        tulosTappio_btn.setSelected(false);
        
        TyhjennaVastustajat();
    }
    
    private void TyhjennaTuloskentat()
    {
        uusiSelo_out.setText("");
        selomuutos_out.setText("");
        vaihteluvali_out.setText("");

        uusiPelimaara_out.setText("");
        turnauksenTulos_out.setText("");
        odotustulos_out.setText("");
        keskivahvuus_out.setText("");                
        pisteEro_out.setText("");
    }
          
    private void TallennaTestaustaVartenVastustajia()
    {
        TyhjennaVastustajat();
        
        // Add some data (uncomplete and complete) to help running couple of test cases for window captures
        vastustajanSelo_jComboBox.addItem("");  // to be shown first!
        
        vastustajanSelo_jComboBox.addItem("5,1996,,10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");
        // Also Miettimisaika enint. 10 min, nykyinen SELO 1996, pelimäärä tyhjä
        vastustajanSelo_jComboBox.addItem("10.5 1977 2013 1923 1728 1638 1684 1977 2013 1923 1728 1638 1684");  
                        
        vastustajanSelo_jComboBox.addItem("90,1525,0,+1525 +1441 -1973 +1718 -1784 -1660 -1966");        
        // Also Miettimisaika väh. 90 min, nykyinen SELO 1525, pelimäärä 0
        vastustajanSelo_jComboBox.addItem("+1525 +1441 -1973 +1718 -1784 -1660 -1966");
                
        vastustajanSelo_jComboBox.addItem("90,1683,2,1973,0");
        // Also Miettimisaika väh. 90 min, nykyinen SELO 1683, pelimäärä 2, ottelun tulos 0=tappio
        vastustajanSelo_jComboBox.addItem("1973");
        
        vastustajanSelo_jComboBox.addItem("90,1713,3,1718,1");
        }
    
    private void vastustajanSelo_jComboBox_KasitteleEnter()
    {
        String s = (String)vastustajanSelo_jComboBox.getSelectedItem();
        
        if (s.equals("clear")) {
            // Huom! Jättää muistiin aiemmin lasketut vahvuusluvun ja pelimäärän, jolloin
            // painike Käytä uutta SELOa jatkolaskennassa voi hakea ne (ei siis palauta 1525,0)
            TyhjennaSyotteet();
            TyhjennaTuloskentat();

            // palauta tekstit
            vaihdaSeloPeloTekstit(Vakiot.VaihdaMiettimisaika_enum.VAIHDA_SELOKSI);
            return;
            
        } else if (s.equals("test")) {
            // testauksen helpottamista varten vastustajien tietoja
            TallennaTestaustaVartenVastustajia();
            return;
        }
        
        // Suorita laskenta, kun painettu Enter jComboBox-kentässä
        if (LaskeOttelunTulosLomakkeelta()) {
            // Annettu teksti talteen (jos ei ennestään ollut) -> Drop-down Combo box
            // Tallennus kun klikattu Laske SELO tai painettu enter vastustajan selo-kentässä
            // HUOM! Tämä sama koodi on myös Laske-painikkeen käsittelyssä
            s = (String)vastustajanSelo_jComboBox.getSelectedItem();  // GET the updated value
            vastustajanSelo_jComboBox.setSelectedIndex(-1);            
            vastustajanSelo_jComboBox.setSelectedItem(s);
            if (vastustajanSelo_jComboBox.getSelectedIndex() < 0) {
                vastustajanSelo_jComboBox.addItem(s);
            }               
        }       
    }

    // --------------------------------------------------------------------------------
    // Menu
    // --------------------------------------------------------------------------------
    //    Ohjeita
    //    Laskentakaavat
    //    Tietoa ohjelmasta
    //    Sulje ohjelma
    
    // MenuItem: Ohjeita    
    private void ohjeita_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ohjeita_jMenuItemActionPerformed
        // TODO add your handling code here
        String infoMessage = "Shakin vahvuusluvun laskenta SELO ja PELO"
                + "\r\n"
                + "\r\n" + "Annettavat tiedot:"
                + "\r\n"
                + "\r\n" + "-Miettimisaika. Pitkä peli (väh. 90 minuuttia) on oletuksena. Jos valitset enint. 10 minuuttia, lasketaan pikashakin vahvuuslukua (PELO)"
                + "\r\n" + "-Oma vahvuusluku"
                + "\r\n" + "-Oma pelimäärä, joka tarvitaan vain jos olet pelannut enintään 10 peliä. Tällöin käytetään uuden pelaajan laskentakaavaa."
                + "\r\n" + "-Vastustajien vahvuusluvut ja tulokset jollakin neljästä tavasta:"
                + "\r\n" + "   1) Yhden vastustajan vahvuusluku (esim. 1922) ja lisäksi ottelun tulos 1/0,5/0 nuolinäppäimillä tai hiirellä. Laskennan tulos päivittyy valinnan mukaan."
                + "\r\n" + "   2) Vahvuusluvut tuloksineen, esim. +1505 =1600 -1611 +1558, jossa + voitto, = tasan ja - tappio"
                + "\r\n" + "   3) Turnauksen pistemäärä ja vastustajien vahvuusluvut, esim. 2.5 1505 1600 1611 1558, voi käyttää myös desimaalipilkkua 1,5 1505 1600 1611 1558"
                + "\r\n" + "   4) CSV eli pilkulla erotetut arvot, jossa 2, 3, 4 tai 5 kenttää: HUOM! Käytä tuloksissa desimaalipistettä, esim. 0.5 tai 10.5!"
                + "\r\n" + "           2: oma selo,ottelut   esim. 1712,2.5 1505 1600 1611 1558 tai 1712,+1505  HUOM! Desimaalipiste!"
                + "\r\n" + "           3: oma selo,pelimaara,ottelut esim. 1525,0,+1505 +1441"
                + "\r\n" + "           4: minuutit,oma selo,pelimaara,ottelut  esim. 90,1525,0,+1525 +1441"
                + "\r\n" + "           5: minuutit,oma selo,pelimaara,ottelu,tulos esim. 90,1683,2,1973,0 (jossa tasapeli 1/2 tai 0.5)"
                + "\r\n" + "      Jos miettimisaika on antamatta, käytetään ikkunasta valittua"
                + "\r\n" + "      Jos pelimäärä on antamatta, käytetään tyhjää"
                + "\r\n"
                + "\r\n" + "   HUOM! CSV-formaatissa annettu ottelu on etusijalla ja lomakkeesta käytetään korkeintaan miettimisaikaa (vain jos se puuttui CSV:stä)."
                + "\r\n" + "   HUOM! CSV-formaatissa annettuja arvoja käytetään, vaikka oma selo, pelimäärä, miettimisaika tai tulos olisi annettu erikseenkin."
                + "\r\n"
                + "\r\n" + "Laskenta suoritetaan klikkaamalla laskenta-painiketta tai painamalla Enter vastustajan SELO-kentässä sekä (jos yksi vastustaja) tuloksen valinta -painikkeilla."
                + "\r\n"
                + "\r\n" + "Jos haluat jatkaa laskentaa uudella vahvuusluvulla, klikkaa Käytä uutta SELOa jatkolaskennassa. Jos ei ole vielä ollut laskentaa, saadaan uuden pelaajan oletusarvot SELO 1525 ja pelimäärä 0.";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Ohjeita", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_ohjeita_jMenuItemActionPerformed
                                

    // MenuItem: Laskentakaavat
    private void laskentakaavat_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laskentakaavat_jMenuItemActionPerformed
        // TODO add your handling code here:
        String infoMessage = "Shakin vahvuusluvun laskentakaavat: http://skore.users.paivola.fi/selo.html"
                + " \r\n"
                + "Lisätietoa: http://www.shakkiliitto.fi/ ja http://www.shakki.net/cgi-bin/selo";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Laskentakaavat", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_laskentakaavat_jMenuItemActionPerformed

    // MenuItem: Tietoja ohjelmasta
    private void tietoaOhjelmasta_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tietoaOhjelmasta_jMenuItemActionPerformed
        // TODO add your handling code here:
        String infoMessage = "Shakin vahvuusluvun laskenta"
                + " \r\n"
                + "Ohjelmointikieli Java: https://github.com/isuihko/jSelolaskuri"
                + " \r\n"
                + "Myös C#: https://github.com/isuihko/selolaskuri";
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: Tietoa ohjelmasta", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_tietoaOhjelmasta_jMenuItemActionPerformed

    // Lopetuksen varmistaminen
    //      Valittu Menu->Sulje ohjelma -> Application.Exit()
    // Sama toiminta kuin formWindowClosing(java.awt.event.WindowEvent evt)
    private void suljeOhjelma_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                           
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

    
    // --------------------------------------------------------------------------------
    // Edit
    // --------------------------------------------------------------------------------
    //    Cut
    //    Copy
    //    Paste
    //
    // Edit-menu käsittelee vastustajanSelo-kentän listaa eli historiatietoja

    // Tyhjentää Vastustajat-historiatiedot   
    private void cutVastustajat_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutVastustajat_jMenuItemActionPerformed
        // At first copy into clipboard
        copyVastustajat_jMenuItemActionPerformed(evt);
        TyhjennaVastustajat();        
    }//GEN-LAST:event_cutVastustajat_jMenuItemActionPerformed

    // Kopioi leikekirjaan Vastustajat-historian
    private void copyVastustajat_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyVastustajat_jMenuItemActionPerformed
        String leikekirja = "";
        int size = vastustajanSelo_jComboBox.getItemCount();
        
        for (int i = 0; i < size; i++) {
            String s = vastustajanSelo_jComboBox.getItemAt(i);
            if (s.length() > 0)  // will skip possible empty 1st entry
                leikekirja += vastustajanSelo_jComboBox.getItemAt(i) + "\r\n";
        }
        
        StringSelection selection = new StringSelection(leikekirja);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }//GEN-LAST:event_copyVastustajat_jMenuItemActionPerformed

    // Kopioi leikekirjasta Vastustajat-historiaan tekstirivit
    //
    // Ei tarkisteta, että ovatko vastustajat/tulokset oikeassa formaatissa.
    // Vain tarkistukset, että pituus on vähintään seloluvun pituus (eli 4), eikä tule kahta samaa riviä.
    // Ei saa olla myöskään liian pitkä rivi eikä liian montaa riviä.
    //
    // Osa syötteestä on tarkoitus ajaa CSV-formaatissa (silloin täydellinen tai vain miettimisaika otetaan lomakkeelta)
    // Ja osa on tarkoitettu käytettäväksi erillisesti annetun miettimisajan, oman vahvuusluvun ja pelimäärän kanssa.
    //
    // Tekstistä poistetaan ylimääräiset välilyönnit.    
    private void pasteVastustajat_jMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteVastustajat_jMenuItemActionPerformed
        // Haetaan data leikekirjasta
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        String leikekirjaData = "";
        String[] leikekirja = null ;
        int lisatytRivit = 0;
        
        try {
            leikekirjaData = (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException ex) {
            // Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(JavaSelolaskuriForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // jos leikekirjassa on tekstiä, niin poista aiemmat vastustajat,
        // käsittele riveittäin, tarkista ja tallenna vastustajanSelo-kenttään
        if (!leikekirjaData.isEmpty()) {

            // Ei tallenneta liian pitkiä tai lyhyitä rivejä, eikä liian montaa riviä, eikä samaa riviä kahdesti.
            // Rivin on aloitettava numerolla (eli selo tai miettimisaika) tai ottelutuloksella (+, - tai =)
            leikekirja = leikekirjaData.split("\n");
            for (String rivi : leikekirja) {
                // poista ylimääräiset välilyönnit ennen tarkistusta ja mahdollista tallennusta
                String rivi2 = so.SiistiVastustajatKentta(rivi.trim());

                if (rivi2.length() >= Vakiot.SELO_PITUUS && rivi2.length() <= Vakiot.LEIKEKIRJA_MAX_RIVINPITUUS &&
                   (rivi2.charAt(0) == '+' || rivi2.charAt(0) == '-' || rivi2.charAt(0) == '=' || (rivi2.charAt(0) >= '0' && rivi2.charAt(0) <= '9')))
                {
                    vastustajanSelo_jComboBox.setSelectedIndex(-1);
                    vastustajanSelo_jComboBox.setSelectedItem(rivi2);
                    if (vastustajanSelo_jComboBox.getSelectedIndex() < 0) {
                        // vanhat tiedot poistetaan vain, jos on kelvollista lisättävää
                        if (lisatytRivit == 0) {
                            TyhjennaVastustajat();
                            vastustajanSelo_jComboBox.addItem("");  // XXX:  to be shown first! (not counted)
                        }
                        // on poistanut ylimääräiset välilyönnit ennen tallennusta
                        vastustajanSelo_jComboBox.addItem(rivi2);
                        if (++lisatytRivit >= Vakiot.LEIKEKIRJA_MAX_RIVIMAARA)
                            break;
                    }
                }
            }
        }
        
        if (lisatytRivit > 0 && null != leikekirja) {
            vastustajanSelo_jComboBox.setSelectedIndex(0);  // XXX: select the empty item

            String message = "Vastustajiin lisätty " + lisatytRivit + (lisatytRivit == 1 ? " rivi. " : " riviä. ")
                + "Leikekirjassa oli " + leikekirja.length + (leikekirja.length == 1 ? " rivi. " : " riviä. ")
                + "Lisätään enintään " + Vakiot.LEIKEKIRJA_MAX_RIVIMAARA + " riviä."
                + "\r\n"
                + "Huom! Ei tarkistettu, onko kelvollista ottelutietoa. Tarkistettu vain, että rivi alkaa"
                + "\r\n"
                + "numerolla tai tuloksella (+-=), rivin pituus on välillä 4 (seloluvun pituus) - " + Vakiot.LEIKEKIRJA_MAX_RIVINPITUUS
                + "\r\n"
                + "eikä lisätä samoja rivejä.";
            JOptionPane.showMessageDialog(null, message, "Clipboard", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Paste: Leikekirjan sisältöä ei hyväksytty. Ei muutettu vastustajia/ottelutietoja.", "Clipboard", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_pasteVastustajat_jMenuItemActionPerformed
    
    
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
    private javax.swing.JMenuItem copyVastustajat_jMenuItem;
    private javax.swing.JMenuItem cutVastustajat_jMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenuBar;
    private javax.swing.JMenuBar jMenuBar1;
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
    private javax.swing.JMenuItem laskentakaavat_jMenuItem;
    private javax.swing.JRadioButton miettimisaika_11_59_btn;
    private javax.swing.JRadioButton miettimisaika_60_89_btn;
    private javax.swing.JRadioButton miettimisaika_enint10_btn;
    private javax.swing.JRadioButton miettimisaika_vah90_btn;
    private javax.swing.JTextField odotustulos_out;
    private javax.swing.JMenuItem ohjeita_jMenuItem;
    private javax.swing.JMenuItem pasteVastustajat_jMenuItem;
    private javax.swing.JTextField pelimaara_in;
    private javax.swing.JTextField pisteEro_out;
    private javax.swing.JTextField selo_in;
    private javax.swing.JTextField selomuutos_out;
    private javax.swing.JMenuItem suljeOhjelma_jMenuItem;
    private javax.swing.JMenuItem tietoaOhjelmasta_jMenuItem;
    private javax.swing.JRadioButton tulosTappio_btn;
    private javax.swing.JRadioButton tulosTasapeli_btn;
    private javax.swing.JRadioButton tulosVoitto_btn;
    private javax.swing.JTextField turnauksenTulos_out;
    private javax.swing.JTextField uusiPelimaara_out;
    private javax.swing.JTextField uusiSelo_out;
    private javax.swing.JTextField vaihteluvali_out;
    private javax.swing.JComboBox<String> vastustajanSelo_jComboBox;
    // End of variables declaration//GEN-END:variables

}


