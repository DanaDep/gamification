package com.dep.gamification.repositories;

import com.dep.gamification.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User getUserByUserId(String userId);

    @Query("{ 'firstName' : ?0 , 'lastName' : ?1}")
    public User getUserByFirstNameAndLastName(String firstName, String lastName);

    @Query(value="{}", fields = "{'firstName': 1, 'lastName': 1}")
    List<String> getAllBeneficiariesByCompleteName();
}
