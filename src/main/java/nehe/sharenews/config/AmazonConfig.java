package nehe.sharenews.config;

import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	
	public AmazonS3 s3() {
		
		AWSCredentials credentials = new BasicAWSCredentials("AKIAI3CQII24EJHH3V2A", "tWqyqBD/7T4vyq+SPW31yT5XWCw7qIb1JLri+3RC");
		
		return AmazonS3ClientBuilder
				.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
	}
}
