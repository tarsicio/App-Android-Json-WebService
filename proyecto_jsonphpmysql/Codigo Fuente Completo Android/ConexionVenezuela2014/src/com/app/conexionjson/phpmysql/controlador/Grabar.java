package com.app.conexionjson.phpmysql.controlador;
/*
 * Aplicación realizada por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
 * Se utilizó la Versión 3.8 de Ecipse para el diseño y programación
 * Se corrio bajo Sistema Operativo Linux Debian 7.0, En Windows debe correr sin problema
 * Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.
 */
import com.app.conexionjson.phpmysql.controlador.R;
import com.app.conexionjson.phpmysql.modelo.DatabaseSqlite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Grabar extends Activity {

private Button bt,atras;
private EditText edit,edit2;
ProgressDialog pDialog;
private String IP;
private String url;

 
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_enviar);
 //Relacionamos con XML como ya sabemos
 bt = (Button)findViewById(R.id.bntConfiguracion);
 atras = (Button)findViewById(R.id.button2);
 edit = (EditText)findViewById(R.id.editText1);//clave alfanumérica
 edit2 = (EditText)findViewById(R.id.editText2);//Nombre
 //Añadimos el Listener
 if (!redDisponible()){
	 Toast.makeText(getBaseContext(),"No hay Conexión a Internet...", Toast.LENGTH_SHORT).show();
	    this.finish();
	}
	
 atras.setOnClickListener(new View.OnClickListener(){		      
     public void onClick(View view) {
          finish();   	 
     }
 });
 
 
 bt.setOnClickListener(new View.OnClickListener(){		      
     public void onClick(View view) {
     //Hay que hacerlo dentro del Thread
     //No me dejaba tocar la Clase de Network 
     //directamente en el hilo principal
    	 if (edit.getText().length() != 0 && edit2.getText().length() != 0){
    		 EnviarDatos enviardatos = new EnviarDatos(); 
    		 enviardatos.execute(edit.getText().toString(),edit2.getText().toString());    		 
    	 }else{//fin del if
    		 Toast.makeText(getBaseContext(),"No pueden quedar Campos Vacios.... ", Toast.LENGTH_SHORT).show();
    	 }
    		 }
    		 });	          	      
}
 
/* Se chequea la red Inalámbrica del teléfono y la red de datos 
 * de la operadora del teléfono.
 * Esta función no te dice si tiene acceso a internet solo si
 * cuentas con acceso a datos.
 */
public boolean redDisponible() {
	boolean red = false;
	Context context = getApplicationContext();
	ConnectivityManager connectMgr = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	if (connectMgr != null) {
		NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
		if (netInfo != null) {
			for (NetworkInfo net : netInfo) {
				if (net.getState() == NetworkInfo.State.CONNECTED) {
					red = true;
				}
			}
		}
	} 		
	return red;
}	

/*
 * Esta clase (EnviarDatos) puede estar como un archivo aparte y colocarlo en el paquete
 * de com.app.conexionjson.phpmysql.modelo, si se desea. para darle mejor
 * lectura al código, la dejo como investigación, nada complicado y más ordenado el código. 
 */
private class EnviarDatos extends AsyncTask<String, String, String>{
	
	protected void onPreExecute() {
     	//para el progress dialog
         pDialog = new ProgressDialog(Grabar.this);
         pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         pDialog.setTitle("Enviando Datos");
         pDialog.setMessage("Por favor Espere....");
         pDialog.setIndeterminate(false);
         pDialog.setCancelable(true);
         pDialog.show();
     }
	
	 @Override
	  protected String doInBackground(String... params){
		 String error = "error";		      		    
		    DatabaseSqlite Database = new DatabaseSqlite(Grabar.this,"DbPrueba",null,1);
		    Cursor fila = Database.retornar_configuracion(Database);
		  	if (fila != null){
		  		fila.moveToFirst();			
		  		IP = fila.getString(1);			
		  	}
		    Database.close();
		    //Utilizamos la clase Httpclient para conectar.
		    HttpClient httpclient = new DefaultHttpClient();
		    url = "http://" + IP + "/prueba/datos.class.php?tipo=add";
		    //A la url donde se encuentre nuestro archivo receptor.
 		    //Utilizamos la HttpPost conectarnos con nuestro Web Service.
		    HttpPost httppost = new HttpPost(url);    
		    try {         
		    //Añadimos los datos a enviar en este caso solo uno
		    //que le llamamos de nombre 'a'
		    //La segunda linea podría repetirse tantas veces como queramos
		    //siempre cambiando el nombre ('a')
		    List<NameValuePair> postValues = new ArrayList<NameValuePair>(2);
		    //params[0] contiene Clave AlfaNumnérica
		    //params[1] contiene el nombre
		    postValues.add(new BasicNameValuePair("a", params[0])); 
		    postValues.add(new BasicNameValuePair("b", params[1]));
		    //Encapsulamos
		    httppost.setEntity(new UrlEncodedFormEntity(postValues));          
		    //Lanzamos la petición con la url + los datos
		    HttpResponse respuesta = httpclient.execute(httppost);
		    if (respuesta != null){		    	
		    	error ="NO";// realizó el trabajo de incluir sin problema	
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
	 
@Override
protected void onPostExecute(String result) {
	if (result == "NO"){//Si NO hubo error se va por esta vía		  
	   	  pDialog.dismiss();//ocultamos progess dialog.
	   	  edit.setText("");
		  edit2.setText("");
		  dato_guardado();//Le enviamos un mensaje al usuario de que todo salio bien.
	 }else{// Si hubo un error le indicamos al usuario
		 pDialog.dismiss();//ocultamos progess dialog.
		 err_conexion();
		 edit.setText("");
   		 edit2.setText("");
		 }        
  } 
    
}// Fin de AsyncTask *************************************************************************
 
public void err_conexion(){
 	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(1000);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Servidor No Encontrado, Verifique Conexión....", Toast.LENGTH_SHORT);
	    toast1.show();    	
 }

public void dato_guardado(){
 	Vibrator vibrator =(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	    vibrator.vibrate(1000);
	    Toast toast1 = Toast.makeText(getApplicationContext(),"Registro Guardado con Éxito en INTERNET....", Toast.LENGTH_SHORT);
	    toast1.show();    	
 }
}