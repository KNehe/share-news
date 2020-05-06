package nehe.sharenews.repositories;

import nehe.sharenews.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment,Long> {

    @Query(value="SELECT COUNT(*) FROM comment WHERE post_id = ?1", nativeQuery =true)
    int countById(Long id);

    @Query(value="SELECT * FROM comment WHERE post_id = ?1", nativeQuery =true)
    List<Comment> getCommentsByPostId(Long id);

    @Modifying
    @Query(value="DELETE FROM comment WHERE post_id = ?1", nativeQuery =true)
    void deleteCommentByPostId(Long id);

    @Modifying
    @Query(value="DELETE FROM comment WHERE id =?1", nativeQuery =true)
    void deleteCommentByPostIdAndCommentId(Long commentId);

    @Modifying
    @Query(value="DELETE FROM comment WHERE user_id =?1", nativeQuery =true)
    void deleteCommentByUserId(Long userId);
}
