/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * @author Javier - Edgardo c:
 */
public class FXMLExplorerController implements Initializable {

    @FXML
    private TreeView tvArbol;
    
    TreeItem<String> folder;
    GestorArchivos Archivos;

    @FXML private void abrir(ActionEvent e){
       //Archivos.openFile();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Archivos = new GestorArchivos("1");
        String[] subDirectorio = Archivos.contenido(Archivos.getDirectorio());

        folder = new TreeItem<>("Este equipo",icono("folder.png"));
        tvArbol.setRoot(folder);

        Pattern pattern = Pattern.compile(".*\\..*");
        for(int i =0; i<subDirectorio.length;i++){
            Matcher matcher = pattern.matcher(subDirectorio[i]);
            if(matcher.matches()){
                 TreeItem<String> txt = new TreeItem<> (subDirectorio[i],icono("txt.png"));
                 folder.getChildren().add(txt);
                 System.out.println("Matches archivo");
            }else{
                
                TreeItem<String> fold = new TreeItem<>(subDirectorio[i],icono("folder.png"));
                folder.getChildren().add(fold);
                System.out.println("Matches carpeta");
            }
        }
        
        
    }    
    
    public ImageView icono(String imagen){
        Image imageFolder = new Image(getClass().getResourceAsStream(imagen));
        ImageView iF = new ImageView(imageFolder);
        iF.setFitWidth(25);
        iF.setFitHeight(25);
        return iF;
    }
    
    
}
