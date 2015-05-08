import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
   private Room currentRoom;
   private Stack<Room> roomHistorial;
   private ArrayList<Item> items;
   private int cartera;
   private int cebo;
   private int hambre;
   private int contBano;
   private int contVegiga;
   private boolean necesidadBano;
   private static final int TOPE_VEGIGA = 10;
   private static final int TOPE_BANO = 5;
   private static final int TOPE_HAMBRE = 200;
   private static final int INTERVALO_HAMBRE = 20;
   private static final int DINERO_INICIAL = 20;
   
   public Player(){
        roomHistorial = new Stack<>();
        items = new ArrayList<>();
        cartera = DINERO_INICIAL;
        cebo = 0;
        hambre = 0;
        contBano = 0;
        contVegiga = TOPE_VEGIGA;
        necesidadBano = false;
   }
   
   public void setCurrentRoom(Room room){
       currentRoom = room;
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("ir donde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("eso no es una puerta!");
        }
        else {
            if(hambre < TOPE_HAMBRE){
                if(contVegiga > 0){
                    //Actualizamos la habitación de donde venimos
                    roomHistorial.push(currentRoom);
                    currentRoom = nextRoom;
                    printLocationInfo();
                    hambre += INTERVALO_HAMBRE;
                    if(necesidadBano){
                        contVegiga--;
                        System.out.println("Necesito ir al Baño!!!, me quedan " + contVegiga + " movimientos");
                    }
                    if(currentRoom.getDescription().equals("El baño del bar, que mal huele")){
                        contVegiga = TOPE_VEGIGA;
                        necesidadBano = false;
                        contBano = 0;
                        System.out.println("Gracias!!! lo necesitaba!!!");
                    }
                }
                else{
                    System.out.println("Has Muerto de un fallo renal");
                }
            }
            else{
                System.out.println("No puedo moverme de hambre!");
            }
        }
    }
    /**
     * Imprime por pantalla la información y salidas disponibles del lugar 
     * en el que está el personaje.
     */
    public void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * Hace retroceder al personaje a la habitación anterior
     */
    public void backInstruction(){
        if(!roomHistorial.isEmpty()){
                System.out.println("Has retrocedido!\n");
                currentRoom = roomHistorial.pop();
                printLocationInfo();
            }
            else{
                System.out.println("No puedes retroceder");
            }
    }
    /**
     * Recoge un objeto si puede
     */
    public void takeObject(Command command){
        if(command.hasSecondWord()){
            boolean found = false;
            Item searched = null;
            for(Item item : currentRoom.getItems()){
                if(item.getClave().equals(command.getSecondWord())){
                    found = true;
                    searched = item;
                }
            }
            if(found &&  searched.getPrecio()!=0){
                if(currentRoom.getDescription().equals("Una tienda de alimentos")
                    || currentRoom.getDescription().equals("Una tienda de pesca")
                    ||currentRoom.getDescription().equals("Unos chinos, que barato todo!")){
                        if(searched.getPrecio() <= cartera){
                            if(searched.getClave().equals("cebo")){
                                cebo++;
                            }
                            else if(searched.getClave().equals("tortilla")){
                                items.add(searched);
                            }
                            else if(searched.getClave().equals("chorizo")){
                                items.add(searched);
                            }
                            else if(searched.getClave().equals("morcilla")){
                                items.add(searched);
                            }
                            else{
                                items.add(searched);
                                currentRoom.quitItem(command.getSecondWord());
                            }
                            gastar(searched.getPrecio());
                        }
                        else{
                            System.out.println("No tienes dinero suficiente!");
                        }
                }
                else{
                    items.add(searched);
                    currentRoom.quitItem(command.getSecondWord());
                }
            }
            if(found && searched.getPrecio() == 0){
                System.out.println("El objeto no se puede coger, no vale para nada!");
            }
            if(!found){
                System.out.println("No hay ningún " + command.getSecondWord());
            }
        }
        else{
            System.out.println("¿Coger el qué?");
        }
    }
    /**
     * Muestra por pantalla todos los objetos que lleva el jugador
     */
    public void showItems(){
        System.out.println("Tengo " + hambre + " puntos de hambre de " + TOPE_HAMBRE);
        System.out.println("Tengo " + cartera + " euros");
        if(cebo>0){
            System.out.println("Tengo " + cebo + " piezas de cebo para pescar");
        }
        System.out.println("----------------------------");
        System.out.println("Tengo " + items.size() + " objetos en la mochila");
        for(Item item : items){
            System.out.println(item.getInfo());
        }
    }
    /**
     * Suelta el objeto indicado
     */
    public void dropObject(Command command){
        if(command.hasSecondWord()){
            Item searched = null;
            boolean found = false;
            for(Item item : items){
                if(item.getClave().equals(command.getSecondWord())){
                    searched = item;
                    found = true;
                }
            }
            if(!found){
                System.out.println("Yo no tengo ese objeto");
            }
            
            if(currentRoom.getDescription().equals("Una tienda de alimentos")
                || currentRoom.getDescription().equals("Una tienda de pesca")
                ||currentRoom.getDescription().equals("Unos chinos, que barato todo!") 
                && searched != null){
                if(searched.getClave().equals("pez")){
                    ganar(searched.getPrecio());
                    items.remove(searched);
                }
                else{
                    ganar(searched.getPrecio());
                    currentRoom.addItem(searched);
                    items.remove(searched);
                }        
            }
            else{
                currentRoom.addItem(searched);
                items.remove(searched);
            }
        }
        else{
            System.out.println("Que suelte el qué?");
        }
    }
    /**
     * El personaje come
     */
    public void eat(Command command){
        if(command.hasSecondWord()){
            Item searched = null;
            boolean found = false;
            for(Item item : items){
                if(item.getClave().equals(command.getSecondWord())){
                    hambre = 0;
                    contBano++;
                    found = true;
                    searched = item;
                    if(contBano >= TOPE_BANO){
                        necesidadBano = true;
                    }
                }
            }
            items.remove(searched);
            if(!found){
                System.out.println("No tengo nada que comer con ese nombre");
            }
        }
        else{
            System.out.println("Que coma el qué?");
        }
    }
    public void pesca(){
        if(currentRoom.getDescription().equals("Un bonito rio que pasa por el pueblo")){
            boolean cannaFound = false;
            boolean ceboFound = false;
            if(cebo > 0){
                ceboFound = true;
            }
            for(Item item : items){
                if(item.getClave().equals("canna")){
                    cannaFound = true;
                }
            }
            if(cannaFound && ceboFound){
                System.out.println("Pescando... Espera a ver...");
                cebo--;
                Random rnd = new Random();
                try{
                    Thread.sleep(5000);
                }
                catch(Exception ex){}
                if(rnd.nextInt(5) == 1 || rnd.nextInt(5) == 2){
                    System.out.println("Han picado!!!!");
                    items.add(new Item("pez", "Un rico pez y fresco", rnd.nextInt(5) + 1, rnd.nextInt(10)));
                    System.out.println("Mira la mochila para ver lo que has pescado");
                }
                else{
                    System.out.println("No han picado... lástima!");
                }
            }
            else{
                System.out.println("Para pescar necesitas una caña y cebo");
            }
        }
        else{
            System.out.println("Solo puedes pescar en el río");
        }
    }
    public void soborna(Command command){
        if(command.hasSecondWord()){
            Person searched = null;
            boolean found = false;
            for(Person persona : currentRoom.getPersonas()){
                if(persona.getNombre().equals(command.getSecondWord())){
                    searched = persona;
                    found = true;
                }
            }
            if(!found){
                System.out.println("No veo a ese tal " + command.getSecondWord());
            }
            else{
                if(searched.getPrecioSoborno() <= cartera){
                    searched.setSoborno(searched.getPrecioSoborno());
                    cartera -= searched.getPrecioSoborno();
                }
                else{
                    searched.setSoborno(0);
                }
            }
        }
        else{
            System.out.println("Soborna a quien?");
        }
    }
    public void gastar(int cant){
        cartera -= cant;
    }
    public void ganar(int cant){
        cartera += cant;
    }
    public int getDinero(){
        return cartera;
    }
}
