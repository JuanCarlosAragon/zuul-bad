import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    
    //Atributos del objeto de la habitación
    private ArrayList<Item> objetos;
    private ArrayList<Person> personas;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        objetos = new ArrayList<>();
        personas = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction o the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    /**
     * @param direction de direction searched description
     * 
     * @return the Room descripted in the parameter direction
     */
    public Room getExit(String direction){
        return exits.get(direction);
    }
    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     * 
     * @return A description ofe the available exits
     */
    public String getExitString(){
        String description = new String();
        description += "Salidas: ";
        
        String[] createdExits = new String[exits.size()];
        exits.keySet().toArray(createdExits);
        for(String direction : createdExits){
            description += direction + " ";
        }
             
        return description;
    }
    /**
     * Return a long description of this room, of the form:
     *  You are in the 'name of room'
     *  Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription(){
        String toReturn = new String();
        toReturn = "Estas en " + description + "\n";
        if(objetos.isEmpty()){
            toReturn += "No hay objetos en esta habitación \n";
        }
        else{
            toReturn += "Hay " + objetos.size() 
                        + " objetos en este sitio: \n";
            for(Item objeto : objetos){
                try{
                    toReturn += "----------------------\n";
                    toReturn += objeto.getInfo();
                    toReturn += "----------------------\n";
                }
                catch(Exception ex){}
            }
            toReturn += "Hay " + personas.size() + " personas en este sitio: \n";
            for(Person persona : personas){
                try{
                    toReturn += "----------------------\n";
                    toReturn += persona.getInfo();
                    toReturn += "----------------------\n";
                }
                catch(Exception ex){}
            }
        }
        toReturn +=  getExitString();
        return toReturn;
    }
    
    /**
     * Set the object in the room
     */
    public void addItem(String clave, String description, double weight, int precio){
        objetos.add(new Item(clave, description, weight, precio));
    }
    public void addItem(Item item){
        objetos.add(item);
    }
    
    /**
     * Añade personas a los emplazamientos
     */
    public void addPerson(String nombre, String descripcion, int precio){
        personas.add(new Person(nombre, descripcion, precio));
    }
    public void addPerson(Person persona){
        personas.add(persona);
    }
    /**
     * Quita un objeto de la habitacion
     * 
     */
    public void quitItem(String clave){
        Item found = null;
        for(Item item : objetos){
           if(item.getClave().equals(clave)){
               found = item;
           }
        }
        try{
            objetos.remove(found);
        }
        catch(Exception ex){}
    }
    /**
     * Devuelve todos los objetos que tiene la habitacion
     */
    public ArrayList<Item> getItems(){
        return objetos;
    }
    /**
     * Devuelve las personas de una habitacion
     */
    public ArrayList<Person> getPersonas(){
        return personas;
    }
}
