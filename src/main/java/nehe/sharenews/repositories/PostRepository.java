package nehe.sharenews.repositories;

import nehe.sharenews.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    @Modifying
    @Query(value="DELETE FROM post WHERE user_id =?1", nativeQuery =true)
    void deletePostByUserId(Long id);
}
