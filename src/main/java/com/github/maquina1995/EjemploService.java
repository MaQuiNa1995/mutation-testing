package com.github.maquina1995;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EjemploService {

	/**
	 * Tenemos un método que suma 2 números, ademas llama a un método privado que
	 * añadde trazas al log
	 * 
	 * @param number1 numero en formato int
	 * @param number2 numero en formato int
	 * 
	 * @return int con el resultado de la suma de los números pasados por parámetro
	 */
	public int sum(int number1, int number2) {

		loguearMensaje(number1, number2);

		return number1 + number2;
	}

	private void loguearMensaje(int number1, int number2) {
		log.info("Se van a sumar {} y {}", number1, number2);
	}

}
