package com.github.hasoo.ircs.core.entity;

import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Data
@RedisHash("user")
public class User implements UserDetails {
  private static final long serialVersionUID = 1L;

  @Id
  private String username;
  private String password;
  private List<GrantedAuthority> grantedAuthorities;
  private boolean accountNonExpired;
  private boolean accountNonLocked;
  private boolean credentialsNonExpired;
  private boolean enabled;

  public User(String username, String password, String[] authorities, boolean accountNonExpired,
      boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
    this.username = username;
    this.password = password;
    this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
    this.accountNonExpired = accountNonExpired;
    this.accountNonLocked = accountNonLocked;
    this.credentialsNonExpired = credentialsNonExpired;
    this.enabled = enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return grantedAuthorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
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
}
