package nehe.sharenews.repositories;

import nehe.sharenews.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository  extends PagingAndSortingRepository<User,Long> {

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    @Query(value="SELECT first_name from user WHERE email=?1", nativeQuery = true)
    String getFirstNameByEmail(String email);

    @Query(value="SELECT first_name from user WHERE id =?1", nativeQuery = true)
    String findFirstName(Long id);


    @Query(value="SELECT last_name from user WHERE id =?1", nativeQuery = true)
    String findLastName(Long id);

    @Query(value="SELECT last_name from user WHERE email =?1", nativeQuery = true)
    String getLastNameByEmail(String email);
}
