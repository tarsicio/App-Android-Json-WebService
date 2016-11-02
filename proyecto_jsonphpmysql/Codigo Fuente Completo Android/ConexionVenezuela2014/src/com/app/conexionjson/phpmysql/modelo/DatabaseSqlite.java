/* Esta Clase le permite interactuar con las peticiones del controlador que le solicite información
 * o en su caso le solicite realizar alguna tarea en especifico.
 * Esta clase es realizada por Tarsicio Carrizales telecom.com.ve@gmail.com */

package com.app.conexionjson.phpmysql.modelo;
/*
 * Aplicación realizada por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
 * Se utilizó la Versión 3.8 de Ecipse para el diseño y programación
 * Se corrio bajo Sistema Operativo Linux Debian 7.0, En Windows debe correr sin problema
 * Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;


public class DatabaseSqlite extends SQLiteOpenHelper {
		
	public DatabaseSqlite(Context context, String nombre, CursorFactory factory,int version) {
			super(context, nombre, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase DB) {		
		        // tabla de Configuración para comunicación con el Servidor Principal en la WEB
		DB.execSQL("CREATE  TABLE configuracion (id TEXT PRIMARY KEY  NOT NULL  UNIQUE , servidor TEXT NOT NULL )");				
	}

	@Override
	public void onUpgrade(SQLiteDatabase DB, int VersionAnte, int VersionNueva) {			
		DB.execSQL("drop table if exists configuracion");				
		        // tabla de Configuración para comunicación con el Servidor Principal en la WEB
    	DB.execSQL("CREATE  TABLE configuracion (id TEXT PRIMARY KEY  NOT NULL  UNIQUE , servidor TEXT NOT NULL )");					
	}	
			
	public boolean tabla_vacia_Configuracion(DatabaseSqlite Database){
		try{
			SQLiteDatabase DB = Database.getReadableDatabase();
			Cursor fila = DB.rawQuery("SELECT * FROM configuracion", null);	        
			if (!fila.moveToFirst()) {			
			return true;
			} else{				
				return false;	
			}
		}
		catch(Exception e){
			return false;
		}		
	}
	
/* 
 * ********************************************************************
 * Consultar Incluir y Modificar la tabla Configuración 
 * ********************************************************************
 * */	

	/* 
	 * **************
	 * Consultar
	 * **************
	 * */
public boolean configuracion_vacia(DatabaseSqlite Database) {
	   try{
		   SQLiteDatabase bd = Database.getReadableDatabase();	
		   Cursor fila = bd.rawQuery("SELECT * FROM configuracion WHERE id = '1'", null);
		if (fila.moveToFirst()) {
		   return false;
		}else{			
		   return true;	
		     }
		}
		catch(Exception e){
			return true;
				}
		}

/* 
 * *******************
 * Retornar Consultar
 * *******************
 * */
public Cursor retornar_configuracion(DatabaseSqlite Database) {
   try{
	   SQLiteDatabase bd = Database.getReadableDatabase();	
	   Cursor fila = bd.rawQuery("SELECT * FROM configuracion WHERE id = '1'", null);
	if (fila.moveToFirst()) {
	   return fila;
	}else{			
	   return null;	
	     }
	}
	catch(Exception e){
		return null;
			}
	}

/* 
 * **************
 * incluir
 * **************
 * */
public boolean incluir_configuracion(DatabaseSqlite Database,EditText servidor) {
   try{	   
	   SQLiteDatabase bd = Database.getWritableDatabase();	
	   bd.execSQL("INSERT INTO configuracion (id,servidor)" +
				  " VALUES ('1','" + servidor.getText().toString() + "')");
	   return true;	
	}
	catch(Exception e){
		return false;
			}
	}

/** 
 * INCLUIR Pasando STRING*/

public boolean incluir_configuracion(DatabaseSqlite Database,String servidor) {
try{
SQLiteDatabase bd = Database.getWritableDatabase();	
bd.execSQL("INSERT INTO configuracion (id,servidor) VALUES ('1','" + servidor + "')");
return true;	
}
catch(Exception e){
return false;
}
}


/* 
* **************
* Actualizar
* **************
* */
public boolean actualizar_configuracion(DatabaseSqlite Database,EditText servidor) {
  try{
	   SQLiteDatabase bd = Database.getWritableDatabase();	
	   bd.execSQL("UPDATE configuracion " + 
	   			  "SET servidor='" +  servidor.getText().toString() + "' WHERE id = '1'");
	   return true;	
	}
	catch(Exception e){
		return false;
			}
	}

}
