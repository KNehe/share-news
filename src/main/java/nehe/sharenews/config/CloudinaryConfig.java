package nehe.sharenews.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {
	
	@Value("${coudinary.api.key}")
	private String API_KEY;
	
	@Value("${coudinary.api.secret}")
	private String API_SECRET;
	
	@Value("${coudinary.folder}")
	private String FOLDER;
	
	@Value("${coudinary.cloud.name}")
	private String COUD_NAME;
	
	@Bean
	public Cloudinary cloudinary() {
		
		var cloudinaryParams = ObjectUtils.asMap(
				"cloud_name", COUD_NAME,
				"api_key",API_KEY ,
				"api_secret", API_SECRET,
				"folder", FOLDER
				);
		
		Cloudinary cloudinary = new Cloudinary(cloudinaryParams);

		return cloudinary;
	}

}
