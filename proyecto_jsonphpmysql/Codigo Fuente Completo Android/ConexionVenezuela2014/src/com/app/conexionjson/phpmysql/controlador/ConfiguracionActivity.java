package com.app.conexionjson.phpmysql.controlador;
/*
 * Aplicación realizada por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
 * Se utilizó la Versión 3.8 de Ecipse para el diseño y programación
 * Se corrio bajo Sistema Operativo Linux Debian 7.0, En Windows debe correr sin problema
 * Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.
 */
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.app.conexionjson.phpmysql.controlador.R;
import com.app.conexionjson.phpmysql.modelo.DatabaseSqlite;

public class ConfiguracionActivity extends Activity {

	
	EditText IP_Servidor;	
    ImageButton Menuprincipal,btn_guardar;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		

		IP_Servidor =       (EditText) findViewById(R.id.et_servidor);
		btn_guardar =       (ImageButton) findViewById(R.id.btn_guardar);		
		this.Menuprincipal     = (ImageButton) findViewById(R.id.BtnMenu);
		
		//Cuando le de Click en el Boton Menú Principal
		Menuprincipal.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view){
		    	 finish();
		    	 //Intent conf = new Intent(ConfiguracionActivity.this, PrincipalActivity.class);					
			     //startActivity(conf);
		     }
		}); // Fin del Boton Menú Principal;
		
		DatabaseSqlite Database = new DatabaseSqlite(ConfiguracionActivity.this,"DbPrueba",null,1);
		Cursor fila = Database.retornar_configuracion(Database);
		if (fila != null){
			fila.moveToFirst();			
			IP_Servidor.setText(fila.getString(1));			
		}
		//fila.close();
		Database.close();
		
		//Metodo onClick del Boton Guardar Configuración
		btn_guardar.setOnClickListener(new View.OnClickListener(){		      
        	public void onClick(View view){
        		if (IP_Servidor.getText().toString().length() == 0){
        			mensajeAlerta();	
        		}
        		else{
        			DatabaseSqlite Database = new DatabaseSqlite(ConfiguracionActivity.this,"DbPrueba",null,1);
        			if (Database.configuracion_vacia(Database)){
        				Database.incluir_configuracion(Database,IP_Servidor);
        				mensajeIncluir();
        			}
        			else{
        				Database.actualizar_configuracion(Database,IP_Servidor);
        				mensajeActualizado();
        				}
        			Database.close();
        			}        			        		
        	}//onClick
        	});// Fin del Metodo setOnClickListener del Boton Guardar Configuración
					
	}
	
	 @Override protected void onStart() {
		   super.onStart();
		   //Toast.makeText(this, "onStart Cliente", Toast.LENGTH_SHORT).show();
		}

		@Override protected void onResume() {
		   super.onResume();
		  // Toast.makeText(this, "onResume Cliente", Toast.LENGTH_SHORT).show();
		}

		@Override protected void onPause() {		   
		   super.onPause();		   
		   //Toast.makeText(this, "onPause Cliente", Toast.LENGTH_SHORT).show();
		}

		@Override protected void onStop() {
		   super.onStop();
		   //Toast.makeText(this, "onStop Cliente", Toast.LENGTH_SHORT).show();
		 }

		@Override protected void onRestart() {
		   super.onRestart();
		   //Toast.makeText(this, "onRestart Cliente", Toast.LENGTH_SHORT).show();
		}

		@Override protected void onDestroy() {		   
		   super.onDestroy();		   
		   //Toast.makeText(this, "onDestroy Cliente", Toast.LENGTH_SHORT).show();
		}


		//vibra y muestra un Toast
	public void mensajeIncluir(){
			Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		    vibrator.vibrate(400);
		    Toast toast1 = Toast.makeText(getApplicationContext(),"Información: Configuración Guardada", Toast.LENGTH_SHORT);
		    toast1.show();    	
	}// Fin Class mensajeIncluir
	
	//vibra y muestra un Toast
	public void mensajeError(){
			Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		    vibrator.vibrate(400);
		    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: No se pudo acceder a los datos correctamente", Toast.LENGTH_SHORT);
		    toast1.show();    	
	}// Fin Class mensajeError
	
	//vibra y muestra un Toast
		public void mensajeActualizado(){
				Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			    vibrator.vibrate(400);
			    Toast toast1 = Toast.makeText(getApplicationContext(),"Información: Configuración Actualizada", Toast.LENGTH_SHORT);
			    toast1.show();    	
		}// Fin Class mensajeActualizado
		
		//vibra y muestra un Toast
				public void mensajeAlerta(){
						Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					    vibrator.vibrate(400);
					    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: No pueden quedar campos Vacios", Toast.LENGTH_SHORT);
					    toast1.show();    	
				}// Fin Class mensajeActualizado
				
				
}
