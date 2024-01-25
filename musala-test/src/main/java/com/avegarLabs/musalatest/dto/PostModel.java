package com.avegarLabs.musalatest.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostModel {
    private String text;
    private String visibility;
    private int userId;
}
