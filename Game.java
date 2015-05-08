import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game
{
    private Parser parser;
    private Player player;
    private ArrayList<Person> personasCiudad;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game () 
    {
        personasCiudad = new ArrayList<>();
        player = new Player();
        createRooms();
        parser = new Parser();
        
    }
    
    /**
     * Create all the rooms and link their exits together.
     */
    protected void createRooms()
    {
        Room plaza1, bar, iglesia, ayuntamiento, oficina, calle, plaza2, casa1;
        Room casa2, patio, cobertizo, centroComercial, tiendaComida, tiendaPesca, almacen;
        Room pasilloSecreto, garita, calabozo, laboratorio, agujero, bano, tiendaChinos, rio;
      
        // create the rooms
        plaza1 = new Room("la plaza mayor del pueblo");
        bar = new Room("el bar del pueblo");
        bano = new Room("El baño del bar, que mal huele");
        iglesia = new Room("La iglesia del pueblo, huele a incienso");
        ayuntamiento = new Room("el holl del ayuntamiento");
        oficina = new Room("el despacho del alcalde");
        calle = new Room("Una callejuela");
        plaza2 = new Room("Una plaza adoquinada");
        casa1 = new Room("La casa de Pepa la frutera");
        casa2 = new Room("La casa de Jose el pescadero");
        rio = new Room("Un bonito rio que pasa por el pueblo");
        patio = new Room("El patio trasero de Pepa");
        cobertizo = new Room("Un cobertizo en el patio de Pepa");
        centroComercial = new Room("Un centro comercial fantasma");
        tiendaComida = new Room("Una tienda de alimentos");
        tiendaPesca = new Room("Una tienda de pesca");
        tiendaChinos = new Room("Unos chinos, que barato todo!");
        almacen = new Room("Un almacen lleno de ratas y humedad");
        pasilloSecreto = new Room("Un pasillo oscuro y estrecho");
        garita = new Room("La garita de seguridad");
        calabozo = new Room("El calabozo del centro comercial");
        laboratorio = new Room("Un extraño laboratorio en un centro comercial");
        
        
        // initialise room exits
        plaza1.setExit("ayuntamiento",ayuntamiento);
        plaza1.setExit("iglesia",iglesia);
        plaza1.setExit("bar", bar);
        plaza1.setExit("calle", calle);
        
        bar.setExit("salida", plaza1);
        bar.setExit("bano", bano);
        
        bano.setExit("salida", bar);
        
        iglesia.setExit("salida", plaza1);

        ayuntamiento.setExit("despacho",oficina);
        ayuntamiento.setExit("salida",plaza1);

        oficina.setExit("salida",ayuntamiento);

        calle.setExit("plazaMayor", plaza1);
        calle.setExit("plaza", plaza2);

        plaza2.setExit("casaPepa",casa1);
        plaza2.setExit("calle",calle);
        plaza2.setExit("centroComercial", centroComercial);
        plaza2.setExit("casaJose", casa2);
        plaza2.setExit("rio", rio);
        
        rio.setExit("pueblo", plaza2);

        casa1.setExit("puerta", patio);
        casa1.setExit("salida", plaza2);

        casa2.setExit("salida",plaza2);

        patio.setExit("volverDentro", casa1);
        patio.setExit("cobertizo", cobertizo);

        cobertizo.setExit("salida", patio);

        centroComercial.setExit("salida",plaza2);
        centroComercial.setExit("tiendaComida",tiendaComida);
        centroComercial.setExit("puerta", almacen);
        centroComercial.setExit("tiendaPesca", tiendaPesca);
        centroComercial.setExit("tiendaChinos", tiendaChinos);

        tiendaPesca.setExit("salida", centroComercial);
 
        tiendaComida.setExit("salida", centroComercial);
        
        tiendaChinos.setExit("salida", centroComercial);

        almacen.setExit("salida", centroComercial);
        almacen.setExit("puerta", pasilloSecreto);

        pasilloSecreto.setExit("puertaSeguridad",garita);
        pasilloSecreto.setExit("salida",almacen);

        garita.setExit("calabozo", calabozo);
        garita.setExit("laboratorio", laboratorio);
        garita.setExit("salida",pasilloSecreto);

        calabozo.setExit("salida", garita);

        laboratorio.setExit("salida", garita);
        
        plaza1.addItem("moneda", "Una moneda antigua de plata", 0.1, 10);
        plaza1.addItem("papel", "Un papel en el suelo", 0.0, 0);
        plaza1.addItem("petaca", "Una petaca llena de alcohol", 0.1, 5);
        bar.addItem("banqueta", "Una banqueta de bar", 2.5, 6);
        bar.addItem("vaso", "Un vaso lleno de whisky", 0.2, 5);
        bar.addItem("cacahuetes", "Un plato con cacahuetes", 0.1, 0);
        bar.addItem("baraja", "Una baraja de cartas", 0.3, 2);
        bano.addItem("retrete", "El retrete", 5.0, 0);
        bano.addItem("espejo", "Un espejo roñoso", 3.0, 0);
        iglesia.addItem("banco", "Un banco de madera", 20, 50);
        iglesia.addItem("incienso", "Incienso... espera!, huele a...", 0.5, 100);
        iglesia.addItem("vela", "Una vela casi gastada", 0.2, 0);
        iglesia.addItem("vino", "La sangre de cristo, ese dia cristo se pasó bebiendo...", 0.3, 0);
        ayuntamiento.addItem("placa", "Una placa conmemorativa del alcalde actual", 0.6, 10);
        ayuntamiento.addItem("encuesta", "La intención de votos del pueblo", 0.1, 0);
        oficina.addItem("foto", "Una foto del alcalde con su mujer", 0.1, 0);
        oficina.addItem("escritorio", "Un majestuoso escritorio de Caoba", 120, 500);
        oficina.addItem("legislacion", "Un aburrimiento de libro echo para ignorarlo", 3.9,50);
        calle.addItem("adoquin", "Un adoquin pesado e inutil", 2.0, 0);
        calle.addItem("arbusto", "Un arbusto seco y sin vida", 1.0, 0);
        plaza2.addItem("pelota", "La pelota de algún niño", 0.3, 10);
        plaza2.addItem("bicicleta", "La bicicleta de algún niño", 3.4, 50);
        plaza2.addItem("boligrafo", "Un boli gastado, piensas que te puede valer?", 0.1, 0);
        rio.addItem("piedra", "Una piedra a la orilla para sentarse!", 50, 0);
        casa1.addItem("television", "Una television... sin mas", 3, 300);
        casa1.addItem("florero", "Un florero con petunias de temporada", 0.5, 20);
        casa2.addItem("portatil", "Un portatil... tiene abierto BlueJ", 0.3, 500);
        casa2.addItem("soga", "Una soga en el techo, el que usaba el portatil debió usarla", 1.1, 0);
        patio.addItem("olmo", "Un olmo, parece que da peras", 80.2, 0);
        cobertizo.addItem("hacha", "Un hacha afilada", 5.3, 30);
        cobertizo.addItem("cortacesped", "Un cortacesped motorizado", 90, 300);
        cobertizo.addItem("abono", "Un saco mierda", 10, 0);
        cobertizo.addItem("oz", "Una oz", 3, 20);
        cobertizo.addItem("martillo", "Un martillo...", 0.5, 20);
        cobertizo.addItem("escopeta", "Una escopeta", 3, 200);
        centroComercial.addItem("fuente", "Una fuente con pececitos", 400, 0);
        tiendaPesca.addItem("cebo", "Cebo para pescar", 0.1, 2);
        tiendaPesca.addItem("canna", "Una caña de pescar", 0.2, 30);
        tiendaComida.addItem("tortilla", "Una rica tortilla de patata", 0.3, 5);
        tiendaComida.addItem("chorizo", "Un sabroso chorizo", 0.2, 3);
        tiendaComida.addItem("morcilla", "Una morcilla", 0.3, 3);
        tiendaChinos.addItem("gorra", "Una gorra", 0.1, 4);
        tiendaChinos.addItem("sombrero", "Un sombrero", 0.1, 9);
        tiendaChinos.addItem("petardo", "Una caja de petardos", 0.1, 5);
        tiendaChinos.addItem("gato", "Un gato dorado que mueve la pata", 0.1, 3);
        tiendaChinos.addItem("escoba", "Una escoba", 0.1, 6);
        tiendaChinos.addItem("recogedor", "Un recogedor", 0.1, 3);
        tiendaChinos.addItem("spray", "Un Spray color transparente", 0.1, 4);
        tiendaChinos.addItem("riñon", "Un riñon de contrabando", 0.1, 5);
        tiendaChinos.addItem("carretillo", "Un carretillo", 0.1,30);
        tiendaChinos.addItem("disquete", "Un disquete", 0.1, 4);
        tiendaChinos.addItem("disco", "Un disco de Mestizo, lo mas vendido sin duda", 0.1, 5);
        almacen.addItem("fregona", "Una fregona mohosa", 0.3, 0);
        garita.addItem("cartel", "Un cartel de los mas buscados", 0.1, 0);
        garita.addItem("silla", "Una silla de escritorio", 0.5, 6);
        calabozo.addItem("lima", "Una lima", 0.2, 10);
        laboratorio.addItem("probeta", "Una probeta", 0.1, 9);
        laboratorio.addItem("bisturi", "Un bisturí", 0.1, 10);
        laboratorio.addItem("raton", "Un raton de laboratorio", 0.1, 0);
        
        Person persona = new Person("Chino", "El barman", 500);
            bar.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Susana", "La hija del Panadero", 300);
            plaza1.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Rodrigo", "El hijo de Susana", 150);
            plaza1.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Pablo", "El tonto del pueblo", 5);
            plaza1.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Chicho", "El del puesto de malacatones", 450);
            plaza1.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Pedrin", "El borracho", 300);
            bar.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Koke", "El ludopata", 400);
            bar.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Padre", "El cura", 1000);
            iglesia.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Pablito", "El niño que tenia escondido el cura", 30);
            iglesia.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Ana", "La secretaria del actual alcalde", 90);
            ayuntamiento.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Julian", "El alcalde", 5000);
            oficina.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Saul", "El mendigo", 3);
            calle.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Brutus", "Un perro muy listo", 50);
            plaza2.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Carlos", "Un parado mas", 500);
            plaza2.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Genaro", "El encargado de mantenimiento", 300);
            centroComercial.addPerson(persona);
            personasCiudad.add(persona);
        persona = new Person("Luis", "El dependiente", 500);
            tiendaPesca.addPerson(persona);
            personasCiudad.add(persona);
         persona = new Person("Pepito", "El dependiente", 500);
            tiendaComida.addPerson(persona);
            personasCiudad.add(persona);
         persona = new Person("ShinLuz", "El dependientle", 500);
            tiendaChinos.addPerson(persona);
            personasCiudad.add(persona);
         persona = new Person("Romero", "El madero", 100);
            garita.addPerson(persona);
            personasCiudad.add(persona);
         persona = new Person("Jose", "El mejor amigo de Romero", 500);
            calabozo.addPerson(persona);
            personasCiudad.add(persona);
         persona = new Person("Zoiberg", "Un cientifico de otro planeta", 1000);
            laboratorio.addPerson(persona);
            personasCiudad.add(persona);

        player.setCurrentRoom(plaza1);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Gracias por jugar.  Hasta luego!.");
    }

    /**
     * Print out the opening message for the player.
     */
    protected void printWelcome()
    {
        System.out.println();
        System.out.println("Bienvenido al mundo de Zuul!");
        System.out.println("El mundo de Zuul es un nuevo e increiblemente aburrido juego");
        System.out.println("Consigue llegar a alcalde");
        System.out.println("teclea 'Ayuda' si tu necesitas ayuda");
        System.out.println();
        player.printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    protected boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        Option commandWord = command.getCommandWord();
        
        switch(commandWord){
            case UNKNOWN:
                System.out.println("No se que me quieres decir");
                return false;
            case help:
                printHelp();
                break;
            case go:
                player.goRoom(command);
                break;
            case quit:
                wantToQuit = quit(command);
                break;
            case look:
                player.printLocationInfo();
                break;
            case eat:
                player.eat(command);
                break;
            case back:
                player.backInstruction();
                break;
            case take:
                player.takeObject(command);
                break;
            case items:
                player.showItems();
                break;
            case drop:
                player.dropObject(command);
                break;
            case pesca:
                player.pesca();
                break;
            case soborna:
                player.soborna(command);
                comprueba();
                break;
            case comprueba:
                comprueba();
                break;
        }
        return wantToQuit;
    }

    /**
     * Devuelve las personas que hay en la ciudad
     */
    private ArrayList<Person> getPersonas(){
        return personasCiudad;
    }
    /**
     * Devuelve el numero de personas que hay en la ciudad
     */
    private int getNumPersonas(){
        return personasCiudad.size();
    }
    /**
     * Devuelve el numero de personas ya sobornadas
     */
    private int getSobornosConseguidos(){
        int contSobornos = 0;
        for(Person persona : personasCiudad){
            if(persona.sobornado()){
                contSobornos++;
            }
        }
        return contSobornos;
    }
    /**
     * Comprueba si se ha completado el juego
     */
    public void comprueba(){
        if(getSobornosConseguidos() == getNumPersonas()){
            System.out.println("HAS GANADO, Serás alcalde!!");
        }
        else{
            System.out.println("Llevas " + getSobornosConseguidos() + " de " + getNumPersonas() + " Sobornos");
        }
    }
    
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    protected void printHelp() 
    {
        System.out.println("Estas perdido, necesitas ayuda");
        System.out.println();
        System.out.println("Las palabras clave son:");
        parser.printAllCommands();
    }
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quitar el qué?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
