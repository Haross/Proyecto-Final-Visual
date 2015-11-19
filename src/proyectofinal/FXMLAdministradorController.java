package proyectofinal;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import static proyectofinal.FXMLLoginController.resultUser;
/**
 * FXML Controller class
 *
 * @author Javier
 */
public class FXMLAdministradorController implements Initializable {
    @FXML //PaneAddUser
    TextField txtUsuario, txtContrasena, txtNombre, txtApellido, txtEdad, 
            txtCorreo, txtDomicilio, txtTelefono;
    @FXML
    TextField txtUsuarioP, txtContrasenaP, txtPerfilP, txtNombreP, txtApellidoP,
              txtEdadP, txtCorreoP, txtDomicilioP, txtTelefonoP;
    @FXML
    TextField txtUsuario2, txtContrasena2, txtPerfil2, txtNombre2, txtApellido2,
              txtEdad2, txtCorreo2, txtDomicilio2, txtTelefono2;
    @FXML
    AnchorPane pane,paneAddUser,paneBorrar, principal, paneConsultar, panePerfil;
    @FXML
    ChoiceBox choiceB;
    @FXML
    Button btnBorrar, btnActualizar,btnActualizar1, btnNext, btnPrev, btnCerrarS,btnPaint,btnBlock;
    @FXML TilePane admiPerfil, userPerfil;
    private ResultSet rs = null;
    private Connection conecc;
    private Statement st = null;
    public   String us;
 
    
    @FXML
    TextField txtUsuarioB, txtContrasenaB, txtPerfilB, txtNombreB, txtApellidoB, 
             txtEdadB, txtCorreoB, txtDomicilioB, txtTelefonoB;
    
    public FXMLAdministradorController(){
        System.out.println("entra aqui");
    }
    
    @FXML private void abrirPaint(ActionEvent e){
        Stage stage = new Stage();
        Parent root = null;
        try {
            Stage stageAux = (Stage) btnPaint.getScene().getWindow();
            stageAux.close();
            root = FXMLLoader.load(getClass().getResource("Paint/FXMLminipaint.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML private void abrirBlock(ActionEvent e){
        Stage stage = new Stage();
        Parent root = null;
        try {
            Stage stageAux = (Stage) btnBlock.getScene().getWindow();
            stageAux.close();
            root = FXMLLoader.load(getClass().getResource("Block/FXMLDocument.fxml"));
        } catch (IOException ex) {
            System.out.println("error");
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void submitData(ActionEvent event) {
        //insertarDatosPuro();
        insertarDatosPuro();
    }

    @FXML
    private void ingresar() {
        principal.setVisible(false);
        paneAddUser.setVisible(true);
        paneBorrar.setVisible(false);
        panePerfil.setVisible(false);
    }

    private void insertarDatosPuro() {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();
            String perfil = choiceB.getValue().toString();
            System.out.println(perfil);
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String edad = txtEdad.getText();
            String correo = txtCorreo.getText();
            String domicilio = txtDomicilio.getText();
            String telefono = txtTelefono.getText();
            Boolean resultado = false;
            resultado = st.execute("INSERT INTO usuarios(usuario, contraseña, perfil, nombre,apellidos,edad,email,domicilio,telefono) "
                    + "VALUES" + "('" + usuario + "','" + contrasena + "','" + perfil + "','" + nombre + "','" + apellido + "','" + edad + "','" + correo + "','" + domicilio + "','" + telefono +  "');");
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El proceso fue exitoso");
                alert.showAndWait();
            }

            System.out.println("El resultado es: " + resultado);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void submitData2(ActionEvent event) {
        System.out.println("HO: "+ us);
        principal.setVisible(false);
        paneAddUser.setVisible(false);
        paneConsultar.setVisible(true);
        paneBorrar.setVisible(false);
        panePerfil.setVisible(false);
        consultarDatosPrimitive();
    }

    @FXML
    public void Perfil() {
        paneBorrar.setVisible(false);
        principal.setVisible(false);
        paneAddUser.setVisible(false);
        paneConsultar.setVisible(false);
        panePerfil.setVisible(true);
        
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            
            us = resultUser.getString("usuario");
        }catch(Exception e){
            
        }
        
        //System.out.println("HO: "+ usuario);
        String instruccion = " select * from usuarios where usuario=('" + this.us + "') ";
        System.out.println("pp"+instruccion);
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
            try {
                if (rs.next()) {
                    
                    txtContrasenaP.setText(rs.getString("contraseña"));
                    txtUsuarioP.setText(rs.getString("usuario"));
                    txtPerfilP.setText(rs.getString("perfil"));
                    txtNombreP.setText(rs.getString("nombre"));
                    txtApellidoP.setText(rs.getString("apellidos"));
                    txtEdadP.setText(rs.getString("edad"));
                    txtCorreoP.setText(rs.getString("email"));
                    txtDomicilioP.setText(rs.getString("domicilio"));
                    txtTelefonoP.setText(rs.getString("telefono"));
                }
                btnActualizar1.setDisable(true);
            } catch (SQLException ex) {
                Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void Cancel(ActionEvent event) {
        principal.setVisible(true);
        paneConsultar.setVisible(false);
        panePerfil.setVisible(false);
        paneAddUser.setVisible(false);

    }
    
    private void openWindowWithOption(String file) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    
    @FXML void Borrar(){
       principal.setVisible(false);
       paneAddUser.setVisible(false);
       paneConsultar.setVisible(false);
       panePerfil.setVisible(false);
       paneBorrar.setVisible(true);
        
    }
    private void consultarDatosPrimitive() {
        btnBorrar.setDisable(false);
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String usuario = txtUsuario.getText();
        String instruccion = " select * from usuarios ";
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nextData();
        //prevData();
        btnActualizar.setDisable(true);
    }

    @FXML
    private void nextData() {
        try {
            if (rs.next()) {
                txtContrasena2.setText(rs.getString("contraseña"));
                txtUsuario2.setText(rs.getString("usuario"));
                txtPerfil2.setText(rs.getString("perfil"));
                txtNombre2.setText(rs.getString("nombre"));
                txtApellido2.setText(rs.getString("apellidos"));
                txtEdad2.setText(rs.getString("edad"));
                txtCorreo2.setText(rs.getString("email"));
                txtDomicilio2.setText(rs.getString("domicilio"));
                txtTelefono2.setText(rs.getString("telefono"));
            }
            if(rs.isLast()){
                btnNext.setDisable(true);
            }else{
                btnNext.setDisable(false);
            }
            if(rs.isFirst())
                btnPrev.setDisable(true);
            else
                btnPrev.setDisable(false);
           btnActualizar.setDisable(true);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void prevData() {
        try {
            if (rs.previous()) {
                txtContrasena2.setText(rs.getString("contraseña"));
                txtUsuario2.setText(rs.getString("usuario"));
                txtPerfil2.setText(rs.getString("perfil"));
                txtNombre2.setText(rs.getString("nombre"));
                txtApellido2.setText(rs.getString("apellidos"));
                txtEdad2.setText(rs.getString("edad"));
                txtCorreo2.setText(rs.getString("email"));
                txtDomicilio2.setText(rs.getString("domicilio"));
                txtTelefono2.setText(rs.getString("telefono"));
            }
           if(rs.isLast()){
                btnNext.setDisable(true);
            }else{
                btnNext.setDisable(false);
            }
            if(rs.isFirst())
                    btnPrev.setDisable(true);
                else
                    btnPrev.setDisable(false);
             btnActualizar.setDisable(true);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Delete(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario2.getText();
            Boolean resultado = false;
            resultado = st.execute("DELETE FROM usuarios WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han eliminado los datos!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        erase();
    }

    @FXML
    private void Actualizar(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario2.getText();
            String contrasena = txtContrasena2.getText();
            String perfil = txtPerfil2.getText();
            String nombre = txtNombre2.getText();
            String apellido = txtApellido2.getText();
            String edad = txtEdad2.getText();
            String correo = txtCorreo2.getText();
            String domicilio = txtDomicilio2.getText();
            String telefono = txtTelefono2.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),correo=('" + correo + "'),nombre=('" + nombre + "'),apellido=('" + apellido + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han actualizado los datos!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        erase();
    }
    
    @FXML
    private void ActualizarP(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuarioP.getText();
            String contrasena = txtContrasenaP.getText();
            String perfil = txtPerfilP.getText();
            String nombre = txtNombreP.getText();
            String apellido = txtApellidoP.getText();
            String edad = txtEdadP.getText();
            String correo = txtCorreoP.getText();
            String domicilio = txtDomicilioP.getText();
            String telefono = txtTelefonoP.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),correo=('" + correo + "'),nombre=('" + nombre + "'),apellido=('" + apellido + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
            System.out.println("El resultado es: " + resultado);
            if (!resultado) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han actualizado los datos!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        erase();
    }

    private boolean erase() {
        txtContrasena2.setText(null);
        txtUsuario2.setText(null);
        txtPerfil2.setText(null);
        txtNombre2.setText(null);
        txtApellido2.setText(null);
        txtEdad2.setText(null);
        txtCorreo2.setText(null);
        txtDomicilio2.setText(null);
        txtTelefono2.setText(null);
        return true;
    }

    @FXML private void CerrarSesion(ActionEvent e){
        Stage s = (Stage) btnCerrarS.getScene().getWindow();
        s.close();
        
        Stage stage = new Stage();
        Parent root = null;
        try {
            
            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        
        
        stage.show();
    }
    @FXML private void Buscar(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root","");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st=conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String usuario= txtUsuarioB.getText();
        String instruccion=("SELECT *FROM usuarios WHERE usuario=('"+usuario+"');");
        System.out.println("Buscando...");
            try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
            try {
           
            if (rs.next()){
                    txtContrasenaB.setText(rs.getString("contraseña"));
                    txtUsuarioB.setText(rs.getString("usuario"));
                    txtPerfilB.setText(rs.getString("perfil"));
                    txtNombreB.setText(rs.getString("nombre"));
                    txtApellidoB.setText(rs.getString("apellido"));
                    txtEdadB.setText(rs.getString("edad"));
                    txtCorreoB.setText(rs.getString("correo"));
                    txtDomicilioB.setText(rs.getString("domicilio"));
                    txtTelefonoB.setText(rs.getString("telefono"));
            }else{
                Alert alert= new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡No se han encontrado los datos!");
                alert.showAndWait();
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }   
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        } 
   }
    @FXML private void DeleteB(ActionEvent event){
         Connection conn=null;
        Statement st;
        try {
                Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
                Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root","");
        } catch (SQLException ex) {
                Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st=conn.createStatement();
            String usuario= txtUsuarioB.getText();
            Boolean resultado=false;
            resultado = st.execute("DELETE FROM usuarios WHERE usuario = ('"+usuario+"');");
            System.out.println("El resultado es: "+ resultado);    
            if(!resultado){
                Alert alert= new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Información");
                alert.setHeaderText("Alerta");
                alert.setContentText("¡Se han eliminado los datos!");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
      eraseB();
     }

  private boolean eraseB(){
          txtContrasenaB.setText(null);
        txtUsuarioB.setText(null);
        txtPerfilB.setText(null);
        txtNombreB.setText(null);
        txtApellidoB.setText(null);
        txtEdadB.setText(null);
        txtCorreoB.setText(null);
        txtDomicilioB.setText(null);
        txtTelefonoB.setText(null);
          return true;
      }
      

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(ProyectoFinal.get().getPerfil().equals("administrador")){
            admiPerfil.setVisible(true);
            userPerfil.setVisible(false);
        }else{
            admiPerfil.setVisible(false);
           userPerfil.setVisible(true);
        }
       pane.getStylesheets().add(FXMLAdministradorController.class.getResource("Estilos/GreenStyle.css").toExternalForm());
        btnNext.setDisable(true);
        btnPrev.setDisable(true);
        btnActualizar.setDisable(true);
        btnBorrar.setDisable(true);
        txtTelefono2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });
        txtDomicilio2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(true);
        });
        txtCorreo2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });
        txtContrasena2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });

        txtNombre2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });
        txtApellido2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });
        txtEdad2.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar.setDisable(false);
        });
        txtTelefonoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });
        txtDomicilioP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(true);
        });
        txtCorreoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });
        txtContrasenaP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });

        txtNombreP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });
        txtApellidoP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });
        txtEdadP.textProperty().addListener((observable, oldValue, nextValue) -> {
            btnActualizar1.setDisable(false);
        });
    }
}
