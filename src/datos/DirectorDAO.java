package src.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import src.logica.Director;

public class DirectorDAO {
    private String path;

    public DirectorDAO(String path) {
        this.path = path;
    }

    public void insertar(Director director) throws SQLException{
        String sql = "INSERT into directores (nombre, url_foto, url_web) values (?,?,?)";
    
        Connection conn = new Utils().getConnection(path);
        PreparedStatement sentenciaSQL = conn.prepareStatement((sql));

        sentenciaSQL.setString(1,director.getNombre());
        sentenciaSQL.setString(2, director.getUrlFoto());
        sentenciaSQL.setString(3, director.getUrlWeb());

        sentenciaSQL.executeUpdate();
        conn.close();    
    }

    public ArrayList<Director> traeTodos() throws SQLException{
        String sql = "SELECT id, nombre, url_foto, url_web FROM directores ORDER BY nombre asc";

        Connection conn = new Utils().getConnection(path);
        Statement sentenciaSQL = conn.createStatement();
        ResultSet resultado = sentenciaSQL.executeQuery(sql);

        ArrayList<Director> listaDirectores = new ArrayList<>();

        while (resultado.next()) {
            Director nuevo;
            nuevo = new Director(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("url_foto"),resultado.getString("url_web"));
            listaDirectores.add(nuevo);
        }
        return listaDirectores;

    }

    public Director buscaPorId(int id) throws SQLException{
        Director director = null;
        String sql = "SELECT id, nombre, url_foto, url_web FROM directores WHERE id = ?";

        Connection conn = new Utils().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultado = statement.executeQuery();

        if(resultado.next()){
            director = new Director(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("url_foto"), resultado.getString("url_web"));
        }

        return director;
        
    }

    public Director buscaPorNombre(String nombre) throws SQLException{
        Director director = null;
        String sql = "SELECT id, nombre, url_foto, url_web FROM directores WHERE nombre = ?";

        Connection conn = new Utils().getConnection(path);
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, nombre);
        ResultSet resultado = statement.executeQuery();

        if(resultado.next()){
            director = new Director(resultado.getInt("id"), resultado.getString("nombre"), resultado.getString("url_foto"), resultado.getString("url_web"));
        }

        return director;
        
    }

    public void borra(int id) throws SQLException{
        Director dir = buscaPorId(id);

        if (dir !=null){
            String sql = "DELETE FROM directores WHERE id = ?";

            Connection conn = new Utils().getConnection(path);
            PreparedStatement sententenciaSQL = conn.prepareStatement(sql);
            sententenciaSQL.setInt(1,id);

            sententenciaSQL.executeUpdate();
            conn.close();
        }
    }

    public void modifica (Director director, Connection conn) throws SQLException {
        String sql = "UPDATE directores set nombre = ?, url_foto = ?, url_web where id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, director.getNombre());
        statement.setString(2, director.getUrlFoto());
        statement.setString(3, director.getUrlWeb());
        statement.setInt(4, director.getId());
        statement.executeUpdate();
       
    }

}
