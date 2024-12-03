package edu.unc.cs.BookSwap.repository;

import edu.unc.cs.BookSwap.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
