package clases;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Usuario
 */
public class Jefe implements Serializable{
    
    private String nombre;
    private int vida;
    private int vidaMax;
    private int defensa;
    private int defensaMag;
    private int dañoBase;
    private int dañoMagico;
    private int fallo;
    private int atckMagico;
    private int objetivo;
    
    public Jefe(String nombre, int vida, int vidaMax, int defensa, int defensaMag, int dañoBase, int dañoMagico, int fallo, int atckMagico, int objetivo) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMax = vidaMax;
        this.defensa = defensa;
        this.defensaMag = defensaMag;
        this.dañoBase = dañoBase;
        this.dañoMagico = dañoMagico;
        this.fallo = fallo;
        this.atckMagico = atckMagico;
        this.objetivo = objetivo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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


    public int getDefensa(){
        return defensa;
    }


    public void setDefensa(int defensa){
        this.defensa = defensa;
    }
    

    public int getDefensaMag(){
        return defensaMag;
    }


    public void setDefensaMag(int defensa){
        this.defensaMag = defensaMag;
    }
    

    public int getDañoBase(){
        dañoBase = ThreadLocalRandom.current().nextInt(3, 4 + 1);
        return dañoBase;
    }


    public int getDañoBaseCroco(){
        dañoBase = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        return dañoBase;
    }


    public int getDañoBaseMack(){
        dañoBase = ThreadLocalRandom.current().nextInt(5, 8 + 1);
        return dañoBase;
    }
    

    public int getDañoMagico(){
        return dañoMagico;
    }

    
    public int getFallo(){
        fallo = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return fallo;
    }


    public int getAtckMagico(){
        atckMagico = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return atckMagico;
    }


    public int getObjetivo(){
        objetivo = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        return objetivo;
    }
}