package kr.inhatc.spring.boardjpa.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="T_BOARD")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Boards {
   
   @Id
   @Column(name="BOARD_IDX")
   // Auto시퀀스 방식
   @GeneratedValue(strategy = GenerationType.AUTO)
   private int idx;
   
   private String title;
   private String contents;
   private int hitCnt;
   private String createId;
   // date 처리
   @Temporal(TemporalType.TIMESTAMP)
   @Column(insertable = false, updatable = false, columnDefinition = "date default sysdate")
   private Date createDate;
   
   @Column(insertable = false, updatable = false, columnDefinition = "varchar(10) default 'N'")
   private String deleteYn;

   public void hitCnt(int hitCnt2) {
	// TODO Auto-generated method stub
	 System.out.println("--------------------> : " + hitCnt2);
   }
   
   // 파일 보여주기 위해 파일리스트 만들어줌
   //private List<FileDto> fileList;
}