package elecciones2021.service;

import elecciones2021.model.User;

public interface UserService {

	 User findByUsernameOrEmail(String usernameOrEmail);
	 
}
