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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static proyectofinal.FXMLLoginController.resultUser;

/**
 * FXML Controller class
 *
 * @author Javier
 */
public class FXMLUsuarioController implements Initializable {
    
    @FXML
    private TextField txtUsuario, txtContrasena, txtPerfil, txtNombre, txtApellido,
                      txtEdad, txtCorreo, txtDomicilio, txtTelefono;
    @FXML
    AnchorPane  principalUser, perfilUser;
    
    @FXML Button btnCerrarS;
    private ResultSet rs = null;
    private Connection conecc;
    private Statement st = null;
    boolean x = false;
    int cont = 0;
    public   String us;
    
    
    @FXML private void abrirPaint(ActionEvent e){
        Stage stage = new Stage();
        Parent root = null;
        try {
       
            root = FXMLLoader.load(getClass().getResource("Paint/FXMLminipaint.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML private void abrirBlock(ActionEvent e){
        Stage stage = new Stage();
        Parent root = null;
        try {
            
            root = FXMLLoader.load(getClass().getResource("Block/FXMLDocument.fxml"));
        } catch (IOException ex) {
            System.out.println("error");
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
     @FXML private void CerrarSesion(ActionEvent e){
        Stage s = (Stage) btnCerrarS.getScene().getWindow();
        s.close();
        
        Stage stage = new Stage();
        Parent root = null;
        try {
            
            root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);

        stage.setScene(scene);
        
        
        stage.show();
    }
     
    @FXML
    private void Cancel(ActionEvent event) {
        principalUser.setVisible(true);
        perfilUser.setVisible(false);
    }
    
     @FXML
    public void Perfil() {
        principalUser.setVisible(false);
        perfilUser.setVisible(true);
        
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
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
                    txtContrasena.setText(rs.getString("contraseña"));
                    txtUsuario.setText(rs.getString("usuario"));
                    txtPerfil.setText(rs.getString("perfil"));
                    txtNombre.setText(rs.getString("nombre"));
                    txtApellido.setText(rs.getString("apellidos"));
                    txtEdad.setText(rs.getString("edad"));
                    txtCorreo.setText(rs.getString("email"));
                    txtDomicilio.setText(rs.getString("domicilio"));
                    txtTelefono.setText(rs.getString("telefono"));
                    }
            } catch (SQLException ex) {
                Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML private void Actualizar(ActionEvent event) {
        Connection conn = null;
        Statement st;
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conn.createStatement();
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasena.getText();
            String perfil = txtPerfil.getText();
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String edad = txtEdad.getText();
            String correo = txtCorreo.getText();
            String domicilio = txtDomicilio.getText();
            String telefono = txtTelefono.getText();
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
            Logger.getLogger(FXMLUsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        erase();
    }

    private boolean erase() {
        txtContrasena.setText(null);
        txtUsuario.setText(null);
        txtPerfil.setText(null);
        txtNombre.setText(null);
        txtApellido.setText(null);
        txtEdad.setText(null);
        txtCorreo.setText(null);
        txtDomicilio.setText(null);
        txtTelefono.setText(null);
        return true;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
