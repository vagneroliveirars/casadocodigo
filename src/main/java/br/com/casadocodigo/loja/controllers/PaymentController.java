package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.loja.models.User;

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
	
	@Autowired
	private MailSender mailer;

	/**
	 * Performs an asynchronous integration with the external payment system
	 * 
	 * @return redirection URL
	 */
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public Callable<ModelAndView> checkout(@AuthenticationPrincipal final User user) {
		return new Callable<ModelAndView>() {
			@Override
			public ModelAndView call() throws Exception {
				BigDecimal total = shoppingCart.getTotal();
				String uriToPay = "http://book-payment.herokuapp.com/payment";

				try {
					restTemplate.postForObject(uriToPay,
							new PaymentData(total), String.class);
					//sendNewPurchaseMail(user);
					return new ModelAndView("redirect:/payment/success");
				} catch (HttpClientErrorException e) {
					return new ModelAndView("redirect:/payment/error");
				}
			}
		};
	}

	private void sendNewPurchaseMail(User user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("compras@casadocodigo.com.br");
		email.setTo(user.getLogin());
		email.setSubject("Nova compra");
		email.setText("corpo do email");
		mailer.send(email);
	}

}
