package com.gongsung.gallery.board.service;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;
import com.gongsung.gallery.board.repository.BoardRepository;
import com.gongsung.gallery.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;


    @Test
    @Rollback(false)
    public void write() throws Exception{
        //given
        User user = new User();
        user.setNickname("mook19");
//        user.setId(44L);
        userRepository.save(user);
        Board board = boardService.createBoard(user);

        //when
        Long boardId = boardService.write(board, "자유게시판 내용입니다", "test", "free");

        //then

    }


    @Test
    @Rollback(false)
    public void deleteTest() throws Exception{
        //given
        User user = new User();
        user.setNickname("mook");
        userRepository.save(user);
        Board board = boardService.createBoard(user);

        Long boardId = boardService.write(board, "삭제되어야한다.", "test title", "자유게시판");
        boardService.deleteBoard(boardId);
        //then
        System.out.println("size :" + user.getBoards().size());

    }




    @Test
    @Rollback(value = false)
    public void updateTest() throws Exception{
        String changeText ="변한 텍스트입니다";
        boardService.updateBoard(34L,changeText);

        assertEquals(changeText,boardRepository.findBoard(34L).getContent());
    }

}