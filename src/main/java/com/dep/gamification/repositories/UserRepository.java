package com.dep.gamification.repositories;

import com.dep.gamification.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User getUserByUserId(String userId);
}
