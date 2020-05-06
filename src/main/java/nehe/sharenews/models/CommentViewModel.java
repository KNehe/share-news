package nehe.sharenews.models;

public class CommentViewModel {

    private Long id;
    private String text;
    private String commentedBy; //user who commented
    private boolean canDelete;
    private String icon;

    public CommentViewModel() {
    }

    public CommentViewModel(Long id, String text, String commentedBy, boolean canDelete) {
        this.id = id;
        this.text = text;
        this.commentedBy = commentedBy;
        this.canDelete = canDelete;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
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

}
