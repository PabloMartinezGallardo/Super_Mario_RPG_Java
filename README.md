# ⭐ Super Mario RPG — Remake en Java

> Recreación de la primera parte del clásico **Super Mario RPG** (SNES): combates por turnos, objetos, tienda, posada, jefes y una estrella que conseguir.

Proyecto desarrollado **en parejas** en **Java**, con interfaces gráficas basadas en **Swing** (a través de una librería propia de funciones gráficas) y gestionado con **Maven**.

> ⚠️ **Fan game gratuito sin ánimo de lucro.** Super Mario RPG, sus personajes, imágenes y música son propiedad de **Nintendo**. Consulta el [aviso legal](#-aviso-legal).

---

## 📖 Historia

Bowser ha secuestrado a la Princesa Peach, pero Mario se planta en su castillo para rescatarla. Justo cuando derrota a Bowser, una gigantesca espada llamada **Exor** cae sobre el castillo y todo se viene abajo.

De vuelta en el Reino Champiñón, Mario descubre que Peach ha desaparecido y que debe acudir al **Canciller**. En el camino rescatará a Toads en apuros, conocerá a **Mallow**, perseguirá al ladrón **Croco** por el Camino de los Bandidos y, al regresar, encontrará el reino invadido por los secuaces de **Mack**. Tu misión: **explorar el Reino Champiñón y conseguir la primera estrella**.

---

## 🎮 Características

- 🗡️ **Combates por turnos** con ataque básico, ataque mágico, objetos y acciones especiales.
- 👥 **Dos personajes jugables**: Mario y, a partir del Reino Champiñón, **Mallow**.
- 📈 **Sistema de experiencia y niveles**, con subida de vida y magia aleatoria y nuevos ataques que se desbloquean.
- 🎒 **Inventario** con objetos curativos y pociones de revivir.
- 🛒 **Tienda** (compra y venta) y 🛏️ **posada** para recuperar fuerzas.
- 📦 **Cofres** con monedas, objetos y mejoras de magia repartidos por los caminos.
- 👹 **Jefes**: Bowser, los Hammer Bros, Croco y Mack.
- 🎬 **Cinemáticas** con GIFs animados y **música de fondo en bucle** distinta para cada zona, combate y jefe.
- 🏆 **Hall of Fame** persistente con nombre, nivel, monedas, tiempo y fecha de cada partida completada.
- 📜 **Menú de inicio** con instrucciones, copyright y salida.

---

## 🕹️ Cómo se juega

Todo el juego se maneja con **ventanas de diálogo (Swing)**: cada escena muestra una imagen, un texto y un menú de opciones; los combates usan menús y desplegables para elegir acción y objetivo.

### Menú principal

| Opción | Descripción |
|--------|-------------|
| **Jugar** | Empieza la aventura (se pide el nombre del jugador y arranca el cronómetro). |
| **Instrucciones** | Explica el objetivo y detalla objetos y ataques mágicos. |
| **Hall of Fame** | Muestra las partidas completadas anteriormente. |
| **Copyright** | Aviso legal del fan game. |
| **Salir** | Cierra el juego. |

### Combate

En cada turno eliges una de estas acciones:

| Acción | Efecto |
|--------|--------|
| **Ataque básico** | Golpe físico a un enemigo. Daño aleatorio de **10–20** (**15–30** con el martillo). |
| **Ataque mágico** | Consume puntos mágicos (ver tabla inferior). |
| **Objetos** | Usa un objeto del inventario. |
| **Otros → Defender** | Reduce en 1 el daño recibido ese turno. |
| **Otros → Huir** | No se puede huir de ningún combate. |

- Todos los ataques tienen un **20 % de probabilidad de fallo**.
- Los enemigos atacan **al azar** a Mario o a Mallow.
- Algunos enemigos tienen **defensa**, que se resta al daño recibido.
- Si **Mario cae** (en solitario) o **caen Mario y Mallow** a la vez, es *Game Over*.
- Cuando solo cae uno de los dos, puedes **revivirlo** con una poción.
- Al ganar obtienes **experiencia y monedas**.

### Ataques mágicos

| Ataque | Quién | Coste | Efecto |
|--------|-------|-------|--------|
| **Salto** | Mario | 3 pts. mág. | Gran daño a un enemigo (¡no lo uses contra Spikeys, te pinchas!). |
| **Flama** | Mario | 5 pts. mág. | Bola de fuego de gran daño a un enemigo. Se aprende al **nivel 3**. |
| **Relámpago** | Mallow | 2 pts. mág. | Cae un rayo sobre **todos** los enemigos. |

> La magia es una **reserva compartida**: Mallow gasta los puntos mágicos de Mario.

### Objetos

| Objeto | Efecto | Compra | Venta |
|--------|--------|--------|-------|
| **Champiñón** | Cura 30 de vida | 4 monedas | 2 monedas |
| **Jarabe** | Recupera 10 puntos mágicos | 8 monedas | 4 monedas |
| **Revivir** | Revive al compañero desplomado | 5 monedas | 3 monedas |

### Niveles

| Nivel | Experiencia necesaria | Extra |
|-------|-----------------------|-------|
| 2 | 20 | |
| 3 | 40 | Mario aprende **Flama** |
| 4 | 80 | |
| 5 | 120 | |
| 6 | 150 | |

Al subir de nivel se recuperan **vida y magia por completo**. Mario empieza con 20 de vida y 10 de magia; Mallow se une con 20 de vida y en nivel 2.

---

## 🗺️ Recorrido del juego

| Zona | Qué ocurre |
|------|------------|
| **Castillo de Bowser** | Cinemática del secuestro. Combate contra 4 tropas y después contra **Bowser** (hay que destruir la **Cadena Kinklink**). Cae la espada Exor. |
| **Casa de Mario** | Recibes las malas noticias y 3 champiñones. |
| **Mushroom Way 1** | Goombas, SkyTroopas y cofres con monedas y jarabe. |
| **Mushroom Way 2** | Goombas, cofres con una flor y recuperación total, y el rescate de un Toad. |
| **Mushroom Way 3** | Spikeys y el mini jefe **Hammer Bros** al rescatar a Toad. Consigues el **martillo**. |
| **Reino Champiñón** | Reunión con el Canciller. Habla con **Mallow** para que se una al equipo. Tienda, posada e inventario. |
| **Bandit's Way 1** | Persecución de Croco: Goomba, Spikeys, Frogogs y un cofre con una flor. |
| **Bandit's Way 2** | Cofre de recuperación total, matorrales y el mini jefe **Croco**. |
| **Reino invadido** | 3 combates contra Springers, tienda y posada. Al entrar al castillo, el jefe **Mack**. |
| **Final** | Consigues la **primera estrella** y se muestra tu resumen: nombre, nivel, monedas, tiempo y fecha. |

---

## 🏆 Hall of Fame

Al completar el juego se guarda un registro del jugador (`Jugador_Mario`) con:

| Campo | Descripción |
|-------|-------------|
| Nombre | El que introduces al empezar |
| Nivel | Nivel final de Mario |
| Monedas | Monedas acumuladas |
| Tiempo | Duración de la partida (en segundos) |
| Fecha | Fecha de la partida |

Los datos se serializan en `src/main/java/ficheros/datos.dat` y se cargan al arrancar. Se consultan desde el menú principal.

---

## ▶️ Cómo ejecutarlo

### Requisitos

- **JDK 25** (el `pom.xml` compila con `maven.compiler.release = 25`).
- **Maven**.
- Las dos librerías locales del proyecto, que **no están en Maven Central** y deben instalarse en tu repositorio local de Maven:

  | Librería | groupId / artifactId | Versión | Uso |
  |----------|----------------------|---------|-----|
  | `MiLibreria` | `MiLibreria:MiLibreria` | 1.0 | Paquete `modulos`: `FuncionesGraficas` (diálogos Swing con imagen, menús, entrada de datos y sonido) y `Ficheros` (lectura/escritura de datos serializados). |
  | `JackMp3` | `JackMp3:JackMp3` | 1.0 | Reproducción de audio MP3. |

  ```bash
  mvn install:install-file -Dfile=MiLibreria.jar -DgroupId=MiLibreria -DartifactId=MiLibreria -Dversion=1.0 -Dpackaging=jar
  mvn install:install-file -Dfile=JackMp3.jar   -DgroupId=JackMp3   -DartifactId=JackMp3   -Dversion=1.0 -Dpackaging=jar
  ```

### Compilar y lanzar

Ejecuta **siempre desde la raíz del proyecto** (`SuperMarioRPG/`): las rutas a imágenes, sonidos y al fichero de datos son relativas (`src/main/java/img/...`).

```bash
mvn compile exec:java -Dexec.mainClass=com.mycompany.supermariorpg.SuperMarioRPG
```

También puedes generar un `.jar` ejecutable con todas las dependencias (el `pom.xml` incluye `maven-assembly-plugin`):

```bash
mvn package
java -jar target/SuperMarioRPG-1.0-SNAPSHOT-jar-with-dependencies.jar
```

(Lanza el `.jar` desde la raíz del proyecto para que encuentre los recursos.)

---

## 🧩 Estructura del proyecto

```
SuperMarioRPG/
├── pom.xml
└── src/main/java/
    ├── com/mycompany/supermariorpg/
    │   └── SuperMarioRPG.java   # Lógica del juego: una función por escena y por combate
    ├── clases/
    │   ├── Jugador_Mario.java   # Mario: estadísticas, daños aleatorios y datos del Hall of Fame
    │   ├── Mallow.java          # Compañero: estadísticas y Relámpago
    │   ├── Enemigo.java         # Enemigos normales (Goomba, Spikey, Frogog, Springer...)
    │   └── Jefe.java            # Jefes (Hammer Bros, Croco, Mack)
    ├── modulo/                  # Reproductor de música en bucle (ReproductorBucle)
    ├── ficheros/
    │   └── datos.dat            # Datos persistentes del Hall of Fame
    ├── img/                     # Fondos, objetos, pantallas de combate y cinemáticas (GIF)
    └── sonidos/                 # Música ambiente, de combate, de jefes y efectos (.mp3 / .wav)
```

### Diseño del código

- **`SuperMarioRPG.main`** pide el nombre del jugador y encadena todas las escenas en orden; al final calcula el tiempo, guarda la partida y muestra el resumen.
- Cada escena y cada combate es un **método estático** (`escenaMushroomWay1`, `peleaGoomba1`, `jefeCroco`...) con su propio bucle de menú.
- El estado global (inventario, ataques mágicos, experiencia) vive en **variables estáticas** (`ArrayList` para inventario y menús de magia).
- Las **clases de personajes** (`Jugador_Mario`, `Mallow`, `Enemigo`, `Jefe`) son `Serializable` y generan sus daños y probabilidades con `ThreadLocalRandom`.
- La **música de fondo** se reproduce en hilos independientes que se detienen y se relanzan al cambiar de escena.

---

<details>
<summary>💡 <strong>Consejos y estrategia</strong> (contienen pequeños spoilers)</summary>

- **Abre todos los cofres**: dan monedas, jarabe, flores (+1 magia máxima) y recuperaciones completas.
- **Evita usar Salto contra los Spikeys**: te pinchas, pierdes 1 de vida y no les haces daño. Usa Flama o ataques básicos.
- **Relámpago de Mallow** es ideal contra grupos grandes (Springers de Mack).
- **Recuerda que la magia es compartida**: guarda jarabes para los jefes.
- **Compra pociones de Revivir** antes de entrar al castillo; los jefes golpean fuerte.
- **Visita la posada** (10 monedas) para recuperar vida y magia de ambos personajes.
- **En Bandit's Way 2** solo uno de los matorrales esconde a Croco: busca bien.
- **Para avanzar de Mushroom Way 3** necesitas rescatar a Toad (mini jefe Hammer Bros).
- **Para salir del Reino Champiñón** primero debes hablar con Mallow.

</details>

---

## 🛠️ Limitaciones y errores conocidos

Cosas que conviene saber si vas a seguir desarrollando el proyecto:

- **Enemigos con el mismo nombre**: en combates con varios enemigos iguales (por ejemplo, 2 *Spikey*, 2 *Frogog* o los *Springer*) el objetivo se elige comparando nombres, así que los ataques de un solo objetivo siempre acaban en el primero. El segundo solo puede eliminarse con el Relámpago de Mallow.
- **La fecha del Hall of Fame** muestra la fecha actual y no la de la partida, porque se lee con `Calendar.getInstance()` (método estático) en lugar de la fecha guardada.
- **La magia de Mallow** se descuenta de la reserva de Mario, y `levelUpMallow` sube el máximo de magia de Mario en lugar de uno propio.
- **Rutas sin prefijo** en `peleaGoomba3`: `sonidos/curar.wav` e `img/mallowRevivir.png` no apuntan a `src/main/java/...` y no se encontrarán.
- Una **derrota (Game Over)** cierra el juego con `System.exit(0)`: la partida no se guarda en el Hall of Fame.
- El **Hall of Fame no está ordenado** (aparece por orden de partida).
- Las **rutas relativas** obligan a ejecutar el juego desde la raíz del proyecto.
- **Mucho código repetido**: cada combate duplica la lógica de turnos. Un motor de combate genérico (lista de enemigos + objetos comunes) reduciría el código drásticamente.
- `Jefe.setDefensaMag` no asigna el valor recibido (el parámetro y el atributo no coinciden).

---

## 👥 Autores

Proyecto realizado en parejas por:

- Pablo

---

## 📄 Aviso legal

Este es un **fan game gratuito** creado exclusivamente por amor a la franquicia y con fines de entretenimiento y aprendizaje.

*Super Mario RPG*, sus personajes y su música son propiedad intelectual de **Nintendo**. Este proyecto no está afiliado, respaldado ni patrocinado por Nintendo.

**Queda prohibida su venta o distribución comercial.**
