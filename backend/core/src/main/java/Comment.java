import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
public class Comment {
    @Id @GeneratedValue
    private Long id;
    private String content;
    private String author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}