package com.app.conexionjson.phpmysql.controlador;
/*
 * Aplicación realizada por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
 * Se utilizó la Versión 3.8 de Ecipse para el diseño y programación
 * Se corrio bajo Sistema Operativo Linux Debian 7.0, En Windows debe correr sin problema
 * Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.
 */
import com.app.conexionjson.phpmysql.controlador.R;
import com.app.conexionjson.phpmysql.modelo.DatabaseSqlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
 
public class Buscar extends Activity {
 private String jsonResult;
 private ListView listView;
 private ProgressDialog pDialog;
 private Button atras;
 private String IP;
 
 private String url;
  
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_traer);
  listView = (ListView) findViewById(R.id.listView1);
  atras = (Button)findViewById(R.id.atras);
  
 if (!redDisponible()){
	Toast.makeText(getBaseContext(),"No hay Conexión a Internet... ", Toast.LENGTH_SHORT).show();
	this.finish();
	//onPause();
	}
 
  atras.setOnClickListener(new View.OnClickListener(){		      
	     public void onClick(View view) {
	         finish();   	 
	     }
	 });
  /*
   * Aqui instanciamos a Database, para que nos traiga cual es la URL o la IP
   * que tenemos configurda para nuestro acceso al servicio WEB.
   * una Vez tenemos la IP o URL armamos la url completa que en definitiva
   * es la que vamos a pasar a la clase AsyncTask
   */
  DatabaseSqlite Database = new DatabaseSqlite(Buscar.this,"DbPrueba",null,1);
  Cursor fila = Database.retornar_configuracion(Database);
  
	if (fila != null){
		fila.moveToFirst();			
		IP = fila.getString(1);			
	}
  Database.close();
  
  /*
   * Esta url, ustedes la pueden armar como quieran luego de la variable IP
   * es decir esta parte "/prueba/datos.class.php?tipo=mostrar", la pueden
   * modificar a su antojo, como mejor quieran, la colocamos así, para utilizar una
   * misma página para mostrar e incluir los datos. 
   */
  url = "http://" + IP + "/prueba/datos.class.php?tipo=mostrar";
  
  try{
	  BuscarDatosJSON datosJson = new BuscarDatosJSON();	  
	  datosJson.execute(url);	  
} catch(CancellationException e) {
      Toast.makeText(this, "Error al conectar con servidor",Toast.LENGTH_LONG).show();
} catch(Exception e) {
      Toast.makeText(this, "Error con tarea asíncrona",Toast.LENGTH_LONG).show();
}
 
 } 
 @Override 
 protected void onStart() {
	   super.onStart();
	   //Toast.makeText(this, "onStart logo", Toast.LENGTH_SHORT).show();
	}

@Override
protected void onResume() {
	   super.onResume();
	   //Toast.makeText(this, "onResume logo", Toast.LENGTH_SHORT).show();
	}

@Override
protected void onPause() {		   
	   super.onPause();
	   //finish();
	   //Toast.makeText(this, "onPause logo", Toast.LENGTH_SHORT).show();
	}

@Override
protected void onStop() {
	   super.onStop();
	   //Toast.makeText(this, "onStop logo", Toast.LENGTH_SHORT).show();		   
	 }

@Override
protected void onRestart() {
	   super.onRestart();
	   //Toast.makeText(this, "onRestart logo", Toast.LENGTH_SHORT).show();
	}

@Override
protected void onDestroy() {		   
	   super.onDestroy();		   
	   //Toast.makeText(this, "onDestroy logo", Toast.LENGTH_SHORT).show();
	}	
 
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
 }
 
 /* 
  * Se chequea la red Inalámbrica del teléfono y la red de datos 
  * de la operadora del teléfono.
  */
public boolean redDisponible() {
	boolean red = false;
	Context context = getApplicationContext();
	ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	if (connectMgr != null) {
		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
		if (netInfo != null) {
			for (NetworkInfo net : netInfo) {
				//Si esta conectado a Internet y la Conexión es WIFI, le permite realizar la tarea.				
				if (net.getState() == NetworkInfo.State.CONNECTED) {
					red = true;
				}
			}
		}
	} 		
		return red;
	}	
	
 // Async Task to access the web
/*****************************************************************************************/	
 private class BuscarDatosJSON extends AsyncTask<String, String, String> {
	 

	protected void onPreExecute() {
     	//para el progress dialog
         pDialog = new ProgressDialog(Buscar.this);
         pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         pDialog.setTitle("Cargando Datos");
         pDialog.setMessage("Por favor Espere....");
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
     }
	 
  @Override
  protected String doInBackground(String... params) {
	  /* 
	   * Como ven el Metodo doInBackground debe retornar un String
	   * en este caso vamos a moner que retorne si lo hizo bien o no
	   * para ello creamos, String error = "error"; por defecto
	   * si lo hizo bien le monemos error ="NO"; para que no se aborte el programa
	   * y el Usuario de la aplicación tenga información de lo que ocurre.
	   */
   String error = "error";	  
   HttpClient clientes = new DefaultHttpClient();
   HttpGet traer = new HttpGet(params[0]);//Aquí va la URL en params[0], en la posicion 0
   try {
	   /*
	    * Guardamos en respuesta, el json_encode() que nos da el servicio WEB PHP
	    * Si respuesta es != de null es por que trae el objeto json con datos
	    * y utilizamos el metodo inputStreamToString, para descomponerlo y poder
	    * mostrarlo en el ListView.
	    */
    HttpResponse respuesta = clientes.execute(traer);
    if (respuesta != null){
    	jsonResult = inputStreamToString(respuesta.getEntity().getContent()).toString();
    	error ="NO";	
    }else{
    	// Retorna el Error de no Conexion.
    }
    	
   }catch (ClientProtocolException e) {
	   Log.e ("Error conectando http " + e, "Servidor no encontrado"); //e.printStackTrace();
   } catch (IOException e) {
	   Log.e ("Error conectando http " + e,"Servidor no encontrado"); //e.printStackTrace();
   }catch (Exception e){
	   publishProgress();
	   Log.e ("Error al Sincronizar " + e,"Servidor no encontrado"); //e.printStackTrace();
   }

   return error;
  }
 // este metodo no hace nada aquí, pero se puede utilizar para otras tareas.
  protected void onProgressUpdate(String... progress) {
	  Toast.makeText(getApplicationContext(),
		      "Error... Servidor no Encontrado", Toast.LENGTH_LONG).show();
  }
  
  /*
   * Este Metodo ordena lo que tiene "is" colocandolo en "pregunta", para ello
   * crea un BufferedReader "rd" y lee linea a linea y la pasa la variable rline
   * para luego pasarla a "pregunta" y una vez terminado retorna lo que tenga "pregunta"
   */
  private StringBuilder inputStreamToString(InputStream is) {
   String rLine = "";
   StringBuilder pregunta = new StringBuilder();
   BufferedReader rd = new BufferedReader(new InputStreamReader(is));
 
   try {
    while ((rLine = rd.readLine()) != null) {
     pregunta.append(rLine);
    }
   }
 
   catch (IOException e) {
    // e.printStackTrace();
    Toast.makeText(getApplicationContext(),
      "Error..." + e.toString(), Toast.LENGTH_LONG).show();
   }
   return pregunta;
  }
 
  @Override
  protected void onPostExecute(String result) {
	 if (result == "NO"){
		 ListadoCompleto();
   	  pDialog.dismiss();//ocultamos progess dialog. 
	 }else{
		 pDialog.dismiss();//ocultamos progess dialog.
		 err_conexion();
	 }      
  }
  
  @Override
  protected void onCancelled() {
      Toast.makeText(Buscar.this, "Tarea cancelada!",
              Toast.LENGTH_SHORT).show();
  }
 }// end async task
 /********************************************************************************************/
   
//vibra y muestra un Toast
 public void err_conexion(){
 	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(200);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Sin Servidor", Toast.LENGTH_SHORT);
	    toast1.show();    	
 }
 
 // build hash set for list view
 public void ListadoCompleto() {
  List<Map<String, String>> ListaDatos = new ArrayList<Map<String, String>>();
 
  try {
   JSONObject jsonRespuesta = new JSONObject(jsonResult);
   /*
    * jsonNodoPrincipal es lo que esta registrado en PHP en la clase empleados.class.php
    * en la function getJSONEmpleados
    */
   JSONArray jsonNodoPrincipal = jsonRespuesta.optJSONArray("datos");
   /*
    * En el siguiente ciclo for vamos armando el par Nombre y la Clave
    * separada por parentesis que le ponemos y un guión para separlos a los dos
    * luego si no da ningun error, cargamos el ListView y lo mostramos el usuario.
    */
   for (int i = 0; i < jsonNodoPrincipal.length(); i++) {
    JSONObject jsonNodoHijo = jsonNodoPrincipal.getJSONObject(i);
    String name = jsonNodoHijo.optString("nombre");
    String number = jsonNodoHijo.optString("numero");
    String outPut = "(" + name +  ")"+ " - " + "(" + number + ")";
    ListaDatos.add(crearEmpleados("empleados", outPut));
   }
  } catch (JSONException e) {
   Toast.makeText(getApplicationContext(), "Error" + e.toString(),
     Toast.LENGTH_SHORT).show();
  }
  /*
   * y al final de todos le pasamos ya los datos arreglados al ListView
   * a través del SimpleAdapter, en este caso se llama. "simple_list_item_1"
   */
  SimpleAdapter simpleAdapter = new SimpleAdapter(this, ListaDatos,
    android.R.layout.simple_list_item_1,
    new String[] { "empleados" }, new int[] { android.R.id.text1 });
  listView.setAdapter(simpleAdapter);//Cargamos el ListView con toda la Data.
 }
 
 /*
  * Este Metodo HashMap permite cargar la clave y el dato.
  * en este caso le estan pasando del JSON el nombre y la clave número
  * que por cierto en la tabla esta como campo único y no se repite
  * por lo tanto no debes tenet problemas a la hora de cargar los datos.
  * 
  *  Recuerdas puedes utilizar diferentes conceptos para recuperar los datos que te
  *  envia el servicio web para luego mostrarlo al usuario en tu dispositivo android
  *  para este ejemplo utilizamos HasMap, pero tamben puedes Utilizar arreglo para
  *  luego ser cargados a tu ListView.
  *  
  *  Cual Utilizar depende de lo que desees hacer y qu tan rapido quieras cargar las cosas 
  *  en pantalla del Android.
  */
 private HashMap<String, String> crearEmpleados(String nombre, String numero) {
  HashMap<String, String> empleadoNombreNumero = new HashMap<String, String>();
  empleadoNombreNumero.put(nombre, numero);
  return empleadoNombreNumero;
 }
}