package nehe.sharenews.models;


import javax.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO)
    private Long id;
    private String image;
    private String description;

    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;

    public Post() {
    }

    public Post(String image, String description, User user) {
        this.image = image;
        this.description = description;
        this.user = user;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
