import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class Main {
    private static final String SQL_TABLE_CREATE = "DROP TABLE IF EXISTS ODONTOLOGO; CREATE TABLE ODONTOLOGO(matricula int primary key not null, nombre varchar(255) not null, apellido varchar(255) not null);";
    private static final String SQL_INSERT = "INSERT INTO ODONTOLOGO(MATRICULA, NOMBRE, APELLIDO) VALUES (?,?,?);";
    private static final String SQL_UPDATE = "UPDATE ODONTOLOGO SET MATRICULA = ? WHERE MATRICULA = ?;";

    private static Connection obtenerConexion() throws Exception{
        return DriverManager.getConnection("jdbc:h2:~/db_odontologo");
    }

    private static void insert(Odontologo o) throws Exception {
        Connection conexion = obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(SQL_INSERT);

        statement.setInt(1, o.getMatricula());
        statement.setString(2, o.getNombre());
        statement.setString(3, o.getApellido());
        statement.execute();
    }

    private static void update(Odontologo o, int matricula) throws Exception {
        Connection conexion = obtenerConexion();
        PreparedStatement statement = conexion.prepareStatement(SQL_UPDATE);
        statement.setInt(1, matricula);
        statement.setInt(2, o.getMatricula());
        statement.execute();
    }

    public static void main(String[] args) throws Exception {
        Connection conexion = obtenerConexion();

        Odontologo odontologo1 = new Odontologo(123456, "Salo", "Levi");
        Odontologo odontologo2 = new Odontologo(654321, "Nico", "Condezo");
        Odontologo odontologo3 = new Odontologo(836482, "Vevis", "Villalobos");

        try{
            Statement statement = conexion.createStatement();
            statement.execute(SQL_TABLE_CREATE);
            conexion.setAutoCommit(false);

            insert(odontologo1);
            insert(odontologo2);
            insert(odontologo3);

            update(odontologo1, 3749376);
            conexion.commit();
            conexion.setAutoCommit(true);

            String SELECT_ALL = "SELECT * FROM ODONTOLOGO";
            Statement stmt1 = conexion.createStatement();
            ResultSet rs = stmt1.executeQuery(SELECT_ALL);
            while (rs.next()){
                System.out.println("Matricula: " + rs.getInt(1) + ", Nombre: " + rs.getString(2) + " Apellido: " + rs.getString(3));
            }

        } catch (SQLException e){
            e.printStackTrace();
            conexion.rollback();
        } finally {
            conexion.close();
        }
    }
}
