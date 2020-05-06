package nehe.sharenews;

import nehe.sharenews.controllers.PostController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class ShareNewsApplication {

	public static void main(String[] args) {
		new File(PostController.uploadDirectory).mkdir();
		SpringApplication.run(ShareNewsApplication.class, args);
	}

}
