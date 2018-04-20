package org.openntf.todo.todo.mongo.repository;

import org.openntf.todo.todo.mongo.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StoreRepository extends MongoRepository<Store, String> {
}
