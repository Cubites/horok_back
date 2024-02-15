package com.metamon.horok.service;

import com.metamon.horok.dto.PaysDTO;
import com.metamon.horok.dto.SimplePayDTO;

import java.util.List;


public interface PaysService {

    public List<PaysDTO> getPaysListByUserId(Integer userId);
    public SimplePayDTO getPayByPayId(Integer payId);
}
