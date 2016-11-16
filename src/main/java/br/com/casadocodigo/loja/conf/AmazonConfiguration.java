package br.com.casadocodigo.loja.conf;

import org.springframework.context.annotation.Bean;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;

/**
 * This class set ups S3 Ninja (S3 Amazon emulator)
 * 
 * @author vagner
 *
 */
public class AmazonConfiguration {
	
	private static final String ACCESS_KEY = "AKIAIOSFODNN7EXAMPLE";
	private static final String SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY";
	
	@Bean
	public AmazonS3Client amazonS3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		
		AmazonS3Client amazonS3Client = new AmazonS3Client(credentials, new ClientConfiguration());
		amazonS3Client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		amazonS3Client.setEndpoint("http://localhost:9444/s3");
		
		return amazonS3Client;
	}

}
