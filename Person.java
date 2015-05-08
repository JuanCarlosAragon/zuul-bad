
/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Person
{
    private String nombre;
    private String descripcion;
    private int precioSoborno;
    private boolean sobornado;
    public Person(String nombre, String descripcion, int precioSoborno){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioSoborno = precioSoborno;
        sobornado = false;
    }
    public String getNombre(){
        return nombre;
    }
    public String getDescripcion(){
        return descripcion;
    }
    public int getPrecioSoborno(){
        return precioSoborno;
    }
    public boolean sobornado(){
        return sobornado;
    }
    public void setSoborno(int cant){
        System.out.println("Tendrás que intentar convencerme...");
        try{
            Thread.sleep(2000);
        }
        catch(Exception ex){}
        if(cant >= precioSoborno){
            sobornado = true;
            System.out.println(nombre + " dice: Muchas gracias futuro Alcalde!");
        }
        else{
            System.out.println("El alcalde que hay ahora me cae mejor que tu");
        }
    }
    public String getInfo(){
        String info = "Nombre: " + nombre + "\n" + descripcion + "\n"; 
        if(sobornado){
            info += "Cuenta con mi voto!!!";
        }
        else{
            info += precioSoborno + "€";
        }
        return info;
    }
    
}
