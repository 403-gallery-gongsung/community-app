package com.gongsung.gallery.board.controller;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;


import com.gongsung.gallery.board.service.BoardService;
import com.gongsung.gallery.user.repository.UserRepository;
import com.gongsung.gallery.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;


    @GetMapping("/board/boardList/{category}")
    public String boardList(@PathVariable("category") String category, Model model){

        List<Board> boards = boardService.scanBoard(category);
        model.addAttribute("boards",boards);
        return "board/boardList";
    }

    @GetMapping("/board/{userId}/writeBoard")
    public String craeteBoard(@PathVariable("userId") Long userId,Model model){

        model.addAttribute("boardForm",new Board());
        model.addAttribute("userId",userId);

        return "board/writeBoard";
    }

    @PostMapping("/board/{userId}/writeBoard")
    public String writeBoard(@PathVariable("userId") Long userId,Board boardForm){

        User user = userService.findById(userId);
        Board board = boardService.createBoard(user);
        boardService.write(board,boardForm.getContent(),boardForm.getTitle(),boardForm.getCategory());

        return "redirect:/board/boardList/all";
    }


}
