package com.iqkv.incubator.sample.reactivecrudsecured.repository;

import com.iqkv.incubator.sample.reactivecrudsecured.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
  @Query("SELECT * FROM users WHERE age >= $1")
  Flux<User> findByAge(int age);
}
