
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
   private String description;
   private double weight;
   
   public Item(String description, double weight){
       this.description = description;
       this.weight = weight;
    }
    
   public String getDescription(){
       return description;
    }
   
   public double getWeight(){
       return weight;
    }
    
   public String getInfo(){
       return description + "\nPeso: " + weight + "\n";
    }
}
