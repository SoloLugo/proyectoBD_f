
package Vista;

import Main.control;
import java.io.IOException;
import Main.InicioSesion;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;


public class ConexionController extends control  implements Initializable {

    @FXML
    private Button BTNinicio_sesion;
    @FXML
    private TextField TXTcontraseña;
    @FXML
    private TextField TXTusuario;
    @FXML
    private TextField TXTpuerto;
    @FXML
    private TextField TXTserver;
    
    private final control supp;
    
    String driver = "com.mysql.cj.jdbc.Driver";
    private final String ruta;
    Connection cx;
    @FXML
    private AnchorPane PanelAzul;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    public Connection inicio_sesion(ActionEvent event) throws IOException {
    String servidor = this.TXTserver.getText();
    String puerto = this.TXTpuerto.getText(); //3306
    String user = this.TXTusuario.getText();
    String Password = this.TXTcontraseña.getText();
    String ubicacion, titulo;
    String URL = "jdbc:mysql://" + servidor + ":" + puerto;
    InicioSesion IS = new InicioSesion(URL,driver,user,Password);
    this.guarda(IS);
       
        try {
            Class.forName(driver);
            cx = (Connection) DriverManager.getConnection(URL, user, Password);
            System.out.println("Se conecto a " + URL);
            ubicacion = "/Vista/Principal.fxml";
            titulo = servidor + ":" + puerto;
            supp.cambiaVentana(ubicacion, titulo, this.PanelAzul);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a " + URL);
        }
        return cx;
}
    private void verificaArchivo()
    {
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
    private void guarda(InicioSesion IS)
    {
        try{
            File file= new File (this.ruta);
            FileWriter fr= new FileWriter(file,true);
            PrintWriter ps = new PrintWriter(fr);
            ps.println(IS);
            ps.close();
            
        }
        catch (IOException ioe){
            Alert msg = new Alert(Alert.AlertType.ERROR);
            msg.setHeaderText(null);
            msg.setTitle("ERROR");
            msg.setContentText("Fallo buscando ruta del archivo");
            msg.showAndWait();
        }
    }
    
    public ConexionController() {
        this.ruta = "./src/Temporal/temp.txt";
        this.verificaArchivo();
        this.supp = new control();
    }

}
