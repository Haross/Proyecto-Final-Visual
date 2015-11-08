
package proyectofinal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ERIDE21
 */
//ea_2305@hotmai.com
public class GestorArchivos {
    //Variable que establece la ruta donde se guardarán los archivos
    private final String directorio = "..\\datos";  //Se pone \\ porque es el comodin de \
    private File fDirectorio = new File(directorio);  //fDirectorio = folder directorio

    public GestorArchivos(){           
        if(!fDirectorio.exists()){
            try{
                fDirectorio.mkdir();
            }catch(Exception e){
                System.out.println("El fichero raiz no pudo ser creado");
            }
        }
        
    }
    
    public GestorArchivos(String ID){       
        if(!fDirectorio.exists()){
            fDirectorio.mkdir(); 
            System.out.println("directorio creado");
        }
        checkDirectorio(directorio+"\\"+ID);
    }
    
    public boolean setUsuario(String ID){
        return checkDirectorio(directorio+"\\"+ID);
    }
    public String getDirectorio(){
        return fDirectorio.getPath();
    }
    
    private boolean checkDirectorio(String PATH){
        fDirectorio = new File(PATH);
        if(fDirectorio.exists())
            return true;
        else{
            try{
                fDirectorio.mkdir();
                System.out.println("carpeta de usuario creada");
                return true;
            }catch(SecurityException e){
                return false;
            }
        }
    }
    
    public String[] contenido(String PATH){
        File f = new File(PATH);
        String[] ficheros = f.list();
        for(int i = 0; i<ficheros.length;i++)
            System.out.println(ficheros[i]);
        return ficheros;
    }
    
    

}
