/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ERIDE21
 */
public class NuevaCarpetaController implements Initializable {

    @FXML TextField txtNombreCarp;
    @FXML Button btnCerrar;
    String nombreCarpeta; 
    GestorArchivos carpeta;
    Stage ventanaC;
    File fDirectorio;
    FXMLExplorerController exc = new FXMLExplorerController();
    
    @FXML 
    private void crearNueva(ActionEvent e){
        Ruta();
    }
    
    public void Ruta(){
        nombreCarpeta = txtNombreCarp.getText();
        String PATH = exc.getRuta();
        PATH = "..\\"+PATH; 
        fDirectorio = new File(PATH +"\\"+nombreCarpeta);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    
}
