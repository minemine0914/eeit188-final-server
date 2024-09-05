package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.UserCollection;
import com.ispan.eeit188_final.model.composite.UserCollectionId;

public interface UserCollectionRepository extends JpaRepository<UserCollection, UserCollectionId> {

}
