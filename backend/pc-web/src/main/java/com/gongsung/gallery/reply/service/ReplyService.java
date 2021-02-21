package com.gongsung.gallery.reply.service;

import com.gongsung.gallery.Reply;
import com.gongsung.gallery.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    public void save(Reply reply){
        replyRepository.save(reply);
    }

    public Reply findById(Long id){
        return replyRepository.findOne(id);
    }

    public List<Reply> findByAuthor(String name){
        return replyRepository.findByAuthor(name);
    }

    public List<Reply> findReplies(){
        return replyRepository.findAll();
    }

    @Transactional
    public void update(Reply reply){
        Reply dbReply = replyRepository.findOne(reply.getId());
        dbReply.updateContent(reply.getContent());
    }

    @Transactional
    public void delete(Reply reply) {
        replyRepository.remove(reply.getId());
    }
}
