/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Javier - Edgardo c:
 */
public class FXMLExplorerController implements Initializable {

    @FXML
    private TreeView tvArbol;
    @FXML
    private AnchorPane control;
 
    TreeItem<String> folder;
    GestorArchivos Archivos;

    @FXML private void abrir(ActionEvent e){
       //Archivos.openFile();
    }
    @FXML private void get(){
        System.out.println(tvArbol.getSelectionModel());
    }
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
        Archivos = new GestorArchivos("1");
        folder = new TreeItem<>("Este equipo",icono("folder.png"));
        tvArbol.setRoot(folder);
        setDirectorio(folder,Archivos.getDirectorio(),"");
        
        tvArbol.getSelectionModel().selectedItemProperty().addListener( new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue,Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                System.out.println("Selected Text : " + selectedItem.getValue());
                System.out.println(selectedItem.getChildren()); 
            }

        });
    }
    
    private void setIconos(){
        
    }
    
    private void setDirectorio(TreeItem<String> folder, String raiz,String subRaiz){
        String a = raiz +"\\"+subRaiz;
        String subDirectorio[] = Archivos.contenido(a);
        if(subDirectorio != null)
            for(int i =0; i<subDirectorio.length;i++){
                if(subDirectorio[i].matches(".*\\..*")){
                     TreeItem<String> txt = new TreeItem<> (subDirectorio[i],icono("txt.png"));
                     folder.getChildren().add(txt);
                     System.out.println("Matches archivo");
                }else{
                    TreeItem<String> fold = new TreeItem<>(subDirectorio[i],icono("folder.png"));
                    folder.getChildren().add(fold);
                    String nuevaRaiz = a + "";
                    setDirectorio(fold,a,subDirectorio[i]);
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
