
package Main;

import Main.InicioSesion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{
    
    InicioSesion IS;
    
    public static void main(String[] args)
    {
        launch(args);
    }
   
    
    @Override
    public void start(Stage ventana) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Conexion.fxml"));
        Scene scene = new Scene(root);
        ventana.setScene(scene);
        ventana.setTitle("Ingrese Usuario");
        ventana.setResizable(false);
        ventana.show();
    }

  
}
