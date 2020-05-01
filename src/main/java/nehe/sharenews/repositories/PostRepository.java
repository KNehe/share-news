package nehe.sharenews.repositories;

import nehe.sharenews.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
}
