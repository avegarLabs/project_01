package com.avegarLabs.musalatest.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Posts> postsList ;

    @ManyToMany
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "friendId", referencedColumnName = "id"))
    private List<Users> friends = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "followed", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "followingId", referencedColumnName = "id"))
    private List<Users> followed = new ArrayList<>();

    @ManyToMany(mappedBy = "likes")
    Set<Posts> likedPosts;

}
