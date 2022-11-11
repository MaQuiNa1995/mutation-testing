package com.github.maquina1995;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

/**
 * Creamos un test unitario de {@link EjemploService}
 * <p>
 * le decimos al test que cree un objeto que despues inyectaremos en nuestros
 * test para poder leer las trazas de log, en este caso nos limitaremos a las
 * trazas de info
 * 
 * @author MaQuiNa1995
 *
 */
@TestPropertySource(properties = "logging.level.root=INFO")
class EjemploServiceTest {

	private EjemploService sut;

	@BeforeEach
	public void setup() {
		this.sut = new EjemploService();
	}

	/**
	 * Inyectamos un {@link CapturedOutput} para poder leer las trazas de log
	 * 
	 * @param capture {@link CapturedOutput}
	 */
	@Test
	@ExtendWith(OutputCaptureExtension.class)
	void sumTest(CapturedOutput capture) {
		// given
		int number1 = 5;
		int number2 = 5;
		int expectedSum = number1 + number2;
		// Se monta la traza que la ejecución dejaría con los números definidos
		String expectedEndTrace = "[main] INFO com.github.maquina1995.EjemploService - Se van a sumar " + number1
				+ " y " + number2 + "\r";

		// when
		int result = this.sut.sum(number1, number2);

		// then
		String[] splitByJumpLines = capture.getOut()
				.split("\n");

		// verificamos el número de trazas
		Assertions.assertEquals(1, splitByJumpLines.length);
		/**
		 * Obtenemos la 1º traza y verificamos que termine con cierta String
		 * 
		 * Esto se hace para evitar el lidiar con la fecha y hora que dejaría la traza
		 * original
		 * 
		 * <code>01:43:13.717 [main] INFO com.github.maquina1995.EjemploService - Se van a sumar 5 y 5</code>
		 */
		Assertions.assertTrue(splitByJumpLines[0].endsWith(expectedEndTrace));
		// hacemos el assertion típico del resultado esperado
		Assertions.assertEquals(expectedSum, result);
	}

}