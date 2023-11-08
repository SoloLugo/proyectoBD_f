/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Vista;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author rcort
 */
public class ConexionController implements Initializable {

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
    String servidor = "localhost";
    String puerto = "3306";
    String user = "root";
    String Password = "Do517728S312$";
    String URL = "jdbc:mysql://"+servidor+":"+puerto;
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private Connection inicio_sesion(ActionEvent event) {
        try {
            Class.forName(driver);
            cx = (Connection) DriverManager.getConnection(URL , user, Password);
            System.out.println("Se conecto a "+URL);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a "+URL);
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }
      
}
