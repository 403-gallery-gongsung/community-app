package com.gongsung.gallery.web.comment.domain;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    private String category;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}