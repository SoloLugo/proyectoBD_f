
package Main;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
    
    String servidor = "";
    String puerto = "";
    String user = "root";
    String Password = "miamala2";
    String URL = "jdbc:mysql://"+servidor+":"+puerto;
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;
    
    public static void main(String[] args)
    {
        Main conexion = new Main();
        conexion.conectar();
        launch(args);
    }

    public Main() {
    }
    
    
    
    public Connection conectar(){
        try {
            Class.forName(driver);
            cx = (Connection) DriverManager.getConnection(URL , user, Password);
            System.out.println("Se conecto a "+URL);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a "+URL);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }
    
    @Override
    public void start(Stage ventana) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Conexion.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.setTitle("Ingrese Usuario");
        ventana.setResizable(false);
        ventana.setOnCloseRequest(event -> {event.consume();});
        ventana.show();
    }

    
}
