package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import src.datos.DirectorDAO;
import src.datos.PeliculaDAO;
import src.datos.Utils;
import src.logica.Director;
import src.logica.Generos;
import src.logica.Pelicula;

public class TestDAO {
    
    @Test
    public void testJDBCConecta() {

        boolean laClaseJDBCExiste = false;
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Todo ha ido bien");
            laClaseJDBCExiste = true;
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC no encontrado");
            e.printStackTrace();
        }
        
        assertTrue(laClaseJDBCExiste);
    }

    @Test
    public void testCrearConexion() throws SQLException {
        Utils utils = new Utils();

        Connection conn = utils.getConnection("./data/video.sqlite");
        assertNotNull(conn);
        conn.close();
    }
    
    @Test
    public void testAnyadeDirector() throws SQLException{
        String sql = "DELETE FROM directores";
        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");
        Director director = new Director(1, "James Cameron", "www.cameron.com/cameron.png", "www.cameron.com");

        dao.insertar(director);

        int id = -1;
        String nombre = "";
        String urlFoto = "";
        String urlWeb = "";

        sql = "SELECT id, nombre, url_foto, url_web FROM directores";

        try (Connection conn = new Utils().getConnection("./data/video.sqlite");
             Statement sentenciaSQL = conn.createStatement();
             ResultSet resultado = sentenciaSQL.executeQuery(sql)) {

            if (resultado.next()) {
                id = resultado.getInt("id");
                nombre = resultado.getString("nombre");
                urlFoto = resultado.getString("url_foto");
                urlWeb = resultado.getString("url_web");
            }
        }

        assertEquals(1, id);
        assertEquals("James Cameron", nombre);
        assertEquals("www.cameron.com/cameron.png", urlFoto);
        assertEquals("www.cameron.com", urlWeb);
    }

    @Test
    public void testDameTodosDirectores() throws SQLException{

        String sql = "DELETE FROM directores";
        try (Connection conn = new Utils().getConnection("./data/video.sqlite");
             Statement sentenciaSQL = conn.createStatement()) {

            sentenciaSQL.executeUpdate(sql);
        }

        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        dao.insertar(director);

        
        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");
        dao.insertar(director);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");
        dao.insertar(director);

        ArrayList<Director> directores = dao.traeTodos();

        assertEquals(3, directores.size());

        assertEquals(3, directores.get(0).getId());
        assertEquals("Jim Sheridan", directores.get(0).getNombre());
        assertEquals("www.sheridan.com/sheridan.png", directores.get(0).getUrlFoto());
        assertEquals("www.sheridan.com", directores.get(0).getUrlWeb());

        assertEquals(2, directores.get(1).getId());
        assertEquals("Quentin Tarantino", directores.get(1).getNombre());
        assertEquals("www.tarantino.com/tarantino.png", directores.get(1).getUrlFoto());
        assertEquals("www.tarantino.com", directores.get(1).getUrlWeb());

        assertEquals(1, directores.get(2).getId());
        assertEquals("Stanley Kubrik", directores.get(2).getNombre());
        assertEquals("www.kubrik.com/kubrik.png", directores.get(2).getUrlFoto());
        assertEquals("www.kubrik.com", directores.get(2).getUrlWeb());

    }

    @Test
    public void testBuscaDirectorPorID() throws SQLException{
        String sql = "DELETE FROM directores";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        dao.insertar(director);
        
        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");
        dao.insertar(director);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");
        dao.insertar(director);

        director = dao.buscaPorId(1);
        assertEquals("Stanley Kubrik", director.getNombre());
        assertEquals("www.kubrik.com/kubrik.png", director.getUrlFoto());
        assertEquals("www.kubrik.com", director.getUrlWeb());

        director = dao.buscaPorId(2);
        assertEquals("Quentin Tarantino", director.getNombre());
        assertEquals("www.tarantino.com/tarantino.png", director.getUrlFoto());
        assertEquals("www.tarantino.com", director.getUrlWeb());

        director = dao.buscaPorId(3);
        assertEquals("Jim Sheridan", director.getNombre());
        assertEquals("www.sheridan.com/sheridan.png", director.getUrlFoto());
        assertEquals("www.sheridan.com", director.getUrlWeb());
    }

    @Test
    public void testBuscaDirectorPorNombre() throws SQLException{
        String sql = "DELETE FROM directores";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        dao.insertar(director);
        
        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");
        dao.insertar(director);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");
        dao.insertar(director);

        director = dao.buscaPorNombre("Stanley Kubrik");
        assertEquals(1, director.getId());
        assertEquals("www.kubrik.com/kubrik.png", director.getUrlFoto());
        assertEquals("www.kubrik.com", director.getUrlWeb());

        director = dao.buscaPorNombre("Quentin Tarantino");
        assertEquals(2, director.getId());
        assertEquals("www.tarantino.com/tarantino.png", director.getUrlFoto());
        assertEquals("www.tarantino.com", director.getUrlWeb());

        director = dao.buscaPorNombre("Jim Sheridan");
        assertEquals(3, director.getId());
        assertEquals("www.sheridan.com/sheridan.png", director.getUrlFoto());
        assertEquals("www.sheridan.com", director.getUrlWeb());
    }

    @Test
    public void testBorraDirector() throws SQLException{
        String sql = "DELETE FROM directores";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        dao.insertar(director);

        dao.borra(1);

        assertNull(dao.buscaPorId(1));
    }

    @Test
    public void testModificaDirector() throws SQLException{
        String sql = "DELETE FROM directores";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        DirectorDAO dao = new DirectorDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Cubrik", "www.cubrik.com/kubrik.png", "www.cubrik.com");
        dao.insertar(director);

        director= new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        dao.modifica(director, conn);

        assertEquals("Stanley Kubrik", director.getNombre());
        assertEquals("www.kubrik.com/kubrik.png", director.getUrlFoto());
        assertEquals("www.kubrik.com", director.getUrlWeb());

    }

    @Test
    public void testAnyadePelicula() throws SQLException{
        String sql = "DELETE FROM peliculas";
        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "John Lasseter", "www.lasseter.com/lasseter.png", "www.lasseter.com");

        Pelicula pelicula = new Pelicula(1, "Toy Story", director,1995,"www.toystory.com/poster.png", Generos.COMEDIA, true);

        dao.insertar(pelicula);

        int id = -1;
        String titulo = "";
        int idDirector = -1;
        int anyo = -1;
        String urlCaratula = "";
        int idGenero = -1;
        boolean es_animacion = false;

        sql = "SELECT id, titulo, anyo, id_director url_caratula, id_genero, es_animacion FROM peliculas";
        try (Connection conn = new Utils().getConnection("./data/video.sqlite");
             Statement sentenciaSQL = conn.createStatement();
             ResultSet resultado = sentenciaSQL.executeQuery(sql)) {

                if (resultado.next()) {
                    id = resultado.getInt("id");
                    titulo = resultado.getString("titulo");
                    idDirector = resultado.getInt("id_director");
                    anyo = resultado.getInt("anyo");
                    urlCaratula = resultado.getString("url_caratula");
                    idGenero = resultado.getInt("id_genero");
                    es_animacion = resultado.getBoolean("es_animacion");
                }
        }

        assertEquals(1, id);
        assertEquals("Toy Story", titulo);
        assertEquals(1, idDirector);
        assertEquals(1995, anyo);
        assertEquals("www.toystory.com/poster.png", urlCaratula);
        assertEquals(3, idGenero);
        assertTrue(es_animacion);
    }

    @Test
    public void testDameTodasPeliculas() throws SQLException{

        String sql = "DELETE FROM peliculas";
        try (Connection conn = new Utils().getConnection("./data/video.sqlite");
            Statement sentenciaSQL = conn.createStatement()) {
                sentenciaSQL.executeUpdate(sql);
            }

        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        
        Pelicula pelicula = new Pelicula(1, "Full Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.insertar(pelicula);

        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");

        pelicula = new Pelicula(2, "Pulp Fiction", director, 1994, "www.pulpfiction.com/poster.png", Generos.DRAMA, false);
        dao.insertar(pelicula);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");

        pelicula = new Pelicula(3, "In The Name Of The Father", director, 1993, "www.inthenameofthefather.com/poster.png", Generos.BIOPIC, false);
        dao.insertar(pelicula);

        ArrayList<Pelicula> peliculas = dao.traeTodos();

        assertEquals(3, peliculas.size());

        assertEquals(1, peliculas.get(0).getId());
        assertEquals("Full Metal Jacket", peliculas.get(0).getTitulo());
        assertEquals(1, peliculas.get(0).getIdDirector());
        assertEquals(1987, peliculas.get(0).getAnyo());
        assertEquals("www.fullmetaljacket.com/poster.png",peliculas.get(0).getUrlCaratula());
        assertEquals(3, peliculas.get(0).getGeneros().hashCode());
        assertFalse(peliculas.get(0).isEsAnimacion());

        assertEquals(2, peliculas.get(0).getId());
        assertEquals("Full Metal Jacket", peliculas.get(0).getTitulo());
        assertEquals(2, peliculas.get(0).getIdDirector());
        assertEquals(1995, peliculas.get(0).getAnyo());
        assertEquals("www.toystory.com/poster.png",peliculas.get(0).getUrlCaratula());
        assertEquals(3, peliculas.get(0).getGeneros().hashCode());
        assertFalse(peliculas.get(0).isEsAnimacion());

        assertEquals(3, peliculas.get(2).getId());
        assertEquals("In The Name Of The Father", peliculas.get(2).getTitulo());
        assertEquals(3, peliculas.get(2).getIdDirector());
        assertEquals(1993, peliculas.get(2).getAnyo());
        assertEquals("www.inthenameofthefather.com/poster.png",peliculas.get(2).getUrlCaratula());
        assertEquals(12, peliculas.get(2).getGeneros().hashCode());
        assertFalse(peliculas.get(2).isEsAnimacion());

    }

    @Test
    public void testBuscaPeliculaPorID() throws SQLException{
        String sql = "DELETE FROM peliculas";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        
        Pelicula pelicula = new Pelicula(1, "Full Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.insertar(pelicula);

        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");

        pelicula = new Pelicula(2, "Pulp Fiction", director, 1994, "www.pulpfiction.com/poster.png", Generos.DRAMA, false);
        dao.insertar(pelicula);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");

        pelicula = new Pelicula(3, "In The Name Of The Father", director, 1993, "www.inthenameofthefather.com/poster.png", Generos.BIOPIC, false);
        dao.insertar(pelicula);

        pelicula = dao.buscaPorId(1);
        assertEquals("Full Metal Jacket", pelicula.getTitulo());
        assertEquals(1, pelicula.getIdDirector());
        assertEquals(1987, pelicula.getAnyo());
        assertEquals("www.fullmetaljacket.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(3, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());

        pelicula = dao.buscaPorId(2);
        assertEquals("Pulp Fiction", pelicula.getTitulo());
        assertEquals(2, pelicula.getIdDirector());
        assertEquals(1995, pelicula.getAnyo());
        assertEquals("www.pulpfiction.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(3, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());

        pelicula = dao.buscaPorId(3);
        assertEquals("In The Name Of The Father", pelicula.getTitulo());
        assertEquals(3, pelicula.getIdDirector());
        assertEquals(1993, pelicula.getAnyo());
        assertEquals("www.inthenameofthefather.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(12, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());
    }

    @Test
    public void testBuscaPeliculaPorTitulo() throws SQLException{
        String sql = "DELETE FROM peliculas";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        
        Pelicula pelicula = new Pelicula(1, "Full Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.insertar(pelicula);

        director = new Director(2, "Quentin Tarantino", "www.tarantino.com/tarantino.png", "www.tarantino.com");

        pelicula = new Pelicula(2, "Pulp Fiction", director, 1994, "www.pulpfiction.com/poster.png", Generos.DRAMA, false);
        dao.insertar(pelicula);

        director = new Director(3, "Jim Sheridan", "www.sheridan.com/sheridan.png", "www.sheridan.com");

        pelicula = new Pelicula(3, "In The Name Of The Father", director, 1993, "www.inthenameofthefather.com/poster.png", Generos.BIOPIC, false);
        dao.insertar(pelicula);

        pelicula = dao.buscaPorTitulo("Full Metal Jacket");
        assertEquals(1, pelicula.getId());
        assertEquals(1, pelicula.getIdDirector());
        assertEquals(1987, pelicula.getAnyo());
        assertEquals("www.fullmetaljacket.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(3, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());

        pelicula = dao.buscaPorTitulo("Pulp Fiction");
        assertEquals(2, pelicula.getId());
        assertEquals(2, pelicula.getIdDirector());
        assertEquals(1995, pelicula.getAnyo());
        assertEquals("www.pulpfiction.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(3, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());

        pelicula = dao.buscaPorTitulo("In The Name Of The Father");
        assertEquals(3, pelicula.getId());
        assertEquals(3, pelicula.getIdDirector());
        assertEquals(1993, pelicula.getAnyo());
        assertEquals("www.inthenameofthefather.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(12, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());
    }

    @Test
    public void testBorraPelicula() throws SQLException{
        String sql = "DELETE FROM peliculas";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        
        Pelicula pelicula = new Pelicula(1, "Full Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.insertar(pelicula);

        dao.borra(1);

        assertNull(dao.buscaPorId(1));
    }

    @Test
    public void testModificaPelicula() throws SQLException{
        String sql = "DELETE FROM peliculas";
        Connection conn = new Utils().getConnection("./data/video.sqlite");
        Statement sentenciaSQL = conn.createStatement();

        sentenciaSQL.executeUpdate(sql);
        conn.close();

        PeliculaDAO dao = new PeliculaDAO("./data/video.sqlite");

        Director director = new Director(1, "Stanley Kubrik", "www.kubrik.com/kubrik.png", "www.kubrik.com");
        
        Pelicula pelicula = new Pelicula(1, "Half Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.insertar(pelicula);

        pelicula = new Pelicula(1, "Full Metal Jacket", director, 1987, "www.fullmetaljacket.com/poster.png", Generos.ACCION, false);
        dao.modifica(pelicula, conn);

        assertEquals(1, pelicula.getId());
        assertEquals("Full Metal Jacket", pelicula.getTitulo());
        assertEquals(1, pelicula.getIdDirector());
        assertEquals(1987, pelicula.getAnyo());
        assertEquals("www.fullmetaljacket.com/poster.png",pelicula.getUrlCaratula());
        assertEquals(3, pelicula.getGeneros().hashCode());
        assertFalse(pelicula.isEsAnimacion());

    }
}