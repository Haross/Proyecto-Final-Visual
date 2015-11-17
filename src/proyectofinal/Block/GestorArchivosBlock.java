/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.Block;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Javier
 */
public class GestorArchivosBlock {
    private final FileChooser fileDialog;
    private File file;
    private File myPath;
    
    public GestorArchivosBlock(){
        this.fileDialog = new FileChooser();
        this.myPath = null;
        this.file = null;
    }
    public GestorArchivosBlock(String PATH ){
        this.fileDialog = new FileChooser();
        this.myPath = new File(PATH);
        this.file = null;
    }
    
    public boolean checkDirectory(){
        if (this.myPath.exists()){
            System.out.println("El directorio existe");
            return true;
        }else{
            System.out.println("Fichero no existe");
            try{
                this.myPath.mkdir();
            }catch(Exception e){
                System.out.println("El fichero no pudo ser creado");
                return false;
            }
        }
        return false;
    }
    
    public String openFile(){
        
        fileDialog.setTitle("Apertura");
        fileDialog.setInitialDirectory(this.myPath);
        
        file = fileDialog.showOpenDialog(new Stage());
        
        if(file == null)
            return "";
        else{
            try{
                String texto = "";
                List<String> ls = Files.readAllLines(this.file.toPath());
                for(int i = 0; i<ls.size();i++){
                    texto+= ls.get(i)+ "\n";
                }
                return texto;
            }catch(IOException e){
                e.printStackTrace();
                return "";           
            }
        }
    }
    
    public void saveFileAs(String texto){
        fileDialog.setTitle("Guardar");
        fileDialog.setInitialDirectory(this.myPath);
        
        file = fileDialog.showSaveDialog(new Stage());
        
        if(file == null)
            return;
        try{
            
            Files.write(file.toPath(), texto.getBytes());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
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
    

}

    