package com.gongsung.gallery.comment.controller;

import static java.util.stream.Collectors.toList;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.Comment;
import com.gongsung.gallery.board.service.BoardService;
import com.gongsung.gallery.comment.service.CommentService;
import com.gongsung.gallery.user.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final UserService userService;
    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/comment/write/{id}")
    public String saveComment(@PathVariable("id") Long id,
        CommentDto request, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        String author = userService.findById(userId).getNickname();

        Board board = boardService.findById(id);
        Comment comment = Comment.createComment(board, author, request.getContent());
        commentService.save(comment);
        return "redirect:/board/" + id;
    }

    @GetMapping("/comment/all")
    public String getAllComments(Model model) {
        List<Comment> comments = commentService.findComments();
        model.addAttribute("comments", comments);
        return "/comment/commentList";
    }

    @GetMapping("/comment/{id}")
    public String getComment(Model model, @PathVariable("id") Long id) {
        Comment comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "/comment/comment";
    }

    @GetMapping("/comment/update/{boardId}/{commentId}")
    public String updateCommentForm(
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId, Model model) {

        Comment comment = commentService.findById(commentId);
        CommentDto commentDto = new CommentDto(comment);
        commentDto.setId(commentId);

        model.addAttribute("boardId", boardId);
        model.addAttribute("comment", commentDto);
        return "/comment/updateComment";
    }

    @PostMapping("/comment/update/{boardId}/{commentId}")
    public String updateComment(
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId,
        CommentDto commentDto) {
        Comment comment = commentService.findById(commentId);
        comment.updateContent(commentDto.getContent());
        commentService.save(comment);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/comment/delete/{boardId}/{commentId}")
    public String deleteComment(
        @PathVariable("boardId") Long boardId,
        @PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findById(commentId);
        commentService.delete(comment);
        return "redirect:/board/" + boardId;
    }

    @Data
    static class UpdateRequest {
        String content;
    }

    @Data
    @AllArgsConstructor
    static class Result<T, V> {

        private int count;
        private T data;
        private V board;
    }

}
