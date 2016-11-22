package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.ShoppingCart;

/**
 * This class is a controller for payments that performs integration with an
 * external payment system
 * 
 * @author vagner
 * 
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Performs integration with the external payment system
	 * 
	 * @return redirection URL
	 */
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public String checkout() {
		BigDecimal total = shoppingCart.getTotal();
		
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		
		try {
			String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
			System.out.println(response);
			return "redirect:/payment/success";
		} catch (HttpClientErrorException e) {
			return "redirect:/payment/error";
		}
	}

}
