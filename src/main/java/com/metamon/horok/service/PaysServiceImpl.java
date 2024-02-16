package com.metamon.horok.service;

import com.metamon.horok.dto.PaysDTO;
import com.metamon.horok.dto.SimplePayDTO;
import com.metamon.horok.repository.CardsRepository;
import com.metamon.horok.repository.PaysRepository;
import com.metamon.horok.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaysServiceImpl implements PaysService {

    private final UsersRepository userRepo;
    private final CardsRepository cardRepo;
    private final PaysRepository payRepo;
    @Override
    public List<PaysDTO> getPaysListByUserId(Integer userId){
        List<Object[]> resultList = payRepo.findPaysByUserId(userId);
        List<PaysDTO> paysDTOList = new ArrayList<>();

        for(Object[] result : resultList){
            PaysDTO paysDTO = new PaysDTO();
            paysDTO.setPayId((Integer)result[0]);
            paysDTO.setCardNumber((String) result[1]);
            paysDTO.setCredit((Integer) result[2]);
            paysDTO.setPayDate((Timestamp)result[3]);
            paysDTO.setIsWritten((Byte) result[4]);
            paysDTO.setExpiration((Byte) result[5]);
            paysDTO.setStoreName((String) result[6]);
            paysDTO.setStoreCategory((String) result[7]);
            paysDTO.setPayAddr((String) result[8]);
            //cardNumber duplicated
            paysDTO.setUserId((Integer) result[10]);
            paysDTO.setCardName((String) result[11]);
            paysDTO.setCardImg((String) result[12]);
            paysDTO.setCardLogo((String) result[13]);

            paysDTOList.add(paysDTO);
        }
        paysDTOList.sort(Comparator.comparing(PaysDTO::getPayDate).reversed());
        return paysDTOList;
    }

    @Override
    public SimplePayDTO getPayByPayId(Integer payId){
        return payRepo.findByPayId(payId);
    }
}
