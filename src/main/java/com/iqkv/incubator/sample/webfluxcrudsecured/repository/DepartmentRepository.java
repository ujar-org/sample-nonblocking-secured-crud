package com.iqkv.incubator.sample.webfluxcrudsecured.repository;

import com.iqkv.incubator.sample.webfluxcrudsecured.entity.Department;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DepartmentRepository extends ReactiveCrudRepository<Department, Integer> {
  Mono<Department> findByUserId(Integer userId);
}
