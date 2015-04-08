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
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
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
        plaza1 = new Room("En la plaza mayor del pueblo");
        bar = new Room("En el bar de donde te echaron");
        iglesia = new Room("La iglesia del pueblo, huele a incienso");
        ayuntamiento = new Room("En el holl del ayuntamiento");
        oficina = new Room("El despacho del alcalde");
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
        plaza1.setExits(ayuntamiento, iglesia, bar, calle, null);
        bar.setExits(plaza1, null, null, null, null);
        iglesia.setExits(null, null, null, plaza1, null);
        ayuntamiento.setExits(oficina, null, plaza1, null, null);
        oficina.setExits(null, null, ayuntamiento, null, null);
        calle.setExits(null, plaza1, null, plaza2, null);
        plaza2.setExits(casa1, calle, centroComercial, casa2, null);
        casa1.setExits(patio, null, plaza2, null, null);
        casa2.setExits(null, plaza2, null, null, null);
        patio.setExits(null, null, casa1, cobertizo, null);
        cobertizo.setExits(null, patio, null, null, null);
        centroComercial.setExits(plaza2, tienda2, almacen, tienda1, null);
        tienda1.setExits(null, centroComercial, null, null, null);
        tienda2.setExits(null, null, null, centroComercial, null);
        almacen.setExits(centroComercial, pasilloSecreto, null,null, null);
        pasilloSecreto.setExits(null, garita, null, almacen, null);
        garita.setExits(calabozo, laboratorio, null, pasilloSecreto, null);
        calabozo.setExits(null, null, garita, null, null);
        laboratorio.setExits(null, null, null, garita, agujero);
        agujero.setExits(null,null,null,null,null);

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
        System.out.println("   go quit help");
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
        Room nextRoom = null;
        if(direction.equals("north")) {
            nextRoom = currentRoom.northExit;
        }
        if(direction.equals("east")) {
            nextRoom = currentRoom.eastExit;
        }
        if(direction.equals("south")) {
            nextRoom = currentRoom.southExit;
        }
        if(direction.equals("west")) {
            nextRoom = currentRoom.westExit;
        }
        if(direction.equals("southeast")){
            nextRoom = currentRoom.southEastExit;
        }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
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
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Exits: ");
        if(currentRoom.northExit != null) {
            System.out.print("north ");
        }
        if(currentRoom.eastExit != null) {
            System.out.print("east ");
        }
        if(currentRoom.southExit != null) {
            System.out.print("south ");
        }
        if(currentRoom.westExit != null) {
            System.out.print("west ");
        }
        if(currentRoom.southEastExit != null){
            System.out.print("southeast ");
        }
        System.out.println();
    }
}
