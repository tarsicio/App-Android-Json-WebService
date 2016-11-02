<?php
//Realizado por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
//Este Servicio web fue creado con:
//PHP-PEAR Versión 5.4.4-14 
//Mysql Versión 5.5.35 
//Apache2 Versión 2.2.22-13
//Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.

include_once('datos_conexion.class.php');

class database{

// Consultar Registro
 public function ejecutarConsulta($sql){
  $con = pg_connect(datos_conexion::getServidor(), datos_conexion::getUsuario_Conexion(), datos_conexion::getClave_DataBase());
  //Aquí colocamos a formato UTF8, para que se puedan ver las palabras con acentos
  //si comentas la línea mysql_set_charset('utf8',$con); veras que cuando cargues el ListView en Android no presenta nada 
  //por que lo muestra como null.
  pg_set_client_encoding($con,'utf8');
  if (!$con)
    {
     die('No se pudo Conectar al Servidor: ' . pg_result_error());
    }
    else{
	  pg_select(datos_conexion::getDataBase(), $con);
	  $result = pg_query($sql);
	  pg_close($con);
	  return $result;
    }
 }

// Insertar Registros
public function incluirRegistro($sql){
 $con = pg_connect(datos_conexion::getServidor(), datos_conexion::getUsuario_Conexion(), datos_conexion::getClave_DataBase());
 if (!$con)
    {
    die('No se pudo Conectar Al Servidor: ' . pg_result_error());
    }
    else{
	  pg_select(datos_conexion::getDataBase(), $con);
	  pg_query($sql); 
	  pg_close($con);	  
          return true;
    }
 }
}
?>
