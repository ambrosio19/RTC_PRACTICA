package net.atos.practica.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserService implements UserDetailsService {

	@Autowired
	private UserDaoImpl userDaoImpl;
	
	@Override
	public CustomUser loadUserByUsername(String username)
			throws UsernameNotFoundException {
		return userDaoImpl.cargarColaboradorPorDas(username);
	}

}
