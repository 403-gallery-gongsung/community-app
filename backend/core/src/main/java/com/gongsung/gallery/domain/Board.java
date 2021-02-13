package com.gongsung.gallery.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
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


    //==  연관관계  정의  ==//

    public void setUser(User user) {
        this.user=user;
        user.getBoards().add(this);
    }


    //== 비즈니스 메소드 ==//

    public void updateContent(String content){
        this.content=content;
    }

}