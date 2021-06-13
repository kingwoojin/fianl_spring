package kr.inhatc.spring.user.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity //db객체, 테이블을 만들수 있게 해줌
@Table(name = "users") //테이블 이름 설정
@NoArgsConstructor  //디폴트 생성자
@AllArgsConstructor //전체 컬럼 생성자
@Data //get, set  지우면 @Getter, @ToString, @Builder 만들어야함
@Builder
public class Users {

	@Id //기본키
	@Column(name = "USER_ID") // 컬럼명 변경, @Column의 속성 변경
	private String id;
	private String pw;
	
	//@Column(length = 20)
	private String name;
	private String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
	private Date joinDate;
	private String enabled;
	private String role;
	
	//파일 관리를 위한 리스트 추가

}
