package edu.unc.cs.BookSwap.repository;

import edu.unc.cs.BookSwap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    @Modifying
//    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
//    int updateUserEmail(@Param("id") Long id, @Param("email") String email);

    //TODO
//    @Modifying
//    @Query("INSERT User u SET u.email = :email WHERE u.id = :id")
//    void addUser(User user);

}
