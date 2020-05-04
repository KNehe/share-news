package nehe.sharenews.models;

public class PostViewModel {

    private Long postId;
    private String image;
    private String description;
    private int noOfComments;
    private String postByName;

    public PostViewModel() {
    }

    public PostViewModel(Long postId, String image, String description, int noOfComments, String postByName) {
        this.postId = postId;
        this.image = image;
        this.description = description;
        this.noOfComments = noOfComments;
        this.postByName = postByName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostByName() {
        return postByName;
    }

    public void setPostByName(String postByName) {
        this.postByName = postByName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNoOfComments() {
        return noOfComments;
    }

    public void setNoOfComments(int noOfComments) {
        this.noOfComments = noOfComments;
    }
}
