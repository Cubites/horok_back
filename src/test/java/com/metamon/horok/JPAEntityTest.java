package com.metamon.horok;


import com.metamon.horok.domain.*;

import com.metamon.horok.repository.*;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
@Transactional
@Slf4j
public class JPAEntityTest {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private CardsRepository cardsRepository;
    @Autowired
    private EntityManager em;

    @Autowired
    private FolderReviewsRepository folderReviewsRepository;

    @Autowired
    private ParticipantsRepository participantsRepository;

    @Autowired
    private FolderRepository folderRepository;
    @BeforeEach
    public void initData(){
        Users users1 = new Users();
        users1.setUserNickname("강태바리");
        users1.setUserLoginType("Naver");
        users1.setPersonalCode("AAA");
        users1.setAgreement(true);

        Users users2 = new Users();
        users2.setUserNickname("이슬킴");
        users2.setUserLoginType("Google");
        users2.setPersonalCode("BBB");
        users2.setAgreement(true);
        
        userRepository.save(users1);
        userRepository.save(users2);

    }
    
    @Test
    public void userInitTest(){
        Users findUser = userRepository.findByUserNickname("강태바리");

        List<Users> all = userRepository.findAll();
        assertThat(findUser.getUserNickname()).isEqualTo("강태바리");
        assertThat(findUser.getUserLoginType()).isEqualTo("Naver");
        assertThat(all.size()).isEqualTo(2);
    
    }

    @Test
    public void initCardAndSelectCards(){
        Users findUser = userRepository.findByUserNickname("강태바리");
        Cards card = Cards.builder("111-111-111")
                .user_id(findUser.getUserId())
               .cardName("신한 deep Dream")
               .cardImg("path")
               .build();
        Cards card2 = Cards.builder("111-111-112")
                .user_id(findUser.getUserId())
                .cardName("신한 deep Dream2")
                .cardImg("path")
                .build();
        
       findUser.getCardsList().add(card);
       findUser.getCardsList().add(card2);
       em.flush();
        Cards findCard1 = cardsRepository.findById("111-111-111").orElse(null);
        Cards findCard2 = cardsRepository.findById("111-111-112").orElse(null);

       assertThat(findUser.getCardsList().size()).isEqualTo(2);
       assertThat(findCard1.getCardName()).isEqualTo("신한 deep Dream");
        assertThat(findCard2.getCardName()).isEqualTo("신한 deep Dream2");

        assertThat(findCard1.getUser_id()).isEqualTo(findUser.getUserId());
        assertThat(findCard2.getUser_id()).isEqualTo(findUser.getUserId());

    }

    @Test
    public void userFolderJoinAndCreateTest(){
        //폴더 생성 유저 (저장은안하지만..)
        Users findUser = userRepository.findByUserNickname("강태바리");
        Users findUser2 = userRepository.findByUserNickname("이슬킴");
        //폴더 생성
        //생성할 폴더
        Folders folder1 = Folders.builder().folderName("f2").folderImg("path").participantsList(new ArrayList<>()).build();
        //참가할 폴더
        Folders folders2 = Folders.builder().folderName("f1").folderImg("path").participantsList(new ArrayList<>()).build();
        //이미 생성된 폴더

        //폴더 생성
        Participants pa1 = Participants.builder().user(findUser).folder(folder1).folderFavor(false).build();
        Participants pa2 = Participants.builder().user(findUser2).folder(folder1).folderFavor(false).build();


        pa1.setFoldersAndUsers(folder1,findUser);
        pa2.setFoldersAndUsers(folder1,findUser2);
        folderRepository.save(folder1);

        em.flush();
        em.clear();
        Folders findFolder = folderRepository.findByFolderName(folder1.getFolderName());

        assertThat(findFolder.getParticipantsList().get(0).getFolderParticipantsId()).isEqualTo(pa1.getFolderParticipantsId());
        assertThat(findFolder.getParticipantsList().size()).isEqualTo(2);

        assertThat(findFolder.getParticipantsList().get(0).getUser().getUserNickname()).isEqualTo("강태바리");
        assertThat(findFolder.getParticipantsList().get(1).getUser().getUserNickname()).isEqualTo("이슬킴");


        //이미 생성된 폴더 저장
        folderRepository.save(folders2);

        //새로운 참가 이력 생성
        Participants pa3 = Participants.builder().user(findUser).folder(folders2).folderFavor(false).build();

        pa3.setFoldersAndUsers(folders2,findUser);
        //참가이력 폴더에 저장


        em.flush();
        em.clear();
        Folders findFolder2 = folderRepository.findByFolderName(folders2.getFolderName());
        assertThat(findFolder2.getParticipantsList().get(0).getUser().getUserNickname()).isEqualTo("강태바리");
    }

    @Test
    public void userReviewTest(){
        //폴더에 리뷰를 남기려면,
        //폴더,유저,폴더리뷰필요함
        //유저가 리뷰를 각각 다른 폴더에 공유하고, 한쪽 리뷰에만 댓글을 달았을 때 다르게 인식되어야한다.

        //폴더 생성
        //리뷰남길 폴더
        Folders folder1 = Folders.builder().folderName("뱁").folderImg("path"). participantsList(new ArrayList<>()).folderList(new ArrayList<>()).build();
        //리뷰안남길 폴더
        Folders folders2 = Folders.builder().folderName("김").folderImg("path").participantsList(new ArrayList<>()).folderList(new ArrayList<>()).build();

        //리뷰 남길 유저 -> 이미 등록된 유저이다.
        Users findUser = userRepository.findByUserNickname("강태바리");

        //먼저 유저가 폴더에 참가
        Participants paticipants1 = Participants.builder().folderFavor(false).build();
        Participants participants2 = Participants.builder().folderFavor(false).build();

        paticipants1.setFoldersAndUsers(folder1,findUser);
        participants2.setFoldersAndUsers(folders2,findUser);

        folderRepository.save(folder1);
        folderRepository.save(folders2);
        em.flush();
        em.clear();

        Users findUser2 = userRepository.findByUserNickname("강태바리");
        //먼저 folder에 유저가 참가된건지 확인
        assertThat(findUser2.getParticipantsList().size()).isEqualTo(2);
        assertThat(findUser2.getParticipantsList().get(0).getFolder().getFolderName()).isEqualTo("뱁");
        assertThat(findUser2.getParticipantsList().get(1).getFolder().getFolderName()).isEqualTo("김");

        //리뷰 하나 만들기
        Reviews review1 = Reviews.builder()
                .address("ㅇ")
                .reviewContent("옹심이 좋아요")
                .credit(123)
                .image1("path")
                .latitude(1)
                .longitude(1)
                .payDate(LocalDateTime.now())
                .storeName("옹심이")
                .reviewDate(LocalDateTime.now())
                .storeCategory("밥집")
                .reviewScore(4.5)
                .folderReviewsList(new ArrayList<>())
                .build();
        review1.setUsersReview(findUser2);

        //뱁 폴더에 리뷰를 남김
        FolderReviews folderReviews = FolderReviews.builder().build();
        FolderReviews folderReviews2 =  FolderReviews.builder().build();
        folderReviews.setFoldersAndReviews(folder1,review1);
        folderReviews2.setFoldersAndReviews(folders2,review1);

        em.flush();
        em.clear();

        Users findUser3 = userRepository.findByUserNickname("강태바리");

        assertThat(findUser3.getParticipantsList().get(0).getFolder().getFolderList().get(0).getReview().getReviewId())
                .isEqualTo(findUser3.getParticipantsList().get(1).getFolder().getFolderList().get(0).getReview().getReviewId());


    }

    @Test
    public void userFavorTest(){
        //폴더에 좋아요를 남기려면,
        //폴더,유저,폴더리뷰필요함
        //유저가 리뷰를 각각 다른 폴더에 공유하고, 한쪽 리뷰에만 좋아요를 달았을 때 다르게 인식되어야한다.
    }



}
