package com.metamon.horok;

import com.metamon.horok.domain.*;
import com.metamon.horok.mapper.MapMapper;
import com.metamon.horok.repository.*;
import com.metamon.horok.vo.MapReviewVO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class MapTest {
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private StoresRepository storesRepo;
    @Autowired
    private ReviewsRepository reviewsRepo;
    @Autowired
    private FolderRepository folderRepo;
    @Autowired
    private FolderReviewsRepository folderReviewsRepo;

    @Autowired
    private MapMapper mapMapper;

    @Test
    @Commit
    public void createUserForTest() {
        Users user = Users.builder()
            .userNickname("졸린몰랑이")
            .agreement(true)
            .userProfile("/images/folder_icon.png")
            .personalCode("weetbt98brtb987rtb")
            .userLoginType("1")
            .build();
        userRepo.save(user);

        user = Users.builder()
            .userNickname("즐거운망곰이")
            .agreement(true)
            .userProfile("/images/folder_icon.png")
            .personalCode("brtkblr9ts0b80tbrb")
            .userLoginType("1")
            .build();
        userRepo.save(user);
    }

    @Test
    @Commit
    public void addStoreForTest() {
        Stores store = Stores.builder()
                .storeName("한상")
                .storeCategory("한식 음식점")
                .storeAddr("서울 마포구 동교로25길 26 1층 한상")
                .latitude(37.5581666)
                .longitude(126.9213406)
                .build();
        storesRepo.save(store);
        store = Stores.builder()
                .storeName("파쿠모리 홍대2호점")
                .storeCategory("일식 음식점")
                .storeAddr("서울 마포구 어울마당로 132-1")
                .latitude(37.5557729)
                .longitude(126.924787)
                .build();
        storesRepo.save(store);
        store = Stores.builder()
                .storeName("카페게이트 동교점")
                .storeCategory("카페")
                .storeAddr("서울 마포구 동교로 203 1층")
                .latitude(37.5582736)
                .longitude(126.923047)
                .build();
        storesRepo.save(store);
        store = Stores.builder()
                .storeName("피에스타7")
                .storeCategory("카페")
                .storeAddr("서울 마포구 동교로 235-1 1층")
                .latitude(37.555935)
                .longitude(126.9238853)
                .build();
        storesRepo.save(store);
        store = Stores.builder()
                .storeName("감나무집기사식당")
                .storeCategory("한식 음식점")
                .storeAddr("서울 마포구 연남로 25")
                .latitude(37.5618562)
                .longitude(126.9218703)
                .build();
        storesRepo.save(store);
        store = Stores.builder()
                .storeName("홍대최대포")
                .storeCategory("한식 음식점")
                .storeAddr("서울 마포구 양화로19길 10")
                .latitude(37.5581413)
                .longitude(126.9243477)
                .build();
        storesRepo.save(store);
    }

    @Test
    @Commit
    public void addReviewOneForTest() {
        Users user1 = userRepo.findByUserNickname("졸린몰랑이");
        Users user2 = userRepo.findByUserNickname("즐거운망곰이");
        Stores store = storesRepo.findByStoreName("한상");
        Reviews review = Reviews.builder()
                .user(user1)
                .store(store)
                .credit(9500)
                .reviewScore(4)
                .reviewContent("비빔밥중 연어덮밥시켰는데 연어가 비리지않고 들깨가루도 들어가서 고소~하니 둘의 조화가 잘어울렸고 카츠는 고기 두툼, 튀김옷 완전 바삭바삭하니 제가 좋아하는 스타일이였습니다.얘도 맛있음\n" +
                        "그리고 밥이 부족하지않나싶었는데 리필도 가능해서 아주 배불리먹었습니다-(후식으로 나오는 미니푸딩,,,,맛있어서 큰그릇에다 먹고싶은 맛,,,,ㅋㅋㅋㅋㅋㅋ)")
                .payDate(LocalDateTime.of(2024, 1, 16, 13, 5))
                .reviewDate(LocalDateTime.of(2024, 1, 17, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);
        review = Reviews.builder()
                .user(user2)
                .store(store)
                .credit(9500)
                .reviewScore(3)
                .reviewContent("음... 그냥 그랬습니다. 도저히 맛없기 힘든 메뉴들이 맛없었네요. 초밥도 그닥 신선하지는 않은 것 같고요. 파리도 날아다녔네요")
                .payDate(LocalDateTime.of(2024, 1, 24, 13, 5))
                .reviewDate(LocalDateTime.of(2024, 1, 25, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);
    }

    @Test
    @Commit
    public void addReviewsForTest() {
        Users user1 = userRepo.findByUserNickname("졸린몰랑이");
        Users user2 = userRepo.findByUserNickname("즐거운망곰이");
        Stores store = storesRepo.findByStoreName("한상");
        Reviews review = Reviews.builder()
                .user(user1)
                .store(store)
                .credit(9500)
                .reviewScore(4.5)
                .reviewContent("육회비빔밥이 싸고 양도 많고 맛도있어요. 다음에가면 다른 것도 먹어봐야겠어요.")
                .payDate(LocalDateTime.of(2023, 11, 26, 13, 5))
                .reviewDate(LocalDateTime.of(2023, 11, 26, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("파쿠모리 홍대2호점");
        review = Reviews.builder()
                .user(user1)
                .store(store)
                .credit(10800)
                .reviewScore(3.5)
                .reviewContent("신기한 카레가 있어서 먹으러갔는데, 맛은 그냥 그래요.")
                .payDate(LocalDateTime.of(2023, 12, 14, 13, 5))
                .reviewDate(LocalDateTime.of(2023, 12, 15, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("카페게이트 동교점");
        review = Reviews.builder()
                .user(user1)
                .store(store)
                .credit(3900)
                .reviewScore(4)
                .reviewContent("인생 디저트 맛집을 찾았습니다ㅠㅠ 친구랑 지나가다가 보이길래 마카롱 하나 사고 매장 나가면서 한 입 먹었는데.. 바로 다시 들어가서 까눌레랑 휘낭시에랑 더 샀어요. 진짜 하나도 빠짐없이 너무너무 맛있어요 ㄷㄷ 퀄리티 좋고 정성들인 미친 맛.. 앞으로 연남동 갈 때마다 갈 거예요!!!!")
                .payDate(LocalDateTime.of(2023, 12, 17, 13, 5))
                .reviewDate(LocalDateTime.of(2023, 12, 18, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("피에스타7");
        review = Reviews.builder()
                .user(user1)
                .store(store)
                .credit(2800)
                .reviewScore(4)
                .reviewContent("메뉴가 전반적으로 달아요. 티 메뉴도 달달하네요.")
                .payDate(LocalDateTime.of(2024, 2, 15, 13, 5))
                .reviewDate(LocalDateTime.of(2024, 2, 16, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("피에스타7");
        review = Reviews.builder()
                .user(user2)
                .store(store)
                .credit(3700)
                .reviewScore(5)
                .reviewContent("다른데 안파는 메뉴가 있는데 진짜 달아요")
                .payDate(LocalDateTime.of(2023, 12, 20, 13, 5))
                .reviewDate(LocalDateTime.of(2023, 12, 22, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("감나무집기사식당");
        review = Reviews.builder()
                .user(user2)
                .store(store)
                .credit(11000)
                .reviewScore(4)
                .reviewContent("반찬이 다양하게 나와서 좋아요. 무난하게 먹을만해요.")
                .payDate(LocalDateTime.of(2023, 12, 28, 13, 5))
                .reviewDate(LocalDateTime.of(2023, 12, 29, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);

        store = storesRepo.findByStoreName("홍대최대포");
        review = Reviews.builder()
                .user(user2)
                .store(store)
                .credit(10000)
                .reviewScore(4.5)
                .reviewContent("적당한 가격에 맛도 좋고 양도 많아서 좋아요. 매주 갈만합니다.")
                .payDate(LocalDateTime.of(2024, 2, 3, 13, 5))
                .reviewDate(LocalDateTime.of(2024, 2, 4, 14, 15))
                .image1("/images/review_image_sample.jpg")
                .image2("/images/review_image_sample.jpg")
                .image3("/images/review_image_sample.jpg")
                .build();
        reviewsRepo.save(review);
    }

    @Test
    @Commit
    public void addFolderForTest() {
        Folders folder = Folders.builder()
                .folderName("맛집")
                .folderImg("/images/folder_FFBEBE.png")
                .build();
        folderRepo.save(folder);
        folder = Folders.builder()
                .folderName("카페")
                .folderImg("/images/folder_FFBEBE.png")
                .build();
        folderRepo.save(folder);
        folder = Folders.builder()
                .folderName("밥집")
                .folderImg("/images/folder_FFBEBE.png")
                .build();
        folderRepo.save(folder);
    }

    @Test
    public void addShareReviewForTest() {
        Folders folder1 = folderRepo.findByFolderName("맛집");
        Folders folder2 = folderRepo.findByFolderName("카페");
        Folders folder3 = folderRepo.findByFolderName("밥집");

//        Reviews review1 = reviewsRepo.findByReviewWithStore("한상", "졸린몰랑이");
//        Reviews review2 = reviewsRepo.findByReviewWithStore("파쿠모리 홍대2호점", "졸린몰랑이");
//        Reviews review3 = reviewsRepo.findByReviewWithStore("카페게이트 동교점", "졸린몰랑이");
//        Reviews review4 = reviewsRepo.findByReviewWithStore("피에스타7", "즐거운망곰이");
//        Reviews review5 = reviewsRepo.findByReviewWithStore("감나무집기사식당", "즐거운망곰이");
//        Reviews review6 = reviewsRepo.findByReviewWithStore("홍대최대포", "즐거운망곰이");

//        FolderReviews folderReview = FolderReviews.builder().folder(folder1).review(review1).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder1).review(review2).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder2).review(review3).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder2).review(review4).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder3).review(review2).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder3).review(review5).build();
//        folderReviewsRepo.save(folderReview);
//        folderReview = FolderReviews.builder().folder(folder3).review(review6).build();
//        folderReviewsRepo.save(folderReview);
    }

    @Test
    public void readParticipantForTest() {
        Users user1 = userRepo.findByUserWithReviews("졸린몰랑이");
        Users user2 = userRepo.findByUserWithReviews("즐거운망곰이");
        System.out.println("**********************************");
        System.out.println("유저 id: " + user1.getUserId());
        for(Reviews r : user1.getReviewsList()) {
            System.out.println(r.getStore().getStoreName());
        }
        System.out.println("유저 id: " + user2.getUserId());
        for(Reviews r : user2.getReviewsList()) {
            System.out.println(r.getStore().getStoreName());
        }
        System.out.println("**********************************");
    }

    @Test
    public void readReviewsFromUser() {
        Users user1 = userRepo.findByUserWithReviews("졸린몰랑이");
        List<MapReviewVO> reviews = mapMapper.readAllReviewFromUserId(user1.getUserId());
        System.out.println("*****************************************");
        for(MapReviewVO a : reviews) {
            System.out.println(a.toString());
        }
        System.out.println("*****************************************");
    }

}
