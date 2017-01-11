/**
 * 
 */
package net.atos.practica.springsecurity;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;


public class CustomUser implements UserDetails {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;

	private List<Role> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public List<Role> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<Role> authorities) {
		this.authorities = authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
		
}
