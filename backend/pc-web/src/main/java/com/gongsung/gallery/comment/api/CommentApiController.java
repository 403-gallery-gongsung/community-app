package com.gongsung.gallery.comment.api;

import com.gongsung.gallery.Board;
import com.gongsung.gallery.Comment;
import com.gongsung.gallery.board.service.BoardService;
import com.gongsung.gallery.comment.service.CommentService;
import com.gongsung.gallery.user.service.UserService;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final UserService userService;
    private final CommentService commentService;
    private final BoardService boardService;

    @PostMapping("/api/comment/write/{boardId}")
    public Long saveComment(@PathVariable("boardId") Long boardId,
        @RequestBody CommentDto request,
        HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        String author = userService.findById(userId).getNickname();

        Board board = boardService.findById(boardId);
        Comment comment = Comment.createComment(board, author, request.getContent());
        commentService.save(comment);
        return comment.getId();
    }

    @GetMapping("/api/comment/all")
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentService.findComments();
        List<CommentDto> result = comments.stream()
            .map(CommentDto::new)
            .collect(toList());
        //return new Result(result.size(), result);
        return result;
    }

    @GetMapping("/api/comment/{id}")
    public CommentDto getComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findById(id);
        return new CommentDto(comment);
    }

    @PutMapping("/api/comment/update/{commentId}")
    public CommentDto updateComment(
        @PathVariable("commentId") Long commentId,
        @RequestBody UpdateRequest request) {

        Comment comment = commentService.findById(commentId);

        comment.updateContent(request.getContent());
        return new CommentDto(comment);
    }

    @PostMapping("/api/comment/delete/{id}")
    public String deleteComment(
        @PathVariable("id") Long id) {
        Comment comment = commentService.findById(id);
        commentService.delete(comment);
        return "Completely removed";
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

    @Data
    @AllArgsConstructor
    static class CommentDto {

        private String author;
        private String content;

        public CommentDto(Comment c) {
            this.author = c.getAuthor();
            this.content = c.getContent();
        }
    }
}
