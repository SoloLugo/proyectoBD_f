package Vista;

import java.sql.Statement;
import java.sql.ResultSet;
import java.net.URL;
import Main.InicioSesion;
import Main.control;
import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    
    private ObservableList<ObservableList<String>> data;
    
    private Connection cx;
    private String url2;
    private String url1 = null,driver = null,user = null,password = null;
    InicioSesion is;
    private final String ruta;
    @FXML
    private TableView<ObservableList<String>> TBV_Contenido = new TableView<>();
    private RadioButton RBTN_BD1;
    private RadioButton RBTN_TABLE;
    @FXML
    private Button BTN_crearBD;
    @FXML
    private Button BTN_deleteBD;
    @FXML
    private Button BTN_crearTB;
    @FXML
    private Button BTN_deleteTB;
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
    private void MostrarTabla(ActionEvent event) throws SQLException, ClassNotFoundException{ 
        this.MostrarTabla();

    }

    @FXML
    private void Crear(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.AgregarDatos();
    }

    @FXML
    private void Modificar(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.ModificarDatos();
    }

    @FXML
    private void Consultar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.EliminarDatos();
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
        this.TBV_Contenido.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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
        ResultSet rs;
        Statement st;
        CMB_BasesDatos.getItems().clear();
        st = cx.createStatement();
        rs = st.executeQuery("show databases;");
        /*System.out.println("Nombre bases de datos:");*/
        while (rs.next()) {
            String dbName = rs.getString(1);
            /*System.out.println(dbName);*/
            CMB_BasesDatos.getItems().add(dbName);
        }
          
    }

    @FXML
    private void agregarTB(MouseEvent event) throws SQLException, ClassNotFoundException {
        ResultSet rs;
        Statement st;
        CMB_Tablas.getItems().clear();
        String BD = CMB_BasesDatos.getValue();
        this.conexionCX(cx);
        st = cx.createStatement();
        rs = st.executeQuery("SHOW TABLES FROM " + BD);

        /*System.out.println("Tablas en la base de datos " + BD + ":");*/
        while (rs.next()) {
            String nombreTabla = rs.getString(1);
            /*System.out.println(nombreTabla);*/
            this.CMB_Tablas.getItems().add(nombreTabla);
        }
         
    }

    private void MostrarTabla() throws SQLException, ClassNotFoundException {
        ResultSet rs;
        Statement st;
        String BD = CMB_BasesDatos.getValue();
        st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        url2 = url1+"/"+BD;
        
        System.out.println(url2);

        Class.forName(driver);

        cx = DriverManager.getConnection(url2, user, password);
        
        /*Connection cx1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + CMB_BasesDatos.getValue(), user, password);*/
        st = cx.createStatement();
        
        rs = st.executeQuery("SELECT * FROM " + CMB_Tablas.getValue());
        TBV_Contenido.getColumns().clear();
        if (rs.next()) {
            data = FXCollections.observableArrayList();

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                TBV_Contenido.getColumns().add(col);
            }

            do {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            } while (rs.next());

            TBV_Contenido.setItems(data);
        }
         
    }
    
    private void EliminarDatos() throws SQLException, ClassNotFoundException{
        this.conexionCX(cx);
        /*Connection cx1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + CMB_BasesDatos.getValue(), user, password);*/
        DatabaseMetaData metaData = (DatabaseMetaData) cx.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, CMB_Tablas.getValue());
        
        String valorELM = JOptionPane.showInputDialog(null, "Ingresa el valor de la PK:");

        if (primaryKeys.next()) {
            String columnaPrimaryKey = primaryKeys.getString("COLUMN_NAME");
            Statement st = cx.createStatement();
            int Eliminar = st.executeUpdate("DELETE FROM " + CMB_Tablas.getValue() + " WHERE " + columnaPrimaryKey + " = '" + valorELM + "'");
            st.close();
            System.out.println("Datos Eliminados");
        } else {
            JOptionPane.showMessageDialog(null, "ERROR" + CMB_Tablas.getValue());
        }
    }
    private void ModificarDatos() throws SQLException, ClassNotFoundException{
        this.conexionCX(cx);
        /*Connection cx1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + CMB_BasesDatos.getValue(), user, password);*/
        DatabaseMetaData metaData = (DatabaseMetaData) cx.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, CMB_Tablas.getValue());
        
        String valorELM = JOptionPane.showInputDialog(null, "Ingresa el valor de la PK:");
        
        String query = JOptionPane.showInputDialog(null, "Ingresa el Query pls:");
        if (primaryKeys.next()) {
            String columnaPrimaryKey = primaryKeys.getString("COLUMN_NAME");
            Statement st = cx.createStatement();
            int modificar = st.executeUpdate(query);
            st.close();
            System.out.println("Datos modifaicados");
            } else {
            JOptionPane.showMessageDialog(null, "ERROR" + CMB_Tablas.getValue());
        }
    }
    private void AgregarDatos() throws SQLException, ClassNotFoundException{
        this.conexionCX(cx);
        /*Connection cx1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + CMB_BasesDatos.getValue(), user, password);*/
        DatabaseMetaData metaData = (DatabaseMetaData) cx.getMetaData();
        ResultSet Columnas = metaData.getColumns(null, null, CMB_Tablas.getValue(), null);

        ArrayList<String> nameColumnas = new ArrayList<>();
        while (Columnas.next()) {
            String Columna = Columnas.getString("COLUMN_NAME");
            nameColumnas.add(Columna);
        }
        Columnas.close();

        // Mostrar JOptionPane para cada columna de la tabla
        String[] valores = new String[nameColumnas.size()];
        for (int i = 0; i < nameColumnas.size(); i++) {
            String valor = JOptionPane.showInputDialog(null, "Ingrese valor para " + nameColumnas.get(i));
            if (valor.isEmpty()) {
                valores[i] = null; // Si el valor está en blanco, asignar null
            } else {
                valores[i] = "'" + valor + "'"; // Si se ingresa un valor, agregar comillas simples
            }
        }

        // Construir la consulta de inserción utilizando los nombres y valores obtenidos
        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + CMB_Tablas.getValue() + " VALUES (");
        for (int i = 0; i < valores.length; i++) {
            insertQuery.append(valores[i]);
            if (i != valores.length - 1) {
                insertQuery.append(", ");
            }
        }
        insertQuery.append(")");

        // Ejecutar la consulta de inserción
        Statement st = cx.createStatement();
        st.executeUpdate(insertQuery.toString());
        st.close();
    }

    @FXML
    private void crearBD(ActionEvent event) throws ClassNotFoundException, SQLException {
        ResultSet rs;
        Statement st;
        String BD = CMB_BasesDatos.getValue();
        st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        url2 = url1;
        
        System.out.println(url2);

        Class.forName(driver);

        cx = DriverManager.getConnection(url2, user, password);
        String nombreBD = JOptionPane.showInputDialog(null, "ingrese el nombre de la base de datos:");
        st.executeUpdate("create database "+nombreBD);
    }

    @FXML
    private void deleteBD(ActionEvent event) throws ClassNotFoundException, SQLException {
        ResultSet rs;
        Statement st;
        String BD = CMB_BasesDatos.getValue();
        st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        url2 = url1;
        
        System.out.println(url2);

        Class.forName(driver);

        cx = DriverManager.getConnection(url2, user, password);
        String nombreBD = JOptionPane.showInputDialog(null, "ingrese el nombre de la base de datos:");
        st.executeUpdate("drop database "+nombreBD);
    }

    @FXML
    private void crearTB(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.conexionCX(cx);
        Statement st = cx.createStatement();
        String nombreTabla = JOptionPane.showInputDialog("Ingrese el nombre de la tabla:");
        int columns = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas:"));
        String[] columnas = new String[columns];
        for (int i = 0; i < columnas.length; i++) {
            String columnName = JOptionPane.showInputDialog("Ingrese el nombre de la columna " + (i + 1) + ":");
            columnas[i] = columnName;
        }
        System.out.println("Columnas: " + Arrays.toString(columnas));

        // Build the CREATE TABLE query
        StringBuilder queryCrearTabla = new StringBuilder("CREATE TABLE " + nombreTabla + " (");
        for (int i = 0; i < columnas.length; i++) {
            queryCrearTabla.append(columnas[i] + " VARCHAR(255)"); // Change VARCHAR(255) to the appropriate data type
            if (i != columnas.length - 1) {
                queryCrearTabla.append(", ");
            }
        }
        queryCrearTabla.append(")");

        // Execute the query
        st.executeUpdate(queryCrearTabla.toString());

        // Close the statement and connection
        st.close();

    }

    @FXML
    private void deleteTB(ActionEvent event) throws SQLException, ClassNotFoundException {
        ResultSet rs;
        Statement st;
        String BD = CMB_BasesDatos.getValue();
        st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        url2 = url1+"/"+BD;
        
        System.out.println(url2);

        Class.forName(driver);

        cx = DriverManager.getConnection(url2, user, password);
        String nombreTB = JOptionPane.showInputDialog(null, "ingrese el nombre de la tabla:");
        st.executeUpdate("drop table "+nombreTB);
    }
    
    private Connection conexionCX(Connection cx) throws SQLException, ClassNotFoundException{
        ResultSet rs;
        Statement st;
        String BD = CMB_BasesDatos.getValue();
        st = cx.createStatement();
        ArrayList<InicioSesion> temp=this.getTodos();
        for (InicioSesion car: temp){
            url1 = car.getUrl();
            driver = car.getDriver();
            user = car.getUser();
            password = car.getPassword();
        }
        
        url2 = url1+"/"+BD;
        
        System.out.println(url2);

        Class.forName(driver);

        cx = DriverManager.getConnection(url2, user, password);
        
        return cx;
    }
  }

