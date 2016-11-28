package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.User;

/**
 * DAO class for {@link User}
 * 
 * @author vagner
 *
 */
@Repository
public class UserDAO implements UserDetailsService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		String jpql = "select u from User u where u.login = :login";
		
		User user = entityManager.createQuery(jpql, User.class)
				.setParameter("login", username).getSingleResult();
		
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + " doesn't exist");
		}

		return user;
	}

}
