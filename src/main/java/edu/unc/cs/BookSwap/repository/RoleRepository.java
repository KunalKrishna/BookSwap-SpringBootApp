package edu.unc.cs.BookSwap.repository;


import edu.unc.cs.BookSwap.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}