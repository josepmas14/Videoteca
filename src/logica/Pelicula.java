package src.logica;

public class Pelicula {
    private int id;
    private String titulo;
    private Director director;
    private int idDirector;
    private int anyo;
    private String urlCaratula;
    private Enum<?> generos;
    private boolean esAnimacion;
    
    public Pelicula(int id, String titulo, Director director, int anyo, String urlCaratula, Enum<?> generos,
            boolean esAnimacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anyo = anyo;
        this.urlCaratula = urlCaratula;
        this.generos = generos;
        this.esAnimacion = esAnimacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public int getAnyo() {
        return anyo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public String getUrlCaratula() {
        return urlCaratula;
    }

    public void setUrlCaratula(String urlCaratula) {
        this.urlCaratula = urlCaratula;
    }

    public Enum<?> getGeneros() {
        return generos;
    }

    public void setGeneros(Enum<?> generos) {
        this.generos = generos;
    }

    public boolean isEsAnimacion() {
        return esAnimacion;
    }

    public void setEsAnimacion(boolean esAnimacion) {
        this.esAnimacion = esAnimacion;
    }
    

    
}
