package com.gongsung.gallery.reply.api;

import static java.util.stream.Collectors.toList;

import com.gongsung.gallery.Comment;
import com.gongsung.gallery.Reply;

import com.gongsung.gallery.comment.service.CommentService;
import com.gongsung.gallery.reply.service.ReplyService;
import com.gongsung.gallery.user.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyApiController {
    private final UserService userService;
    private final CommentService commentService;
    private final ReplyService replyService;

    @PostMapping("/api/reply/write/{commentId}")
    public Long saveReply(@PathVariable("commentId") Long commentId,
        @RequestBody ReplyDto request,
        HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        String author = userService.findById(userId).getNickname();

        Comment comment = commentService.findById(commentId);
        Reply reply = Reply.createReply(comment, author, request.getContent());

        replyService.save(reply);
        return reply.getId();
    }

    @GetMapping("/api/reply/all")
    public List<ReplyDto> getAllReplies() {
        List<Reply> replies = replyService.findReplies();
        List<ReplyDto> result = replies.stream()
            .map(ReplyDto::new)
            .collect(toList());
        return result;
    }

    @GetMapping("/api/reply/{id}")
    public ReplyDto getReply(@PathVariable("id") Long id) {
        Reply reply = replyService.findById(id);
        return new ReplyDto(reply);
    }

    @PutMapping("/api/reply/update/{replyId}")
    public ReplyDto updateReply(
        @PathVariable("replyId") Long replyId,
        @RequestBody UpdateRequest request) {

        Reply reply = replyService.findById(replyId);
        reply.updateContent(request.getContent());
        replyService.save(reply);
        return new ReplyDto(reply);
    }

    @PostMapping("/api/reply/delete/{id}")
    public String deleteReply(
        @PathVariable("id") Long id) {
        Reply reply = replyService.findById(id);
        replyService.delete(reply);
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
}
