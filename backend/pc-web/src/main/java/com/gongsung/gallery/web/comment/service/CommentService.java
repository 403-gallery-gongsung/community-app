package com.gongsung.gallery.web.comment.service;

import com.gongsung.gallery.web.comment.domain.Comment;
import com.gongsung.gallery.web.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public Comment findById(Long id){
        return commentRepository.findOne(id);
    }

    public List<Comment> findByAuthor(String name){
        return commentRepository.findByAuthor(name);
    }

    public List<Comment> findComments(){
        return commentRepository.findAll();
    }

    @Transactional
    public void update(Comment comment){
        Comment dbComment = commentRepository.findOne(comment.getId());
        dbComment.updateContent(comment.getContent());
    }

    @Transactional
    public void delete(Comment comment) {
        commentRepository.remove(comment.getId());
    }
}
