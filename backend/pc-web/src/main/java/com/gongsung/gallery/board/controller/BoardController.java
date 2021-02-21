package com.gongsung.gallery.board.controller;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.User;


import com.gongsung.gallery.board.service.BoardService;
import com.gongsung.gallery.comment.controller.CommentDto;
import com.gongsung.gallery.user.dto.UserDto;
import com.gongsung.gallery.user.repository.UserRepository;
import com.gongsung.gallery.user.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
  public String boardList(@PathVariable("category") String category, Model model) {

    List<Board> boards = boardService.scanBoard(category);
    model.addAttribute("boards", boards);
    return "board/boardList";
  }

  @GetMapping("/board/writeBoard")
  public String craeteBoard(HttpServletRequest request, HttpServletResponse response,Model model)
      throws IOException {

    HttpSession httpSession = request.getSession();
    Long userId = (Long)httpSession.getAttribute("userId");

    if(userId==null){
      response.getOutputStream().println("Not Authorize!");
    }

    model.addAttribute("boardForm", new Board());
    model.addAttribute("userId", userId);

    return "board/writeBoard";
  }

  @PostMapping("/board/writeBoard")
  public String writeBoard(HttpServletRequest request, HttpServletResponse response,Board boardForm)
      throws IOException {

    HttpSession httpSession = request.getSession();
    Long userId = (Long)httpSession.getAttribute("userId");

    if(userId==null){
      response.getOutputStream().println("Not Authorize!");
    }

    User user = userService.findById(userId);
    Board board = boardService.createBoard(user);
    boardService
        .write(board, boardForm.getContent(), boardForm.getTitle(), boardForm.getCategory());

    return "redirect:/board/boardList/all";
  }
    @GetMapping("/board/{boardId}")
    public String readBoard(@PathVariable("boardId") Long boardId, Model model, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        String author = userService.findById(userId).getNickname();
        CommentDto commentDto = new CommentDto();
        commentDto.setAuthor(author);

        Board board = boardService.findById(boardId);
        model.addAttribute("board",board);
        model.addAttribute("commentForm", commentDto);
        return "board/templateBoard";
    }
}
