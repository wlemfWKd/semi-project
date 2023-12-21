package com.semi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.semi.dto.QaBoard;

public interface QaBoardService {

	public void write(QaBoard board);

	public Page<QaBoard> qaBoardList(Pageable pageable);

	public QaBoard qaBoardView(Long seq);

	public void qaboardDelete(Long seq);

	public Page<QaBoard> boardSearchList(String searchKeyword, Pageable pageable);

	public void qaBoardUpdate(Long seq);

	public Page<QaBoard> boardSearchList2(String searchKeyword, Pageable pageable);

	public Page<QaBoard> boardSearchList3(String searchKeyword, Pageable pageable);

	public Optional<QaBoard> getBoard(QaBoard board);
}
