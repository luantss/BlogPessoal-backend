package com.generation.blogpessoal.model;

public class UserLogin {

	private Long id;
	
	private String nome;

	private String usuarioLogin;

	private String senha;

	private String foto;

	private String token;

	public UserLogin(Long id, String nome, String usuarioLogin, String senha, String foto, String token) {
		this.id = id;
		this.nome = nome;
		this.usuarioLogin = usuarioLogin;
		this.senha = senha;
		this.foto = foto;
		this.token = token;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
