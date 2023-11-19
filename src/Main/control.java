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
        /*this.conexionSQL();*/
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
    
    /*public Connection conexionSQL() throws ClassNotFoundException, SQLException{
    
    ArrayList<InicioSesion> temp=this.getTodos();
    String url = null,driver = null,user = null,password = null;
    for (InicioSesion car: temp){
    url = car.getUrl();
    driver = car.getDriver();
    user = car.getUser();
    password = car.getPassword();
    }
    
    try {
    Class.forName(driver);
    cx = (Connection) DriverManager.getConnection(url , user, password);
    System.out.println("Se conecto a "+url);
    } catch (ClassNotFoundException | SQLException ex) {
    System.out.println("No se conecto a "+url);
    //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
    return cx;
    }
    
    public ArrayList<InicioSesion> getTodos() {
    ArrayList<InicioSesion> temp= new ArrayList();
    FileReader file;
    BufferedReader br;
    String registro;
    try {
    file=new FileReader(this.ruta);
    br = new BufferedReader(file);
    while ((registro = br.readLine()) != null && registro.length()!=0) {
    String[] c=registro.split(",");
    InicioSesion car=new InicioSesion(c[0],c[1],c[2],c[3]);
    temp.add(car);
    }
    }
    catch (IOException ioe){
    System.exit(1);
    }
    return temp;
    }*/

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
