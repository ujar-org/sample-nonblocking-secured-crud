/*
 * Copyright 2024 IQKV.
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

package com.iqkv.incubator.sample.reactivecrudsecured.client;

import com.iqkv.incubator.sample.reactivecrudsecured.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserClient {
  private final WebClient client = WebClient.create("http://localhost:8080");

  public Mono<User> getUser(String userId) {
    return client.get()
        .uri("/users/{userId}", userId)
        .retrieve()
        .bodyToMono(User.class).log(" User fetched : ");
  }

  public Flux<User> getAllUsers() {
    return client.get()
        .uri("/users")
        .exchange()
        .flatMapMany(clientResponse -> clientResponse.bodyToFlux(User.class))
        .log("Users Fetched : ");
  }

  public Mono<User> createUser(User user) {
    Mono<User> userMono = Mono.just(user);
    return client.post().uri("/users").contentType(MediaType.APPLICATION_JSON)
        .body(userMono, User.class)
        .retrieve()
        .bodyToMono(User.class)
        .log("Created User : ");

  }


}
