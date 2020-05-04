package nehe.sharenews.models;

public class CommentViewModel {

    private Long id;
    private String text;
    private String name; //user who commented
    private Post post;

    public CommentViewModel() {
    }

    public CommentViewModel(Long id, String text, String name, Post post) {
        this.id = id;
        this.text = text;
        this.name = name;
        this.post = post;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
