package modulo; // Debe ser el mismo paquete que tus otras funciones

import javax.sound.sampled.*;
import java.io.File;

public class ReproductorBucle implements Runnable {
    private String rutaArchivo;
    private Clip clip;
    private volatile boolean encendido = true; // 'volatile' asegura que el hilo vea el cambio al detenerse

    public ReproductorBucle(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void run() {
        try {
            File archivo = new File(rutaArchivo);
            AudioInputStream stream = AudioSystem.getAudioInputStream(archivo);
            clip = AudioSystem.getClip();
            clip.open(stream);
            
            // Loop continuo
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            // El hilo se queda esperando aquí mientras la música suena
            while (encendido) {
                Thread.sleep(100); 
            }
            
            clip.stop();
            clip.close();
        } catch (Exception e) {
            System.err.println("Error en la música: " + e.getMessage());
        }
    }

    public void detener() {
        this.encendido = false;
    }
}