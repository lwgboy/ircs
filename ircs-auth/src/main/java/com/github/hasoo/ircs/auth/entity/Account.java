package com.github.hasoo.ircs.auth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Entity
@Data
public class Account implements UserDetails {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  private String username;
  private String password;

  private String groupname;
  private String firstName;
  private String lastName;
  private String email;

  @ElementCollection(fetch = FetchType.EAGER)
  private List<String> roles;

  private boolean accountNonExpired, accountNonLocked, credentialsNonExpired, enabled;

  // @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  @MapKey(name = "type")
  private Map<String, ContentPrice> contentPrices;

  private double fee;

  public Account() {
    this.accountNonExpired = true;
    this.accountNonLocked = true;
    this.credentialsNonExpired = true;
    this.enabled = true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void grantAuthority(String authority) {
    if (roles == null)
      roles = new ArrayList<>();
    roles.add(authority);
  }

  @Override
  public List<GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
    return authorities;
  }
}
