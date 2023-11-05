
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
    
    public static void main(String[] args)
    {
        launch(args);
        //cosas del CONECTOR
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql","","");
            System.out.println(con);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //lo de la libreria
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
