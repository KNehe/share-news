package nehe.sharenews.models;

public class CommentRequest {
    Long postId;
    String text;


   public CommentRequest(){}

   public CommentRequest(Long postId,String text){
       this.postId = postId;
       this.text = text;
   }

   public Long getPostId(){
       return postId;
   }

   public String getText() {
       return text;
   }


    
}
