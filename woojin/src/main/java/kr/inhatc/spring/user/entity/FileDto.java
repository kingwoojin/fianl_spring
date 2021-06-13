package kr.inhatc.spring.user.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //db객체, 테이블을 만들수 있게 해줌
@Table(name = "Files") //테이블 이름 설정
@NoArgsConstructor  //디폴트 생성자
@AllArgsConstructor //전체 컬럼 생성자
@Data //get, set  지우면 @Getter, @ToString, @Builder 만들어야함
@Builder
public class FileDto {
	
	@Id //기본키
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;
	
	private String userid;
	
	private String originalFileName;
	
	private String storedFilePath;
	
	private long fileSize;
}
