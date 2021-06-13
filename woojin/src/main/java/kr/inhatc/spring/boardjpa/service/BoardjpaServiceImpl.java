package kr.inhatc.spring.boardjpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.inhatc.spring.boardjpa.entity.Boards;
import kr.inhatc.spring.boardjpa.repository.BoardjpaRepository;

@Service
public class BoardjpaServiceImpl implements BoardjpaService{

	@Autowired
	BoardjpaRepository boardjpaRepository;
	
	@Override
	public List<Boards> boardjpaList() {
		List<Boards> list = boardjpaRepository.findAllByOrderByIdxDesc();
		System.out.println("===============> 크기 : "+ list.size());
		return list;
	}

	@Override
	public void saveBoardjpa(Boards board) {
		boardjpaRepository.save(board);
		
	}
	
	@Override
	public void saveBoardjpa2(Boards board) {
		
		boardjpaRepository.save(board);
		
	}
	
	@Override
	public void savecntup(Boards board) {
		
		boardjpaRepository.save(board);
		
	}

	@Override
	public Boards boardjpaDetail(Integer idx) {
		Optional<Boards> optional = boardjpaRepository.findById(idx);
		Optional<Boards> optional1 = boardjpaRepository.findById(idx);
		if(optional.isPresent()) {
			
			Boards board = optional.get();
			return board;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void boardjpaDelete(Integer idx) {
		boardjpaRepository.deleteById(idx);
		
	}

}
