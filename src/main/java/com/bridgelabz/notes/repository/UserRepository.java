package com.bridgelabz.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	// Using custom query
	@Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
	UserModel findByEmailAddress(String email);
}
