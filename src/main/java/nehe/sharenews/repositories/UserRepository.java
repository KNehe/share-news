package nehe.sharenews.repositories;

import nehe.sharenews.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository  extends PagingAndSortingRepository<User,Long> {

    User findByEmail(String email);

    User findByResetPasswordToken(String token);
}
