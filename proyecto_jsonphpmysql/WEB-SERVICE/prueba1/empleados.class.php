<?php
//Realizado por Tarsicio Carrizales telecom.com.ve@gmail.com Estado Lara Venezuela
//Este Servicio web fue creado con:
//PHP-PEAR Versión 5.4.4-14 
//Mysql Versión 5.5.35 
//Apache2 Versión 2.2.22-13
//Esta obra está bajo una licencia de Creative Commons Reconocimiento 4.0 Internacional.

include_once('database.class.php');

class empleados{

//Función  que permite traer todos los empleados en la tabla
//datos ordenados por Nombre de la A  ala Z.
private function getEmpleados(){
	$sql = "SELECT * FROM datos ORDER BY nombre ASC";
	$db = new database();
	return $db->ejecutarConsulta($sql);
	}

//Función que permite Incluir un nuevo Emplado.
private function setEmpleado($nombre,$numero){
	$sql = "INSERT INTO datos (nombre,numero) values('".$nombre."','".$numero."')";
	$db = new database();
	return $db->incluirRegistro($sql);
	}

public function setEmpleadoSQL($nombre,$numero){ 
        return $this->setEmpleado($nombre,$numero);
	}

public function getJSONEmpleados(){
	$json = array(); 	
	$result = $this->getEmpleados();		
	if(pg_num_rows($result)){
		while($row=pg_fetch_assoc($result)){
		$json['datos'][]=$row;	
		}
	}
        return $json; 
    }
}
?>
