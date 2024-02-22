package com.metamon.horok.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReplyFavorDTO {
    private int replyCnt;
    private int favorCnt;
    private Byte isFavor;
}
