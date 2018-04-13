package com.angelorobson.mailinglist.security.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class JwtAuthenticationDto {

	private String email;
	private String senha;

	public JwtAuthenticationDto() {
	}

	@NotEmpty(message = "Email can not be empty.")
	@Email(message = "Invalid email.")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@NotEmpty(message = "Password can not be empty.")
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "JwtAuthenticationRequestDto [email=" + email + ", senha=" + senha + "]";
	}

}
