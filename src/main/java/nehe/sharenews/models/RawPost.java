package nehe.sharenews.models;


public class RawPost {

    String description;

    String imageUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public RawPost() {
    }

    public RawPost(String description, String imageUrl) {
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    
 

    
    
}
