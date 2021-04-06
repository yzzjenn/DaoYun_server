package com.yzz.security.dao;

import com.yzz.system.dao.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserDto implements UserDetails {

    private final UserDto user;

    private final List<Long> dataScopes;

    @JsonIgnore
    private final List<GrantedAuthority> authorities;

    public JwtUserDto(UserDto user, List<Long> dataScopes, List<GrantedAuthority> authorities) {
        this.user = user;
        this.dataScopes = dataScopes;
        this.authorities = authorities;
    }

    public Set<String> getRoles() {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public UserDto getUser() {
        return user;
    }

    public List<Long> getDataScopes() {
        return dataScopes;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
