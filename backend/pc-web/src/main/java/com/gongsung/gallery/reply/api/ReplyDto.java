package com.gongsung.gallery.reply.api;

import com.gongsung.gallery.Reply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyDto {

    private String author;
    private String content;
    private LocalDateTime writtenAt;

    public ReplyDto(Reply r) {
        this.author = r.getAuthor();
        this.content = r.getContent();
        this.writtenAt = LocalDateTime.now();
    }
}
