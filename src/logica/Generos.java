package src.logica;

public enum Generos {
    ACCION (1),
    AVENTURA (2),
    COMEDIA (3),
    DRAMA (4),
    FANTASIA (5),
    TERROR (6),
    SCIFI (7),
    MUSICAL (8),
    SUSPENSE (9),
    WESTERN (10),
    DOCUMENTAL (11),
    BIOPIC (12),
    ROMANCE (13);

    private int id;

    private Generos(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
