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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proyectofinal.Explorador.FXMLExplorerController;

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
            conecc = DriverManager.getConnection("jdbc:mysql://localhost/pvisual", "root", "");
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
                     User u = ProyectoFinal.get();
                            u.setUser(rs.getString("usuario"));
                            u.setPerfil(rs.getString("perfil"));
                            //---------------------------------
                            FXMLExplorerController a = new FXMLExplorerController();
                            a.checkCarpeta(rs.getString("usuario"));
                            openWindowWithOption("FXMLAdministrador.fxml");
                            resultUser = rs;
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
    @FXML
     private void creditos(ActionEvent e){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Universidad Politécnica de Chiapas \n\n\t");
        alert.setTitle("Créditos");
        ImageView a = icono("Estilos/logoUPCH.png",50,50);
        alert.setContentText(" \n\n\tNombre de la investigacion: Proyecto final\n\n\tJavier de Jesus Flores Herrerra \n\n\t Matricula: 143372 \n\n\t Edgardo Rito Deheza \n\n\t Matricula: 143370 \n\n\t Nombre del profesor: Juan Carlos Lopez pimentel \n\n\t Asignatura: Programacion Visual \n\n\t Fecha: 23/Nov/2015");
       
       
        alert.setGraphic(a);
        alert.showAndWait();
        
 
     }
      public ImageView icono(String imagen, double width, double height) {
        Image imageFolder = new Image(getClass().getResourceAsStream(imagen));
        ImageView iF = new ImageView(imageFolder);
        iF.setFitWidth(width);
        iF.setFitHeight(height);
        return iF;
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtUsuario.textProperty().addListener((observable, oldValue, nextValue) -> {
            try{if (nextValue.equals("")) {
                 icon.setStyle("-fx-fill:#a89d9d;"); 
            } else {
                icon.setStyle("-fx-fill:#69F0AE;"); 
            }
            }catch(Exception e){
                
            }
        });
        /*ObservableList<String> aux = PaneLogin.getStylesheets();
        String a = aux.get(aux.size()-1);
        String[] aa = a.split("/");
                System.out.println("hola:"+aa[aa.length-1]);
*/
        txtContrasena.textProperty().addListener((observable, oldValue, nextValue) -> {
            try{if (nextValue.equals("")) {
                 icon2.setStyle("-fx-fill:#a89d9d;"); 
            } else {
                icon2.setStyle("-fx-fill:#69F0AE;"); 
            }}catch(Exception e){}
        });
       
    }    
    
}
