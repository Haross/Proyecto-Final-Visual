package proyectofinal.Block;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializadora {
	private  ObjectInputStream deserializador;
	private  ObjectOutputStream serializador;

	public void  serializar(String archivo, Object ap){
		try{
			FileOutputStream fo = new FileOutputStream(archivo);
			serializador = new  ObjectOutputStream(fo);
			serializador.writeObject(ap);
		
		} catch(FileNotFoundException e){
			 e.printStackTrace();
		} catch(IOException e){
			 e.printStackTrace();
		}
	}


	public Object deserializar(String archivo){
		Object vuelta = null;
		try{	
			FileInputStream fi = new FileInputStream(archivo);
			deserializador = new ObjectInputStream(fi); 
			vuelta = deserializador.readObject();

		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){ 
			e.printStackTrace();
		}
		
		return vuelta;
	}
}