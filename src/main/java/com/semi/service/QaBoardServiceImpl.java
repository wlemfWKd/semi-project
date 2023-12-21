package com.semi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.semi.dto.QaBoard;
import com.semi.persistence.QaBoardRepository;

@Service
public class QaBoardServiceImpl implements QaBoardService {

	@Autowired
	private QaBoardRepository qaBoardRepository;

	@Override
	public void write(QaBoard board) {

		qaBoardRepository.save(board);
	}

	@Override
	public Page<QaBoard> qaBoardList(Pageable pageable) {
		return qaBoardRepository.findAll(pageable);
	}

	@Override
	public QaBoard qaBoardView(Long seq) {
		return qaBoardRepository.findById(seq).get();
	}

	@Override
	public void qaboardDelete(Long seq) {
		qaBoardRepository.deleteById(seq);
	}

	@Override
	public Page<QaBoard> boardSearchList(String searchKeyword, Pageable pageable) { // 검색 기능 추가
		return qaBoardRepository.findByTitleContaining(searchKeyword, pageable);
	}

	@Override
	public void qaBoardUpdate(Long seq) {
		QaBoard board = qaBoardRepository.findById(seq).get();
		board.setCnt(board.getCnt() + 1);
		qaBoardRepository.save(board);

	}

	@Override
	public Page<QaBoard> boardSearchList2(String searchKeyword, Pageable pageable) {
		return qaBoardRepository.findByContentContaining(searchKeyword, pageable);
	}

	@Override
	public Page<QaBoard> boardSearchList3(String searchKeyword, Pageable pageable) {
		return qaBoardRepository.findByWriterContaining(searchKeyword, pageable);
	}

	@Override
	public Optional<QaBoard> getBoard(QaBoard board) {
		Long seq = board.getSeq();
        QaBoard qaBoard = qaBoardRepository.findById(seq).orElse(null);

        return Optional.ofNullable(qaBoard);
	}

}
