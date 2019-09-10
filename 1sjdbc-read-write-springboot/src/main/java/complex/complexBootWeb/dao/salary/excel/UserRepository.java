package complex.complexBootWeb.dao.salary.excel;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import complex.complexBootWeb.model.po.User;

@Mapper
public interface UserRepository {
	
	Long addUser(User user);
	
	List<User> list();
	
}
