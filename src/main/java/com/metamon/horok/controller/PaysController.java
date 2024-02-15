package com.metamon.horok.controller;

import com.metamon.horok.dto.PaysDTO;
import com.metamon.horok.dto.SimplePayDTO;
import com.metamon.horok.service.PaysService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaysController {

    private final PaysService paysService;

    //의존성 생성자 주입
    public PaysController(PaysService paysService){
        this.paysService = paysService;
    }
    @GetMapping("/api/pays/paylist/{userId}")
    public List<PaysDTO> payList(@PathVariable Integer userId){
        return paysService.getPaysListByUserId(userId);
    }

    @GetMapping("/api/pays/{payId}")
    public SimplePayDTO payInfo(@PathVariable Integer payId){
        return paysService.getPayByPayId(payId);
    }
}
