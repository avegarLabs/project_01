package com.avegarLabs.musalatest.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostsListItems {
    private String text;
    private LocalDateTime postedOn;
    private String userFullName;
    private int countLikes;

}
