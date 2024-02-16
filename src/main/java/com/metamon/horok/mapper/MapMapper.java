package com.metamon.horok.mapper;

import com.metamon.horok.vo.MapReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MapMapper {
    List<MapReviewVO> readAllReviewFromUserId(Integer user_id);
}
