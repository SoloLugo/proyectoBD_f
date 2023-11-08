
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
    
    //cambiar eso para que cuadre.
    
    
    public static void main(String[] args)
    {
        Main conexion = new Main();
        conexion.conectar();
        launch(args);
    }

    public Main() {
    }
    
    public void conectar(){
        
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
