package com.semi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.semi.dto.ChBoard;

@Service
public interface ChBoardService {

   
   public void write(ChBoard board, MultipartFile file) throws Exception;
   
   public Page<ChBoard> chBoardList(Pageable pageable);

   public ChBoard chBoardView(Long seq);
   
   public void chboardDelete(Long seq);
   
   public Page<ChBoard> boardSearchList(String searchKeyword, Pageable pageable);
   
   public List<ChBoard> myChBoardList(ChBoard board, String id);
   
   public Page<ChBoard> myChBoardList(ChBoard board, String id, Pageable pageable);
   
   public void save(ChBoard board);
   
   Optional<ChBoard> getBoard(ChBoard board);
   
   public List<ChBoard> allBoard();
}