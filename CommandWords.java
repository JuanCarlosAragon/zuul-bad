import java.util.HashMap;
import java.util.Set;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private HashMap<String,Option> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        validCommands.put("ir", Option.go);
        validCommands.put("salir", Option.quit);
        validCommands.put("ayuda", Option.help);
        validCommands.put("mira", Option.look);
        validCommands.put("come", Option.eat);
        validCommands.put("atras", Option.back);
        validCommands.put("coge", Option.take);
        validCommands.put("suelta", Option.drop);
        validCommands.put("mochila", Option.items);
        validCommands.put("pesca", Option.pesca);
        validCommands.put("soborna", Option.soborna);
        validCommands.put("comprueba", Option.comprueba);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        for(int i = 0; i < validCommands.size(); i++) {
            if(validCommands.containsKey(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }
    public boolean isCommand(Option option){
        return validCommands.containsValue(option);
    }
    
    /**
     * Print all valid commands to System.out
     */
    public void showAll(){
        String toPrint = new String();
        Set<String> keys = validCommands.keySet();
        for (String key : keys){
            toPrint += key;
            toPrint += " ";
        }
        System.out.println(toPrint);
    }
    
    /**
     * Return the object Option associated with a word.
     * @param commandWord the word to look up (as a string)
     * 
     * @return the object Option corresponding to the parameter commandWord,
     * or de object Option.UNKNOWN if it is not a valid command word
     */
    public Option getCommandWord(String commandWord){
        Option toReturn = Option.UNKNOWN;
        if(isCommand(commandWord)){
            toReturn = validCommands.get(commandWord);
        }
        return toReturn;
    }
}
