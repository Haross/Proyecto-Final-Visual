package proyectofinal.Block;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import proyectofinal.Controla;
import proyectofinal.FXMLLoginController;
/**
 *
 * @author none
 */

public class FXMLDocumentController implements Initializable {
    public static String nombreArchivo = null;
    public static String rutaArchivoBlock = null;
    public static String textoArchivoBlock = null;
    //Declaraciones de Componentes
    @FXML private Label label;
    @FXML TextArea TextoArea;
    @FXML ComboBox cbFonts,cbSize;
    @FXML Button btnGuardar;
    //Variables
    private Scene scene;
    String text = "";
    public static String guardar;
    //Instancias Utiles
    GestorArchivosBlock Archivos;
    Controla ct = new Controla();
    private Version v = new Version();  //Gestion de entrada de datos, texto
    
    //Evento de entrada de teclado
    @FXML private void TextAreaArray(Event event){
        System.out.println("Entrada");
        ct.setString(TextoArea.getText());
              
        //Modificacion de Hacer y deshacer.     
        v.setSiguiente(new Version(TextoArea.getText()));
        v = v.getSiguiente();
    }
    
    @FXML private void rehacer(ActionEvent event){   
        if(v.getSiguiente() != null){
            v = v.getSiguiente();
            TextoArea.setText(v.getTexto());
        }
    }
    
    @FXML private void deshacer(ActionEvent event){
       
        if(v.getAnterior() != null){
            v = v.getAnterior();
            TextoArea.setText(v.getTexto());
        }
    }
    
    @FXML private void salir(ActionEvent event){
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("¿Desea salir?");
        alert.setHeaderText("Importante");
        alert.setContentText("¿Desea salir realmente?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.get() == ButtonType.OK)
            System.exit(0);   
        
    }
    
     @FXML private void creditos(ActionEvent event){
        
        Alert alert1 = new Alert(AlertType.INFORMATION);
        alert1.setTitle("Universidad Politécnica de Chiapas");
        alert1.setHeaderText("Creditos");
        alert1.setContentText("Alumno: Javier de Jesus Flores Herrera");
        alert1.showAndWait();
        
    }
     @FXML
     private void fontVerdana(ActionEvent e){
         cbFonts.setValue("Verdana");
         selectFont(e);
     }
     @FXML
     private void fontComic(ActionEvent e){
         cbFonts.setValue("Comic");
         selectFont(e);
     }
     @FXML
     private void fontCalibri(ActionEvent e){
         cbFonts.setValue("Calibri");
         selectFont(e);
     }
     @FXML
     private void fontArial(ActionEvent e){
         cbFonts.setValue("Arial");
         selectFont(e);
     }
     @FXML
     private void fontBroadway(ActionEvent e){
         cbFonts.setValue("Broadway");
         selectFont(e);
     }
     @FXML
     private void size11(ActionEvent e){
         cbSize.setValue("11");
         selectFont(e);
     }
     @FXML
     private void size15(ActionEvent e){
         cbSize.setValue("15");
         selectFont(e);
     }
     @FXML
     private void size18(ActionEvent e){
         cbSize.setValue("18");
         selectFont(e);
     }
     @FXML
     private void size22(ActionEvent e){
         cbSize.setValue("22");
         selectFont(e);
     }
     @FXML
     private void size30(ActionEvent e){
         cbSize.setValue("30");
         selectFont(e);
     }
  
     String opcSize;
     
     @FXML
     private void selectFont(ActionEvent event){
         try {
             String opc;
             int value;
             opc = (String) cbFonts.getValue();
             opcSize = (String) cbSize.getValue();
             value = Integer.parseInt(opcSize);
             switch (opc) {

                 case "Verdana":
                     TextoArea.setFont(new Font("Verdana", value));
                     break;
                 case "Comic Sans MS":
                     TextoArea.setFont(new Font("Comic Sans MS", value));
                     break;
                 case "Calibri":
                     TextoArea.setFont(new Font("Calibri", value));
                     break;
                 case "Arial":
                     TextoArea.setFont(new Font("Arial", value));
                     break;
                 case "Broadway":
                     TextoArea.setFont(new Font("Broadway", value));
                     break;
             }
         } catch (Exception e) {

         }

     }
     
     ObservableList<String> list = FXCollections.observableArrayList(
             "Verdana","Comic Sans MS","Calibri","Arial","Broadway");
     ObservableList<String> listSize = FXCollections.observableArrayList(
             "11","15","18","22","30");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbFonts.setItems(list);
        cbSize.setItems(listSize);
        cbFonts.setValue("Verdana");
        cbSize.setValue("18");
    }    
    
    @FXML private void abrir(ActionEvent event){
        
        Stage stage = new Stage();
        Parent root = null;
        try {            
            root = FXMLLoader.load(FXMLLoginController.class.getResource("Explorador/FXMLExplorerAbrir.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
        
        
        //////////////formato
        String text2[] = textoArchivoBlock.split("@#1codigo4k781#@");
        text = text2[1];
        this.TextoArea.setText(text2[0]);
        String text3[] = text.split(",");
        cbFonts.setValue(text3[0]);
        
        cbSize.setValue(text3[1]);
        selectFont(event);
        
    }
    
    
    
    
    @FXML private void guardarComo(ActionEvent event){
       Stage stage = new Stage();
        Parent root = null;
        try {            
            //Investigar manera correcta root = FXMLLoader.load(getClass().getResource("FXMLExplorer.fxml"));
            root = FXMLLoader.load(FXMLLoginController.class.getResource("Explorador/FXMLExplorerGuardarBlock.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        String save = this.TextoArea.getText() + "@#1codigo4k781#@" + (String)cbFonts.getValue() + "," + (String) cbSize.getValue() + ",";
        guardar = save;
        //this.Archivos.saveFileAs( save);
        
    }
    
    @FXML 
    private void guardar(ActionEvent e){
        Stage stage = new Stage();
        Parent root = null;
        String save = this.TextoArea.getText() + "@#1codigo4k781#@" + (String) cbFonts.getValue() + "," + (String) cbSize.getValue() + ",";
        guardar = save;
        if (nombreArchivo != null) {
            System.out.println("nom" + nombreArchivo);
            System.out.println("ru" + rutaArchivoBlock);
            String[] extension = nombreArchivo.split("\\.");
            File file = new File(rutaArchivoBlock, nombreArchivo);
            try {
                Files.write(file.toPath(), guardar.getBytes());
            } catch (Exception exs) {
                //exs.printStackTrace();
            }
        } else {
            try {
                //Investigar manera correcta root = FXMLLoader.load(getClass().getResource("FXMLExplorer.fxml"));
                root = FXMLLoader.load(FXMLLoginController.class.getResource("Explorador/FXMLExplorerGuardar.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        
    }
    /*
    public void saveFile(String texto){
       fileDialog.setTitle("Guardar");
        fileDialog.setInitialDirectory(this.myPath);
        
        
        
        if(file == null){
            file = fileDialog.showSaveDialog(new Stage());
            if(file == null)
                return;
        }try{
            
            Files.write(file.toPath(), texto.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    */


    void setScene(Scene scene) {
        this.scene = scene;
    }

    void setDireccion(String direccion) {
        this.Archivos = new GestorArchivosBlock(direccion);
        Archivos.checkDirectory();
    }
    
    
    
}
