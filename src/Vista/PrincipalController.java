/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Vista;
import java.sql.Statement;
import java.sql.ResultSet;
import java.net.URL;
import Main.InicioSesion;
import Main.control;
import com.mysql.cj.jdbc.DatabaseMetaData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author rcort
 */
public class PrincipalController implements Initializable {

    @FXML
    private ComboBox<String> CMB_BasesDatos = new ComboBox<String>();
    @FXML
    private ComboBox<String> CMB_Tablas = new ComboBox<String>();
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
    @FXML
    private AnchorPane VentanaBD;
    
    private Connection cx;
    InicioSesion is;
    private final String ruta;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void BasesDatos(ActionEvent event) throws SQLException {
          
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
    
    public Connection conexionSQL() throws ClassNotFoundException, SQLException{
        
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
            /*Statement st = cx.createStatement();
            ResultSet rs = st.executeQuery("show databases;");*/
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

    public PrincipalController() throws ClassNotFoundException, SQLException {
        this.ruta = "./src/Temporal/temp.txt";
        this.verificaArchivo();
        try {
            System.out.println("Antes de llamar a conexionSQL");
            this.conexionSQL();
            System.out.println("Después de llamar a conexionSQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void agregarBD(MouseEvent event) throws SQLException {
        CMB_BasesDatos.getItems().clear();
        Statement st = cx.createStatement();
        ResultSet rs = st.executeQuery("show databases;");
        System.out.println("Nombre bases de datos:");
        while (rs.next()) {
            String dbName = rs.getString(1);
            System.out.println(dbName);
            CMB_BasesDatos.getItems().add(dbName);
        }
          
    }

    @FXML
    private void agregarTB(MouseEvent event) throws SQLException, ClassNotFoundException {
        CMB_Tablas.getItems().clear();
        String BD = CMB_BasesDatos.getValue();
        Statement st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        String url1 = null,driver = null,user = null,password = null;
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        String url2 = url1+"/"+BD;
        System.out.println(url2);
        
        // Cargar el controlador JDBC
        Class.forName(driver);
        
        // Establecer la conexión con la base de datos
        Connection conexion = DriverManager.getConnection(url2, user, password);

        // Ejecutar la consulta SQL para obtener el nombre de las tablas
        String consultaSQL = "SHOW TABLES FROM " + BD;
        ResultSet resultSet = st.executeQuery(consultaSQL);

        // Mostrar el nombre de las tablas de la base de datos seleccionada
        System.out.println("Tablas en la base de datos " + BD + ":");
        while (resultSet.next()) {
            String nombreTabla = resultSet.getString(1);
            System.out.println(nombreTabla);
            this.CMB_Tablas.getItems().add(nombreTabla);
        }

    }
    
}
