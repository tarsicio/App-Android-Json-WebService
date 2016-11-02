package com.app.conexionjson.phpmysql.controlador;
/*
 * Aplicación realizada por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
 * Se utilizó la Versión 3.8 de Ecipse para el diseño y programación
 * Se corrio bajo Sistema Operativo Linux Debian 7.0, En Windows debe correr sin problema
 * Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.
 */
import com.app.conexionjson.phpmysql.controlador.R;
import com.app.conexionjson.phpmysql.modelo.DatabaseSqlite;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Inicio extends Activity {

	Button Recuperar,Enviar,salir,configuracion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		Recuperar = (Button) findViewById(R.id.button2014);
		Enviar = (Button) findViewById(R.id.button2);
		salir = (Button) findViewById(R.id.salir);
		configuracion = (Button) findViewById(R.id.bntConfiguracion);
				
		DatabaseSqlite Database = new DatabaseSqlite(Inicio.this,"DbPrueba",null,1);
		String IP = "10.0.0.10";
		if(Database.incluir_configuracion(Database,IP)){
           mensajeIncluir();	
          }
		Database.close();
		salir.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view) {
		          finish();   	 
		     }
		 });
		
		Recuperar.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view){
		    	 Intent recuperarTabla = new Intent(Inicio.this, Buscar.class);
			        startActivity(recuperarTabla);
		     }
		}); 
		
		
		Enviar.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view){
		    	 Intent recuperarTabla = new Intent(Inicio.this, Grabar.class);
			        startActivity(recuperarTabla);
		     }
		});
		
		configuracion.setOnClickListener(new View.OnClickListener(){		      
		     public void onClick(View view){
		    	 Intent configurar = new Intent(Inicio.this, ConfiguracionActivity.class);
			        startActivity(configurar);
		     }
		});
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}

	public void mensajeIncluir(){
		Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(400);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"NOTA: Configuración Inicial Realizada con Éxito", Toast.LENGTH_SHORT);
		    toast1.show();    	
	}
	
	public void mensajeNoIncluido(){
		Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(400);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Configuración Inicial No Realizada", Toast.LENGTH_SHORT);
		    toast1.show();    	
	}
}
