import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Column(unique = true)
    private String nickname;
    private String emailCheckToken;
    private LocalDateTime joinedAt;
    private String birthdate;
    private String bio; // 자기소개
    private String occupation; // 직업
    private String location; // varchar(255)
    private String profileUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();
}