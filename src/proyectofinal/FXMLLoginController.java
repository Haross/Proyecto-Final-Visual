/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import de.jensd.fx.glyphs.materialicons.MaterialIconView;
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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Javier - Edgardo
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private Button btnLogin;
    @FXML
    private AnchorPane PaneLogin;
    @FXML
    private MaterialIconView icon, icon2;
    
    private ResultSet rs = null;
    private Connection conecc;
    private Statement st = null;
    public static ResultSet resultUser;
    
     private void openWindowWithOption(String file) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            Stage stageAux = (Stage) btnLogin.getScene().getWindow();
            stageAux.close();
            root = FXMLLoader.load(getClass().getResource(file));
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void Perfil() {
        BuscarDatosPuro();
    }

    public void BuscarDatosPuro() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        try {
            Class.forName("com.mysql.jdbc.Driver"); //Comprobar el conector    org.gjt.mm.mysql.Driver
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "facil");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st = conecc.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String usuario = txtUsuario.getText();
        String contrasena = txtContrasena.getText();

        String instruccion = "SELECT * FROM usuarios WHERE(usuario=('" + usuario + "') AND contraseña=('" + contrasena + "'));";
        System.out.println(instruccion);

        try {
            rs = st.executeQuery(instruccion);

            txtUsuario.setText(null);
            txtContrasena.setText(null);
            try {
                if (rs.next()) {
                    switch(rs.getString("perfil")){
                        case "administrador":
                            openWindowWithOption("FXMLAdministrador.fxml");
                            resultUser = rs;
                            break;
                        case "usuario":
                            openWindowWithOption("FXMLUsuario.fxml");
                            resultUser = rs;
                            break;
                        default:
                            System.out.println("case default");
                            break;     
                    }
                }else{
                    alert.setTitle("Error");
                    alert.setHeaderText("Error");
                    alert.setContentText("El usuario o la contraseña no son correctas");
                    alert.showAndWait(); 
                }   
            }catch (SQLException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("user: " + usuario);
    } 
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUsuario.setOnKeyTyped((e)->{
            icon.setStyle("-fx-fill:#69F0AE;"); 
        });
        /*ObservableList<String> aux = PaneLogin.getStylesheets();
        String a = aux.get(aux.size()-1);
        String[] aa = a.split("/");
                System.out.println("hola:"+aa[aa.length-1]);
*/
        txtContrasena.setOnKeyTyped((e)->{
            icon2.setStyle("-fx-fill:#69F0AE;"); 
        });
    }    
    
}
