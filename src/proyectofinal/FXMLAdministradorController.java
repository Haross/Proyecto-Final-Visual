/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Stage;
import static proyectofinal.FXMLLoginController.resultUser;
/**
 * FXML Controller class
 *
 * @author Javier
 */
public class FXMLAdministradorController implements Initializable {
    @FXML
    TextField txtUsuario, txtContrasena, txtNombre, txtApellido, txtEdad, txtCorreo, txtDomicilio, txtTelefono, txtEstado, txtCiudad;
    @FXML
    TextField txtUsuarioP, txtContrasenaP, txtPerfilP, txtNombreP, txtApellidoP, txtEdadP, txtCorreoP, txtDomicilioP, txtTelefonoP, txtEstadoP, txtCiudadP;
    @FXML
    TextField txtusuario, txtcontrasena, txtperfil, txtnombre, txtapellido, txtedad, txtcorreo, txtdomicilio, txttelefono, txtestado, txtciudad;
    @FXML
    AnchorPane paneIngresar, principal, consultas, panePerfil;
    @FXML
    ChoiceBox choiceB;
    @FXML
    Button btnBorrar, btnActualizar, btnNext, btnPrev, btnCerrarS;
    private ResultSet rs = null;
    private Connection conecc;
    private Statement st = null;
    boolean x = false;
    int cont = 0;
    public   String us;
   
    

    
    public FXMLAdministradorController(){

        System.out.println("entra aqui");
    }

    @FXML
    private void submitData(ActionEvent event) {
        //insertarDatosPuro();
        insertarDatosPuro();
    }

    @FXML
    private void ingresar() {
        principal.setVisible(false);
        paneIngresar.setVisible(true);
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
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
            String estado = txtEstado.getText();
            String ciudad = txtCiudad.getText();
            Boolean resultado = false;
            resultado = st.execute("INSERT INTO usuarios(usuario, contraseña, perfil, nombre,apellido,edad,correo,domicilio,telefono,estado,ciudad) "
                    + "VALUES" + "('" + usuario + "','" + contrasena + "','" + perfil + "','" + nombre + "','" + apellido + "','" + edad + "','" + correo + "','" + domicilio + "','" + telefono + "','" + estado + "','" + ciudad + "');");
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
        paneIngresar.setVisible(false);
        consultas.setVisible(true);
        consultarDatosPrimitive();
    }

    @FXML
    public void Perfil() {
        principal.setVisible(false);
        paneIngresar.setVisible(false);
        consultas.setVisible(false);
        panePerfil.setVisible(true);
        
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
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
        try {
            rs = st.executeQuery(instruccion); //Ejecuta el query de SQL
            try {
                if (rs.next()) {
                    x = false;
                    cont = 0;
                    txtContrasenaP.setText(rs.getString("contraseña"));
                    txtUsuarioP.setText(rs.getString("usuario"));
                    txtPerfilP.setText(rs.getString("perfil"));
                    txtNombreP.setText(rs.getString("nombre"));
                    txtApellidoP.setText(rs.getString("apellido"));
                    txtEdadP.setText(rs.getString("edad"));
                    txtCorreoP.setText(rs.getString("correo"));
                    txtDomicilioP.setText(rs.getString("domicilio"));
                    txtTelefonoP.setText(rs.getString("telefono"));
                    txtEstadoP.setText(rs.getString("estado"));
                    txtCiudadP.setText(rs.getString("ciudad"));
                }
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
        consultas.setVisible(false);
        panePerfil.setVisible(false);
        paneIngresar.setVisible(false);

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
        openWindowWithOption("FXMLBorrar.fxml");
        
    }
    private void consultarDatosPrimitive() {
        btnBorrar.setDisable(false);
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
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
        prevData();
        btnActualizar.setDisable(true);
    }

    @FXML
    private void nextData() {
        try {
            if (rs.next()) {
                x = false;
                cont = 0;
                txtcontrasena.setText(rs.getString("contraseña"));
                txtusuario.setText(rs.getString("usuario"));
                txtperfil.setText(rs.getString("perfil"));
                txtnombre.setText(rs.getString("nombre"));
                txtapellido.setText(rs.getString("apellido"));
                txtedad.setText(rs.getString("edad"));
                txtcorreo.setText(rs.getString("correo"));
                txtdomicilio.setText(rs.getString("domicilio"));
                txttelefono.setText(rs.getString("telefono"));
                txtestado.setText(rs.getString("estado"));
                txtciudad.setText(rs.getString("ciudad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void prevData() {
        try {
            if (rs.previous()) {
                x = false;
                cont = 0;
                txtcontrasena.setText(rs.getString("contraseña"));
                txtusuario.setText(rs.getString("usuario"));
                txtperfil.setText(rs.getString("perfil"));
                txtnombre.setText(rs.getString("nombre"));
                txtapellido.setText(rs.getString("apellido"));
                txtedad.setText(rs.getString("edad"));
                txtcorreo.setText(rs.getString("correo"));
                txtdomicilio.setText(rs.getString("domicilio"));
                txttelefono.setText(rs.getString("telefono"));
                txtestado.setText(rs.getString("estado"));
                txtciudad.setText(rs.getString("ciudad"));
            }
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtusuario.getText();
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLAdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtusuario.getText();
            String contrasena = txtcontrasena.getText();
            String perfil = txtperfil.getText();
            String nombre = txtnombre.getText();
            String apellido = txtapellido.getText();
            String edad = txtedad.getText();
            String correo = txtcorreo.getText();
            String domicilio = txtdomicilio.getText();
            String telefono = txttelefono.getText();
            String estado = txtestado.getText();
            String ciudad = txtciudad.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),ciudad=('" + ciudad + "'),estado=('" + estado + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),correo=('" + correo + "'),nombre=('" + nombre + "'),apellido=('" + apellido + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
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
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pfinal", "root", "");
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
            String estado = txtEstadoP.getText();
            String ciudad = txtCiudadP.getText();
            Boolean resultado = false;
            resultado = st.execute("UPDATE usuarios SET contraseña=('" + contrasena + "'),ciudad=('" + ciudad + "'),estado=('" + estado + "'),telefono=('" + telefono + "'),domicilio=('" + domicilio + "'),correo=('" + correo + "'),nombre=('" + nombre + "'),apellido=('" + apellido + "'), edad=('" + edad + "') WHERE usuario = ('" + usuario + "');");
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
        txtcontrasena.setText(null);
        txtusuario.setText(null);
        txtperfil.setText(null);
        txtnombre.setText(null);
        txtapellido.setText(null);
        txtedad.setText(null);
        txtcorreo.setText(null);
        txtdomicilio.setText(null);
        txttelefono.setText(null);
        txtestado.setText(null);
        txtciudad.setText(null);
        return true;
    }

    private void desaparecer() {
        if (x) {
            btnActualizar.setDisable(false);
        } else {
            cont = cont + 1;
            btnActualizar.setDisable(true);
            if (cont == 4) {
                x = true;
                cont = 0;
            }
        }
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        btnActualizar.setDisable(true);
        btnBorrar.setDisable(true);
        txttelefono.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtestado.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtciudad.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtdomicilio.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtcorreo.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtcontrasena.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });

        txtnombre.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtapellido.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
        txtedad.textProperty().addListener((observable, oldValue, nextValue) -> {
            desaparecer();
        });
    }
}
