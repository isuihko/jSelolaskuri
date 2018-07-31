/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jSelolaskuri;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ismo
 */
public class Ottelulista  {

    /*
    public class Ottelu {
        public int VastustajanSelo;
        public int OttelunTulos;

        public Ottelu(int selo, int tulos)
        {
            VastustajanSelo = selo;
            OttelunTulos = tulos;
        }
    }
    */
    private final List<Ottelu> tallennetutOttelut = new ArrayList<>();
       
    public void LisaaOttelunTulos(int vastustajanSelo, int ottelunTulos)
    {
        Ottelu ottelu = new Ottelu(vastustajanSelo, ottelunTulos);
        tallennetutOttelut.add(ottelu);
    }

    private int _index;
    
    private Ottelu HaeOttelu(int index)
    {
        if (index < getLukumaara())
            return tallennetutOttelut.get(index);
        else
            return new Ottelu(0, Vakiot.TULOS_MAARITTELEMATON);
    }
        
    public Ottelu HaeEnsimmainen()
    {
        _index = 0;
        return HaeOttelu(_index);
    }

    public Ottelu HaeSeuraava()
    {
        _index++;
        return HaeOttelu(_index);
    }
    
    public int getLukumaara() {
        return tallennetutOttelut.size();
    }
    
    public int getKeskivahvuus() {
        int summa = 0;
        for (int i = 0; i < getLukumaara(); i++)
            summa += tallennetutOttelut.get(i).VastustajanSelo;
        return (int)Math.round((float)summa / getLukumaara());
    }
}
