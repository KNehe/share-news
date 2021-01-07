package nehe.sharenews.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class UploadService {
	
    private Cloudinary cloudinary;
    
    @Autowired
    public UploadService(Cloudinary cloudinary) {
    	this.cloudinary = cloudinary;
    }
    
    
    public String uploadImageWithCloudinary(  byte[] file) throws Exception {
    	
    	try {
    	 var uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap());
    	 
    	 return uploadResult.get("secure_url").toString();
    	 
    	}catch(Exception e) {
    		throw new Exception(e);
    	}
    	
    }


}
