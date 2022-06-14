package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

//Ela indica que a classe é uma classe do tipo test. Os parametros indicam que caso a porta principal
//esteja ocupada, o Spring vai atribuir uma outra porta automaticamente
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

//Indica que terá apenas um ciclo de vida e será por classe. Quando parar de executar irá remover os dados
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	//Irá apagar todos os dados 
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		usuarioRepository.save(new Usuario(0L,"João da Silva","joao@gmail.com","123456789", "https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"Manuela da Silva","manuela@gmail.com","123456789", "https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"Paulo da Silva","paulo@gmail.com","123456789", "https://i.imgur.com/FETvs2O.jpg"));
		usuarioRepository.save(new Usuario(0L,"Adriana Santos","adriana@gmail.com","123456789", "https://i.imgur.com/FETvs2O.jpg"));
	}
	
	//Indica que esse método vai executar um teste
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuarioLogin("joao@gmail.com");
		assertTrue(usuario.get().getUsuarioLogin().equals("joao@gmail.com"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Paulo da Silva"));
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
}
