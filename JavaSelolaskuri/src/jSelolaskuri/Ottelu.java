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
public class Ottelu {
    private int VastustajanSelo;
    private int OttelunTulos;

    public Ottelu(int selo, int tulos)
    {
        VastustajanSelo = selo;
        OttelunTulos = tulos;
    }

    public int getVastustajanSelo() {
        return this.VastustajanSelo;
    }
    public int getOttelunTulos() {
        return this.OttelunTulos;
    }
}
