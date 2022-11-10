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
		String expectedTrace = "Se van a sumar " + number1 + " y " + number2;
		int expectedSum = number1 + number2;

		// when
		int result = this.sut.sum(number1, number2);

		// then
		Assertions.assertTrue(capture.getOut()
				.contains(expectedTrace));
		Assertions.assertEquals(expectedSum, result);
	}

}