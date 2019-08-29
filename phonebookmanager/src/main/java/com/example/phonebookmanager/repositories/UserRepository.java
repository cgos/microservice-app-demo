package com.example.phonebookmanager.repositories;

import com.example.phonebookmanager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
