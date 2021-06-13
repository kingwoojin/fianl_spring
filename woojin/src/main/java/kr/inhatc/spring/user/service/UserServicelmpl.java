package kr.inhatc.spring.user.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sun.el.stream.Optional;

import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.user.entity.Users;
import kr.inhatc.spring.user.repository.FileRepository;
import kr.inhatc.spring.user.repository.UserRepository;
import kr.inhatc.spring.utils.FileUtiles;


@Service
public class UserServicelmpl implements UserService{

	@Autowired
	UserRepository userRespository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Autowired
	private FileUtiles fileUtiles;
	
	@Override
	public List<Users> userList() {

//		userRespository.findAllByOrderByIdDesc();
		
//		Optional<Users> result = userRespository.findById("ab");
//		if(result.isPresent()) {
//			Users user = result.get();
//			user.getId();
//		} 
		
		//findAll 다 땡겨온다.
		//userRepository.findAll()
		
		List<Users> list =  userRespository.findAllByOrderByIdDesc();
		return list;
	}
	
	@Override
	public void saveUsers(Users user) {
		userRespository.save(user);
	}

	@Override
	public Users userDetail(String id) {
		java.util.Optional<Users> optional = userRespository.findById(id);
		System.out.println("asdfasdf~~~~~~ : "+ optional );
		if(optional.isPresent()) {
			Users user = optional.get();
			return user;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void userDelete(String id) {
		userRespository.deleteById(id);
	}

	@Override
	public void saveUsers1(Users user, MultipartHttpServletRequest multipartHttpServletRequest) {
		userRespository.save(user);
		
         //파일 임시확인 
      if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//          System.out.println("여기 eHLD");
      	Iterator<String> iter = multipartHttpServletRequest.getFileNames();
//          System.out.println("읎냐? : "+ iter.hasNext());
          // 다음 내용이 있는지 확인
          while(iter.hasNext()) {
              String name = iter.next();
              System.out.println("-----------*******>" + name);
              List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
              for(MultipartFile multipartFile : list) {
                  System.out.println("==============> file name : "+ multipartFile.getOriginalFilename());
                  System.out.println("==============> file size : "+ multipartFile.getSize());
                  System.out.println("==============> file type : "+ multipartFile.getContentType());
              }
          }
      }
      // 1.파일저장
      List<kr.inhatc.spring.user.entity.FileDto> list = fileUtiles.parseFileInfo(user.getId(), multipartHttpServletRequest);
      System.out.println("nani~~~~~ : "+ list);
//      //2. db 저장
//      if(CollectionUtils.isEmpty(list) == false) {
//    	  fileRepository.saveAll(list);
//		
//      }
	}
}


