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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
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
    private TreeView tvArbol;
    @FXML
    private TilePane control;

    TreeItem<String> folder;
    GestorArchivos Archivos;
    String ruta; //ruta del elemento del treeview seleccionado
    String nombreC;
    File fDirectorio;

    @FXML
    private void abrir(ActionEvent e) {
        //Archivos.openFile();
    }

    @FXML
    private void get() {
        System.out.println(tvArbol.getSelectionModel());
    }

    @FXML
    private void nuevaVentana(ActionEvent e) throws IOException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Carpeta");
        dialog.setHeaderText("Crear nueva carpeta");
        dialog.setContentText("Nombre de la carpeta");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            nombreC = result.get();
            String Path = "\\" + ruta + "\\"+nombreC;
            fDirectorio = new File(Path);
            System.out.println(Path);
            fDirectorio.mkdir();
        }
    }

    public String getRuta() {
        return ruta;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Archivos = new GestorArchivos("1");
        folder = new TreeItem<>("Este equipo", icono("folder.png", 20, 20));
        tvArbol.setRoot(folder);
        setDirectorio(folder, Archivos.getDirectorio(), "");
        control.setPadding(new Insets(10, 10, 10, 10));
        control.setVgap(5);
        control.setHgap(5);
        tvArbol.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                TreeItem<String> padre = selectedItem.getParent();
                //System.out.println("Selected Text : " + selectedItem.getValue());

                ruta = selectedItem.getValue();
                while (padre != null) {
                    ruta = "\\" + padre.getValue() + "\\" + ruta;
                    padre = padre.getParent();

                }

                System.out.println("prueba" + ruta);

                //setRuta(ruta);         
                setIconFiles(selectedItem.getChildren());

            }

        });
    }

    /*
     * método que pone los iconos en el controler
     */
    private void setIconFiles(ObservableList<TreeItem<String>> aux) {
        control.getChildren().clear();
        for (int i = 0; i < aux.size(); i++) {
            Button b = new Button();
            b.setId(ruta);
            b.setGraphic(icono("txt.png", 40, 40));
            b.setText(aux.get(i).getValue());
            b.setTextAlignment(TextAlignment.CENTER);
            b.setOnAction((e) -> {
                
            });
            control.getChildren().addAll(b);
        }
    }

    private void setDirectorio(TreeItem<String> folder, String raiz, String subRaiz) {
        String a = raiz + "\\" + subRaiz;
        String subDirectorio[] = Archivos.contenido(a);
        if (subDirectorio != null) {
            for (int i = 0; i < subDirectorio.length; i++) {
                if (subDirectorio[i].matches(".*\\..*")) {
                    TreeItem<String> txt = new TreeItem<>(subDirectorio[i], icono("txt.png", 20, 20));
                    folder.getChildren().add(txt);
                    System.out.println("Matches archivo");
                } else {
                    TreeItem<String> fold = new TreeItem<>(subDirectorio[i], icono("folder.png", 20, 20));
                    folder.getChildren().add(fold);
                    String nuevaRaiz = a + "";
                    setDirectorio(fold, a, subDirectorio[i]);
                    System.out.println("Matches carpeta");
                }
            }
        }
    }

    public ImageView icono(String imagen, double width, double height) {
        Image imageFolder = new Image(getClass().getResourceAsStream(imagen));
        ImageView iF = new ImageView(imageFolder);
        iF.setFitWidth(width);
        iF.setFitHeight(height);
        return iF;
    }

}
