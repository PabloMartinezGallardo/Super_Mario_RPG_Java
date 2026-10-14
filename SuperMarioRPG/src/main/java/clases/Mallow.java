package clases;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;











public class Mallow implements Serializable{
    
    
  
    private int nivel;
    private int vida;
    private int vidaMax;
    private int fallo;
    private int dañoBase;
    private int dañoRelampago;
    private int lluviaHp;
    private int defensa;
    private int subidaVida;
    private int subidaMagia;

    public Mallow(int nivel, int vida, int vidaMax, int fallo, int dañoBase, int dañoRelampago, int lluviaHp, int defensa, int subidaVida, int subidaMagia) {
      
       
        this.nivel = nivel;
        this.vida = vida;
        this.vidaMax = vidaMax;
        this.fallo = fallo;
        this.dañoBase = dañoBase;
        this.dañoRelampago = dañoRelampago;
        this.lluviaHp = lluviaHp;
        this.defensa = defensa;
        this.subidaVida = subidaVida;
        this.subidaMagia = subidaMagia;
    }

   

    /**
     * @return the nivel
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    /**
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * @param vida the vida to set
     */
    public void setVida(int vida) {
        this.vida = vida;
    }


    /**
     * @return the vidaMax
     */
    public int getVidaMax() {
        return vidaMax;
    }

    /**
     * @param vidaMax the vidaMax to set
     */
    public void setVidaMax(int vidaMax) {
        this.vidaMax = vidaMax;
    }

    
    
    public int getFallo(){
        fallo = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return fallo;
    }
    
    
    public int getDañoBaseMallow(){
        dañoBase = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        return dañoBase;
    }

    /**
     * @return the dañoSalto
     */
    public int getDañoRelampago() {
        return dañoRelampago;
    }

    /**
     * @param dañoSalto the dañoSalto to set
     */
    public void setDañoRelampago(int dañoRelampago) {
        this.dañoRelampago = dañoRelampago;
    }


    public int getLluviaHp() {
        return lluviaHp;
    }

    /**
     * @return the defensa
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * @param defensa the defensa to set
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
    
    
    public int getSubidaVida(){
        subidaVida = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        return subidaVida;
    }


    public int getSubidaMagia(){
        subidaMagia = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        return subidaMagia;
    }
    
}