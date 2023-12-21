package com.semi.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.semi.dto.ChBoard;
import com.semi.persistence.ChBoardRepository;

@Service
public class ChBoardServiceImpl implements ChBoardService {

	@Autowired
	private ChBoardRepository chBoardRepository;

	@Override
	public void write(ChBoard board, MultipartFile file) throws Exception {

		String projectPath = System.getProperty("user.dir") + "//src//main//resources//static//files";

		UUID uuid = UUID.randomUUID(); // 랜덤으로 식별자를 생성

		String fileName = uuid + "_" + file.getOriginalFilename(); // UUID와 파일이름을 포함된 파일 이름으로 저장

		File saveFile = new File(projectPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

		file.transferTo(saveFile);

		board.setFilename(fileName);
		board.setFilepath("/files/" + fileName);

		chBoardRepository.save(board);
	}

	@Override
	public Page<ChBoard> chBoardList(Pageable pageable) {
		return chBoardRepository.findAll(pageable);
	}

	@Override
	public ChBoard chBoardView(Long seq) {
		return chBoardRepository.findById(seq).get();
	}

	@Override
	public void chboardDelete(Long seq) {
		chBoardRepository.deleteById(seq);
	}

	@Override
	public Page<ChBoard> boardSearchList(String searchKeyword, Pageable pageable) { // 검색 기능 추가
		return chBoardRepository.findByTitleContaining(searchKeyword, pageable);
	}

	@Override
	public List<ChBoard> myChBoardList(ChBoard board, String id) {
		return (List<ChBoard>) chBoardRepository.findById(id);
	}

	@Override
	public Page<ChBoard> myChBoardList(ChBoard board, String id, Pageable pageable) {
		return (Page<ChBoard>) chBoardRepository.findById(id, pageable);
	}
	
	@Override
	public void save(ChBoard board) {
		chBoardRepository.save(board);
	}

	@Override
	public Optional<ChBoard> getBoard(ChBoard board) {
		Long seq = board.getSeq();
		ChBoard chBoard = chBoardRepository.findById(seq).orElse(null);

		return Optional.ofNullable(chBoard);
	}

	@Override
	public List<ChBoard> allBoard() {

		List<ChBoard> list = chBoardRepository.findAll();

		return list;
	}
}
