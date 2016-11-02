<?php
//Realizado por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
//Este Servicio web fue creado con:
//PHP-PEAR Versión 5.4.4-14 
//Mysql Versión 5.5.35 
//Apache2 Versión 2.2.22-13
//Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.

class datos_conexion{
 public static function getServidor() {
  return '127.0.0.1';
 }

 public static function getDataBase(){
  return  'prueba'; 
 }
  
 public static function getUsuario_Conexion(){
  return 'postgres';
 } 

 public static function getClave_DataBase(){
  return 'f123456';
 }
}
?>



