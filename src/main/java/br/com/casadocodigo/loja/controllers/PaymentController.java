package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

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
	 * Performs an asynchronous integration with the external payment system
	 * 
	 * @return redirection URL
	 */
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public Callable<ModelAndView> checkout() {
		return new Callable<ModelAndView>() {
			@Override
			public ModelAndView call() throws Exception {
				BigDecimal total = shoppingCart.getTotal();
				String uriToPay = "http://book-payment.herokuapp.com/payment";
				
				try {
					String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
					return new ModelAndView("redirect:/payment/success");
				} catch (HttpClientErrorException e) {
					return new ModelAndView("redirect:/payment/error");
				}
			}
		};
	}

}