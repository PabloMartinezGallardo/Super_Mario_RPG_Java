package clases;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Usuario
 */
public class Enemigo implements Serializable{
    
    private String nombre;
    private int vida;
    private int vidaMax;
    private int defensa;
    private int dañoBase;
    private int fallo;
    private int objetivo;
    
    public Enemigo(String nombre, int vida, int vidaMax,int defensa, int dañoBase, int fallo, int objetivo) {
        this.nombre = nombre;
        this.vida = vida;
        this.vidaMax = vidaMax;
        this.defensa = defensa;
        this.dañoBase = dañoBase;
        this.fallo = fallo;
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
    
    
    public int getDañoBase(){
        dañoBase = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        return dañoBase;
    }


    public int getDañoGoomba(){
        dañoBase = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        return dañoBase;
    }


    public int getDañoSkyTroopa(){
        dañoBase = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        return dañoBase;
    }
    

    public int getDañoSpikey(){
        dañoBase = ThreadLocalRandom.current().nextInt(2, 4 + 1);
        return dañoBase;

    }

    public int getDañoFrogog(){
        dañoBase = ThreadLocalRandom.current().nextInt(3, 5 + 1);
        return dañoBase;
    }


    public int getDañoSpringer(){
        dañoBase = ThreadLocalRandom.current().nextInt(3, 6 + 1);
        return dañoBase;
    }
    
    public int getFallo(){
        fallo = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return fallo;
    }


    public int getObjetivo(){
        objetivo = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        return objetivo;
    }
}