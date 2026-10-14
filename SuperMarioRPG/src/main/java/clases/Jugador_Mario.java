package clases;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Usuario
 */
public class Jugador_Mario implements Serializable{
    
    private String nombre;
    private Calendar fecha;
    private long tiempo;
    private int nivel;
    private int monedas;
    private int vida;
    private int magia;
    private int vidaMax;
    private int magiaMax;
    private int fallo;
    private int dañoBase;
    private int dañoMartillo;
    private int dañoSalto;
    private int dañoFlama;
    private int defensa;
    private int subidaVida;
    private int subidaMagia;

    public Jugador_Mario(String nombre, Calendar fecha, long tiempo, int nivel, int monedas, int vida, int magia, int vidaMax, int magiaMax, int fallo, int dañoBase, int dañoMartillo, int dañoSalto, int dañoFlama, int defensa, int subidaVida, int subidaMagia) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.nivel = nivel;
        this.monedas = monedas;
        this.vida = vida;
        this.magia = magia;
        this.vidaMax = vidaMax;
        this.magiaMax = magiaMax;
        this.fallo = fallo;
        this.dañoBase = dañoBase;
        this.dañoMartillo = dañoMartillo;
        this.dañoSalto = dañoSalto;
        this.dañoFlama = dañoFlama;
        this.defensa = defensa;
        this.subidaVida = subidaVida;
        this.subidaMagia = subidaMagia;
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
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }


    public long getTiempo(){
        return tiempo;
    }


    public void setTiempo(long tiempo){
        this.tiempo = tiempo;
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
     * @return the monedas
     */
    public int getMonedas() {
        return monedas;
    }

    /**
     * @param monedas the monedas to set
     */
    public void setMonedas(int monedas) {
        this.monedas = monedas;
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
     * @return the magia
     */
    public int getMagia() {
        return magia;
    }

    /**
     * @param magia the magia to set
     */
    public void setMagia(int magia) {
        this.magia = magia;
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

    /**
     * @return the magiaMax
     */
    public int getMagiaMax() {
        return magiaMax;
    }

    /**
     * @param magiaMax the magiaMax to set
     */
    public void setMagiaMax(int magiaMax) {
        this.magiaMax = magiaMax;
    }
    
    
    public int getFallo(){
        fallo = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        return fallo;
    }
    
    
    public int getDañoBaseMario(){
        dañoBase = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        return dañoBase;
    }


    public int getDañoMartillo(){
        dañoMartillo = ThreadLocalRandom.current().nextInt(15, 30 + 1);
        return dañoMartillo;
    }

    /**
     * @return the dañoSalto
     */
    public int getDañoSalto() {
        return dañoSalto;
    }

    /**
     * @param dañoSalto the dañoSalto to set
     */
    public void setDañoSalto(int dañoSalto) {
        this.dañoSalto = dañoSalto;
    }


    public int getDañoFlama() {
        return dañoFlama;
    }

    /**
     * @param dañoSalto the dañoSalto to set
     */
    

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
        subidaVida = ThreadLocalRandom.current().nextInt(3, 5 + 1);
        return subidaVida;
    }


    public int getSubidaMagia(){
        subidaMagia = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        return subidaMagia;
    }
    
}