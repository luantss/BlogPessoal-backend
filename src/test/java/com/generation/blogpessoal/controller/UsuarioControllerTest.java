package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//Indica em qual ordem os testes serão executados
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	// Envia as requisições dos Requests para dentro da nossa aplicação
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	// Injetando o objeto da classe UsuarioService para persistir o objeto no banco de dados 
	//de Teste com a senha criptografada
	@Autowired
	private UsuarioService usuarioService;
	
	 // Injetando objeto da Interface UsuarioRepository para limpar o banco de dados de Teste
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("Cadastrar um Usuário")
	public void deveCriarUmUsuario() {
		
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(
				0L,"Paulo Antunes", "paulo@gmail.com", "12345678", "https://i.imgur.com/JR7kUFU.jpg"));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
				"/usuarios/cadastrar",HttpMethod.POST,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.CREATED,corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuarioLogin(),corpoResposta.getBody().getUsuarioLogin());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,
				"Maria da Silva", "maria@gmail.com", "12345678", "https://i.imgur.com/T12NIp9.jpg"));
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
				"Maria da Silva","maria@gmail.com","12345678", "https://i.imgur.com/T12NIp9.jpg"));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange(
				"/usuarios/cadastrar",HttpMethod.POST,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST,corpoResposta.getStatusCode());
	}
	
	@Test
	@Order(3)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {
		Optional<Usuario> usuarioCadastrado = usuarioService.atualizarUsuario(new Usuario(0L,
				"Juliana Andrews", "juliana_andrews@gmail.com", "juliana123", "https://i.imgur.com/yDRVeK7.jpg"));
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
				"Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123" , "https://i.imgur.com/yDRVeK7.jpg");
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
		ResponseEntity<Usuario> corpoResposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuarioLogin(), corpoResposta.getBody().getUsuarioLogin());
	}
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários")
	public void deveMostrarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));
		usuarioService.cadastrarUsuario(new Usuario(0L, 
			"Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("usuarios/all", HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	/*
	@Test
	@Order(5)
	@DisplayName("Listar um Usuário Específico")
	public void desveListarApenasUmUsuario() {
		Optional<Usuario> usuarioBusca = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Laura Santolia", "laura_santolia@email.com.br", "laura12345", "https://i.imgur.com/EcJG8kB.jpg"));
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/" + usuarioBusca.get().getId(), HttpMethod.GET, null, String.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@Order(6)
	@DisplayName("Login do Usuário")
	public void deveAutenticasUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Marisa Souza", "marisa_souza@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
		HttpEntity<UserLogin> corpoRequisicao = new HttpEntity<UserLogin>(new UserLogin(0L, "",
				"marisa_souza@email.com.br", "13465278", "", ""));
		ResponseEntity<UserLogin> corpoResposta = testRestTemplate
				.exchange("/usuarios/logar", HttpMethod.POST, corpoRequisicao, UserLogin.class);
		assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
	}
	*/
}
