package com.gongsung.gallery.board.api;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;
import com.gongsung.gallery.board.service.BoardService;
import com.gongsung.gallery.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@Transactional
public class BoardApiController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    @PostMapping("/board/write/{name}")
    public Long write(@PathVariable("name") String name,
                      @RequestBody BoardDto boardDto){
        User user = new User();
        user.setNickname(name);
        userRepository.save(user);
        Board board = boardService.createBoard(user);
        return boardService.write(board, boardDto.getContent(), boardDto.getTitle(), boardDto.getCategory());
    }

    @GetMapping("/board/list")
    public List<Result> boardList(){
        List<Board> boards = boardService.findAll();
        List<Result> boardList = new ArrayList<>();
        for (Board board : boards) {
            String nickname = board.getUser().getNickname();
            boardList.add(new Result(nickname, new BoardDto(board), board.getComments()));
        }

        return boardList;
    }

    @Data
    @AllArgsConstructor
    static class Result<T, V> {
        private String author;
        private T board_info;
        private V comments;
    }

    @Data
    @AllArgsConstructor
    static class BoardDto {
        String category;
        String content;
        String title;

        public BoardDto(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.category = board.getCategory();
        }
    }
}
