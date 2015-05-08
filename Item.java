
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
   private String description;
   private String clave;
   private int precio;
   private double weight;
   
   public Item(String clave, String description, double weight, int precio){
       this.description = description;
       this.weight = weight;
       this.clave = clave;
       this.precio = precio;
    }
    
   public String getDescription(){
       return description;
    }
   
   public double getWeight(){
       return weight;
    }
    
   public String getInfo(){
       String info = clave + "\n" + description + "\nPeso: " + weight + "\n";
       if(precio != 0){
           info += precio + "€\n";
        }
       return info;
    }
   
   public String getClave(){
       return clave;
    }
   public int getPrecio(){
       return precio;
    }
}
