package com.semi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semi.dto.UsBoardDTO;
import com.semi.persistence.UsBoardRepository;

@Service
public class UsBoardServiceImpl implements UsBoardService {

	@Autowired
	private UsBoardRepository UsboardRepo;
	
	@Override
	public List<UsBoardDTO> getUsBoardList(UsBoardDTO board) {
		return (List<UsBoardDTO>) UsboardRepo.findAll();
	}
	
	@Override
	public void insertUsBoard(UsBoardDTO board) {
		UsboardRepo.save(board);
	}
	
	@Override
	public UsBoardDTO getUsBoard(UsBoardDTO board) {
		return UsboardRepo.findById(board.getSeq()).get();
	}
	
	@Override
	public void updateUsBoard(UsBoardDTO board) {
		UsBoardDTO findBoard = UsboardRepo.findById(board.getSeq()).get();
		
		findBoard.setUsTitle(board.getUsTitle());
		findBoard.setUsContent(board.getUsContent());
		UsboardRepo.save(findBoard);
	}
	
	@Override
	public void deleteUsBoard(UsBoardDTO board) {
		UsboardRepo.deleteById(board.getSeq());
	}
	
	@Override
	public void usBoardUpdate(Long seq) {
		UsBoardDTO board = UsboardRepo.findById(seq).get();
		board.setUsCnt(board.getUsCnt()+1);
		UsboardRepo.save(board);
	}
	
	public Optional<UsBoardDTO> getBoard(UsBoardDTO board) {
		Long seq = board.getSeq(); // 또는 다른 방식으로 게시글 식별자 획득
        UsBoardDTO usBoard = UsboardRepo.findById(seq).orElse(null);

        return Optional.ofNullable(usBoard);
	}
}
