/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Vista;

import java.net.URL;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void inicio_sesion(ActionEvent event) {
    }
      
}
