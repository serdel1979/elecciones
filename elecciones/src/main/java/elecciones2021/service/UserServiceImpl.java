package elecciones2021.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import elecciones2021.model.User;
import elecciones2021.repository.UserRepository;



@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = true)
	public User findByUsernameOrEmail(String usernameOrEmail) {
		User user = null;
		try {
			user = userRepository.findByUsername(usernameOrEmail);
		} catch (Exception e) {
			throw e;
		}
		return user;
	}
	
}
