package complex.complexBootWeb.service;

import java.util.List;

import complex.complexBootWeb.model.po.User;

public interface UserService {

	List<User> list();
	
	Long add(User user);
	
}
