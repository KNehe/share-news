package nehe.sharenews.models;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO)
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
    }

    public Comment( String text, User user,Post post) {
        this.text = text;
        this.user = user;
        this.post =post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return text;
    }

    public void setComment(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
