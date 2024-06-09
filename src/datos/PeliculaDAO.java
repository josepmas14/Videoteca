package src.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.logica.Director;
import src.logica.Generos;
import src.logica.Pelicula;

public class PeliculaDAO {

    private String path;

    public PeliculaDAO(String path) {
        this.path = path;
    }

    public void insertar(Pelicula pelicula) throws SQLException{
        String sql = "INSERT into peliculas (titulo, id_director, anyo, url_caratula, id_genero, es_animacion) values (?,?,?,?,?,?)";
    
        Connection conn = new Utils().getConnection(path);
        PreparedStatement sentenciaSQL = conn.prepareStatement((sql));

        sentenciaSQL.setString(1,pelicula.getTitulo());
        sentenciaSQL.setInt(2, pelicula.getDirector().getId());
        sentenciaSQL.setInt(3, pelicula.getAnyo());
        sentenciaSQL.setString(4,pelicula.getUrlCaratula());
        sentenciaSQL.setInt(5, pelicula.getGeneros().hashCode());
        sentenciaSQL.setBoolean(6,pelicula.isEsAnimacion());
        
        sentenciaSQL.executeUpdate();
        conn.close();    
    }

    public ArrayList<Pelicula> traeTodos() throws SQLException{
        String sql = "SELECT p.id, p.titulo, p.id_director, d.nombre, d.url_foto, d.url_web, p.anyo, p.url_caratula, p.id_genero, p.es_animacion " +
                     "FROM peliculas p " +
                     "JOIN directores d ON p.id_director = d.id";

        Connection conn = new Utils().getConnection(path);
        Statement sentenciaSQL = conn.createStatement();
        ResultSet resultado = sentenciaSQL.executeQuery(sql);
        ArrayList<Pelicula> listaPeliculas = new ArrayList<>();

        while (resultado.next()) {
            Pelicula nuevo;
            Director director = new Director(resultado.getInt("id_director"), resultado.getString("nombre"), resultado.getString("url_foto"), resultado.getString("url_web"));
            int idGenero = resultado.getInt("id_genero");
            Generos genero = Generos.values()[idGenero];

            nuevo = new Pelicula(resultado.getInt("id"), resultado.getString("titulo"), director, resultado.getInt("anyo"), resultado.getString("url_caratula"),genero, resultado.getBoolean("es_animacion"));
            listaPeliculas.add(nuevo);
        }
        conn.close();
        return listaPeliculas;

    }

    public Pelicula buscaPorId(int id) throws SQLException{
        Pelicula pelicula = null;
        String sql = "SELECT p.id, p.titulo, p.id_director, d.nombre, d.url_foto, d.url_web, p.anyo, p.url_caratula, p.id_genero, p.es_animacion " +
                     "FROM peliculas p " +
                     "JOIN directores d ON p.id_director = d.id " +
                     "WHERE p.id = ?";
        
        Connection conn = new Utils().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultado = statement.executeQuery();


        if(resultado.next()){
            Director director = new Director(resultado.getInt("id_director"), resultado.getString("nombre"), resultado.getString("url_foto"), resultado.getString("url_web"));
            int idGenero = resultado.getInt("id_genero");
            Generos genero = Generos.values()[idGenero];

            pelicula = new Pelicula(resultado.getInt("id"), resultado.getString("titulo"), director, resultado.getInt("anyo"), resultado.getString("url_caratula"),genero, resultado.getBoolean("es_animacion"));
            
        }
        conn.close();
        return pelicula;
        
    }

    public Pelicula buscaPorTitulo(String titulo) throws SQLException{
        Pelicula pelicula = null;
        String sql = "SELECT p.id, p.titulo, p.id_director, d.nombre, d.url_foto, d.url_web, p.anyo, p.url_caratula, p.id_genero, p.es_animacion " +
                     "FROM peliculas p " +
                     "JOIN directores d ON p.id_director = d.id " +
                     "WHERE p.nombre = ?";
        
        Connection conn = new Utils().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, titulo);
        ResultSet resultado = statement.executeQuery();
  

        if(resultado.next()){
            Director director = new Director(resultado.getInt("id_director"), resultado.getString("nombre"), resultado.getString("url_foto"), resultado.getString("url_web"));
            int idGenero = resultado.getInt("id_genero");
            Generos genero = Generos.values()[idGenero];

            pelicula = new Pelicula(resultado.getInt("id"), resultado.getString("titulo"), director, resultado.getInt("anyo"), resultado.getString("url_caratula"),genero, resultado.getBoolean("es_animacion"));
            
        }
        conn.close();
        return pelicula;
        
    }

    public void borra(int id) throws SQLException{
        Pelicula dir = buscaPorId(id);

        if (dir !=null){
            String sql = "DELETE FROM peliculas WHERE id = ?";

            Connection conn = new Utils().getConnection(path);
            PreparedStatement sententenciaSQL = conn.prepareStatement(sql);
            sententenciaSQL.setInt(1,id);

            sententenciaSQL.executeUpdate();
            conn.close();
        }
    }

    public void modifica (Pelicula pelicula, Connection conn) throws SQLException {
        String sql = "UPDATE peliculas SET titulo = ?, id_director = ?, anyo = ?, url_caratula = ?, id_genero = ?, es_animacion = ? WHERE id = ?";
        
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pelicula.getTitulo());
            statement.setInt(2, pelicula.getDirector().getId());
            statement.setInt(3, pelicula.getAnyo());
            statement.setString(4, pelicula.getUrlCaratula());
            statement.setInt(5, pelicula.getGeneros().hashCode());
            statement.setBoolean(6, pelicula.isEsAnimacion());
            statement.setInt(7, pelicula.getId());
            
            statement.executeUpdate();
        }
    }

}
