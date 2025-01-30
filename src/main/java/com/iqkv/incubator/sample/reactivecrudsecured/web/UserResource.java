/*
 * Copyright 2025 IQKV Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqkv.incubator.sample.reactivecrudsecured.web;

import java.util.List;

import com.iqkv.incubator.sample.reactivecrudsecured.dto.UserDepartmentDTO;
import com.iqkv.incubator.sample.reactivecrudsecured.entity.User;
import com.iqkv.incubator.sample.reactivecrudsecured.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
record UserResource(UserService userService) {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(@ApiResponse(code = 201, message = "Created", response = User.class))
  Mono<User> create(@RequestBody final User user) {
    return userService.createUser(user);
  }

  @GetMapping
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = User.class, responseContainer = "List"))
  Flux<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{userId}")
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = User.class))
  Mono<ResponseEntity<User>> getUserById(@PathVariable final Integer userId) {
    Mono<User> user = userService.findById(userId);
    return user.map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{userId}")
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = User.class))
  Mono<ResponseEntity<User>> updateUserById(@PathVariable final Integer userId, @RequestBody final User user) {
    return userService.updateUser(userId, user)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.badRequest().build());
  }

  @DeleteMapping("/{userId}")
  Mono<ResponseEntity<Void>> deleteUserById(@PathVariable final Integer userId) {
    return userService.deleteUser(userId)
        .map(r -> ResponseEntity.ok().<Void>build())
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/age/{age}")
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = User.class, responseContainer = "List"))
  Flux<User> getUsersByAge(@PathVariable final int age) {
    return userService.findUsersByAge(age);
  }

  @PostMapping("/search/id")
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = User.class, responseContainer = "List"))
  public Flux<User> fetchUsersByIds(@RequestBody final List<Integer> ids) {
    return userService.fetchUsers(ids);
  }

  @GetMapping("/{userId}/department")
  @ApiResponses(@ApiResponse(code = 200, message = "Ok", response = UserDepartmentDTO.class))
  Mono<UserDepartmentDTO> fetchUserAndDepartment(@PathVariable final Integer userId) {
    return userService.fetchUserAndDepartment(userId);
  }

}
