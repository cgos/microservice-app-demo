package com.example.passwordmanager.repositories;

import com.example.passwordmanager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
