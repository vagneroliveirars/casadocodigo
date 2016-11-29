package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

/**
 * This class is a DAO for {@link Product}
 * 
 * @author vagner
 * 
 */
@Repository
public class ProductDAO {

	@PersistenceContext
	private EntityManager manager;

	public void save(Product product) {
		manager.persist(product);
	}

	/**
	 * Lists all the products
	 * 
	 * @return
	 */
	public List<Product> list() {
		return manager.createQuery(
				"select distinct(p) from Product p join fetch p.prices",
				Product.class).getResultList();
	}

	/**
	 * Finds a especific product by id
	 * 
	 * @param id
	 * @return {@link Product}
	 */
	public Product find(Integer id) {
		TypedQuery<Product> query = manager
				.createQuery(
						"select distinct(p) from Product p join fetch p.prices where p.id=:id",
						Product.class).setParameter("id", id);

		return query.getSingleResult();
	}

	/**
	 * Sum prices per book type
	 * 
	 * @param bookType
	 * @return sum result
	 */
	public BigDecimal sumPricesPerType(BookType bookType) {
		TypedQuery<BigDecimal> query = manager
				.createQuery(
						"select sum(price.value) from Product p join p.prices price where price.bookType =:bookType",
						BigDecimal.class);
		query.setParameter("bookType", bookType);
		return query.getSingleResult();
	}

}
