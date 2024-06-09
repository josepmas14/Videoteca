package src.logica;

public class Director {
    
    // Atributos
    private int id;
    private String nombre;
    private String urlFoto;
    private String urlWeb;

    // Constructor
    public Director(int id, String nombre, String urlFoto, String urlWeb){
        this.id = id;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.urlWeb = urlWeb;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public void setUrlWeb(String urlWeb) {
        this.urlWeb = urlWeb;
    }


}
