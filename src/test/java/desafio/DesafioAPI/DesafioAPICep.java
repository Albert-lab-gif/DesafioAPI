package desafio.DesafioAPI;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;

/**
 * Você deverá utilizar a seguinte API para realizer o desafio :
 * 
 * https://viacep.com.br/ws/CEP/json
 * 
 * ➢ Você deverá validar o retorno dos cenários abaixo:
 * 
 * ➢ Cenário 1 : Cep Válido ➢ Cenário 2: Cep Inválido
 * 
 * OBS: O CEP DEVERÁ SER PASSADO NA URL DA REQUISIÇÃO
 */
public class DesafioAPICep {

	private String url = "https://viacep.com.br/ws/";
	private String cepValido = "53401074";
	private String cepComTraco = "53401-074";
	private String cepInvalido = "5340107";
	private String cepNaoEncontrado = "00000000";
	private String cepInvalidoComLetra = "0123AB00";
	private String cepVazio = "";

	/**
	 * Teste passando um CEP válido e validando o retorno OK - Status 200
	 */
	@Test
	public void cepValido() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK);
	}

	/**
	 * Teste passando um CEP válido com traço e validando retorno OK - Status 200
	 */
	@Test
	public void cepComTraco() {
		given().when().get(url + cepComTraco + "/json").then().statusCode(HttpStatus.SC_OK);
	}

	/**
	 * Validando retorno para o valor Logradouro no json de acordo com o CEP válido
	 * passado como parâmetro na URL
	 */
	@Test
	public void validarLogradouro() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK).body("logradouro",
				Is.is("Via Local D"));
	}

	/**
	 * Validando retorno para o valor Bairro no json de acordo com o CEP válido
	 * passado como parâmetro na URL
	 */
	@Test
	public void validarBairro() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK).body("bairro",
				Is.is("Centro"));
	}

	/**
	 * Validando retorno para o valor Localidade no json de acordo com o CEP válido
	 * passado como parâmetro na URL
	 */
	@Test
	public void validarLocalidade() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK).body("localidade",
				Is.is("Paulista"));
	}

	/**
	 * Validando retorno para o valor UF no json de acordo com o CEP válido passado
	 * como parâmetro na URL
	 */
	@Test
	public void validarUf() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK).body("uf", Is.is("PE"));
	}

	/**
	 * Validando retorno para o valor DDD no json de acordo com o CEP válido passado
	 * como parâmetro na URL
	 */
	@Test
	public void validarDdd() {
		given().when().get(url + cepValido + "/json").then().statusCode(HttpStatus.SC_OK).body("ddd", Is.is("81"));
	}

	/**
	 * Teste passando um CEP inválido e validando o retorno BAD REQUEST - Status 400
	 */
	@Test
	public void cepInvalido() {
		given().when().get(url + cepInvalido + "/json").then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	/**
	 * Teste passando um CEP incorreto (com menos de 8 números) e validando o
	 * retorno da mensagem erro true
	 */
	@Test
	public void cepNaoEncontrado() {
		given().when().get(url + cepNaoEncontrado + "/json").then().assertThat().body("erro", Is.is(true));
	}

	/**
	 * Teste passando um CEP inválido (contendo letras no meio) e validando o
	 * retorno BAD REQUEST - Status 400
	 */
	@Test
	public void cepInvalidoComLetra() {
		given().when().get(url + cepInvalidoComLetra + "/json").then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	/**
	 * Teste passando um CEP vazio e validando o retorno BAD REQUEST - Status 400
	 */
	@Test
	public void cepVazio() {
		given().when().get(url + cepVazio + "/json").then().statusCode(HttpStatus.SC_BAD_REQUEST);
	}
}
