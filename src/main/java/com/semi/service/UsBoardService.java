package com.semi.service;

import java.util.List;
import java.util.Optional;

import com.semi.dto.UsBoardDTO;

public interface UsBoardService {

	List<UsBoardDTO> getUsBoardList(UsBoardDTO board);

	void insertUsBoard(UsBoardDTO board);

	UsBoardDTO getUsBoard(UsBoardDTO board);

	void updateUsBoard(UsBoardDTO board);
	
	void deleteUsBoard(UsBoardDTO board);

	public void usBoardUpdate(Long seq);
	
	Optional<UsBoardDTO> getBoard(UsBoardDTO board);
}