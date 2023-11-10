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
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author rcort
 */
public class PrincipalController implements Initializable {

    @FXML
    private ComboBox<?> CMB_BasesDatos;
    @FXML
    private ComboBox<?> CMB_Tablas;
    @FXML
    private Button Btn_MostrarTabla;
    @FXML
    private Button Btn_Crear;
    @FXML
    private Button Btn_Modificar;
    @FXML
    private Button Btn_Consultar;
    @FXML
    private Button Btn_Eliminar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BasesDatos(ActionEvent event) {
    }

    @FXML
    private void Tablas(ActionEvent event) {
    }

    @FXML
    private void MostrarTabla(ActionEvent event) {
    }

    @FXML
    private void Crear(ActionEvent event) {
    }

    @FXML
    private void Modificar(ActionEvent event) {
    }

    @FXML
    private void Consultar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) {
    }
    
}
