/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.supermariorpg;

import modulos.*;
import clases.Enemigo;
import clases.Jefe;
import clases.Jugador_Mario;
import clases.Mallow;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import modulo.ReproductorBucle;
import modulos.Ficheros;


public class SuperMarioRPG {
    
    static ArrayList<Jugador_Mario> hallOfFame;
    static File datosJugadores=new File("src/main/java/ficheros/datos.dat");
    static ArrayList<String> menuAtaquesMagicosMario = new ArrayList<>(); // array list de los ataques mágicos de Mario
    static ArrayList<String> menuAtaquesMagicosMallow = new ArrayList<>();
    static ArrayList<String> objetosInventario = new ArrayList<>(); //array list del inventario
    static String[] inventario = new String[objetosInventario.size()]; //array del inventario donde se verán los objetos
    static String listarInventario = "";
    static int exp = 0;
    static long t1, t2;
    
    
    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException{
        
        
        var fecha=Calendar.getInstance();

        cargarDatos();
        
        if (hallOfFame==null) hallOfFame=new ArrayList<>();
        
        //preguntar datos
        String nombre=FuncionesGraficas.pedirDatos("Nombre jugador", "Introduce tu nombre:");

        var Mario = new Jugador_Mario(nombre, fecha.getInstance(), 0, 1, 0, 20, 10, 20, 10, 0, 0, 0, 50, 70, 0, 0, 0);
        var Mallow = new Mallow(2, 20, 20, 0, 0, 30, 50, 0, 0, 0);



        //JUEGO PRINCIPAL
        
        escenaInicioJuego();
        escenaPrincipio(Mario);
        escenaCasaMario();
        escenaMushroomWay1(Mario);
        escenaMushroomWay2(Mario);
        escenaMushroomWay3(Mario);
        FuncionesGraficas.FotoMensajeSonido("Reunión Canciller", "src/main/java/img/reunionCanciller.gif", "", "src/main/java/sonidos/mushroomKingdom.mp3", 1.8, 1, false);
        FuncionesGraficas.FotoMensajeSonido("Robo", "src/main/java/img/cryingMallow.gif", "", "src/main/java/sonidos/mushroomKingdom.mp3", 1.8, 1, false);
        escenaReinoChamp(Mario, Mallow);
        FuncionesGraficas.FotoMensajeSonido("Huida Croco", "src/main/java/img/huidaCroco1.gif", "", "src/main/java/sonidos/banditWay.wav", 1.8, 0, false);
        escenaBanditWay1(Mario, Mallow);
        FuncionesGraficas.FotoMensajeSonido("Huida Croco", "src/main/java/img/huidaCroco2.gif", "", "src/main/java/sonidos/banditWay.wav", 1.8, 0, false);
        escenaBanditWay2(Mario, Mallow);
        escenaReinoInvadido(Mario, Mallow);
        
        
        
        t2=System.currentTimeMillis();

        long tiempo = (t2-t1)/1000;

        Mario.setTiempo(tiempo);

        //guardar datos y hallOfFame
        hallOfFame.add(Mario);
        
        
        FuncionesGraficas.mostrarDatos("Resultados", "Nombre: " + Mario.getNombre() + "\n" + "Nivel: " + Mario.getNivel() + "\n" + "Monedas: " + Mario.getMonedas() + "\n" + "Tiempo: " + Mario.getTiempo() + " segundos" + "\n" + "Fecha: " + Mario.getFecha().getInstance().getTime());
        
        
        salvarDatos();
        
        //fin main
    }
    



    
    
    //=============================================================================================================================================================================//




    
    //CARGAR Y SALVAR DATOS
    public static void cargarDatos() throws FileNotFoundException, ClassNotFoundException{
        
        try{
            hallOfFame=(ArrayList<Jugador_Mario>) Ficheros.leerTabla(datosJugadores);
            
        }catch (IOException e){
            FuncionesGraficas.warning("ERROR", "Error al leer el fichero " + e.getMessage());
        }
    }
    
    

    
    public static void salvarDatos() throws IOException{
        
        Ficheros.escribirTabla(hallOfFame, datosJugadores);
    }
    
    




    //=============================================================================================================================================================================//





    
    // inicio juego con menu
    public static void escenaInicioJuego(){
        String[] opciones = {"Jugar", "Instrucciones", "Hall of Fame", "Copyright" ,"Salir"};
        
        do{
            int menu=FuncionesGraficas.FotoMensajeMenuSonido("Inicio", opciones, "src/main/java/img/inicio.png", "Creado por: XxPablete2007xX\n \n \n", "src/main/java/sonidos/intro.mp3", 0.7, 1, false); 
            
            switch (menu){
                case 0:
                    t1 = System.currentTimeMillis();
                    return;
                    
                case 1:
                    FuncionesGraficas.FotoMensajeSonido("Instrucciones", "src/main/java/img/instrucciones.png", "Este juego recrea la primera parte del juego Super Mario RPG (SNES)\n \nTu misión será explorar y avanzar por el Reino Champiñón hasta\nconseguir la primera estrella del juego\n \nSuerte y diviértete ;)", "src/main/java/sonidos/instrucciones.mp3", 0.5, 1, false); 
                    FuncionesGraficas.FotoMensajeSonido("Información útil", "", "OBJETOS:\n¯¯¯¯¯¯¯¯¯¯\nChampiñón: Cura 30 de vida al personaje que lo use\nJarabe: Cura 10 puntos mágicos para realizar ataques mágicos\nRevivir: Revive al compañero desplomado en combate\n \n \nATAQUES MÁGICOS:\n¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\nSalto: Utiliza el famoso salto de Mario para hacer gran daño a un enemigo  -->  (-3 ptos. mág.)\nFlama: Lanza las míticas bolas de fuego para quemar a un enemigo  -->  (-5 ptos. mág.)\nRelámpago: Caen varios rayos a todos los enemigos  -->  (-2 ptos. mág.)", "src/main/java/sonidos/info.mp3", 1, 1, false);
                    break;
                    
                case 2:
                    if(hallOfFame==null || hallOfFame.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No hay partidas registradas");
                        
                    }else{
                        String mostrarHallOfFame = "";
                        for(int i=0; i<hallOfFame.size(); i++){
                            mostrarHallOfFame += "Nombre: " + hallOfFame.get(i).getNombre() + "     Nivel: " + hallOfFame.get(i).getNivel() + "     Monedas: " + hallOfFame.get(i).getMonedas() + "     Tiempo: " + hallOfFame.get(i).getTiempo() + " segundos" + "     Fecha: " + hallOfFame.get(i).getFecha().getInstance().getTime() + "\n";
                        }
                        FuncionesGraficas.mostrarDatos("Hall of Fame", mostrarHallOfFame);
                    }

                    break;


                case 3:
                    String mensajeLegal = "<html><body style='text-align: center; width: 350px;'>"
                                            + "<h2 style='color: red;'>AVISO IMPORTANTE</h2>"
                                            + "<hr>"
                                            + "Este es un <b>FAN GAME GRATUITO</b> creado exclusivamente<br>"
                                            + "por amor a la franquicia y con fines de entretenimiento.<br><br>"
                                            + "<i>Super Mario RPG</i>, sus personajes y música son<br>"
                                            + "propiedad intelectual de <b>NINTENDO</b>.<br><br>"
                                            + "Este proyecto no está afiliado, respaldado ni<br>"
                                            + "patrocinado por Nintendo.<br><br>"
                                            + "<b style='color: red;'>PROHIBIDA SU VENTA O DISTRIBUCIÓN COMERCIAL</b>"
                                            + "</body></html>";
                    FuncionesGraficas.FotoyMensaje("Aviso de Copyright", "", mensajeLegal, 1, false);
                    break;
                    
                case 4:
                    System.exit(0);
                    
            }
            
        }while(true);
    }
    
    


    //=============================================================================================================================================================================//




    
    // escena incial Peach + castillo Bowser
    public static void escenaPrincipio(Jugador_Mario Mario){
        
        
        var bowserCastle = new ReproductorBucle("src/main/java/sonidos/bowserCastle.wav");  // musica castillo bowser
        var hiloBowserCastle = new Thread(bowserCastle);
        
        var bowserFight = new ReproductorBucle("src/main/java/sonidos/peleaBowser.wav");  //musica pelea bowser
        var hiloBowserFight = new Thread(bowserFight);
        
        menuAtaquesMagicosMario.add("Salto");
        
        FuncionesGraficas.FotoMensajeSonido("", "src/main/java/img/escenaPeachSecuestrada.gif", "", "src/main/java/sonidos/escenaPeachSecuestrada.wav", 1.8, 0, false); // escena se raptan a Peach
        
        hiloBowserCastle.start();
        FuncionesGraficas.FotoyMensaje("", "src/main/java/img/peleaTropas.gif", "", 1.8, false); // escena Mario se topa con las tropas
        
        peleaTropas(Mario, bowserCastle, hiloBowserCastle);
        
        hiloBowserFight.start();
        FuncionesGraficas.FotoyMensaje("", "src/main/java/img/peleaBowser.gif", "", 1.8, false);
        
        peleaBowser(Mario, bowserFight, hiloBowserFight);
        
        FuncionesGraficas.FotoyMensaje("", "src/main/java/img/bowserVencido.gif", "", 1.8, false);
        bowserFight.detener();
        FuncionesGraficas.FotoyMensaje("", "src/main/java/img/temblorCastillo.gif", "", 1.8, false);
        FuncionesGraficas.FotoMensajeSonido("", "src/main/java/img/exor.gif", "", "src/main/java/sonidos/exor.wav", 1.8, 0, false);
        
    }
    
    


    //=============================================================================================================================================================================//




    
    // pelea tropas del castillo bowser
    public static void peleaTropas(Jugador_Mario Mario, ReproductorBucle bowserCastle, Thread hiloBowserCastle){
        
        Enemigo Tropa1 = new Enemigo("Tropa 1", 10, 10, 0, 0, 0, 0);
        Enemigo Tropa2 = new Enemigo("Tropa 2", 10, 10, 0, 0, 0, 0);
        Enemigo Tropa3 = new Enemigo("Tropa 3", 10, 10, 0, 0, 0, 0);
        Enemigo Tropa4 = new Enemigo("Tropa 4", 10, 10, 0, 0, 0, 0);
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Tropa1.getNombre());
        arrayListEnemigos.addLast(Tropa2.getNombre());
        arrayListEnemigos.addLast(Tropa3.getNombre());
        arrayListEnemigos.addLast(Tropa4.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaTropas.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Tropa 1: " + Tropa1.getVida() + "\nVida Tropa 2: " + Tropa2.getVida() + "\nVida Tropa 3: " + Tropa3.getVida() + "\nVida Tropa 4: " + Tropa4.getVida(), 0.5, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Tropa1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Tropa1.setVida(Tropa1.getVida()-Mario.getDañoBaseMario());
                            if (Tropa1.getVida()<=0){
                                Tropa1.setVida(0);
                                arrayListEnemigos.remove(Tropa1.getNombre());
                            }
                        }
                        
                    }else if (enemigoElegido.equals(Tropa2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Tropa2.setVida(Tropa2.getVida()-Mario.getDañoBaseMario());
                            if (Tropa2.getVida()<=0){
                                Tropa2.setVida(0);
                                arrayListEnemigos.remove(Tropa2.getNombre());
                            }
                        }
                        
                    }else if (enemigoElegido.equals(Tropa3.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Tropa3.setVida(Tropa3.getVida()-Mario.getDañoBaseMario());
                            if (Tropa3.getVida()<=0){
                                Tropa3.setVida(0);
                                arrayListEnemigos.remove(Tropa3.getNombre());
                            }
                        }
                        
                    }else if (enemigoElegido.equals(Tropa4.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Tropa4.setVida(Tropa4.getVida()-Mario.getDañoBaseMario());
                            if (Tropa4.getVida()<=0){
                                Tropa4.setVida(0);
                                arrayListEnemigos.remove(Tropa4.getNombre());
                            }
                        }
                        
                    }
                    
                    
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(Tropa1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Tropa1.setVida(Tropa1.getVida()-Mario.getDañoSalto());
                                if (Tropa1.getVida()<=0){
                                    Tropa1.setVida(0);
                                    arrayListEnemigos.remove(Tropa1.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Tropa2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Tropa2.setVida(Tropa2.getVida()-Mario.getDañoSalto());
                                if (Tropa2.getVida()<=0){
                                    Tropa2.setVida(0);
                                    arrayListEnemigos.remove(Tropa2.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Tropa3.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Tropa3.setVida(Tropa3.getVida()-Mario.getDañoSalto());
                                if (Tropa3.getVida()<=0){
                                    Tropa3.setVida(0);
                                    arrayListEnemigos.remove(Tropa3.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Tropa4.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Tropa4.setVida(Tropa4.getVida()-Mario.getDañoSalto());
                                if (Tropa4.getVida()<=0){
                                    Tropa4.setVida(0);
                                    arrayListEnemigos.remove(Tropa4.getNombre());
                                }
                                
                            }
                        break;
                            
                    }
                    break;
                
                    
                //objetos
                case 2:
                    FuncionesGraficas.warning("Atención", "No tienes objetos");
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(Tropa1.getVida()<=0 && Tropa2.getVida()<=0 && Tropa3.getVida()<=0 && Tropa4.getVida()<=0){
                bowserCastle.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n0 pts. Exp.\n0 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Tropa1.getVida()>0){
                Tropa1.getFallo();
                
                if(Tropa1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Tropa 1", "Falló el ataque");
                    
                }else{
                    int dañoTropa1 = Tropa1.getDañoBase();
                    Mario.setVida(Mario.getVida()-(dañoTropa1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Tropa 1", "Te quitó: " + (dañoTropa1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");                }
                
            }
            
            
            if(Tropa2.getVida()>0){
                Tropa2.getFallo();
                
                if(Tropa2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Tropa 2", "Falló el ataque");
                    
                }else{
                    int dañoTropa2 = Tropa2.getDañoBase();
                    Mario.setVida(Mario.getVida()-(dañoTropa2-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Tropa 2", "Te quitó: " + (dañoTropa2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            } 
            
            
            if(Tropa3.getVida()>0){
                Tropa3.getFallo();
                
                if(Tropa3.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Tropa 3", "Falló el ataque");
                    
                }else{
                    int dañoTropa3 = Tropa3.getDañoBase();
                    Mario.setVida(Mario.getVida()-(dañoTropa3-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Tropa 3", "Te quitó: " + (dañoTropa3-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");                }
                
            }
            
            
            if(Tropa4.getVida()>0){
                Tropa4.getFallo();
                
                if(Tropa4.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Tropa 4", "Falló el ataque");
                    
                }else{
                    int dañoTropa4 = Tropa4.getDañoBase();
                    Mario.setVida(Mario.getVida()-(dañoTropa4-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Tropa 4", "Te quitó: " + (dañoTropa4-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");                }
                
            }
            
            
            if(Mario.getVida()<=0){
                bowserCastle.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);  
        
    }
    
    




    //=============================================================================================================================================================================//




    
    
    //pelea Bowser
    public static void peleaBowser(Jugador_Mario Mario, ReproductorBucle bowserFight, Thread hiloBowserFight){
        
        Enemigo Bowser = new Enemigo("Bowser", 999, 999, 0, 0, 0, 0);
        Enemigo Cadena = new Enemigo("Cadena Kinklink", 60, 60, 0, 0, 0, 0);
        Mario.setVida(Mario.getVidaMax());
        Mario.setMagia(Mario.getMagiaMax());
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Bowser.getNombre());
        arrayListEnemigos.addLast(Cadena.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaBowser.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Bowser: " + Bowser.getVida() + "\nVida Cadena Kinklink: " + Cadena.getVida(), 0.5, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Bowser.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        }else Bowser.setVida(Bowser.getVida()-Mario.getDañoBaseMario());
                        
                    }else if (enemigoElegido.equals(Cadena.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else Cadena.setVida(Cadena.getVida()-Mario.getDañoBaseMario()); 
                    }
                    
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(Bowser.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Bowser.setVida(Bowser.getVida()-Mario.getDañoSalto());
                                
                            }else if (enemigoElegido.equals(Cadena.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Cadena.setVida(Cadena.getVida()-Mario.getDañoSalto());
                            }
                            
                        break;
                            
                    }
                    break;
                
                    
                //objetos
                case 2:
                    FuncionesGraficas.warning("Atención", "No tienes objetos");
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            
            if(Cadena.getVida()<=0) break;
            
            
            
            //TURNO ENEMIGOS

            if(Bowser.getVida()>0){
                Bowser.getFallo();
                
                if(Bowser.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Bowser", "Falló el ataque");
                    
                }else{
                    int dañoBowser = Bowser.getDañoBase();
                    Mario.setVida(Mario.getVida()-(dañoBowser-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Bowser", "Te quitó: " + (dañoBowser-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }
            
            
            
            if(Mario.getVida()<=0){
                bowserFight.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);
        
    }





    //=============================================================================================================================================================================//





    
    // ESCENA CASA MARIO
    public static void escenaCasaMario(){
        FuncionesGraficas.FotoMensajeSonido("", "src/main/java/img/escenaCasaMario.gif", "", "src/main/java/sonidos/escenaCasaMario.mp3", 1.8, 1, false);
        FuncionesGraficas.FotoMensajeSonido("Casa Mario", "src/main/java/img/casaMario.png", "Mario, malas noticias!!\nUna espada llamada Exor ha caído en Castillo Bowser y la\nPrincesa Peach ha desaparecido.\nDebemos ir al castillo a ver al Canciller para contarle la situación.\n \nTen esto, seguro que te resulta útil en el viaje.", "src/main/java/sonidos/casaMario.mp3", 0.45, 1, false);
        FuncionesGraficas.FotoMensajeSonido("", "src/main/java/img/seta.png", "Obtienes 3 champiñones", "src/main/java/sonidos/item.mp3", 0.3, 1, false);

        //añadir objetos al inventario y actualizar inventario 
        objetosInventario.add("Champiñón");
        objetosInventario.addLast("Champiñón");
        objetosInventario.addLast("Champiñón");
        inventario = objetosInventario.toArray(new String[0]);

        
        mostrarInventario();


        FuncionesGraficas.FotoMensajeSonido("Mapa", "src/main/java/img/mapToMushroomWay.gif", "", "src/main/java/sonidos/worldMap.mp3", 2, 1, false);
    }
    



    //=============================================================================================================================================================================//


    

    //MOSTRAR INVENTARIO COMPLETO
    public static void mostrarInventario(){
        listarInventario = "";

        if(objetosInventario.isEmpty()){
            listarInventario="No tienes objetos en el inventario";
        }else{
            for(String item: inventario){
            listarInventario += "- " + item + "\n";
            }
        }
        FuncionesGraficas.mostrarDatos("Inventario", listarInventario);

    }




    //=============================================================================================================================================================================//





    //ESCENA MUSHROOM WAY 1
    public static void escenaMushroomWay1(Jugador_Mario Mario){

        var mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
        var hiloMushroomWay = new Thread(mushroomWay);

        Mario.setVida(Mario.getVidaMax());
        Mario.setMagia(Mario.getMagiaMax());
        ArrayList<String> arrayListOpcMushWay1 = new ArrayList<>();
        arrayListOpcMushWay1.add("Pelear Goomba 1");
        arrayListOpcMushWay1.addLast("Abrir cofre 1");
        arrayListOpcMushWay1.addLast("Pelear SkyTropa 1");
        arrayListOpcMushWay1.addLast("Pelear SkyTropa 2");
        arrayListOpcMushWay1.addLast("Abrir cofre 2");
        arrayListOpcMushWay1.addLast("Pelear Goomba 2");
        arrayListOpcMushWay1.addLast("Inventario");
        arrayListOpcMushWay1.addLast("Continuar camino");

        hiloMushroomWay.start();

        do{
            String[] menuMushWay1 = arrayListOpcMushWay1.toArray(new String[0]);

            int opcMushWay1 = FuncionesGraficas.FotoMensajeMenu("Mushroom Way 1", menuMushWay1, "src/main/java/img/mushroomWay1.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.5, false);
        
            String opcElegida = menuMushWay1[opcMushWay1];


            if(opcElegida.equals("Pelear Goomba 1")){
                mushroomWay.detener();
                peleaGoomba1(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay1.remove(opcElegida);
                
                

            }else if(opcElegida.equals("Abrir cofre 1")){
                FuncionesGraficas.FotoMensajeSonido("Cofre 1", "src/main/java/img/moneda.png", "Obtienes 10 monedas", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);  //poner gif y sonido cofre con moneda
                Mario.setMonedas(Mario.getMonedas()+10);
                arrayListOpcMushWay1.remove(opcElegida);


            }else if(opcElegida.equals("Pelear SkyTropa 1")){
                mushroomWay.detener();
                peleaSkyTroopa1(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay1.remove(opcElegida);


            }else if(opcElegida.equals("Pelear SkyTropa 2")){
                mushroomWay.detener();
                peleaSkyTroopa2(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay1.remove(opcElegida);


            }else if(opcElegida.equals("Abrir cofre 2")){
                FuncionesGraficas.FotoMensajeSonido("Cofre 2", "src/main/java/img/jarabe.png", "Obtienes jarabe", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false);  
                objetosInventario.addLast("Jarabe");
                inventario = objetosInventario.toArray(new String[0]);
                arrayListOpcMushWay1.remove(opcElegida);


            }else if(opcElegida.equals("Pelear Goomba 2")){
                mushroomWay.detener();
                peleaGoomba2(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay1.remove(opcElegida);


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Continuar camino")){
                mushroomWay.detener();
                return;
            }

        }while(true);

    }





    //=============================================================================================================================================================================//





    //PELEA GOOMBA 1
    public static void peleaGoomba1(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Goomba1 = new Enemigo("Goomba 1", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        Enemigo Goomba2 = new Enemigo("Goomba 2", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Goomba1.getNombre());
        arrayListEnemigos.addLast(Goomba2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaGoomba1.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Goomba: " + Goomba1.getVida() + "\nVida Goomba: " + Goomba2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Goomba1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba1.setVida(Goomba1.getVida()-Mario.getDañoBaseMario());
                            if (Goomba1.getVida()<=0){
                                Goomba1.setVida(0);
                                arrayListEnemigos.remove(Goomba1.getNombre());
                            }
                        }
                        
                    }else if (enemigoElegido.equals(Goomba2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba2.setVida(Goomba2.getVida()-Mario.getDañoBaseMario());
                            if (Goomba2.getVida()<=0){
                                Goomba2.setVida(0);
                                arrayListEnemigos.remove(Goomba2.getNombre());
                            }
                        }
                    }
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoSalto());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Goomba2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba2.setVida(Goomba2.getVida()-Mario.getDañoSalto());
                                if (Goomba2.getVida()<=0){
                                    Goomba2.setVida(0);
                                    arrayListEnemigos.remove(Goomba2.getNombre());
                                }
                                
                            }
                        break;
                            
                    }
                
                break;
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(Goomba1.getVida()<=0 && Goomba2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n4 pts. Exp.\n5 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 4;
                Mario.setMonedas(Mario.getMonedas()+5);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Goomba1.getVida()>0){
                Goomba1.getFallo();
                
                if(Goomba1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba1 = Goomba1.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");                }
                
            }
            
            
            if(Goomba2.getVida()>0){
                Goomba2.getFallo();
                
                if(Goomba2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba2 = Goomba2.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba2-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            } 

            
            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);
    }





    //=============================================================================================================================================================================//


    



    //PELEA GOOMBA 2
    public static void peleaGoomba2(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Goomba1 = new Enemigo("Goomba 1", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        Enemigo Goomba2 = new Enemigo("Goomba 2", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        Enemigo Goomba3 = new Enemigo("Goomba 3", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Goomba1.getNombre());
        arrayListEnemigos.addLast(Goomba2.getNombre());
        arrayListEnemigos.addLast(Goomba3.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaGoomba2.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Goomba: " + Goomba1.getVida() + "\nVida Goomba: " + Goomba2.getVida() + "\nVida Goomba: " + Goomba3.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Goomba1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba1.setVida(Goomba1.getVida()-Mario.getDañoBaseMario());
                            if (Goomba1.getVida()<=0){
                                Goomba1.setVida(0);
                                arrayListEnemigos.remove(Goomba1.getNombre());
                            }
                        }
                        
                    }else if (enemigoElegido.equals(Goomba2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba2.setVida(Goomba2.getVida()-Mario.getDañoBaseMario());
                            if (Goomba2.getVida()<=0){
                                Goomba2.setVida(0);
                                arrayListEnemigos.remove(Goomba2.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Goomba3.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba3.setVida(Goomba3.getVida()-Mario.getDañoBaseMario());
                            if (Goomba3.getVida()<=0){
                                Goomba3.setVida(0);
                                arrayListEnemigos.remove(Goomba3.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoSalto());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Goomba2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba2.setVida(Goomba2.getVida()-Mario.getDañoSalto());
                                if (Goomba2.getVida()<=0){
                                    Goomba2.setVida(0);
                                    arrayListEnemigos.remove(Goomba2.getNombre());
                                }
                                
                            }else if (enemigoElegido.equals(Goomba3.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba3.setVida(Goomba3.getVida()-Mario.getDañoSalto());
                                if (Goomba3.getVida()<=0){
                                    Goomba3.setVida(0);
                                    arrayListEnemigos.remove(Goomba3.getNombre());
                                }
                                
                            }
                        break;
                            
                    }
                
                break;
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }
                
                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(Goomba1.getVida()<=0 && Goomba2.getVida()<=0 && Goomba3.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n6 pts. Exp.\n10 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 6;
                Mario.setMonedas(Mario.getMonedas()+10);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Goomba1.getVida()>0){
                Goomba1.getFallo();
                
                if(Goomba1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba1 = Goomba1.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");                }
                
            }
            
            
            if(Goomba2.getVida()>0){
                Goomba2.getFallo();
                
                if(Goomba2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba2 = Goomba2.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba2-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            } 


            if(Goomba3.getVida()>0){
                Goomba3.getFallo();
                
                if(Goomba3.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba3 = Goomba3.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba3-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba3-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            } 

            

            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }




    //=============================================================================================================================================================================//






    public static void peleaSkyTroopa1(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo SkyTroopa = new Enemigo("SkyTroopa", 10, 10, 8, 0, 0, 0);  //daño entre 1-3
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(SkyTroopa.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaSkyTropa1.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida SkyTroopa: " + SkyTroopa.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(SkyTroopa.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            SkyTroopa.setVida(SkyTroopa.getVida()-(Mario.getDañoBaseMario()-SkyTroopa.getDefensa()));
                            if (SkyTroopa.getVida()<=0){
                                SkyTroopa.setVida(0);
                                arrayListEnemigos.remove(SkyTroopa.getNombre());
                            }
                        }
                    }
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(SkyTroopa.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                SkyTroopa.setVida(SkyTroopa.getVida()-(Mario.getDañoSalto()-SkyTroopa.getDefensa()));
                                if (SkyTroopa.getVida()<=0){
                                    SkyTroopa.setVida(0);
                                    arrayListEnemigos.remove(SkyTroopa.getNombre());
                                }
                            }
                        break;
                            
                    }
                
                break;
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(SkyTroopa.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n2 pts. Exp.\n3 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 2;
                Mario.setMonedas(Mario.getMonedas()+3);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(SkyTroopa.getVida()>0){
                SkyTroopa.getFallo();
                
                if(SkyTroopa.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Falló el ataque");
                    
                }else{
                    int dañoSkyTroopa = SkyTroopa.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoSkyTroopa-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Te quitó: " + (dañoSkyTroopa-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }

            
            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }





    //=============================================================================================================================================================================//





    public static void peleaSkyTroopa2(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo SkyTroopa1 = new Enemigo("SkyTroopa 1", 10, 10, 8, 0, 0, 0);  //daño entre 1-3
        Enemigo SkyTroopa2 = new Enemigo("SkyTroopa 2", 10, 10, 8, 0, 0, 0);  //daño entre 1-3
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(SkyTroopa1.getNombre());
        arrayListEnemigos.addLast(SkyTroopa2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaSkyTropa2.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida SkyTroopa: " + SkyTroopa1.getVida() + "\nVida SkyTroopa: " + SkyTroopa2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(SkyTroopa1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            SkyTroopa1.setVida(SkyTroopa1.getVida()-(Mario.getDañoBaseMario()-SkyTroopa1.getDefensa()));
                            if (SkyTroopa1.getVida()<=0){
                                SkyTroopa1.setVida(0);
                                arrayListEnemigos.remove(SkyTroopa1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(SkyTroopa2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            SkyTroopa2.setVida(SkyTroopa2.getVida()-(Mario.getDañoBaseMario()-SkyTroopa2.getDefensa()));
                            if (SkyTroopa2.getVida()<=0){
                                SkyTroopa2.setVida(0);
                                arrayListEnemigos.remove(SkyTroopa2.getNombre());
                            }
                        }
                    }
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);
                    
                    //hacer lo mismo que con el ataque basico
                    switch (opcMagiaMario){
                        case 0:
                            opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            enemigoElegido = menuEnemigos[opcEnemigos];
                            
                            if (enemigoElegido.equals(SkyTroopa1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                SkyTroopa1.setVida(SkyTroopa1.getVida()-(Mario.getDañoSalto()-SkyTroopa1.getDefensa()));
                                if (SkyTroopa1.getVida()<=0){
                                    SkyTroopa1.setVida(0);
                                    arrayListEnemigos.remove(SkyTroopa1.getNombre());
                                }
                            }else if (enemigoElegido.equals(SkyTroopa2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                SkyTroopa2.setVida(SkyTroopa2.getVida()-(Mario.getDañoSalto()-SkyTroopa2.getDefensa()));
                                if (SkyTroopa2.getVida()<=0){
                                    SkyTroopa2.setVida(0);
                                    arrayListEnemigos.remove(SkyTroopa2.getNombre());
                                }
                            }
                        break;
                            
                    }
                
                break;
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(SkyTroopa1.getVida()<=0 && SkyTroopa2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n4 pts. Exp.\n6 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 4;
                Mario.setMonedas(Mario.getMonedas()+6);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(SkyTroopa1.getVida()>0){
                SkyTroopa1.getFallo();
                
                if(SkyTroopa1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Falló el ataque");
                    
                }else{
                    int dañoSkyTroopa1 = SkyTroopa1.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoSkyTroopa1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Te quitó: " + (dañoSkyTroopa1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }


            if(SkyTroopa2.getVida()>0){
                SkyTroopa2.getFallo();
                
                if(SkyTroopa2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Falló el ataque");
                    
                }else{
                    int dañoSkyTroopa2 = SkyTroopa2.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoSkyTroopa2-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque SkyTroopa", "Te quitó: " + (dañoSkyTroopa2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }

            
            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }





    //=============================================================================================================================================================================//






    //ESCENA MUSHROOM WAY 2  ---> terminas con 30exp máximo
    public static void escenaMushroomWay2(Jugador_Mario Mario){

        var mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
        var hiloMushroomWay = new Thread(mushroomWay);

        ArrayList<String> arrayListOpcMushWay2 = new ArrayList<>();
        arrayListOpcMushWay2.add("Pelear Goomba 1");
        arrayListOpcMushWay2.addLast("Abrir cofre 1");
        arrayListOpcMushWay2.addLast("Pelear Goomba 2");
        arrayListOpcMushWay2.addLast("Rescatar a Toad");
        arrayListOpcMushWay2.addLast("Abrir cofre 2");
        arrayListOpcMushWay2.addLast("Inventario");
        arrayListOpcMushWay2.addLast("Continuar camino");

        hiloMushroomWay.start();

        do{
            String[] menuMushWay2 = arrayListOpcMushWay2.toArray(new String[0]);

            int opcMushWay2 = FuncionesGraficas.FotoMensajeMenu("Mushroom Way 2", menuMushWay2, "src/main/java/img/mushroomWay2.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMagia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.6, false);
        
            String opcElegida = menuMushWay2[opcMushWay2];


            if(opcElegida.equals("Pelear Goomba 1")){
                mushroomWay.detener();
                peleaGoomba1(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay2.remove(opcElegida);
                
                

            }else if(opcElegida.equals("Abrir cofre 1")){
                FuncionesGraficas.FotoMensajeSonido("Cofre 1", "src/main/java/img/flor.png", "Obtienes 1 flor.\n \nTus puntos mágicos incrementan +1 ptos.", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false); 
                Mario.setMagiaMax(Mario.getMagiaMax()+1);
                arrayListOpcMushWay2.remove(opcElegida);


            }else if(opcElegida.equals("Rescatar a Toad")){
                mushroomWay.detener();
                peleaSkyTroopa2(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                FuncionesGraficas.FotoyMensaje("Rescate Toad", "src/main/java/img/mushroomWay2Toad.png", "Gracias por rescatarme, Mario.\nDebemos seguir y llegar con el Canciller.\nPor cierto, mira lo que he encontrado.", 0.5, false);
                FuncionesGraficas.FotoMensajeSonido("Recompensa", "src/main/java/img/flor.png", "Obtienes 1 flor.\n \nTus puntos mágicos incrementan +1 ptos.", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false);
                FuncionesGraficas.FotoMensajeSonido("Recompensa", "src/main/java/img/seta.png", "Obtienes 3 champiñones", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false);
                FuncionesGraficas.FotoMensajeSonido("Recompensa", "src/main/java/img/jarabe.png", "Obtienes 2 jarabes", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false);
                objetosInventario.addLast("Champiñón");
                objetosInventario.addLast("Champiñón");
                objetosInventario.addLast("Champiñón");
                objetosInventario.addLast("Jarabe");
                objetosInventario.addLast("Jarabe");
                inventario = objetosInventario.toArray(new String[0]);
                Mario.setMagiaMax(Mario.getMagiaMax()+1);
                arrayListOpcMushWay2.remove(opcElegida);


            }else if(opcElegida.equals("Abrir cofre 2")){
                FuncionesGraficas.FotoMensajeSonido("Cofre 2", "src/main/java/img/setaCurar.png", "Vida y mágia recuperadas", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false);  //poner gif y sonido cofre con moneda
                Mario.setVida(Mario.getVidaMax());
                Mario.setMagia(Mario.getMagiaMax());
                arrayListOpcMushWay2.remove(opcElegida);


            }else if(opcElegida.equals("Pelear Goomba 2")){
                mushroomWay.detener();
                peleaGoomba2(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay2.remove(opcElegida);


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Continuar camino")){
                mushroomWay.detener();
                return;
            }

        }while(true);

    }






    //=============================================================================================================================================================================//








    //LEVEL UP
    public static void levelUpMario(Jugador_Mario Mario){

        int subidaVida = Mario.getSubidaVida();
        int subidaMagia = Mario.getSubidaMagia();
        FuncionesGraficas.FotoMensajeSonido("Level Up Mario", "src/main/java/img/levelUpMario.png", "Subes a nivel " + (Mario.getNivel() + 1) + "\n \n \nVida Mario: " + Mario.getVidaMax() + " -----> " + (Mario.getVidaMax()+subidaVida) + "\n \nMágia: " + Mario.getMagiaMax() + " -----> " + (Mario.getMagiaMax()+subidaMagia), "src/main/java/sonidos/victory.mp3", 0.5, 1, false);
        Mario.setNivel(Mario.getNivel()+1);
        Mario.setVidaMax(Mario.getVidaMax()+subidaVida);
        Mario.setMagiaMax(Mario.getMagiaMax()+subidaMagia);
        Mario.setVida(Mario.getVidaMax());
        Mario.setMagia(Mario.getMagiaMax());

    }



    public static void levelUpMallow(Jugador_Mario Mario, Mallow Mallow){

        int subidaVida = Mallow.getSubidaVida();
        int subidaMagia = Mario.getSubidaMagia();
        FuncionesGraficas.FotoMensajeSonido("Level Up Mallow", "src/main/java/img/levelUpMallow.png", "Subes a nivel " + (Mallow.getNivel() + 1) + "\n \n \nVida Mallow: " + Mallow.getVidaMax() + " -----> " + (Mallow.getVidaMax()+subidaVida) + "\n \nMágia: " + Mario.getMagiaMax() + " -----> " + (Mario.getMagiaMax()+subidaMagia), "src/main/java/sonidos/victory.mp3", 0.5, 1, false);
        Mallow.setNivel(Mallow.getNivel()+1);
        Mallow.setVidaMax(Mallow.getVidaMax()+subidaVida);
        Mario.setMagiaMax(Mario.getMagiaMax()+subidaMagia);
        Mallow.setVida(Mallow.getVidaMax());
        Mario.setMagia(Mario.getMagiaMax());
    }





    //=============================================================================================================================================================//





    //ESCENA MUSHROOM WAY 3 + JEFE   ------>  terminas con 51exp máximo
    public static void escenaMushroomWay3(Jugador_Mario Mario){

        var mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
        var hiloMushroomWay = new Thread(mushroomWay);

        ArrayList<String> arrayListOpcMushWay3 = new ArrayList<>();
        arrayListOpcMushWay3.add("Pelear Spikey 1");
        arrayListOpcMushWay3.addLast("Pelear Spikey 2");
        arrayListOpcMushWay3.addLast("Rescatar a Toad");
        arrayListOpcMushWay3.addLast("Inventario");
        
        hiloMushroomWay.start();

        do{
            String[] menuMushWay3 = arrayListOpcMushWay3.toArray(new String[0]);

            int opcMushWay3 = FuncionesGraficas.FotoMensajeMenu("Mushroom Way 3", menuMushWay3, "src/main/java/img/mushroomWay3.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.6, false);
        
            String opcElegida = menuMushWay3[opcMushWay3];


            if(opcElegida.equals("Pelear Spikey 1")){
                mushroomWay.detener();
                peleaSpikey1(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.addLast("Flama");
                }
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay3.remove(opcElegida);
                
                

            }else if(opcElegida.equals("Rescatar a Toad")){
                mushroomWay.detener();
                jefeHammerBro(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                FuncionesGraficas.FotoyMensaje("Rescate Toad", "src/main/java/img/rescateToad.png", "Muchas gracias, Mario. Qué miedo he pasado.\nVaya, parece que a ese Hammer Bro se le ha caído su martillo.\nYa sé, llévalo contigo y úsalo en combate.", 0.4, false);
                FuncionesGraficas.FotoMensajeSonido("Obtienes nueva arma", "src/main/java/img/obtenerMartillo.png", "Obtienes: Martillo\n \nEquipas: Martillo\n \nTu ataque ha aumentado", "src/main/java/sonidos/getWeapon.mp3", 0.4, 1, false);
                arrayListOpcMushWay3.remove(opcElegida);
                arrayListOpcMushWay3.addLast("Continuar camino");


            }else if(opcElegida.equals("Pelear Spikey 2")){
                mushroomWay.detener();
                peleaSpikey2(Mario);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.addLast("Flama");
                }
                mushroomWay = new ReproductorBucle("src/main/java/sonidos/mushroomWay.wav");
                hiloMushroomWay = new Thread(mushroomWay);
                hiloMushroomWay.start();
                arrayListOpcMushWay3.remove(opcElegida);


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Continuar camino")){
                mushroomWay.detener();
                FuncionesGraficas.FotoMensajeSonido("Mapa", "src/main/java/img/mapToMushroomKingdom.gif", "", "src/main/java/sonidos/worldMap.mp3", 1.8, 1, false);
                return;
            }

        }while(true);

    }






    //==========================================================================================================================================================//






    
    //PELEA SPIKEY 1
    public static void peleaSpikey1(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Goomba1 = new Enemigo("Goomba", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        Enemigo Spikey1 = new Enemigo("Spikey", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Goomba1.getNombre());
        arrayListEnemigos.addLast(Spikey1.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaSpikey1.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida SkyTroopa: " + Goomba1.getVida() + "\nVida SkyTroopa: " + Spikey1.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Goomba1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba1.setVida(Goomba1.getVida()-Mario.getDañoBaseMario());
                            if (Goomba1.getVida()<=0){
                                Goomba1.setVida(0);
                                arrayListEnemigos.remove(Goomba1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoBaseMario()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoSalto());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                            }else if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);
                                }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoFlama());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                            }else if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoFlama()-Spikey1.getDefensa()));
                                if (Spikey1.getVida()<=0){
                                    Spikey1.setVida(0);
                                    arrayListEnemigos.remove(Spikey1.getNombre());
                                }

                            }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(Goomba1.getVida()<=0 && Spikey1.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n5 pts. Exp.\n8 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 5;
                Mario.setMonedas(Mario.getMonedas()+8);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Goomba1.getVida()>0){
                Goomba1.getFallo();
                
                if(Goomba1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                }else{
                    int dañoGoomba1 = Goomba1.getDañoGoomba();
                    Mario.setVida(Mario.getVida()-(dañoGoomba1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Goomba", "Te quitó: " + (dañoGoomba1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }


            if(Spikey1.getVida()>0){
                Spikey1.getFallo();
                
                if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                }else{
                    int dañoSpikey1 = Spikey1.getDañoSpikey();
                    Mario.setVida(Mario.getVida()-(dañoSpikey1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Spikey", "Te quitó: " + (dañoSpikey1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }

            
            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }






    //=========================================================================================================================================================//






    //PELEA SPIKEY 2
    public static void peleaSpikey2(Jugador_Mario Mario){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Spikey1 = new Enemigo("Spikey 1", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        Enemigo Spikey2 = new Enemigo("Spikey 2", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Spikey1.getNombre());
        arrayListEnemigos.addLast(Spikey2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla", menuMario, "src/main/java/img/peleaSpikey2.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida SkyTroopa: " + Spikey1.getVida() + "\nVida SkyTroopa: " + Spikey2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoBaseMario()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey2.setVida(Spikey2.getVida()-(Mario.getDañoBaseMario()-Spikey2.getDefensa()));
                            if (Spikey2.getVida()<=0){
                                Spikey2.setVida(0);
                                arrayListEnemigos.remove(Spikey2.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                            if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);

                            }else if (enemigoElegido.equals(Spikey2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);
                                }

                    }else if(magiaElegido.equals("Flama")){
                            if (Mario.getMagia()<5){
                                FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                                break;
                            }

                            if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoFlama()-Spikey1.getDefensa()));
                                if (Spikey1.getVida()<=0){
                                    Spikey1.setVida(0);
                                    arrayListEnemigos.remove(Spikey1.getNombre());
                                }
                            }else if (enemigoElegido.equals(Spikey2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey2.setVida(Spikey2.getVida()-(Mario.getDañoFlama()-Spikey2.getDefensa()));
                                if (Spikey2.getVida()<=0){
                                    Spikey2.setVida(0);
                                    arrayListEnemigos.remove(Spikey2.getNombre());
                                }

                            }
                    }  
                     
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(Spikey1.getVida()<=0 && Spikey2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n6 pts. Exp.\n10 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 6;
                Mario.setMonedas(Mario.getMonedas()+10);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Spikey1.getVida()>0){
                Spikey1.getFallo();
                
                if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                }else{
                    int dañoSpikey1 = Spikey1.getDañoSpikey();
                    Mario.setVida(Mario.getVida()-(dañoSpikey1-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Spikey", "Te quitó: " + (dañoSpikey1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }


            if(Spikey2.getVida()>0){
                Spikey2.getFallo();
                
                if(Spikey2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                }else{
                    int dañoSpikey2 = Spikey2.getDañoSpikey();
                    Mario.setVida(Mario.getVida()-(dañoSpikey2-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque Spikey", "Te quitó: " + (dañoSpikey2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                }
                
            }

            
            
            if(Mario.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }






    //=========================================================================================================================================================================//







    //JEFE HAMMER BRO
    public static void jefeHammerBro(Jugador_Mario Mario){

        var miniJefe = new ReproductorBucle("src/main/java/sonidos/miniJefe.wav");
        var hiloMiniJefe = new Thread(miniJefe);

        hiloMiniJefe.start();

        Jefe HammerBro1 = new Jefe("Hammer Bro 1", 50, 50, 10, 30, 0, 8, 0, 0, 0);  //dañoBase entre 3-4 y dañoMagico 8
        Jefe HammerBro2 = new Jefe("Hammer Bro 2", 50, 50, 10, 30, 0, 8, 0, 0, 0);  //dañoBase entre 3-4 y dañoMagico 8
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(HammerBro1.getNombre());
        arrayListEnemigos.addLast(HammerBro2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0); 
            
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Mini Jefe - Hammer Bro", menuMario, "src/main/java/img/jefeHammerBro.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida SkyTroopa: " + HammerBro1.getVida() + "\nVida SkyTroopa: " + HammerBro2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(HammerBro1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            HammerBro1.setVida(HammerBro1.getVida()-(Mario.getDañoBaseMario()-HammerBro1.getDefensa()));
                            if (HammerBro1.getVida()<=0){
                                HammerBro1.setVida(0);
                                arrayListEnemigos.remove(HammerBro1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(HammerBro2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            HammerBro2.setVida(HammerBro2.getVida()-(Mario.getDañoBaseMario()-HammerBro2.getDefensa()));
                            if (HammerBro2.getVida()<=0){
                                HammerBro2.setVida(0);
                                arrayListEnemigos.remove(HammerBro2.getNombre());
                            }
                        }
                    }
                    break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                            if (enemigoElegido.equals(HammerBro1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                HammerBro1.setVida(HammerBro1.getVida()-(Mario.getDañoSalto()-HammerBro1.getDefensaMag()));
                                if (HammerBro1.getVida()<=0){
                                    HammerBro1.setVida(0);
                                    arrayListEnemigos.remove(HammerBro1.getNombre());
                                }

                            }else if (enemigoElegido.equals(HammerBro2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                HammerBro2.setVida(HammerBro2.getVida()-(Mario.getDañoSalto()-HammerBro2.getDefensaMag()));
                                if (HammerBro2.getVida()<=0){
                                    HammerBro2.setVida(0);
                                    arrayListEnemigos.remove(HammerBro2.getNombre());
                                }
                            }

                    }else if(magiaElegido.equals("Flama")){
                            if (Mario.getMagia()<5){
                                FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                                break;
                            }

                            if (enemigoElegido.equals(HammerBro1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                HammerBro1.setVida(HammerBro1.getVida()-(Mario.getDañoFlama()-HammerBro1.getDefensaMag()));
                                if (HammerBro1.getVida()<=0){
                                    HammerBro1.setVida(0);
                                    arrayListEnemigos.remove(HammerBro1.getNombre());
                                }

                            }else if (enemigoElegido.equals(HammerBro2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                HammerBro2.setVida(HammerBro2.getVida()-(Mario.getDañoFlama()-HammerBro2.getDefensaMag()));
                                if (HammerBro2.getVida()<=0){
                                    HammerBro2.setVida(0);
                                    arrayListEnemigos.remove(HammerBro2.getNombre());
                                }

                            }
                    }
                
                break;
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                    break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            
            
            if(HammerBro1.getVida()<=0 && HammerBro2.getVida()<=0){
                miniJefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n10 pts. Exp.\n30 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 10;
                Mario.setMonedas(Mario.getMonedas()+30);
                break;
            }
            
            
            
            //TURNO ENEMIGOS


            if(HammerBro1.getVida()>0){
                
                HammerBro1.getAtckMagico();

                if(HammerBro1.getAtckMagico()<=10){
                    Mario.setVida(Mario.getVida()-(HammerBro1.getDañoMagico()-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque especial HammerBro", "Te quitó: " + (HammerBro1.getDañoMagico()-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");

                }else{
                    HammerBro1.getFallo();
                
                    if(HammerBro1.getFallo()<=20){
                        FuncionesGraficas.warning("Ataque Hammer Bro", "Falló el ataque");
                    
                    }else{
                        int dañoHammerBro1 = HammerBro1.getDañoBase();
                        Mario.setVida(Mario.getVida()-(dañoHammerBro1-Mario.getDefensa()));
                        FuncionesGraficas.warning("Ataque Hammer Bro", "Te quitó: " + (dañoHammerBro1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }
                
                }

            }




            if(HammerBro2.getVida()>0){
                
                HammerBro2.getAtckMagico();

                if(HammerBro2.getAtckMagico()<=15){
                    Mario.setVida(Mario.getVida()-(HammerBro2.getDañoMagico()-Mario.getDefensa()));
                    FuncionesGraficas.warning("Ataque especial HammerBro", "Te quitó: " + (HammerBro2.getDañoMagico()-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");

                }else{
                    HammerBro2.getFallo();
                
                    if(HammerBro2.getFallo()<=20){
                        FuncionesGraficas.warning("Ataque Hammer Bro", "Falló el ataque");
                    
                    }else{
                        int dañoHammerBro2 = HammerBro2.getDañoBase();
                        Mario.setVida(Mario.getVida()-(dañoHammerBro2-Mario.getDefensa()));
                        FuncionesGraficas.warning("Ataque Hammer Bro", "Te quitó: " + (dañoHammerBro2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }
                
                }

            }

            
            
            if(Mario.getVida()<=0){
                miniJefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }







    //=================================================================================================================================================================//







    //ESCENA CASTILLO
    public static void escenaReinoChamp(Jugador_Mario Mario, Mallow Mallow){

        var mushroomKingdom = new ReproductorBucle("src/main/java/sonidos/mushroomKingdom.wav");
        var hiloMushroomKingdom = new Thread(mushroomKingdom);

        ArrayList<String> arrayListOpcMushKingd = new ArrayList<>();
        arrayListOpcMushKingd.add("Hablar con Mallow");
        arrayListOpcMushKingd.addLast("Tienda");
        arrayListOpcMushKingd.addLast("Posada");
        arrayListOpcMushKingd.addLast("Inventario");
        
        hiloMushroomKingdom.start();

        do{
            String[] menuMushKingd = arrayListOpcMushKingd.toArray(new String[0]);

            int opcMushKingd = FuncionesGraficas.FotoMensajeMenu("Reino Champiñón", menuMushKingd, "src/main/java/img/mushroomKingdom.png", "Elige una opción:", 0.7, false);
        
            String opcElegida = menuMushKingd[opcMushKingd];


            if(opcElegida.equals("Tienda")){
                tienda(Mario);
                
                

            }else if(opcElegida.equals("Hablar con Mallow")){
                FuncionesGraficas.FotoyMensaje("Charla con Mallow", "src/main/java/img/charlaMallow.gif", "", 1.8, false);
                FuncionesGraficas.mostrarMensaje("Nuevo miembro equipo", "Mallow se ha unido al equipo", 1);
                menuAtaquesMagicosMallow.add("Relámpago");
                arrayListOpcMushKingd.remove(opcElegida);
                arrayListOpcMushKingd.addLast("Continuar camino");


            }else if(opcElegida.equals("Posada")){
                boolean resp = FuncionesGraficas.pedirBool("Posada", "¿Quieres tomar un descanso?  -->  Coste: 10 monedas");

                if(resp==false){
                    FuncionesGraficas.mostrarMensaje("Despedida", "Hasta la próxima", 1);
                    continue;
                }

                if(resp==true && Mario.getMonedas()>=10){
                    Mario.setMonedas(Mario.getMonedas()-10);
                    Mario.setVida(Mario.getVidaMax());
                    Mario.setMagia(Mario.getMagiaMax());
                    Mallow.setVida(Mallow.getVidaMax());
                    FuncionesGraficas.mostrarMensaje("Descanso", "Vida y mágia recuperadas", 1);
                
                }else FuncionesGraficas.warning("Atención", "No tienes suficientes monedas");


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Continuar camino")){
                FuncionesGraficas.FotoyMensaje("Ladrón huye", "src/main/java/img/ladronHuye.gif", "", 1.8, false);
                mushroomKingdom.detener();
                FuncionesGraficas.FotoMensajeSonido("Mapa", "src/main/java/img/mapToBanditWay.gif", "", "src/main/java/sonidos/worldMap.mp3", 1.8, 1, false);
                return;
            }

        }while(true);



    }







    //==========================================================================================================================================================//







    //TIENDA
    public static void tienda(Jugador_Mario Mario){

        String[] menuTienda = {"Comprar", "Vender", "Salir"};
        String[] menuComprar = {"Champiñón  --->  4 monedas", "Jarabe  ---------->  8 monedas", "Revivir  --------->  5 monedas"};


        do{
            int opc = FuncionesGraficas.FotoMensajeMenu("Tienda", menuTienda, "src/main/java/img/tienda.png", "Monedas: " + Mario.getMonedas() + "\n \n \nElige una opción:", 0.7, false);  //poner foto tienda

            switch(opc){
                case 0:
                    int elegirObjComprar = FuncionesGraficas.menuDesplegable("Comprar", "Elige un objeto para comprar:", menuComprar);
                    String objComprarElegido = menuComprar[elegirObjComprar];

                    if(objComprarElegido.equals("Champiñón  --->  4 monedas")){
                        if(Mario.getMonedas()>=4){
                            objetosInventario.addLast("Champiñón");
                            inventario = objetosInventario.toArray(new String[0]);
                            Mario.setMonedas(Mario.getMonedas()-4);
                            FuncionesGraficas.FotoMensajeSonido("Comprar", "src/main/java/img/seta.png", "Has comprado 1 champiñón", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);
                            
                        }else FuncionesGraficas.warning("Atención", "No tienes suficientes monedas");
                            
                    }else if(objComprarElegido.equals("Jarabe  ---------->  8 monedas")){
                        if(Mario.getMonedas()>=8){
                            objetosInventario.addLast("Jarabe");
                            inventario = objetosInventario.toArray(new String[0]);
                            Mario.setMonedas(Mario.getMonedas()-8);
                            FuncionesGraficas.FotoMensajeSonido("Comprar", "src/main/java/img/jarabe.png", "Has comprado 1 jarabe", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);

                        }else FuncionesGraficas.warning("Atención", "No tienes suficientes monedas");
                            
                    }else if(objComprarElegido.equals("Revivir  --------->  5 monedas")){
                        if(Mario.getMonedas()>=5){
                            objetosInventario.addLast("Revivir");
                            inventario = objetosInventario.toArray(new String[0]);
                            Mario.setMonedas(Mario.getMonedas()-5);
                            FuncionesGraficas.FotoMensajeSonido("Comprar", "src/main/java/img/pocionRevivir.png", "Has comprado 1 poción de revivir", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);

                        }else FuncionesGraficas.warning("Atención", "No tienes suficientes monedas");  
                    }

                    break;

                case 1:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.mostrarMensaje("Atención", "Tienes el inventario vacío", 2);
                        break;
                    }
                    

                    int elegirObjVender = FuncionesGraficas.menuDesplegable("Vender", "Elige un objeto para vender:", inventario);
                    String objVenderElegido = inventario[elegirObjVender];

                    if(objVenderElegido.equals("Champiñón")){
                        objetosInventario.remove(objVenderElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                        Mario.setMonedas(Mario.getMonedas()+2);
                        FuncionesGraficas.FotoMensajeSonido("Vender", "src/main/java/img/seta.png", "Has vendido 1 champiñón\nObtienes +2 monedas", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);
                            
                            
                    }else if(objVenderElegido.equals("Jarabe")){
                        objetosInventario.remove(objVenderElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                        Mario.setMonedas(Mario.getMonedas()+4);
                        FuncionesGraficas.FotoMensajeSonido("Vender", "src/main/java/img/jarabe.png", "Has vendido 1 jarabe\nObtienes +4 monedas", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);
                            
                    }else if(objVenderElegido.equals("Revivir")){
                        objetosInventario.remove(objVenderElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                        Mario.setMonedas(Mario.getMonedas()+3);
                        FuncionesGraficas.FotoMensajeSonido("Vender", "src/main/java/img/pocionRevivir.png", "Has vendido 1 poción de revivir\nObtienes +3 monedas", "src/main/java/sonidos/moneda.mp3", 0.3, 1, false);
                    }

                    break;

                case 2:
                    return;

            }

        }while(true);

    }
    







    //=========================================================================================================================================================================================//






    

    //ESCENA BANDIT'S WAY 1    acabas con 81exp máximo
    public static void escenaBanditWay1(Jugador_Mario Mario, Mallow Mallow){

        var banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
        var hiloBanditWay = new Thread(banditWay);

        ArrayList<String> arrayListOpcBanditWay1 = new ArrayList<>();
        arrayListOpcBanditWay1.add("Pelear Goomba");
        arrayListOpcBanditWay1.addLast("Pelear Spikey 1");
        arrayListOpcBanditWay1.addLast("Pelear Spikey 2");
        arrayListOpcBanditWay1.addLast("Abrir cofre");
        arrayListOpcBanditWay1.addLast("Inventario");
        arrayListOpcBanditWay1.addLast("Continuar camino");
        

        hiloBanditWay.start();

        do{
            String[] menuBanditWay1 = arrayListOpcBanditWay1.toArray(new String[0]);

            int opcBanditWay1 = FuncionesGraficas.FotoMensajeMenu("Bandit's Way 1", menuBanditWay1, "src/main/java/img/banditWay1.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.7, false);
        
            String opcElegida = menuBanditWay1[opcBanditWay1];


            if(opcElegida.equals("Pelear Goomba")){
                banditWay.detener();
                peleaGoomba3(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
                hiloBanditWay = new Thread(banditWay);
                hiloBanditWay.start();
                arrayListOpcBanditWay1.remove(opcElegida);
                
                

            }else if(opcElegida.equals("Abrir cofre")){
                FuncionesGraficas.FotoMensajeSonido("Cofre", "src/main/java/img/flor.png", "Obtienes 1 flor.\n \nTus puntos mágicos incrementan +1 ptos.", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false); 
                Mario.setMagiaMax(Mario.getMagiaMax()+1);
                arrayListOpcBanditWay1.remove(opcElegida);


            }else if(opcElegida.equals("Pelear Spikey 1")){
                banditWay.detener();
                peleaSpikey3(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
                hiloBanditWay = new Thread(banditWay);
                hiloBanditWay.start();
                arrayListOpcBanditWay1.remove(opcElegida);


            }else if(opcElegida.equals("Pelear Spikey 2")){
                banditWay.detener();
                peleaSpikey4(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
                hiloBanditWay = new Thread(banditWay);
                hiloBanditWay.start();
                arrayListOpcBanditWay1.remove(opcElegida);


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Continuar camino")){
                banditWay.detener();
                return;
            }

        }while(true);

    }








    //================================================================================================================================================================================================//
    







    //PELEA GOOMBA 3
    public static void peleaGoomba3(Jugador_Mario Mario, Mallow Mallow){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Goomba1 = new Enemigo("Goomba", 16, 16, 0, 0, 0, 0);  //daño entre 1-3
        Enemigo Spikey1 = new Enemigo("Spikey", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        Enemigo Frogog = new Enemigo("Frogog", 80, 80, 6, 0, 0, 0);   //daño entre 3-5
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Goomba1.getNombre());
        arrayListEnemigos.addLast(Spikey1.getNombre());
        arrayListEnemigos.addLast(Frogog.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mario", menuMario, "src/main/java/img/peleaGoomba3.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Goomba: " + Goomba1.getVida() + "\nVida Spikey: " + Spikey1.getVida() + "\nVida Frogog: " + Frogog.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Goomba1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba1.setVida(Goomba1.getVida()-Mario.getDañoMartillo());
                            if (Goomba1.getVida()<=0){
                                Goomba1.setVida(0);
                                arrayListEnemigos.remove(Goomba1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoMartillo()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog.setVida(Frogog.getVida()-(Mario.getDañoMartillo()-Frogog.getDefensa()));
                            if (Frogog.getVida()<=0){
                                Frogog.setVida(0);
                                arrayListEnemigos.remove(Frogog.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoSalto());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                        }else if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);
                        
                        }else if (enemigoElegido.equals(Frogog.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Frogog.setVida(Frogog.getVida()-(Mario.getDañoSalto()-Frogog.getDefensa()));
                                if (Frogog.getVida()<=0){
                                    Frogog.setVida(0);
                                    arrayListEnemigos.remove(Frogog.getNombre());
                                }
                        }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Goomba1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Goomba1.setVida(Goomba1.getVida()-Mario.getDañoFlama());
                                if (Goomba1.getVida()<=0){
                                    Goomba1.setVida(0);
                                    arrayListEnemigos.remove(Goomba1.getNombre());
                                }
                        }else if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoFlama()-Spikey1.getDefensa()));
                                if (Spikey1.getVida()<=0){
                                    Spikey1.setVida(0);
                                    arrayListEnemigos.remove(Spikey1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Frogog.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Frogog.setVida(Frogog.getVida()-(Mario.getDañoFlama()-Frogog.getDefensa()));
                                if (Frogog.getVida()<=0){
                                    Frogog.setVida(0);
                                    arrayListEnemigos.remove(Frogog.getNombre());
                                }
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Goomba1.getVida()<=0 && Spikey1.getVida()<=0 && Frogog.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n8 pts. Exp.\n10 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 8;
                Mario.setMonedas(Mario.getMonedas()+10);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mallow", menuMario, "src/main/java/img/peleaGoomba3.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Goomba: " + Goomba1.getVida() + "\nVida Spikey: " + Spikey1.getVida() + "\nVida Frogog: " + Frogog.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Goomba1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Goomba1.setVida(Goomba1.getVida()-Mallow.getDañoBaseMallow());
                            if (Goomba1.getVida()<=0){
                                Goomba1.setVida(0);
                                arrayListEnemigos.remove(Goomba1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mallow.getDañoBaseMallow()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog.setVida(Frogog.getVida()-(Mallow.getDañoBaseMallow()-Frogog.getDefensa()));
                            if (Frogog.getVida()<=0){
                                Frogog.setVida(0);
                                arrayListEnemigos.remove(Frogog.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Goomba1.setVida(Goomba1.getVida()-Mallow.getDañoRelampago());
                        if (Goomba1.getVida()<=0){
                            Goomba1.setVida(0);
                            arrayListEnemigos.remove(Goomba1.getNombre());
                        }    
                       
                        Spikey1.setVida(Spikey1.getVida()-Mallow.getDañoRelampago());
                        if (Spikey1.getVida()<=0){
                            Spikey1.setVida(0);
                            arrayListEnemigos.remove(Spikey1.getNombre());
                        }        
                      
                        Frogog.setVida(Frogog.getVida()-Mallow.getDañoRelampago());
                        if (Frogog.getVida()<=0){
                            Frogog.setVida(0);
                            arrayListEnemigos.remove(Frogog.getNombre());
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }
                
                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mallow.setVida(Mallow.getVida()+30);
                        if(Mallow.getVida()>Mallow.getVidaMax()) Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Goomba1.getVida()<=0 && Spikey1.getVida()<=0 && Frogog.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n8 pts. Exp.\n10 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 8;
                Mario.setMonedas(Mario.getMonedas()+10);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Goomba1.getVida()>0){
                Goomba1.getObjetivo();
                Goomba1.getFallo();

                if(Goomba1.getObjetivo()==1){
                    if(Goomba1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                    }else{
                        int dañoGoomba1 = Goomba1.getDañoGoomba();
                        Mario.setVida(Mario.getVida()-(dañoGoomba1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Goomba", "Goomba ataca a Mario\nTe quitó: " + (dañoGoomba1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Goomba1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Goomba", "Falló el ataque");
                    
                    }else{
                        int dañoGoomba1 = Goomba1.getDañoGoomba();
                        Mallow.setVida(Mallow.getVida()-(dañoGoomba1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Goomba", "Goomba ataca a Mallow\nTe quitó: " + (dañoGoomba1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Spikey1.getVida()>0){
                Spikey1.getObjetivo();
                Spikey1.getFallo();

                if(Spikey1.getObjetivo()==1){
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoSpikey1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mario\nTe quitó: " + (dañoSpikey1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoSpikey1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mallow\nTe quitó: " + (dañoSpikey1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Frogog.getVida()>0){
                Frogog.getObjetivo();
                Frogog.getFallo();

                if(Frogog.getObjetivo()==1){
                    if(Frogog.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog = Frogog.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoFrogog-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mario\nTe quitó: " + (dañoFrogog-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Frogog.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog = Frogog.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoFrogog-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mallow\nTe quitó: " + (dañoFrogog-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }

            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);


    }








    //=========================================================================================================================================================================================================//








    //PEELA SPIKEY3
    public static void peleaSpikey3(Jugador_Mario Mario, Mallow Mallow){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Spikey1 = new Enemigo("Spikey", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        Enemigo Spikey2 = new Enemigo("Spikey", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        Enemigo Frogog = new Enemigo("Frogog", 80, 80, 6, 0, 0, 0);   //daño entre 3-5
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Spikey1.getNombre());
        arrayListEnemigos.addLast(Spikey2.getNombre());
        arrayListEnemigos.addLast(Frogog.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mario", menuMario, "src/main/java/img/peleaSpikey3.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Spikey: " + Spikey1.getVida() + "\nVida Spikey: " + Spikey2.getVida() + "\nVida Frogog: " + Frogog.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoMartillo()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey2.setVida(Spikey2.getVida()-(Mario.getDañoMartillo()-Spikey2.getDefensa()));
                            if (Spikey2.getVida()<=0){
                                Spikey2.setVida(0);
                                arrayListEnemigos.remove(Spikey2.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog.setVida(Frogog.getVida()-(Mario.getDañoMartillo()-Frogog.getDefensa()));
                            if (Frogog.getVida()<=0){
                                Frogog.setVida(0);
                                arrayListEnemigos.remove(Frogog.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);

                        }else if (enemigoElegido.equals(Spikey2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);
                        
                        }else if (enemigoElegido.equals(Frogog.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Frogog.setVida(Frogog.getVida()-(Mario.getDañoSalto()-Frogog.getDefensa()));
                                if (Frogog.getVida()<=0){
                                    Frogog.setVida(0);
                                    arrayListEnemigos.remove(Frogog.getNombre());
                                }
                        }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoFlama()-Spikey1.getDefensa()));
                                if (Spikey1.getVida()<=0){
                                    Spikey1.setVida(0);
                                    arrayListEnemigos.remove(Spikey1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Spikey2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey2.setVida(Spikey2.getVida()-(Mario.getDañoFlama()-Spikey2.getDefensa()));
                                if (Spikey2.getVida()<=0){
                                    Spikey2.setVida(0);
                                    arrayListEnemigos.remove(Spikey2.getNombre());
                                }

                        }else if (enemigoElegido.equals(Frogog.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Frogog.setVida(Frogog.getVida()-(Mario.getDañoFlama()-Frogog.getDefensa()));
                                if (Frogog.getVida()<=0){
                                    Frogog.setVida(0);
                                    arrayListEnemigos.remove(Frogog.getNombre());
                                }
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Spikey1.getVida()<=0 && Spikey2.getVida()<=0 && Frogog.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n10 pts. Exp.\n12 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 10;
                Mario.setMonedas(Mario.getMonedas()+12);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mallow", menuMario, "src/main/java/img/peleaSpikey3.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Goomba: " + Spikey1.getVida() + "\nVida Spikey: " + Spikey2.getVida() + "\nVida Frogog: " + Frogog.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mallow.getDañoBaseMallow()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Spikey2.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey2.setVida(Spikey2.getVida()-(Mallow.getDañoBaseMallow()-Spikey2.getDefensa()));
                            if (Spikey2.getVida()<=0){
                                Spikey2.setVida(0);
                                arrayListEnemigos.remove(Spikey2.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog.setVida(Frogog.getVida()-(Mallow.getDañoBaseMallow()-Frogog.getDefensa()));
                            if (Frogog.getVida()<=0){
                                Frogog.setVida(0);
                                arrayListEnemigos.remove(Frogog.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Spikey1.setVida(Spikey1.getVida()-Mallow.getDañoRelampago());
                        if (Spikey1.getVida()<=0){
                            Spikey1.setVida(0);
                            arrayListEnemigos.remove(Spikey1.getNombre());
                        }     
                       
                        Spikey2.setVida(Spikey2.getVida()-Mallow.getDañoRelampago());
                        if (Spikey2.getVida()<=0){
                            Spikey2.setVida(0);
                            arrayListEnemigos.remove(Spikey2.getNombre());
                        }        
                      
                        Frogog.setVida(Frogog.getVida()-Mallow.getDañoRelampago());
                        if (Frogog.getVida()<=0){
                            Frogog.setVida(0);
                            arrayListEnemigos.remove(Frogog.getNombre());
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mallow.setVida(Mallow.getVida()+30);
                        if(Mallow.getVida()>Mallow.getVidaMax()) Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Spikey1.getVida()<=0 && Spikey2.getVida()<=0 && Frogog.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n10 pts. Exp.\n12 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 10;
                Mario.setMonedas(Mario.getMonedas()+12);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Spikey1.getVida()>0){
                Spikey1.getObjetivo();
                Spikey1.getFallo();

                if(Spikey1.getObjetivo()==1){
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoSpikey1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mario\nTe quitó: " + (dañoSpikey1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoSpikey1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mallow\nTe quitó: " + (dañoSpikey1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Spikey2.getVida()>0){
                Spikey2.getObjetivo();
                Spikey2.getFallo();

                if(Spikey2.getObjetivo()==1){
                    if(Spikey2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey2 = Spikey2.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoSpikey2-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mario\nTe quitó: " + (dañoSpikey2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Spikey2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey2 = Spikey2.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoSpikey2-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mallow\nTe quitó: " + (dañoSpikey2-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Frogog.getVida()>0){
                Frogog.getObjetivo();
                Frogog.getFallo();

                if(Frogog.getObjetivo()==1){
                    if(Frogog.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog = Frogog.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoFrogog-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mario\nTe quitó: " + (dañoFrogog-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Frogog.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog = Frogog.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoFrogog-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mallow\nTe quitó: " + (dañoFrogog-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }

            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);



    }









    //=======================================================================================================================================================================================================//








    //PELEA SPIKEY 4
    public static void peleaSpikey4(Jugador_Mario Mario, Mallow Mallow){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Spikey1 = new Enemigo("Spikey", 20, 20, 4, 0, 0, 0);  //daño entre 2-4
        Enemigo Frogog1 = new Enemigo("Frogog", 80, 80, 6, 0, 0, 0);  //daño entre 3-5
        Enemigo Frogog2 = new Enemigo("Frogog", 80, 80, 6, 0, 0, 0);   //daño entre 3-5
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Spikey1.getNombre());
        arrayListEnemigos.addLast(Frogog1.getNombre());
        arrayListEnemigos.addLast(Frogog2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mario", menuMario, "src/main/java/img/peleaSpikey4.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Spikey: " + Spikey1.getVida() + "\nVida Frogog: " + Frogog1.getVida() + "\nVida Frogog: " + Frogog2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoMartillo()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog1.setVida(Frogog1.getVida()-(Mario.getDañoMartillo()-Frogog1.getDefensa()));
                            if (Frogog1.getVida()<=0){
                                Frogog1.setVida(0);
                                arrayListEnemigos.remove(Frogog1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog2.setVida(Frogog2.getVida()-(Mario.getDañoMartillo()-Frogog2.getDefensa()));
                            if (Frogog2.getVida()<=0){
                                Frogog2.setVida(0);
                                arrayListEnemigos.remove(Frogog2.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                FuncionesGraficas.warning("Cuidado", "Te has pinchado\nHas sufrido -1 de daño");
                                Mario.setVida(Mario.getVida()-1);

                        }else if (enemigoElegido.equals(Frogog1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Frogog1.setVida(Frogog1.getVida()-(Mario.getDañoSalto()-Frogog1.getDefensa()));
                                if (Frogog1.getVida()<=0){
                                    Frogog1.setVida(0);
                                    arrayListEnemigos.remove(Frogog1.getNombre());
                                }
                        
                        }else if (enemigoElegido.equals(Frogog2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-3);
                                Frogog2.setVida(Frogog2.getVida()-(Mario.getDañoSalto()-Frogog2.getDefensa()));
                                if (Frogog2.getVida()<=0){
                                    Frogog2.setVida(0);
                                    arrayListEnemigos.remove(Frogog2.getNombre());
                                }
                        }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Spikey1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Spikey1.setVida(Spikey1.getVida()-(Mario.getDañoFlama()-Spikey1.getDefensa()));
                                if (Spikey1.getVida()<=0){
                                    Spikey1.setVida(0);
                                    arrayListEnemigos.remove(Spikey1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Frogog1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Frogog1.setVida(Frogog1.getVida()-(Mario.getDañoFlama()-Frogog1.getDefensa()));
                                if (Frogog1.getVida()<=0){
                                    Frogog1.setVida(0);
                                    arrayListEnemigos.remove(Frogog1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Frogog2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Frogog2.setVida(Frogog2.getVida()-(Mario.getDañoFlama()-Frogog2.getDefensa()));
                                if (Frogog2.getVida()<=0){
                                    Frogog2.setVida(0);
                                    arrayListEnemigos.remove(Frogog2.getNombre());
                                }
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Spikey1.getVida()<=0 && Frogog1.getVida()<=0 && Frogog2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n12 pts. Exp.\n15 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 12;
                Mario.setMonedas(Mario.getMonedas()+15);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mallow", menuMario, "src/main/java/img/peleaSpikey4.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Spikey: " + Spikey1.getVida() + "\nVida Frogog: " + Frogog1.getVida() + "\nVida Frogog: " + Frogog2.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Spikey1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Spikey1.setVida(Spikey1.getVida()-(Mallow.getDañoBaseMallow()-Spikey1.getDefensa()));
                            if (Spikey1.getVida()<=0){
                                Spikey1.setVida(0);
                                arrayListEnemigos.remove(Spikey1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog1.setVida(Frogog1.getVida()-(Mallow.getDañoBaseMallow()-Frogog1.getDefensa()));
                            if (Frogog1.getVida()<=0){
                                Frogog1.setVida(0);
                                arrayListEnemigos.remove(Frogog1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Frogog2.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Frogog2.setVida(Frogog2.getVida()-(Mallow.getDañoBaseMallow()-Frogog2.getDefensa()));
                            if (Frogog2.getVida()<=0){
                                Frogog2.setVida(0);
                                arrayListEnemigos.remove(Frogog2.getNombre());
                            }
                        }
                    }
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Spikey1.setVida(Spikey1.getVida()-Mallow.getDañoRelampago());
                        if (Spikey1.getVida()<=0){
                            Spikey1.setVida(0);
                            arrayListEnemigos.remove(Spikey1.getNombre());
                        }     
                       
                        Frogog1.setVida(Frogog1.getVida()-Mallow.getDañoRelampago());
                        if (Frogog1.getVida()<=0){
                            Frogog1.setVida(0);
                            arrayListEnemigos.remove(Frogog1.getNombre());
                        }       
                      
                        Frogog2.setVida(Frogog2.getVida()-Mallow.getDañoRelampago());
                        if (Frogog2.getVida()<=0){
                            Frogog2.setVida(0);
                            arrayListEnemigos.remove(Frogog2.getNombre());
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mallow.setVida(Mallow.getVida()+30);
                        if(Mallow.getVida()>Mallow.getVidaMax()) Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Spikey1.getVida()<=0 && Frogog1.getVida()<=0 && Frogog2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n12 pts. Exp.\n15 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 12;
                Mario.setMonedas(Mario.getMonedas()+15);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Spikey1.getVida()>0){
                Spikey1.getObjetivo();
                Spikey1.getFallo();

                if(Spikey1.getObjetivo()==1){
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoSpikey1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mario\nTe quitó: " + (dañoSpikey1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Spikey1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Spikey", "Falló el ataque");
                    
                    }else{
                        int dañoSpikey1 = Spikey1.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoSpikey1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Spikey", "Spikey ataca a Mallow\nTe quitó: " + (dañoSpikey1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Frogog1.getVida()>0){
                Frogog1.getObjetivo();
                Frogog1.getFallo();

                if(Frogog1.getObjetivo()==1){
                    if(Frogog1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog1 = Frogog1.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoFrogog1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mario\nTe quitó: " + (dañoFrogog1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Frogog1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog1 = Frogog1.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoFrogog1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mallow\nTe quitó: " + (dañoFrogog1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Frogog2.getVida()>0){
                Frogog2.getObjetivo();
                Frogog2.getFallo();

                if(Frogog2.getObjetivo()==1){
                    if(Frogog2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog2 = Frogog2.getDañoSpikey();
                        Mario.setVida(Mario.getVida()-(dañoFrogog2-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mario\nTe quitó: " + (dañoFrogog2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Frogog2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Frogog", "Falló el ataque");
                    
                    }else{
                        int dañoFrogog2 = Frogog2.getDañoSpikey();
                        Mallow.setVida(Mallow.getVida()-(dañoFrogog2-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Frogog", "Frogog ataca a Mallow\nTe quitó: " + (dañoFrogog2-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }

            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);



    }









    //====================================================================================================================================================================================================//









    //ESCENA BANDIT'S WAY 2 + JEFE    acabas con 96exp máximo
    public static void escenaBanditWay2(Jugador_Mario Mario, Mallow Mallow){

        var banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
        var hiloBanditWay = new Thread(banditWay);

        ArrayList<String> arrayListOpcBanditWay2 = new ArrayList<>();
        arrayListOpcBanditWay2.add("Abrir cofre");
        arrayListOpcBanditWay2.addLast("Buscar en matorral 1");
        arrayListOpcBanditWay2.addLast("Buscar en matorral 2");
        arrayListOpcBanditWay2.addLast("Buscar en matorral 3");
        arrayListOpcBanditWay2.addLast("Buscar en matorral 4");
        arrayListOpcBanditWay2.addLast("Inventario");
        

        hiloBanditWay.start();

        do{
            String[] menuBanditWay2 = arrayListOpcBanditWay2.toArray(new String[0]);

            int opcBanditWay2 = FuncionesGraficas.FotoMensajeMenu("Bandit's Way 2", menuBanditWay2, "src/main/java/img/banditWay2.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.5, false);
        
            String opcElegida = menuBanditWay2[opcBanditWay2];


            if(opcElegida.equals("Buscar en matorral 1")){
                FuncionesGraficas.warning("Upss...", "Parece que aquí no hay nada...");
                

            }else if(opcElegida.equals("Abrir cofre")){
                FuncionesGraficas.FotoMensajeSonido("Cofre", "src/main/java/img/setaCurar.png", "Vida y mágia recuperadas", "src/main/java/sonidos/cofre.mp3", 0.3, 1, false); 
                Mario.setVida(Mario.getVidaMax());
                Mario.setMagia(Mario.getMagiaMax());
                Mallow.setVida(Mallow.getVidaMax());
                arrayListOpcBanditWay2.remove(opcElegida);


            }else if(opcElegida.equals("Buscar en matorral 2")){
               FuncionesGraficas.warning("Upss...", "Parece que aquí no hay nada...");


            }else if(opcElegida.equals("Buscar en matorral 3")){
                FuncionesGraficas.FotoyMensaje("Croco pillado", "src/main/java/img/crocoPillado.gif", "", 1.8, false);
                banditWay.detener();
                jefeCroco(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                banditWay = new ReproductorBucle("src/main/java/sonidos/banditWay.wav");
                hiloBanditWay = new Thread(banditWay);
                hiloBanditWay.start();
                FuncionesGraficas.FotoyMensaje("Croco vencido", "src/main/java/img/crocoVencido.gif", "", 1.8, false);
                banditWay.detener();
                FuncionesGraficas.FotoMensajeSonido("Mapa", "src/main/java/img/mapBackToMushKing.gif", "", "src/main/java/sonidos/worldMap.mp3", 2, 1, false);
                return;


            }else if(opcElegida.equals("Buscar en matorral 4")){
               FuncionesGraficas.warning("Upss...", "Parece que aquí no hay nada...");


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();
            }

        }while(true);


    }









    //===============================================================================================================================================================================================//









    //JEFE CROCO
    public static void jefeCroco(Jugador_Mario Mario, Mallow Mallow){

        var miniJefe = new ReproductorBucle("src/main/java/sonidos/miniJefe.wav");
        var hiloMiniJJefe = new Thread(miniJefe);

        hiloMiniJJefe.start();

        Jefe Croco = new Jefe("Croco", 400, 400, 10, 5, 0, 15, 0, 0, 0);  //daño entre 5-8 y dañoMagico 15
        
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            
            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Mini Jefe -- Turno de Mario", menuMario, "src/main/java/img/jefeCroco.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Croco: " + Croco.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    if (Mario.getFallo()<=20){
                        FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                    }else{
                        Croco.setVida(Croco.getVida()-(Mario.getDañoMartillo()-Croco.getDefensa()));
                        if (Croco.getVida()<=0) Croco.setVida(0);
                    }
                    
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    
                            
                    if(magiaElegido.equals("Salto")){
                        Mario.setMagia(Mario.getMagia()-3);
                        Croco.setVida(Croco.getVida()-(Mario.getDañoSalto()-Croco.getDefensaMag()));
                        if (Croco.getVida()<=0) Croco.setVida(0);

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        Mario.setMagia(Mario.getMagia()-5);
                        Croco.setVida(Croco.getVida()-(Mario.getDañoFlama()-Croco.getDefensaMag()));
                        if (Croco.getVida()<=0) Croco.setVida(0);
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Croco.getVida()<=0){
                FuncionesGraficas.FotoyMensaje("Victoria", "src/main/java/img/hostionCroco.gif", "", 1.8, false);
                miniJefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n15 pts. Exp.\n25 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 15;
                Mario.setMonedas(Mario.getMonedas()+25);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Mini Jefe -- Turno de Mallow", menuMario, "src/main/java/img/jefeCroco.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Croco: " + Croco.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    if (Mallow.getFallo()<=20){
                        FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                    }else{
                        Croco.setVida(Croco.getVida()-(Mallow.getDañoBaseMallow()-Croco.getDefensa()));
                        if (Croco.getVida()<=0) Croco.setVida(0);
                    }
                    
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Croco.setVida(Croco.getVida()-(Mallow.getDañoRelampago()-Croco.getDefensaMag()));
                        if (Croco.getVida()<=0) Croco.setVida(0);
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mallow.setVida(Mallow.getVida()+30);
                        if(Mallow.getVida()>Mallow.getVidaMax()) Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Croco.getVida()<=0){
                FuncionesGraficas.FotoyMensaje("Victoria", "src/main/java/img/hostionCroco.gif", "", 1.8, false);
                miniJefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n15 pts. Exp.\n25 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 15;
                Mario.setMonedas(Mario.getMonedas()+25);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Croco.getVida()>0){
            
                if(Croco.getObjetivo()==1){
                    Croco.getAtckMagico();

                    if(Croco.getAtckMagico()<=10){
                        Mario.setVida(Mario.getVida()-(Croco.getDañoMagico()-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque especial Croco", "Croco ataca a Mario\nTe quitó: " + (Croco.getDañoMagico()-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");

                    }else{
                        Croco.getFallo();
                    
                        if(Croco.getFallo()<=20){
                            FuncionesGraficas.warning("Ataque Croco", "Falló el ataque");
                        
                        }else{
                            int dañoCroco = Croco.getDañoBaseCroco();
                            Mario.setVida(Mario.getVida()-(dañoCroco-Mario.getDefensa()));
                            if(Mario.getVida()<=0) Mario.setVida(0);
                            FuncionesGraficas.warning("Ataque Croco", "Croco ataca a Mario\nTe quitó: " + (dañoCroco-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                        }
                    }

                }else{
                    Croco.getAtckMagico();

                    if(Croco.getAtckMagico()<=10){
                        Mallow.setVida(Mallow.getVida()-(Croco.getDañoMagico()-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque especial Croco", "Croco ataca a Mallow\nTe quitó: " + (Croco.getDañoMagico()-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");

                    }else{
                        Croco.getFallo();
                    
                        if(Croco.getFallo()<=20){
                            FuncionesGraficas.warning("Ataque Croco", "Falló el ataque");
                        
                        }else{
                            int dañoCroco = Croco.getDañoBaseCroco();
                            Mallow.setVida(Mallow.getVida()-(dañoCroco-Mallow.getDefensa()));
                            if(Mallow.getVida()<=0) Mallow.setVida(0);
                            FuncionesGraficas.warning("Ataque Croco", "Croco ataca a Mallow\nTe quitó: " + (dañoCroco-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                        }
                    }

                } 

            }


            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                miniJefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);


    }










    //==================================================================================================================================================================================================//










    //ESCENA CASTILLO INVADIDO   acabas con 126exp máximo  (nivel 5 en 120exp)
    public static void escenaReinoInvadido(Jugador_Mario Mario, Mallow Mallow){

        var mushroomKingdomInvadido = new ReproductorBucle("src/main/java/sonidos/mushKingInvadido.wav");
        var hiloMushroomKingdomInvadido = new Thread(mushroomKingdomInvadido);

        ArrayList<String> arrayListOpcMushKingInvadido = new ArrayList<>();
        arrayListOpcMushKingInvadido.add("Pelear Springer 1");
        arrayListOpcMushKingInvadido.addLast("Pelear Springer 2");
        arrayListOpcMushKingInvadido.addLast("Pelear Springer 3");
        arrayListOpcMushKingInvadido.addLast("Tienda");
        arrayListOpcMushKingInvadido.addLast("Posada");
        arrayListOpcMushKingInvadido.addLast("Inventario");
        arrayListOpcMushKingInvadido.addLast("Entrar al castillo");
        
        hiloMushroomKingdomInvadido.start();

        do{
            String[] menuMushKingInvadido = arrayListOpcMushKingInvadido.toArray(new String[0]);

            int opcMushKingInvadido = FuncionesGraficas.FotoMensajeMenu("Reino Champiñón", menuMushKingInvadido, "src/main/java/img/mushKingInvadido.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\nMonedas: " + Mario.getMonedas() + "\nExp: " + exp + "\n \n \n \nElige una opción:", 0.7, false);
        
            String opcElegida = menuMushKingInvadido[opcMushKingInvadido];


            if(opcElegida.equals("Tienda")){
                tienda(Mario);
                
                

            }else if(opcElegida.equals("Pelear Springer 1")){
                mushroomKingdomInvadido.detener();
                peleaSpringer(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mario.getNivel()==4 && exp>=120) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==4 && exp>=120) levelUpMallow(Mario, Mallow);
                mushroomKingdomInvadido = new ReproductorBucle("src/main/java/sonidos/mushKingInvadido.wav");
                hiloMushroomKingdomInvadido = new Thread(mushroomKingdomInvadido);
                hiloMushroomKingdomInvadido.start();
                arrayListOpcMushKingInvadido.remove(opcElegida);
                


            }else if(opcElegida.equals("Pelear Springer 2")){
                mushroomKingdomInvadido.detener();
                peleaSpringer(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mario.getNivel()==4 && exp>=120) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==4 && exp>=120) levelUpMallow(Mario, Mallow);
                mushroomKingdomInvadido = new ReproductorBucle("src/main/java/sonidos/mushKingInvadido.wav");
                hiloMushroomKingdomInvadido = new Thread(mushroomKingdomInvadido);
                hiloMushroomKingdomInvadido.start();
                arrayListOpcMushKingInvadido.remove(opcElegida);
                


            }else if(opcElegida.equals("Pelear Springer 3")){
                mushroomKingdomInvadido.detener();
                peleaSpringer(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mario.getNivel()==4 && exp>=120) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==4 && exp>=120) levelUpMallow(Mario, Mallow);
                mushroomKingdomInvadido = new ReproductorBucle("src/main/java/sonidos/mushKingInvadido.wav");
                hiloMushroomKingdomInvadido = new Thread(mushroomKingdomInvadido);
                hiloMushroomKingdomInvadido.start();
                arrayListOpcMushKingInvadido.remove(opcElegida);
                


            }else if(opcElegida.equals("Posada")){
                boolean resp = FuncionesGraficas.pedirBool("Posada", "¿Quieres tomar un descanso?  -->  Coste: 10 monedas");

                if(resp==false){
                    FuncionesGraficas.mostrarMensaje("Despedida", "Hasta la próxima", 1);
                    continue;
                }

                if(resp==true && Mario.getMonedas()>=10){
                    Mario.setMonedas(Mario.getMonedas()-10);
                    Mario.setVida(Mario.getVidaMax());
                    Mario.setMagia(Mario.getMagiaMax());
                    Mallow.setVida(Mallow.getVidaMax());
                    FuncionesGraficas.mostrarMensaje("Descanso", "Vida y mágia recuperadas", 1);
                
                }else FuncionesGraficas.warning("Atención", "No tienes suficientes monedas");


            }else if(opcElegida.equals("Inventario")){
                mostrarInventario();

            }else if (opcElegida.equals("Entrar al castillo")){
                FuncionesGraficas.FotoyMensaje("Encuentro con Mack", "src/main/java/img/encuentroMack.gif", "", 1.8, false);
                mushroomKingdomInvadido.detener();
                jefeMack(Mario, Mallow);
                if(Mario.getNivel()==1 && exp>=20) levelUpMario(Mario);
                if(Mario.getNivel()==2 && exp>=40){
                    levelUpMario(Mario);
                    FuncionesGraficas.mostrarMensaje("Ataque mágico desbloqueado", "Has aprendido un nuevo ataque mágico\n \nAprendiste: Flama", 2);
                    menuAtaquesMagicosMario.add("Flama");
                }
                if(Mario.getNivel()==3 && exp>=80) levelUpMario(Mario);
                if(Mario.getNivel()==4 && exp>=120) levelUpMario(Mario);
                if(Mario.getNivel()==5 && exp>=150) levelUpMario(Mario);
                if(Mallow.getNivel()==2 && exp>=40) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==3 && exp>=80) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==4 && exp>=120) levelUpMallow(Mario, Mallow);
                if(Mallow.getNivel()==5 && exp>=150) levelUpMallow(Mario, Mallow);
                FuncionesGraficas.FotoMensajeSonido("Conseguir estrella", "src/main/java/img/conseguirEstrella1.gif", "", "src/main/java/sonidos/conseguirEstrella1.mp3", 1.8, 1, false);
                return;
            }

        }while(true);


    }









    //===================================================================================================================================================================================================//









    //PELEA SPRINGER
    public static void peleaSpringer(Jugador_Mario Mario, Mallow Mallow){

        var batalla = new ReproductorBucle("src/main/java/sonidos/batalla.wav");
        var hiloBatalla = new Thread(batalla);

        hiloBatalla.start();

        Enemigo Springer1 = new Enemigo("Springer", 30, 30, 5, 0, 0, 0);  //daño entre 3-6
        Enemigo Springer2 = new Enemigo("Springer", 30, 80, 5, 0, 0, 0);  //daño entre 3-6
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Springer1.getNombre());
        arrayListEnemigos.addLast(Springer2.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego
            
            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mario", menuMario, "src/main/java/img/peleaSpringer.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Springer: " + Springer1.getVida() + "\nVida Springer: " + Springer2.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Springer1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer1.setVida(Springer1.getVida()-(Mario.getDañoMartillo()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Springer2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer2.setVida(Springer2.getVida()-(Mario.getDañoMartillo()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                        }
                    }

                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Springer1.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer1.setVida(Springer1.getVida()-(Mario.getDañoSalto()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        
                        }else if (enemigoElegido.equals(Springer2.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer2.setVida(Springer2.getVida()-(Mario.getDañoSalto()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                        }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Springer1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Springer1.setVida(Springer1.getVida()-(Mario.getDañoFlama()-Springer1.getDefensa()));
                                if (Springer1.getVida()<=0){
                                    Springer1.setVida(0);
                                    arrayListEnemigos.remove(Springer1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Springer2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Springer2.setVida(Springer2.getVida()-(Mario.getDañoFlama()-Springer2.getDefensa()));
                                if (Springer2.getVida()<=0){
                                    Springer2.setVida(0);
                                    arrayListEnemigos.remove(Springer2.getNombre());
                                }

                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "Tienes el inventario vacío");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Springer1.getVida()<=0 && Springer2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n10 pts. Exp.\n8 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 10;
                Mario.setMonedas(Mario.getMonedas()+8);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Batalla -- Turno de Mallow", menuMario, "src/main/java/img/peleaSpringer.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Springer: " + Springer1.getVida() + "\nVida Springer: " + Springer2.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Springer1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer1.setVida(Springer1.getVida()-(Mallow.getDañoBaseMallow()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Springer2.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer2.setVida(Springer2.getVida()-(Mallow.getDañoBaseMallow()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                        }
                    }
                    
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Springer1.setVida(Springer1.getVida()-Mallow.getDañoRelampago());
                        if (Springer1.getVida()<=0){
                            Springer1.setVida(0);
                            arrayListEnemigos.remove(Springer1.getNombre());
                        }     
                       
                        Springer2.setVida(Springer2.getVida()-Mallow.getDañoRelampago());
                        if (Springer2.getVida()<=0){
                            Springer2.setVida(0);
                            arrayListEnemigos.remove(Springer2.getNombre());
                        }       
                      
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "Tienes el inventario vacío");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mallow.setVida(Mallow.getVida()+30);
                        if(Mallow.getVida()>Mallow.getVidaMax()) Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mallow ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Springer1.getVida()<=0 && Springer2.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n10 pts. Exp.\n8 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 10;
                Mario.setMonedas(Mario.getMonedas()+8);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Springer1.getVida()>0){
                Springer1.getObjetivo();
                Springer1.getFallo();

                if(Springer1.getObjetivo()==1){
                    if(Springer1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer1 = Springer1.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer1 = Springer1.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Springer2.getVida()>0){
                Springer2.getObjetivo();
                Springer2.getFallo();

                if(Springer2.getObjetivo()==1){
                    if(Springer2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer2 = Springer2.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer2-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer2 = Springer2.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer2-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer2-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }



            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                batalla.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);


    }










    //=========================================================================================================================================================//









    //JEFE MACK  
    public static void jefeMack(Jugador_Mario Mario, Mallow Mallow){

        var jefe = new ReproductorBucle("src/main/java/sonidos/jefe.wav");
        var hiloJefe = new Thread(jefe);

        hiloJefe.start();

        Jefe Mack = new Jefe("Mack", 600, 600, 10, 30, 0, 12, 0, 0, 0);  //daño entre 5-8 y dañoMagico 12
        Enemigo Springer1 = new Enemigo("Springer", 30, 30, 5, 0, 0, 0);  //daño entre 3-6
        Enemigo Springer2 = new Enemigo("Springer", 30, 30, 5, 0, 0, 0);  //daño entre 3-6
        Enemigo Springer3 = new Enemigo("Springer", 30, 30, 5, 0, 0, 0);  //daño entre 3-6
        Enemigo Springer4 = new Enemigo("Springer", 30, 30, 5, 0, 0, 0);  //daño entre 3-6
        String[] menuMario = {"Ataque Básico", "Ataque Mágico", "Objetos", "Otros"};
        String[] menuOtros = {"Defender", "Huir"};
        ArrayList<String> arrayListEnemigos = new ArrayList<>(); // array list de los enemigos a atacar
        arrayListEnemigos.add(Mack.getNombre());
        arrayListEnemigos.addLast(Springer1.getNombre());
        arrayListEnemigos.addLast(Springer2.getNombre());
        arrayListEnemigos.addLast(Springer3.getNombre());
        arrayListEnemigos.addLast(Springer4.getNombre());
        String[] menuMagiaMario = menuAtaquesMagicosMario.toArray(new String[0]);
        String[] menuMagiaMallow = menuAtaquesMagicosMallow.toArray(new String[0]);
        
        
        do{
            String[] menuEnemigos = arrayListEnemigos.toArray(new String[0]);  // array que se verá en el juego

            Mario.setDefensa(0);
            Mallow.setDefensa(0); 
            
            if(Mario.getVida()>0){
            int opcMario = FuncionesGraficas.FotoMensajeMenu("Jefe -- Turno de Mario", menuMario, "src/main/java/img/jefeMack.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Mack: " + Mack.getVida() + "\nVida Springer: " + Springer1.getVida() + "\nVida Springer: " + Springer2.getVida() + "\nVida Springer: " + Springer3.getVida() + "\nVida Springer: " + Springer4.getVida(), 0.7, false);
            
            //TURNO Mario  AÑADIR FOTOS DE LOS ATAQUES
            switch (opcMario){
                
                //ataque basico
                case 0:
                    Mario.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Springer1.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer1.setVida(Springer1.getVida()-(Mario.getDañoMartillo()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Springer2.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer2.setVida(Springer2.getVida()-(Mario.getDañoMartillo()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Springer3.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer3.setVida(Springer3.getVida()-(Mario.getDañoMartillo()-Springer3.getDefensa()));
                            if (Springer3.getVida()<=0){
                                Springer3.setVida(0);
                                arrayListEnemigos.remove(Springer3.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Springer4.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer4.setVida(Springer4.getVida()-(Mario.getDañoMartillo()-Springer4.getDefensa()));
                            if (Springer4.getVida()<=0){
                                Springer4.setVida(0);
                                arrayListEnemigos.remove(Springer4.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Mack.getNombre())){
                        if (Mario.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Mack.setVida(Mack.getVida()-(Mario.getDañoMartillo()-Mack.getDefensa()));
                            if (Mack.getVida()<=0){
                                Mack.setVida(0);
                                arrayListEnemigos.remove(Mack.getNombre());
                            }
                        }
                    }
                    
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<3){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMario = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMario);

                    String magiaElegido = menuMagiaMario[opcMagiaMario];
                    
                    //hacer lo mismo que con el ataque basico
                    
                    opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                            
                    enemigoElegido = menuEnemigos[opcEnemigos];
                            
                    if(magiaElegido.equals("Salto")){
                        if (enemigoElegido.equals(Springer1.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer1.setVida(Springer1.getVida()-(Mario.getDañoSalto()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        
                        }else if (enemigoElegido.equals(Springer2.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer2.setVida(Springer2.getVida()-(Mario.getDañoSalto()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                            
                        }else if (enemigoElegido.equals(Springer3.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer3.setVida(Springer3.getVida()-(Mario.getDañoSalto()-Springer3.getDefensa()));
                            if (Springer3.getVida()<=0){
                                Springer3.setVida(0);
                                arrayListEnemigos.remove(Springer3.getNombre());
                            }
                            
                        }else if (enemigoElegido.equals(Springer4.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Springer4.setVida(Springer4.getVida()-(Mario.getDañoSalto()-Springer4.getDefensa()));
                            if (Springer4.getVida()<=0){
                                Springer4.setVida(0);
                                arrayListEnemigos.remove(Springer4.getNombre());
                            }
                            
                        }else if (enemigoElegido.equals(Mack.getNombre())){
                            Mario.setMagia(Mario.getMagia()-3);
                            Mack.setVida(Mack.getVida()-(Mario.getDañoSalto()-Mack.getDefensa()));
                            if (Mack.getVida()<=0){
                                Mack.setVida(0);
                                arrayListEnemigos.remove(Mack.getNombre());
                            }
                            
                        }

                    }else if(magiaElegido.equals("Flama")){
                        if (Mario.getMagia()<5){
                            FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                            break;
                        }

                        if (enemigoElegido.equals(Springer1.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Springer1.setVida(Springer1.getVida()-(Mario.getDañoFlama()-Springer1.getDefensa()));
                                if (Springer1.getVida()<=0){
                                    Springer1.setVida(0);
                                    arrayListEnemigos.remove(Springer1.getNombre());
                                }

                        }else if (enemigoElegido.equals(Springer2.getNombre())){
                                Mario.setMagia(Mario.getMagia()-5);
                                Springer2.setVida(Springer2.getVida()-(Mario.getDañoFlama()-Springer2.getDefensa()));
                                if (Springer2.getVida()<=0){
                                    Springer2.setVida(0);
                                    arrayListEnemigos.remove(Springer2.getNombre());
                                }

                        }else if (enemigoElegido.equals(Springer3.getNombre())){
                            Mario.setMagia(Mario.getMagia()-5);
                            Springer3.setVida(Springer3.getVida()-(Mario.getDañoFlama()-Springer3.getDefensa()));
                            if (Springer3.getVida()<=0){
                                Springer3.setVida(0);
                                arrayListEnemigos.remove(Springer3.getNombre());
                            }
                            
                        }else if (enemigoElegido.equals(Springer4.getNombre())){
                            Mario.setMagia(Mario.getMagia()-5);
                            Springer4.setVida(Springer4.getVida()-(Mario.getDañoFlama()-Springer4.getDefensa()));
                            if (Springer4.getVida()<=0){
                                Springer4.setVida(0);
                                arrayListEnemigos.remove(Springer4.getNombre());
                            }
                            
                        }else if (enemigoElegido.equals(Mack.getNombre())){
                            Mario.setMagia(Mario.getMagia()-5);
                            Mack.setVida(Mack.getVida()-(Mario.getDañoFlama()-25));
                            if (Mack.getVida()<=0){
                                Mack.setVida(0);
                                arrayListEnemigos.remove(Mack.getNombre());
                            }
                        }
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mallow.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMallow no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/mallowRevivir.png", "Has revivido a Mallow", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mallow.setVida(Mallow.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mario.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }
            



            if(Mack.getVida()<=0){
                jefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n30 pts. Exp.\n50 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 30;
                Mario.setMonedas(Mario.getMonedas()+50);
                break;
            }





            //TURNO Mallow
            if(Mallow.getVida()>0){
            int opcMallow = FuncionesGraficas.FotoMensajeMenu("Jefe -- Turno de Mallow", menuMario, "src/main/java/img/jefeMack.png", "Vida Mario: " + Mario.getVida() + " / " + Mario.getVidaMax() + "\nVida Mallow: " + Mallow.getVida() + " / " + Mallow.getVidaMax() + "\nMágia: " + Mario.getMagia() + " / " + Mario.getMagiaMax() + "\n \nVida Croco: " + Mack.getVida() + "\nVida Springer: " + Springer1.getVida() + "\nVida Springer: " + Springer2.getVida() + "\nVida Springer: " + Springer3.getVida() + "\nVida Springer: " + Springer4.getVida(), 0.7, false);
            
            
            switch (opcMallow){
                
                //ataque basico
                case 0:
                    Mallow.getFallo();
                    
                    int opcEnemigos = FuncionesGraficas.menuDesplegable("Opciones", "Elige un enemigo", menuEnemigos);
                        
                    String enemigoElegido = menuEnemigos[opcEnemigos];
                    
                    if (enemigoElegido.equals(Springer1.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer1.setVida(Springer1.getVida()-(Mallow.getDañoBaseMallow()-Springer1.getDefensa()));
                            if (Springer1.getVida()<=0){
                                Springer1.setVida(0);
                                arrayListEnemigos.remove(Springer1.getNombre());
                            }
                        }
                    }else if (enemigoElegido.equals(Springer2.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer2.setVida(Springer2.getVida()-(Mallow.getDañoBaseMallow()-Springer2.getDefensa()));
                            if (Springer2.getVida()<=0){
                                Springer2.setVida(0);
                                arrayListEnemigos.remove(Springer2.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Springer3.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer3.setVida(Springer3.getVida()-(Mallow.getDañoBaseMallow()-Springer3.getDefensa()));
                            if (Springer3.getVida()<=0){
                                Springer3.setVida(0);
                                arrayListEnemigos.remove(Springer3.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Springer4.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Springer4.setVida(Springer4.getVida()-(Mallow.getDañoBaseMallow()-Springer4.getDefensa()));
                            if (Springer4.getVida()<=0){
                                Springer4.setVida(0);
                                arrayListEnemigos.remove(Springer4.getNombre());
                            }
                        }

                    }else if (enemigoElegido.equals(Mack.getNombre())){
                        if (Mallow.getFallo()<=20){
                            FuncionesGraficas.warning("Fallo", "Fallaste el ataque");
                        
                        }else{
                            Mack.setVida(Mack.getVida()-(Mallow.getDañoBaseMallow()-Mack.getDefensa()));
                            if (Mack.getVida()<=0){
                                Mack.setVida(0);
                                arrayListEnemigos.remove(Mack.getNombre());
                            }
                        }
                    }
                    
                break;
                
                    
                //ataque magico
                case 1:
                    if (Mario.getMagia()<2){
                        FuncionesGraficas.warning("Atención", "No tienes suficiente magia");
                        break;
                    }
                    
                    int opcMagiaMallow = FuncionesGraficas.menuDesplegable("Opciones", "Elige un ataque mágico", menuMagiaMallow);

                    String magiaElegido = menuMagiaMallow[opcMagiaMallow];
                    
                            
                    if(magiaElegido.equals("Relámpago")){
                        Mario.setMagia(Mario.getMagia()-2);
                        Springer1.setVida(Springer1.getVida()-Mallow.getDañoRelampago());
                        if (Springer1.getVida()<=0){
                            Springer1.setVida(0);
                            arrayListEnemigos.remove(Springer1.getNombre());
                        }     
                       
                        Springer2.setVida(Springer2.getVida()-Mallow.getDañoRelampago());
                        if (Springer2.getVida()<=0){
                            Springer2.setVida(0);
                            arrayListEnemigos.remove(Springer2.getNombre());
                        }
                        
                        Springer3.setVida(Springer3.getVida()-Mallow.getDañoRelampago());
                        if (Springer3.getVida()<=0){
                            Springer3.setVida(0);
                            arrayListEnemigos.remove(Springer3.getNombre());
                        }     
                       
                        Springer4.setVida(Springer4.getVida()-Mallow.getDañoRelampago());
                        if (Springer4.getVida()<=0){
                            Springer4.setVida(0);
                            arrayListEnemigos.remove(Springer4.getNombre());
                        }
                        
                        Mack.setVida(Mack.getVida()-Mallow.getDañoRelampago());
                        if (Mack.getVida()<=0){
                            Mack.setVida(0);
                            arrayListEnemigos.remove(Mack.getNombre());
                        }  
                      
                    }
                            
                break;
                
                
                
                    
                //objetos
                case 2:
                    if(objetosInventario.isEmpty()){
                        FuncionesGraficas.warning("Atención", "No tienes objetos");
                        break;
                    }

                    int menuObjetos = FuncionesGraficas.menuDesplegable("Objetos", "", inventario);
                    String objetoElegido = inventario[menuObjetos];

                    if(objetoElegido.equals("Champiñón")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 30 de vida", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setVida(Mario.getVida()+30);
                        if(Mario.getVida()>Mario.getVidaMax()) Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }


                    if(objetoElegido.equals("Jarabe")){
                        FuncionesGraficas.FotoMensajeSonido("Curación", "", "Mario ha recuperado 10 puntos mágicos", "src/main/java/sonidos/curar.wav", 1, 0, false);
                        Mario.setMagia(Mario.getMagia()+10);
                        if(Mario.getMagia()>Mario.getMagiaMax()) Mario.setMagia(Mario.getMagiaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }

                    if(objetoElegido.equals("Revivir")){
                        if(Mario.getVida()>0){
                            FuncionesGraficas.warning("Atención", "No se puede usar este objeto\n \nMario no está desplomado");
                            break;
                        }
                        FuncionesGraficas.FotoMensajeSonido("Revivir", "src/main/java/img/marioRevivir.png", "Has revivido a Mario", "src/main/java/sonidos/revivir.wav", 0.4, 0, false);
                        Mario.setVida(Mario.getVidaMax());
                        objetosInventario.remove(objetoElegido);
                        inventario = objetosInventario.toArray(new String[0]);
                    }
                break;
                
                    
                //otros
                case 3:
                    int opcOtros = FuncionesGraficas.menuDesplegable("Otros", "", menuOtros);
                    
                    switch (opcOtros){
                        case 0:
                            Mallow.setDefensa(1);
                            FuncionesGraficas.mostrarDatos("Defensa", "Tu defensa se ha incrementado +1");
                            break;
                        
                        case 1:
                            FuncionesGraficas.warning("Atención", "No puedes huir de este combate");
                            break;
                            
                    }
                break;   
            }
            }



            
            
            if(Mack.getVida()<=0){
                jefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Victoria", "src/main/java/img/marioVictoryPose.gif", "VICTORIA!!\n \nHas obtenido:\n30 pts. Exp.\n50 monedas", "src/main/java/sonidos/victory.mp3", 2, 1, false);
                exp += 30;
                Mario.setMonedas(Mario.getMonedas()+50);
                break;
            }
            
            
            
            //TURNO ENEMIGOS

            if(Springer1.getVida()>0){
                Springer1.getObjetivo();
                Springer1.getFallo();

                if(Springer1.getObjetivo()==1){
                    if(Springer1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer1 = Springer1.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer1-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer1-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer1.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer1 = Springer1.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer1-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer1-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Springer2.getVida()>0){
                Springer2.getObjetivo();
                Springer2.getFallo();

                if(Springer2.getObjetivo()==1){
                    if(Springer2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer2 = Springer2.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer2-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer2-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer2.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer2 = Springer2.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer2-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer2-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }



            if(Springer3.getVida()>0){
                Springer3.getObjetivo();
                Springer3.getFallo();

                if(Springer3.getObjetivo()==1){
                    if(Springer3.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer3 = Springer3.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer3-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer3-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer3.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer3 = Springer3.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer3-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer3-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Springer4.getVida()>0){
                Springer4.getObjetivo();
                Springer4.getFallo();

                if(Springer4.getObjetivo()==1){
                    if(Springer4.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer4 = Springer4.getDañoSpringer();
                        Mario.setVida(Mario.getVida()-(dañoSpringer4-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mario\nTe quitó: " + (dañoSpringer4-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                    }

                }else{
                    if(Springer4.getFallo()<=20){
                    FuncionesGraficas.warning("Ataque Springer", "Falló el ataque");
                    
                    }else{
                        int dañoSpringer4 = Springer4.getDañoSpringer();
                        Mallow.setVida(Mallow.getVida()-(dañoSpringer4-Mallow.getDefensa()));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque Springer", "Springer ataca a Mallow\nTe quitó: " + (dañoSpringer4-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                    }
                }
            }




            if(Mack.getVida()>0){
            
                if(Mack.getObjetivo()==1){
                    Mack.getAtckMagico();

                    if(Mack.getAtckMagico()<=10){
                        Mario.setVida(Mario.getVida()-(Mack.getDañoMagico()-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        Mallow.setVida(Mallow.getVida()-(Mack.getDañoMagico()-Mallow.getDefensa()-5));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque especial Mack", "Mack ataca a Mario y Mallow\nA Mario le quitó: " + (Mack.getDañoMagico()-Mario.getDefensa()) + "HP\nA Mario le queda: " + Mario.getVida() + "HP\nA Mallow le quitó: " + (Mack.getDañoMagico()-Mallow.getDefensa()-5) + "HP\nA Mallow le queda: " + Mallow.getVida() + "HP");

                    }else{
                        Mack.getFallo();
                    
                        if(Mack.getFallo()<=20){
                            FuncionesGraficas.warning("Ataque Mack", "Falló el ataque");
                        
                        }else{
                            int dañoMack = Mack.getDañoBaseMack();
                            Mario.setVida(Mario.getVida()-(dañoMack-Mario.getDefensa()));
                            if(Mario.getVida()<=0) Mario.setVida(0);
                            FuncionesGraficas.warning("Ataque Mack", "Mack ataca a Mario\nTe quitó: " + (dañoMack-Mario.getDefensa()) + "HP\nTe queda: " + Mario.getVida() + "HP");
                        }
                    }

                }else{
                    Mack.getAtckMagico();

                    if(Mack.getAtckMagico()<=10){
                        Mario.setVida(Mario.getVida()-(Mack.getDañoMagico()-Mario.getDefensa()));
                        if(Mario.getVida()<=0) Mario.setVida(0);
                        Mallow.setVida(Mallow.getVida()-(Mack.getDañoMagico()-Mallow.getDefensa()-5));
                        if(Mallow.getVida()<=0) Mallow.setVida(0);
                        FuncionesGraficas.warning("Ataque especial Mack", "Mack ataca a Mario y Mallow\nA Mario le quitó: " + (Mack.getDañoMagico()-Mario.getDefensa()) + "HP\nA Mario le queda: " + Mario.getVida() + "HP\nA Mallow le quitó: " + (Mack.getDañoMagico()-Mallow.getDefensa()-5) + "HP\nA Mallow le queda: " + Mallow.getVida() + "HP");

                    }else{
                        Mack.getFallo();
                    
                        if(Mack.getFallo()<=20){
                            FuncionesGraficas.warning("Ataque Mack", "Falló el ataque");
                        
                        }else{
                            int dañoMack = Mack.getDañoBaseMack();
                            Mallow.setVida(Mallow.getVida()-(dañoMack-Mallow.getDefensa()));
                            if(Mallow.getVida()<=0) Mallow.setVida(0);
                            FuncionesGraficas.warning("Ataque Mack", "Mack ataca a Mallow\nTe quitó: " + (dañoMack-Mallow.getDefensa()) + "HP\nTe queda: " + Mallow.getVida() + "HP");
                        }
                    }

                } 

            }


            
            
            if(Mario.getVida()<=0 && Mallow.getVida()<=0){
                jefe.detener();
                FuncionesGraficas.FotoMensajeSonido("Game Over", "src/main/java/img/gameOver.png", "", "src/main/java/sonidos/gameOver.mp3", 0.4, 1, false);
                System.exit(0);
            }
            
        }while(true);

    }




    
    //fin funciones
}
