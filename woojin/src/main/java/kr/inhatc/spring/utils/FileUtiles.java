package kr.inhatc.spring.utils;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.user.entity.FileDto;
import kr.inhatc.spring.user.repository.FileRepository;

@Component
public class FileUtiles {
	
	@Autowired
	FileRepository fileRepository;
	
	public List<FileDto> parseFileInfo(String id, MultipartHttpServletRequest multipartHttpServletRequest){
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		List<FileDto> fileList = new ArrayList<FileDto>();
		
		
		
		//업로드한 파일 저장 경로
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "src/main/resources/static/images/" + current.format(format);
		String dbpath = "/images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) { //저장할 폴더 없으면 생성함
			file.mkdir();
		}
		
		Iterator<String> iter = multipartHttpServletRequest.getFileNames();
		
		// 원래 확장자
		String originalFileExtension = null;
		
		//값 존재할때까지
		while(iter.hasNext()) {
			//리스트로
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());
			
			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					//타입
					String contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					//이름중복 없이 파일이름생성
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					FileDto userFile = new FileDto(); //파일을 저장하기 위해
					 //db 저장정보
					userFile.setUserid(id);
					userFile.setFileSize(multipartFile.getSize());
					userFile.setOriginalFileName(multipartFile.getOriginalFilename());
					userFile.setStoredFilePath(dbpath + "/" + newFileName);
					fileRepository.save(userFile);
					
					file = new File(path + "/" + newFileName);
					try {
						//실제 파일 생성 부분
						multipartFile.transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return fileList;
	}
}
