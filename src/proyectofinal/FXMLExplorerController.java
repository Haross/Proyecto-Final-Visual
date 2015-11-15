/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 *
 * @author Javier - Edgardo c:
 */
public class FXMLExplorerController implements Initializable {

    @FXML
    private TreeView tvArbol,tvArbolAbrir,tvArbolGuardar;
    @FXML
    private TilePane control,controlAbrir,controlGuardar;
    @FXML private AnchorPane AnchoPaneAbrir,AnchoPaneGuardar,AnchoPaneExp;
    @FXML private TextField txtNombreBA,txtNombreBG;
    TreeItem<String> auxiliar;
    TreeItem<String> seleccionado;
    TreeItem<String> folder;
    private GestorArchivos Archivos;
    String ruta; //ruta del elemento del treeview seleccionado
    String nombreC;
    String[] extension = new String[2];
    String rutaArchivo = null;
    
    
    @FXML private void abrir(ActionEvent e) {
        String nombreA = txtNombreBA.getText();
        buscar(tvArbol.getRoot().getChildren(),nombreA,Archivos.getDirectorio());
        Alert alert = new Alert(AlertType.CONFIRMATION);
        Alert alertN = new Alert(AlertType.ERROR);
        if (rutaArchivo == null) {
            alertN.setTitle("Archivo no encontrado");
            alertN.setHeaderText("No se ha encontrado el archivo");
            alertN.showAndWait();
        }
        if (rutaArchivo != null) {
            alert.setTitle("Archivo Encotrado");
            alert.setHeaderText("¿Desea abrirlo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //Abrir la aplicacion
            }
            rutaArchivo=null;
        }
    }

    @FXML private void guardar(ActionEvent e){
        String nombreA = txtNombreBG.getText();//nombre del archivo
        buscar(tvArbol.getRoot().getChildren(),nombreA,Archivos.getDirectorio());
        String comparar = rutaArchivo;
        System.out.println("Comparar: "+ comparar);
        System.out.println("Prueba de buscar guardar: "+rutaArchivo);
        if (comparar == null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("No existe el Archivo");
            alert.setHeaderText("¿Desea guardarlo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Nuevo archivo");
                dialog.setContentText("Esriba el nombre la carpeta: ");
                Optional<String> resultG = dialog.showAndWait();
                 String ncarpeta = resultG.get();
                 try {
                    if (resultG.isPresent()){
                        buscar(tvArbol.getRoot().getChildren(), ncarpeta, Archivos.getDirectorio());
                        String nuevo = rutaArchivo;//ruta de la carpeta
                        //guardar archivo
                    }
                } catch (Exception ex) {
                    
                }
               
            }
            
        }
        if (comparar != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Archivo existente");
            alert.setHeaderText("¿Desea sobrescribirlo?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                //Sobrescrbir
            }
            rutaArchivo=null;
        }
    }
    
    private String buscar(ObservableList<TreeItem<String>> nodos,String nombre, String rutaArchivo){
        for(int i=0;i<nodos.size();i++){
            if(nodos.get(i).getValue().equals(nombre) && nodos.get(i).getValue().matches(".*\\..*")){
               this.rutaArchivo = rutaArchivo+"\\"+nombre;
            }else{
               
                    buscar(nodos.get(i).getChildren(),nombre, rutaArchivo+"\\"+nodos.get(i).getValue());
                
               

            }
        }
        return null;
    }

    @FXML
    private void nuevaVentana(ActionEvent e) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Carpeta");
        dialog.setHeaderText("Crear nueva carpeta");
        dialog.setContentText("Nombre de la carpeta");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            nombreC = result.get();
            String Path = "..\\datos\\" + ruta + "\\"+nombreC;
            if(!Archivos.checkDirectorio(Path)){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("El archivo ya existe, ingrese otro nombre");

                alert.showAndWait();
                nuevaVentana(e);
            }else{
                setItem(seleccionado,nombreC);
                
            }
        }
    }
    
    @FXML
    private void eliminarVentana(ActionEvent e) throws IOException {
        File fDirectorio;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Eliminar Carpeta");
        alert.setHeaderText("Si desea eliminar la carpeta seleccion Aceptar");
        String PATH = "..\\datos\\" + ruta;
        fDirectorio = new File(PATH);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            fDirectorio.delete();
             seleccionado.getParent().getChildren().remove(seleccionado);
        } else {
           // TreeItem<String> aux = seleccionado.getParent();
           
        }
    }
    
    

    @FXML
    private void get() {
        System.out.println(tvArbol.getSelectionModel());
    }
    
    @FXML private void viewAbrir(ActionEvent e){
        AnchoPaneAbrir.setVisible(true);
        AnchoPaneExp.setVisible(false);
        AnchoPaneGuardar.setVisible(false);
    }
    
    @FXML private void viewGuardar(ActionEvent e){
        AnchoPaneGuardar.setVisible(true);
        AnchoPaneAbrir.setVisible(false);
        AnchoPaneExp.setVisible(false);
    }
    
    @FXML private void viewExplorer(ActionEvent e){
        AnchoPaneExp.setVisible(true);
        AnchoPaneGuardar.setVisible(false);
        AnchoPaneAbrir.setVisible(false);
    }
    
    public String getRuta() {
        return ruta;
    }
    
    /*
     * método que pone los iconos en el controler
     */
    private void setIconFiles(ObservableList<TreeItem<String>> aux) {
        control.getChildren().clear();
        control.setPrefColumns(3);
        control.setPrefRows(3);
        String[] nextension = new String[2];
        for (int i = 0; i < aux.size(); i++) {
            Button b = new Button();
            b.setId(ruta);
            nextension = aux.get(i).getValue().split("\\.");
            try {
                b.setGraphic(icono("folder.png", 40, 40));
                if (nextension[1].equals("txt")) {
                    b.setGraphic(icono("txt.png", 40, 40));
                }
                if (nextension[1].equals("jpg") || nextension[1].equals("bmp") || nextension[1].equals("jpeg") || nextension[1].equals("jpe") || nextension[1].equals("jfif") || nextension[1].equals("gif") || nextension[1].equals("tif") || nextension[1].equals("tiff") || nextension[1].equals("png")) {
                    b.setGraphic(icono("mult.png", 40, 40));
                }
            } catch (Exception ex) {
            }

            b.setText(aux.get(i).getValue());
            b.setTextAlignment(TextAlignment.CENTER);
            b.setOnAction((e) -> {

            });
            control.setOrientation(Orientation.HORIZONTAL);
            control.getChildren().addAll(b);
        }
    }
    //
    private void setDirectorio(TreeItem<String> folder, String raiz, String subRaiz) {
        String a = raiz + "\\" + subRaiz;
        String subDirectorio[] = Archivos.contenido(a);
        if (subDirectorio != null) {
            for (int i = 0; i < subDirectorio.length; i++) {
                if (setItem(folder,subDirectorio[i]).equals("carpeta")) {
                    String nuevaRaiz = a + "";
                    setDirectorio(getAuxItem(), a, subDirectorio[i]);
                }
            }
        }
    }
    //
    public String setItem(TreeItem<String> folder, String subDirectorio){
        if (subDirectorio.matches(".*\\..*")) {
            extension = subDirectorio.split("\\.");
            if (extension[1].equals("txt")) {
                TreeItem<String> txt = new TreeItem<>(subDirectorio, icono("txt.png", 20, 20));
                folder.getChildren().add(txt);
                //System.out.println("Matches archivo");
                //return "texto";
            }
            if (extension[1].equals("jpg") || extension.equals("bmp")|| extension.equals("jpeg") || extension.equals("jpe") || extension.equals("jfif") || extension.equals("gif") || extension.equals("tif") || extension.equals("tiff") || extension.equals("png") ) {
                TreeItem<String> txt = new TreeItem<>(subDirectorio, icono("mult.png", 20, 20));
                folder.getChildren().add(txt);
                //System.out.println("Matches archivo");
                //return "texto";
            }
            return "texto";
        } else {
            TreeItem<String> fold = new TreeItem<>(subDirectorio, icono("folder.png", 20, 20));
            folder.getChildren().add(fold);
            auxiliar = fold;
            //System.out.println("Matches carpeta");
            return "carpeta";
        }
    }
    
    public TreeItem<String> getAuxItem(){
        return auxiliar;
    }
    //
    public ImageView icono(String imagen, double width, double height) {
        Image imageFolder = new Image(getClass().getResourceAsStream(imagen));
        ImageView iF = new ImageView(imageFolder);
        iF.setFitWidth(width);
        iF.setFitHeight(height);
        return iF;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        explorador();
        exploradorA();
        exploradorG();
    }
    
   
    
    private void explorador(){
        //Mandar el nombre de usuario en el 1
        Archivos = new GestorArchivos("1");
        //Mandar nombre de usuario en el "1"
        folder = new TreeItem<>("1", icono("folder.png", 20, 20));
        tvArbol.setRoot(folder);
        //System.out.println("Obtener root del arbol "+tvArbol.getRoot());
        setDirectorio(folder, Archivos.getDirectorio(), "");
        control.setPadding(new Insets(10, 10, 10, 10));
        control.setVgap(5);
        control.setHgap(5);
        tvArbol.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                TreeItem<String> padre = selectedItem.getParent();
                seleccionado = selectedItem;
                ruta = selectedItem.getValue();
                while (padre != null) {
                    ruta = "\\" + padre.getValue() + "\\" + ruta;
                    padre = padre.getParent();

                }
                //setRuta(ruta);         
                setIconFiles(selectedItem.getChildren());

            }

        });
          //buscar(tvArbol.getRoot().getChildren(),"jeje.txt",Archivos.getDirectorio());
        //System.out.println("hola"+rutaArchivo);
    }
///////////////////////////////////////////////Abrir///////////////////////////////    
     private void exploradorA(){
        Archivos = new GestorArchivos("1");
        folder = new TreeItem<>("1", icono("folder.png", 20, 20));
        tvArbolAbrir.setRoot(folder);
        //System.out.println("Obtener root del arbol "+tvArbolAbrir.getRoot());
        setDirectorio(folder, Archivos.getDirectorio(), "");
        controlAbrir.setPadding(new Insets(10, 10, 10, 10));
        controlAbrir.setVgap(5);
        controlAbrir.setHgap(5);
        tvArbolAbrir.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                TreeItem<String> padre = selectedItem.getParent();
                seleccionado = selectedItem;
                ruta = selectedItem.getValue();
                while (padre != null) {
                    ruta = "\\" + padre.getValue() + "\\" + ruta;
                    padre = padre.getParent();

                }
                //setRuta(ruta);         
                setIconFilesOpen(selectedItem.getChildren());

            }

        });
    }
     
     private void setIconFilesOpen(ObservableList<TreeItem<String>> aux) {
        
        controlAbrir.getChildren().clear();
        controlAbrir.setPrefColumns(3);
        controlAbrir.setPrefRows(3);
        String[] nextension = new String[2];
        for (int i = 0; i < aux.size(); i++) {
            Button b = new Button();
            b.setId(ruta);
            nextension =  aux.get(i).getValue().split("\\.");
            try {
                b.setGraphic(icono("folder.png", 40, 40));
                if (nextension[1].equals("txt")) {
                    b.setGraphic(icono("txt.png", 40, 40));
                }
                if (nextension[1].equals("jpg") || nextension[1].equals("bmp") || nextension[1].equals("jpeg") || nextension[1].equals("jpe") || nextension[1].equals("jfif") || nextension[1].equals("gif") || nextension[1].equals("tif") || nextension[1].equals("tiff") || nextension[1].equals("png")) {
                    b.setGraphic(icono("mult.png", 40, 40));
                }
            } catch (Exception ex) {
            }
            
            b.setText(aux.get(i).getValue());
            b.setTextAlignment(TextAlignment.CENTER);
            b.setOnAction((e) -> {

            });
            controlAbrir.setOrientation(Orientation.HORIZONTAL);
            controlAbrir.getChildren().addAll(b);
        }
    }
     
    ///////////////////////////////////////////////Guardar///////////////////////////////    
     private void exploradorG(){
        Archivos = new GestorArchivos("1");
        folder = new TreeItem<>("1", icono("folder.png", 20, 20));
        tvArbolGuardar.setRoot(folder);
        //System.out.println("Obtener root del arbol "+tvArbolGuardar.getRoot());
        setDirectorio(folder, Archivos.getDirectorio(), "");
        controlGuardar.setPadding(new Insets(10, 10, 10, 10));
        controlGuardar.setVgap(5);
        controlGuardar.setHgap(5);
        tvArbolGuardar.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                TreeItem<String> padre = selectedItem.getParent();
                seleccionado = selectedItem;
                ruta = selectedItem.getValue();
                while (padre != null) {
                    ruta = "\\" + padre.getValue() + "\\" + ruta;
                    padre = padre.getParent();

                }
                //setRuta(ruta);         
                setIconFilesSave(selectedItem.getChildren());

            }

        });
    }
     
     private void setIconFilesSave(ObservableList<TreeItem<String>> aux) {
        
        controlGuardar.getChildren().clear();
        controlGuardar.setPrefColumns(3);
        controlGuardar.setPrefRows(3);
        String[] nextension = new String[2];
        for (int i = 0; i < aux.size(); i++) {
            Button b = new Button();
            b.setId(ruta);
            nextension =  aux.get(i).getValue().split("\\.");
            try {
                b.setGraphic(icono("folder.png", 40, 40));
                if (nextension[1].equals("txt")) {
                    b.setGraphic(icono("txt.png", 40, 40));
                }
                if (nextension[1].equals("jpg") || nextension[1].equals("bmp") || nextension[1].equals("jpeg") || nextension[1].equals("jpe") || nextension[1].equals("jfif") || nextension[1].equals("gif") || nextension[1].equals("tif") || nextension[1].equals("tiff") || nextension[1].equals("png")) {
                    b.setGraphic(icono("mult.png", 40, 40));
                }
            } catch (Exception ex) {
            }
            
            b.setText(aux.get(i).getValue());
            b.setTextAlignment(TextAlignment.CENTER);
            b.setOnAction((e) -> {

            });
            controlGuardar.setOrientation(Orientation.HORIZONTAL);
            controlGuardar.getChildren().addAll(b);
        }
    }
}
