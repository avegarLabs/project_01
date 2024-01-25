package com.avegarLabs.musalatest.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private LocalDateTime postedOn;
    private String visibility;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<Users> likes;

    }
