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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class PrincipalController implements Initializable {

    @FXML
    private ComboBox<String> CMB_BasesDatos = new ComboBox<String>();
    @FXML
    private ComboBox<String> CMB_Tablas = new ComboBox<String>();
    @FXML
    private Button Btn_MostrarTabla;
    @FXML
    private Button Btn_MostrarEstructura;
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
    private TableView<Map<String, Object>> TBV_Contenido = new TableView<>();
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
        BTN_crearTB.setDisable(true);
        BTN_deleteTB.setDisable(true);
    }    

    @FXML
    private void BasesDatos(ActionEvent event) throws SQLException {
          BTN_crearTB.setDisable(false);
          BTN_deleteTB.setDisable(false);
    }

    @FXML
    private void Tablas(ActionEvent event) {
    }

    @FXML
    private void MostrarTabla(ActionEvent event) throws SQLException, ClassNotFoundException{ 
        this.MostrarTabla();
        Btn_Crear.setDisable(false);
        Btn_Eliminar.setDisable(false);
        Btn_Modificar.setDisable(false);

    }
    
    @FXML
    private void MostrarEstructura(ActionEvent event) throws SQLException, ClassNotFoundException{ 
        this.mostrarEstTabla();
        Btn_Crear.setDisable(true);
        Btn_Eliminar.setDisable(true);
        Btn_Modificar.setDisable(true);

    }

    @FXML
    private void Crear(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.AgregarDatos();
        this.MostrarTabla();
    }

    @FXML
    private void Modificar(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.ModificarDatos();
        this.MostrarTabla();
    }

    @FXML
    private void Consultar(ActionEvent event) {
    }

    @FXML
    private void Eliminar(ActionEvent event) throws SQLException, ClassNotFoundException {
        this.EliminarDatos();
        this.MostrarTabla();
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
            
            System.out.println("Se conecto a "+url);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("No se conecto a "+url);
           
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
        
        while (rs.next()) {
            String dbName = rs.getString(1);
            
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
ArrayList<InicioSesion> temp = this.getTodos();

String url1 = "", driver = "", user = "", password = "";
 TBV_Contenido.setEditable(true);
 TBV_Contenido.getSelectionModel().setCellSelectionEnabled(true);

// Obtenemos los datos de inicio de sesión
for (InicioSesion car : temp) {
    url1 = car.getUrl();
    driver = car.getDriver();
    user = car.getUser();
    password = car.getPassword();
}

String url2 = url1 + "/" + BD;
System.out.println(url2);

Class.forName(driver);

cx = DriverManager.getConnection(url2, user, password);
st = cx.createStatement();

rs = st.executeQuery("SELECT * FROM " + CMB_Tablas.getValue());
TBV_Contenido.getColumns().clear();

if (rs.next()) {
    
    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
        final int j = i;
        TableColumn<Map<String, Object>, Object> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
        col.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().get(col.getText())));
        TBV_Contenido.getColumns().add(col);
    }

    
    ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();
    do {
        Map<String, Object> row = new HashMap<>();
        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
            String columnName = rs.getMetaData().getColumnName(i);
            Object columnValue = rs.getObject(i);
            row.put(columnName, columnValue);
        }
        data.add(row);
    } while (rs.next());

    TBV_Contenido.setItems(data);
}

}
    
    
    
    private void EliminarDatos() throws SQLException, ClassNotFoundException{
        this.conexionCX(cx);
        
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
    private void ModificarDatos() throws SQLException, ClassNotFoundException {
    this.conexionCX(cx); 

    DatabaseMetaData metaData = (DatabaseMetaData) cx.getMetaData();
    ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, CMB_Tablas.getValue());

    String valorPK = JOptionPane.showInputDialog(null, "Ingrese el valor de la clave primaria:");
    String columnaAModificar = TBV_Contenido.getSelectionModel().getSelectedCells().get(0).getTableColumn().getText();
    String nuevoValor = JOptionPane.showInputDialog(null, "Ingrese el nuevo valor:");

    if (primaryKeys.next()) {
        String queryModificacion = "UPDATE " + CMB_Tablas.getValue() +
                " SET " + columnaAModificar + " = ? WHERE ";

        do {
            queryModificacion += primaryKeys.getString("COLUMN_NAME") + " = ?";
        } while (primaryKeys.next());

        PreparedStatement pstmt = cx.prepareStatement(queryModificacion);
        pstmt.setString(1, nuevoValor);
        pstmt.setString(2, valorPK);

        int filasModificadas = pstmt.executeUpdate();

        if (filasModificadas > 0) {
            System.out.println("Datos modificados correctamente.");
        } else {
            System.out.println("No se realizaron modificaciones.");
        }

        pstmt.close();
    } else {
        JOptionPane.showMessageDialog(null, "No se encontraron claves primarias para la tabla seleccionada.");
    }
}

    private void AgregarDatos() throws SQLException, ClassNotFoundException{
        this.conexionCX(cx);
        DatabaseMetaData metaData = (DatabaseMetaData) cx.getMetaData();
        ResultSet Columnas = metaData.getColumns(null, null, CMB_Tablas.getValue(), null);

        ArrayList<String> nameColumnas = new ArrayList<>();
        while (Columnas.next()) {
            String Columna = Columnas.getString("COLUMN_NAME");
            nameColumnas.add(Columna);
        }
        Columnas.close();

        String[] valores = new String[nameColumnas.size()];
        for (int i = 0; i < nameColumnas.size(); i++) {
            String valor = JOptionPane.showInputDialog(null, "Ingrese valor para " + nameColumnas.get(i));
            if (valor.isEmpty()) {
                valores[i] = null; // Si el valor está en blanco, asignar null
            } else {
                valores[i] = "'" + valor + "'"; // Si se ingresa un valor, agregar comillas simples
            }
        }

        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + CMB_Tablas.getValue() + " VALUES (");
        for (int i = 0; i < valores.length; i++) {
            insertQuery.append(valores[i]);
            if (i != valores.length - 1) {
                insertQuery.append(", ");
            }
        }
        insertQuery.append(")");

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

        StringBuilder queryCrearTabla = new StringBuilder("CREATE TABLE " + nombreTabla + " (");
        for (int i = 0; i < columnas.length; i++) {
            queryCrearTabla.append(columnas[i] + " VARCHAR(255)"); // Change VARCHAR(255) to the appropriate data type
            if (i != columnas.length - 1) {
                queryCrearTabla.append(", ");
            }
        }
        queryCrearTabla.append(")");

        st.executeUpdate(queryCrearTabla.toString());

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
    
   private void mostrarEstTabla() throws SQLException, ClassNotFoundException {
     String nombreTabla = CMB_Tablas.getValue();
        if (nombreTabla != null) {
            Connection connection = cx;
            if (connection != null) {
                // Obtener los registros de la tabla
                List<Map<String, Object>> registros2 = obtenerEstructura(CMB_BasesDatos.getValue(), nombreTabla);

                // Crear una lista observable para los registros
                ObservableList<Map<String, Object>> registrosObservable = FXCollections.observableArrayList(registros2);

                // Limpiar las columnas existentes en la tabla
                TBV_Contenido.getColumns().clear();

                // Crear columnas dinámicamente basadas en los nombres de columna
                for (String columnName : registros2.get(0).keySet()) {
                    TableColumn<Map<String, Object>, Object> column = new TableColumn<>(columnName);
                    column.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().get(columnName)));
                    TBV_Contenido.getColumns().add(column);
                }

                TBV_Contenido.setItems(registrosObservable);
            } else {
                mostrarAlertaError("No se pudo establecer la conexión");
            }
        } 
}
   private List<Map<String, Object>> obtenerEstructura(String nombreBaseDatos, String nombreTabla) throws SQLException {
        List<Map<String, Object>> Estructura = new ArrayList<>();
        Connection connection = cx;
        if (connection != null) {
            String query = "DESCRIBE " + nombreBaseDatos + "." + nombreTabla;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> registro = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = resultSet.getObject(i);
                    registro.put(columnName, columnValue);
                }
                Estructura.add(registro);
            }
        } else {
            throw new SQLException("No se pudo establecer la conexión");
        }
        return Estructura;
    }




    private void mostrarAlertaError(String no_se_pudo_establecer_la_conexión) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  }

