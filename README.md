# Mutation testing

## Preparación del pom (dependencias)

y un plugin a nuestro pom:

(Se han omitido comentarios aclaratorios si quieres ver que hace cada línea de código <a href="https://github.com/MaQuiNa1995/mutation-testing/blob/master/pom.xml">pincha aqui</a> )
```
<plugin>
	<groupId>org.pitest</groupId>
	<artifactId>pitest-maven</artifactId>
	<version>${pitest.plugin.version}</version>
	<dependencies>
		<dependency>
			<groupId>org.pitest</groupId>
			<artifactId>pitest-junit5-plugin</artifactId>
			<version>${pitest.junit5.plugin.version}</version>
		</dependency>
	</dependencies>
	<configuration>
		<targetClasses>
			<param>com.github.maquina1995.*</param>
		</targetClasses>
	</configuration>
</plugin>
```

## Ejecutando por linea de comandos

`mvn test-compile org.pitest:pitest-maven:mutationCoverage`

## Analizando los resultados

Cuando ejecutemos el test unitario con este código:


Código a probar:

```
public int sum(int number1, int number2) {

	loguearMensaje(number1, number2);

	return number1 + number2;
}

private void loguearMensaje(int number1, int number2) {
	log.info("Se van a sumar {} y {}", number1, number2);
}
```

Test unitario:

```
class EjemploServiceTest {

	private EjemploService sut;

	@BeforeEach
	public void setup() {
		this.sut = new EjemploService();
	}

	@Test
	void sumTest() {
		// given
		int number1 = 5;
		int number2 = 5;

		// when
		int result = this.sut.sum(number1, number2);

		// then
		Assertions.assertEquals(number1 + number2, result);
	}
}
```

Podemos fijarnos en el informe generado:

### Consola
```
- Statistics
================================================================================
>> Line Coverage: 6/6 (100%)
>> Generated 3 mutations Killed 2 (67%)
>> Mutations with no coverage 0. Test strength 67%
>> Ran 3 tests (1 tests per mutation)
Enhanced functionality available at https://www.arcmutate.com/
[INFO] ------------------------------------------------------------------------
```

### Html

ruta `\target\pit-reports\index.html`

![alt text](https://github.com/MaQuiNa1995/mutation-testing/blob/master/imagenesReadme/coverageMutation.png?raw=true)

Si nos fijamos tendremos:

- Lineas cubiertas 11/11 -> 100%
- Mutaciones generadas 3 -> Fallidas 1
- Resistencia a la mutación 67%
- Número de test generados

cuando un informe de coverage normal nos daría un 100% ya que pasamos por todas las líneas de código

Como podemos ver en la imagen o viendo el archivo `target\jacoco.exec` que genera el proyecto cuando se ejecuta `mvn test`

![alt text](https://github.com/MaQuiNa1995/mutation-testing/blob/master/imagenesReadme/coverage.png?raw=true)

## Subiendo el coverage

En nuestro anterior test no se estaba tomando en cuenta la traza de log por lo tanto si esta llamada desaparece
el test debería fallar, es decir necesitaríamos hacer un assert de esa traza para subir el porcentaje de nuestro test de mutación

Esto no se limita solo a trazas de test sino a cualquier mutación que se pueda llevar a cabo:
- Eliminación de métodos llamados
- Si el parámetro es una lista, pasarle ArrayList, LinkedHashList etc

La filosofía del test de mutación es que deberían de fallar los test unitarios si se modifica (muta) el código que se está probando

Es decir nuestro test de mutación pasa verde cuando el test unitario cuando le mutamos (con lo anteriormente mencionado) falla

El test quedaría así (Se han omitido comentarios aclaratorios si quieres ver que hace cada línea de código <a href="https://github.com/MaQuiNa1995/mutation-testing/blob/master/src/test/java/com/github/maquina1995/EjemploServiceTest.java">pincha aqui</a>)

```
@TestPropertySource(properties = "logging.level.root=INFO")
class EjemploServiceTest {

	private EjemploService sut;

	@BeforeEach
	public void setup() {
		this.sut = new EjemploService();
	}

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
		Assertions.assertTrue(capture.getOut().contains(expectedTrace));
		Assertions.assertEquals(expectedSum, result);
	}
}
```

De esta manera ahora el informe quedaría asi

### Consola

```
- Statistics
================================================================================
>> Line Coverage: 6/6 (100%)
>> Generated 3 mutations Killed 3 (100%)
>> Mutations with no coverage 0. Test strength 100%
>> Ran 3 tests (1 tests per mutation)
Enhanced functionality available at https://www.arcmutate.com/
[INFO] ------------------------------------------------------------------------
```

### Html

![alt text](https://github.com/MaQuiNa1995/mutation-testing/blob/master/imagenesReadme/coverageMutation2.png?raw=true)

De esta manera obtenemos el 100% 
















