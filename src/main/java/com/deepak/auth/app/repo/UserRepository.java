package com.deepak.auth.app.repo;

import com.deepak.auth.app.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUserName(String username);

    Optional<User> findByEmailId(String email);

}
