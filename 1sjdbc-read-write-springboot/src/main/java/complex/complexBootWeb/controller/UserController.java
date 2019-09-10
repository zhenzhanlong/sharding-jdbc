package complex.complexBootWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import complex.complexBootWeb.model.po.User;
import complex.complexBootWeb.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public Object list() {
		return userService.list();
	}
	
	@GetMapping("/add")
	public Object add() {
		User user = new User();
		user.setId(System.currentTimeMillis());
		user.setCity("深圳");
		user.setName("李四");
		return userService.add(user);
	}
	
}
