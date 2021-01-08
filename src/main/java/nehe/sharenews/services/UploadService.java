package nehe.sharenews.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class UploadService {
	
    private Cloudinary cloudinary;
    
    @Autowired
    public UploadService(Cloudinary cloudinary) {
    	this.cloudinary = cloudinary;
    }
    
    
    public String uploadImageWithCloudinary(MultipartFile file) throws Exception {
    	
    	try {
    	 var uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap());
    	 
    	 return uploadResult.get("secure_url").toString();
    	 
    	}catch(Exception e) {
    		throw new Exception(e);
    	}
    	
    }


}
