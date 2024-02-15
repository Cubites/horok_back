package com.metamon.horok;


import com.metamon.horok.repository.CardsRepository;
import com.metamon.horok.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
public class UserCardsTest {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private CardsRepository cardsRepository;

    /*@Test
    public void userCardsTest(){
        List<Object[]> obj = userRepository.findByUserIdWithCards(171);

        obj.stream().forEach(o->{
            System.out.println(o[0] +"," + o[1] +","+ o[2]);
        });


    }*/



}
