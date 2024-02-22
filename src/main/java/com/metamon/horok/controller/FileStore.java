package com.metamon.horok.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component


@Slf4j
public class FileStore {
    @Value("${upload.path}")
    private String fileDir;

    // 이미지 여러개 저장
    public List<UploadImage> storeFiles(List<MultipartFile> images) {

        //UploadImage객체는 파일 오리지널 이름, 서버에 저장되는 파일명, 파일 풀 경로
        List<UploadImage> list = new ArrayList<>();

        //image 하나씩 storeFile 메서드로 넘김
        if(images != null) {
            images.stream().filter(image -> !image.isEmpty())
                    .forEach(image ->{list.add(storeFile(image));});
        }

        return list;
    }


    //이미지 저장
   public UploadImage storeFile(MultipartFile image) {

        if(image.isEmpty()) {
            return null;
        }
        //오리지널 이름 알아내기
        String originFileName = image.getOriginalFilename();
        //실제 저장될 때 이름
        String storeFileName = createStoreFileName(originFileName);
        //저장 경로
        String fullPath = getFullPath(storeFileName);
        try {
            //MultipartFile 아주 간단하게 저장 가능..
            image.transferTo(new File(fullPath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //실제 파일을 저장 후 필요한 정보(파일이름,경로 등)만 담은 객체 리턴
        return new UploadImage(originFileName,storeFileName,fullPath);
    }

    private String getFullPath(String storeFileName) {
        //파일 실제 저장 경로
        return fileDir+File.separator+storeFileName;
    }

    private String createStoreFileName(String originFileName) {
        String ext = originFileName.substring(originFileName.lastIndexOf(".")+1);
        return System.nanoTime()+"."+ext;
    }
}
