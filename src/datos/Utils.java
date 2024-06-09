package src.datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utils {
    public Connection getConnection(String path){
        Connection conn = null;

        try{
            conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", path));
            System.out.println("Conexión establecida");
        } catch (SQLException err){
            System.out.println("Error en la conexión a la base de datos");
            err.printStackTrace();
        }
        return conn;
    }
}
