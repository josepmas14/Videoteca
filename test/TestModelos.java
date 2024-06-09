package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import src.logica.Director;
import src.logica.Generos;
import src.logica.Pelicula;

public class TestModelos {
        @Test
    public void testCreaDirector(){
        Director director = new Director(1, "Francis Ford Coppola","www.coppola.com/coppola.png", "www.coppola.com");

        assertEquals("Francis Ford Coppola", director.getNombre());
        assertEquals("www.coppola.com/coppola.png", director.getUrlFoto());
        assertEquals("www.coppola.com", director.getUrlWeb());
    }

    @Test
    public void testCreaPelicula(){
        Director director = new Director(1, "Francis Ford Coppola","www.coppola.com/coppola.png", "www.coppola.com");
        Pelicula pelicula = new Pelicula(1, "El Padrino", director, 1972, "www.thegodfather.com/poster.png", Generos.DRAMA, false);

        assertEquals(1, pelicula.getId());
        assertEquals("El Padrino", pelicula.getTitulo());
        assertEquals(1, pelicula.getDirector().getId());
        assertEquals("Francis Ford Coppola", pelicula.getDirector().getNombre());
        assertEquals("www.coppola.com/coppola.png", pelicula.getDirector().getUrlFoto());
        assertEquals("www.coppola.com", pelicula.getDirector().getUrlWeb());
        assertEquals(1972, pelicula.getAnyo());
        assertEquals("www.thegodfather.com/poster.png", pelicula.getUrlCaratula());
        assertEquals("DRAMA", pelicula.getGeneros().toString());
        assertEquals(false, pelicula.isEsAnimacion());
    }
}
