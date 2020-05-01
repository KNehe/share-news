package nehe.sharenews.models;


import javax.persistence.*;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO)
    private Long id;
    private String title;
    private String image;
    private String description;

    @ManyToOne
    @JoinColumn( name = "user_id")
    private User user;

    public Post() {
    }

    public Post(String title, String image, String description, User user) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
