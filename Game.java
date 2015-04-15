import java.util.Stack;
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
    private Room currentRoom;
    
    //La habitación de donde venimos
    private Stack<Room> roomHistorial;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomHistorial = new Stack<>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room plaza1, bar, iglesia, ayuntamiento, oficina, calle, plaza2, casa1;
        Room casa2, patio, cobertizo, centroComercial, tienda1, tienda2, almacen;
        Room pasilloSecreto, garita, calabozo, laboratorio, agujero;
      
        // create the rooms
        plaza1 = new Room("la plaza mayor del pueblo");
        bar = new Room("el bar de donde te echaron");
        iglesia = new Room("La iglesia del pueblo, huele a incienso");
        ayuntamiento = new Room("el holl del ayuntamiento");
        oficina = new Room("el despacho del alcalde");
        calle = new Room("Una callejuela");
        plaza2 = new Room("Una plaza adoquinada");
        casa1 = new Room("La casa de Pepa la frutera");
        casa2 = new Room("La casa de Jose el pescadero");
        patio = new Room("El patio trasero de Pepa");
        cobertizo = new Room("Un cobertizo en el patio de Pepa");
        centroComercial = new Room("Un centro comercial fantasma");
        tienda1 = new Room("Una tienda abandonada de ropa");
        tienda2 = new Room("Una tienda abandonada de pesca");
        almacen = new Room("Un almacen lleno de ratas y humedad");
        pasilloSecreto = new Room("Un pasillo oscuro y estrecho");
        garita = new Room("La garita de seguridad");
        calabozo = new Room("El calabozo del centro comercial");
        laboratorio = new Room("Un extraño laboratorio en un centro comercial");
        agujero = new Room("Un extraño tunel espacio/temporal");
        
        
        // initialise room exits
        plaza1.setExit("north",ayuntamiento);
        plaza1.setExit("east",iglesia);
        plaza1.setExit("south", bar);
        plaza1.setExit("west", calle);
        
        bar.setExit("north", plaza1);
        
        iglesia.setExit("west", plaza1);

        ayuntamiento.setExit("north",oficina);
        ayuntamiento.setExit("south",plaza1);

        oficina.setExit("south",ayuntamiento);

        calle.setExit("east", plaza1);
        calle.setExit("west", plaza2);

        plaza2.setExit("north",casa1);
        plaza2.setExit("east",calle);
        plaza2.setExit("south", centroComercial);
        plaza2.setExit("west", casa2);

        casa1.setExit("north", patio);
        casa1.setExit("south", plaza2);

        casa2.setExit("east",plaza2);

        patio.setExit("south", casa1);
        patio.setExit("west", cobertizo);

        cobertizo.setExit("east", patio);

        centroComercial.setExit("north",plaza2);
        centroComercial.setExit("east",tienda2);
        centroComercial.setExit("south", almacen);
        centroComercial.setExit("west", tienda1);

        tienda1.setExit("east", centroComercial);
 
        tienda2.setExit("west", centroComercial);

        almacen.setExit("north", centroComercial);
        almacen.setExit("east", pasilloSecreto);

        pasilloSecreto.setExit("east",garita);
        pasilloSecreto.setExit("west",almacen);

        garita.setExit("north", calabozo);
        garita.setExit("east", laboratorio);
        garita.setExit("west",pasilloSecreto);

        calabozo.setExit("south", garita);

        laboratorio.setExit("west", garita);
        laboratorio.setExit("southeast", agujero);

        agujero.setExit("northwest", laboratorio);
        
        plaza1.addItem("Una fuente", 150.5);
        plaza1.addItem("Una papelera", 30);
        garita.addItem("Una pistola", 0.8);
        cobertizo.addItem("Una nave espacial", 340);
        calle.addItem("Un borracho", 80);

        currentRoom = plaza1;  // start game outside
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
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            printLocationInfo();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("back")){
            backInstruction();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.printAllCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            //Actualizamos la habitación de donde venimos
            roomHistorial.push(currentRoom);
            
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * Imprime por pantalla la información y salidas disponibles del lugar 
     * en el que está el personaje.
     */
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * Hace retroceder al personaje a la habitación anterior
     */
    private void backInstruction(){
        if(!roomHistorial.isEmpty()){
                System.out.println("You go back!\n");
                currentRoom = roomHistorial.pop();
                printLocationInfo();
            }
            else{
                System.out.println("You can´t go back");
            }
    }
}
