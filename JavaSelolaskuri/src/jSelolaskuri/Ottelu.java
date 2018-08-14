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
    private final int VastustajanSelo;
    private final Vakiot.OttelunTulos_enum OttelunTulos;

    public Ottelu(int selo, Vakiot.OttelunTulos_enum tulos)
    {
        VastustajanSelo = selo;
        OttelunTulos = tulos;
    }

    public int getVastustajanSelo() {
        return this.VastustajanSelo;
    }
    public Vakiot.OttelunTulos_enum getOttelunTulos() {
        return this.OttelunTulos;
    }
}
