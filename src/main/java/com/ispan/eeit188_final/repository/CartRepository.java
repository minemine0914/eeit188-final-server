package com.ispan.eeit188_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ispan.eeit188_final.model.Cart;
import com.ispan.eeit188_final.model.composite.CartId;

public interface CartRepository extends JpaRepository<Cart, CartId> {

}
