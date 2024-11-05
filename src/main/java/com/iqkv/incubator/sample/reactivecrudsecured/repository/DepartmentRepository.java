package com.iqkv.incubator.sample.reactivecrudsecured.repository;

import com.iqkv.incubator.sample.reactivecrudsecured.entity.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, Integer> {
  Mono<Department> findByUserId(Integer userId);
}
