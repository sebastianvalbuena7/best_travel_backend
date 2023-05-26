package com.sebastian.bestTravel.domain.repositories.mongo;

import com.sebastian.bestTravel.domain.entities.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUserDocument, String> {
    Optional<AppUserDocument> findByUsername(String username);
}