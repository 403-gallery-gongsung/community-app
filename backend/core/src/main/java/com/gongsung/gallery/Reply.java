package com.gongsung.gallery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Getter
@RequiredArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    private Comment comment;

    public Reply(String author, String content) {
        this.author = author;
        this.content = content;
        this.setCreatedDate(LocalDateTime.now());
    }

    @Transactional
    public void updateContent(String content){
        this.content = content;
        this.setModifiedDate(LocalDateTime.now());
    }

    public static Reply createReply(Comment comment, String author, String content){
        Reply reply = new Reply(author, content);
        reply.setReply(comment);
        reply.setCreatedDate(LocalDateTime.now());
        return reply;
    }

    public void setReply(Comment comment){
        this.comment = comment;
        comment.getReplies().add(this);
    }
}