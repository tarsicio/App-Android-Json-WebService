<?php header('Content-Type: text/html; charset=UTF-8');
//Realizado por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
//Este Servicio web fue creado con:
//PHP-PEAR Versión 5.4.4-14 
//Mysql Versión 5.5.35 
//Apache2 Versión 2.2.22-13
//Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.

include_once('empleados.class.php');
$empleados = new empleados();

$dato = $_GET['tipo'];
if ($dato == "add"){
   $parametro1 = $_REQUEST['a'];
   $parametro2 = $_REQUEST['b'];
   $add = $empleados->setEmpleadoSQL($parametro1,$parametro2);
   echo ("Parametros registrados -->" .$add);
}else{
   //La Siguiente Línea pasa los datos a Formato JSON, y son enviados al Dispositivo Móvil, para 
   //Luego ser Mostrado en el ListView en Android.
   echo json_encode($empleados->getJSONEmpleados());
}
?>
