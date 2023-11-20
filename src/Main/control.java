package Main;

import java.io.IOException;
import Main.InicioSesion;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class control {

    Connection cx;
    InicioSesion IS;
    private final String ruta;

    public control() {
        this.ruta = "./src/Temporal/temp.txt";
        this.verificaArchivo();
    }
    
    public void cambiaVentana(String ubicacion, String titulo, AnchorPane panel) throws IOException, ClassNotFoundException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ubicacion));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
        Stage myStage;
        myStage = (Stage)panel.getScene().getWindow();
        myStage.close();
        
    }
    public void volver(ActionEvent event, String ubicacion, String titulo, Pane panel) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ubicacion));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
        Stage myStage = (Stage)panel.getScene().getWindow();
        myStage.close();
    }
    
    

    private void verificaArchivo(){
    try{
        File filex = new File(this.ruta);
        if(!filex.exists())
            filex.createNewFile();
        else{
            filex.deleteOnExit();
    }
    }
    catch (IOException ex){
        Alert msg = new Alert(Alert.AlertType.ERROR);
        msg.setHeaderText(null);
        msg.setTitle("ERROR");
        msg.setContentText("Fallo buscando ruta del archivo");
        msg.showAndWait();
        }   
    }
    
}
