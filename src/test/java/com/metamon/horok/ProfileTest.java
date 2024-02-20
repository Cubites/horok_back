package com.metamon.horok;


import com.metamon.horok.repository.CardsRepository;
import com.metamon.horok.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@SpringBootTest
@Transactional
@Slf4j
public class ProfileTest {
    @Autowired
    private UsersRepository userRepository;

//    @Test
//    void test() throws Exception {
//        String image = getBase64String(new String[]{"D:/html/img/google.png"}, "");
//        System.out.println(image);
//
//    }
//
//    String byteToBase64(byte[] arr) {
//        String result = "";
//        try {
//            result = Base64.getEncoder().encodeToString(arr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "data:image/png;base64,"+result;
//    }
//
//    String getBase64String(String[] imageUrl, String content) throws Exception {
//        if (imageUrl.length > 0) {
//            int imageUrlLength = imageUrl.length;
//            String[] imageString = new String[imageUrlLength];
//            for (int i = 0; i < imageUrlLength; i++) {
//                String filePathName = imageUrl[i].replace("file:///", "");
//                String fileExtName = filePathName.substring(filePathName.lastIndexOf(".") + 1);
//                FileInputStream inputStream = null;
//                ByteArrayOutputStream byteOutStream = null;
//                try {
//                    File file = new File(filePathName);
//                    if (file.exists()) {
//                        System.out.println("존재");
//                        inputStream = new FileInputStream(file);
//                        byteOutStream = new ByteArrayOutputStream();
//                        int len = 0;
//                        byte[] buf = new byte[1024];
//                        while ((len = inputStream.read(buf)) != -1) {
//                            byteOutStream.write(buf, 0, len);
//                        }
//                        byte[] fileArray = byteOutStream.toByteArray();
//                        System.out.println("길이:"+fileArray.length);
////						imageString[i] = new String(Base64.encodeBase64(fileArray));
//                        imageString[i] = new String(fileArray);
//                        String changeString = "data:image/" + fileExtName + ";base64, " + imageString[i];
//                        System.out.println(changeString);
//                        content = content.replace(imageUrl[i], changeString);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    inputStream.close();
//                    byteOutStream.close();
//                }
//            }
//        }
//        return content;
//    }



}
