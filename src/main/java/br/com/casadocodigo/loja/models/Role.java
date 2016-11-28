package br.com.casadocodigo.loja.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class represents an user role
 * 
 * @author vagner
 *
 */
@Entity
public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = -4526712087726628713L;
	
	@Id
	private String name;

	@Override
	public String getAuthority() {
		return name;
	}

}
