package net.atos.practica.springsecurity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserService userService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		CustomUser user = userService.loadUserByUsername(username);
		
		if (user.isEnabled()==false){
			throw new AccountExpiredException("Usuario dado de baja");
		}
		
		if (user == null || !user.getUsername().equalsIgnoreCase(username)){
			throw new BadCredentialsException("Usuario no encontrado");
		}
		
		if(!password.equals(user.getPassword())){
			throw new BadCredentialsException("Password incorrecto");
		}
		
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
		 
	}

	@Override
	public boolean supports(Class<?>arg0) {
		return true;
	}

}
