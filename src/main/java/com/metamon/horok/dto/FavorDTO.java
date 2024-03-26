package com.metamon.horok.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavorDTO {
    private Integer folderReviewId;
    private Integer favorCnt;
    private Boolean isFavor;
}
