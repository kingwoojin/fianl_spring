package kr.inhatc.spring.user.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.user.entity.Users;

public interface UserService {

	List<Users> userList();

	void saveUsers(Users user);

	Users userDetail(String id);

	void userDelete(String id);

	void saveUsers1(Users user, MultipartHttpServletRequest multipartHttpServletRequest);

}
