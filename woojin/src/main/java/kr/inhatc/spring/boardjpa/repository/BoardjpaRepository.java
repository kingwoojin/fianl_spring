package kr.inhatc.spring.boardjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.inhatc.spring.boardjpa.entity.Boards;
import kr.inhatc.spring.user.entity.Users;

@Repository
public interface BoardjpaRepository extends JpaRepository<Boards, Integer>{

	List<Boards> findAllByOrderByIdxDesc();
	
//	@Modifying
//    @Query("update Board p set p.view = p.view + 1 where p.id = :id")
//    int updateView(Long id);
}

