package com.iqkv.incubator.sample.webfluxcrudsecured.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(User.TABLE)
public class User {

  protected static final String TABLE = "users";

  @Id
  private Integer id;

  private String name;

  private int age;

  private double salary;

  @JsonIgnore
  private String password;

  private UserRole role;

  @Override
  public String toString() {
    return "User{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", age=" + age +
           ", salary=" + salary +
           ", role=" + role +
           '}';
  }
}
