package com.iqkv.incubator.sample.webfluxcrudsecured.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(Department.TABLE)
public class Department {

  protected static final String TABLE = "department";

  @Id
  private Integer id;

  private String name;

  @Column("user_id")
  private Integer userId;

  private String location;
}
