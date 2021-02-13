package com.gongsung.gallery.board.service;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;
import com.gongsung.gallery.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
    public Long write(Board board, String content, String title, String category){

        board.setCategory(category);
        board.setTitle(title);
        board.setContent(content);
        boardRepository.save(board);

        return board.getId();
    }

    public Board createBoard(User user){
        Board board = new Board();
        board.setUser(user);
        return board;
    }

    @Transactional
    public List<Board> scanBoard(String category){
        List<Board> boardList;

        if(category.equals("all")){
            boardList=boardRepository.findAll();
        }
        else{
            boardList=boardRepository.findCategory(category);
        }

        return boardList;
    }


    @Transactional
    public void deleteBoard(Long id){
        boardRepository.delete(id);
    }


    @Transactional
    public void updateBoard(Long id,String content){
        Board board = boardRepository.findBoard(id);
        board.updateContent(content);
    }


    // 덧글 업데이트 ( 코멘트쪽에서 댓글을 등록후에 board의 comment 업데이트 필요 )




}
