package com.github.hasoo.ircs.core.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentPrice {
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int seq;

  @JsonIgnore
  @Transient
  private int accountId;

  @JsonIgnore
  private String type;

  private double price;

  //
  // @ManyToOne
  // @JoinColumn(name = "account_id")
  // private Account account;
}
