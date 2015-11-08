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
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Javier - Edgardo
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TreeView tvArbol;
    TreeItem<String> txt,mult,folder,fold;
    GestorArchivos Archivos;
    @FXML
    public void agregar_arbol(ActionEvent event){
        String ruta = "";
        
        final Node icon = new ImageView(new Image(getClass().getResourceAsStream(ruta)));
        TreeItem<String> selectedItem = (TreeItem<String>) tvArbol.getSelectionModel().selectedItemProperty().getValue();
    }
    
    @FXML private void abrir(ActionEvent e){
       Archivos.openFile();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Archivos = new GestorArchivos();
        
        Image imageFolder = new Image(getClass().getResourceAsStream("folder.png"));
        ImageView iF = new ImageView(imageFolder);
        iF.setFitWidth(25);
        iF.setFitHeight(25);
        
        Image imagMult = new Image(getClass().getResourceAsStream("mult.png"));
        ImageView iM = new ImageView(imagMult);
        iM.setFitWidth(25);
        iM.setFitHeight(25);
        
        Image imageTxt = new Image(getClass().getResourceAsStream("txt.png"));
        ImageView iT = new ImageView(imageTxt);
        iT.setFitWidth(25);
        iT.setFitHeight(25); 

        folder = new TreeItem<>("Este equipo",iF);
        fold = new TreeItem<>("...",iF);
        tvArbol.setRoot(folder);
        mult = new TreeItem<> ("...",iM);  
        txt = new TreeItem<> ("...",iT);  
        folder.getChildren().add(fold);
        folder.getChildren().add(mult);
        folder.getChildren().add(txt);
    }    
    
}
