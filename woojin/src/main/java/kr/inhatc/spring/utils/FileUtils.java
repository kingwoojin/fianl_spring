package kr.inhatc.spring.utils;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.FileDto;

@Component
public class FileUtils {
	
	public List<FileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest){
		
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		List<FileDto> fileList = new ArrayList<>();
		
		// 파일이 업로드될 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdir();
		}
		
		//새 파일 이름
		Iterator<String> iter = multipartHttpServletRequest.getFileNames();
		
		String originalFileExtension = null;
		while(iter.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());
			for (MultipartFile multipartFile : list) {
				if(multipartFile.isEmpty() == false) {
					
					String contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					} else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					FileDto boardFile = new FileDto();
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					try {
						//파일을 실제로 만들어내는 기능
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
