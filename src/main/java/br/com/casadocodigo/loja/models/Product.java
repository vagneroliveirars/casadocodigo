package br.com.casadocodigo.loja.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * This class represents a product
 * 
 * @author vagner
 *
 */
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	private String title;
	
	@Lob
	@NotBlank
	private String description;
	
	@Min(30)
	private int pages;
	
	@ElementCollection
	private List<Price> prices = new ArrayList<Price>();
	
	@DateTimeFormat(iso = ISO.DATE)
	private Calendar releaseDate;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public List<Price> getPrices() {
		return prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Calendar getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Calendar releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Id: " + id + ", Title: " + title + ", Description: " + description + ", Pages: " + pages;
	}

}
