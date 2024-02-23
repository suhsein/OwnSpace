package com.example.demo.domain.members;

import com.example.demo.domain.daily.Daily;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String password;
    private String username;
    private String email;

    @OneToMany(mappedBy = "writer")
    private List<Daily> dailyList = new ArrayList<>();

    @Builder
    public Member(String userId, String password, String username, String email) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
    }
}
