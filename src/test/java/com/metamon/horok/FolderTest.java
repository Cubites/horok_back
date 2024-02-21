package com.metamon.horok;

import com.metamon.horok.repository.FolderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FolderTest {

	@Autowired
	FolderRepository repo;

	@Test
	void contextLoads() {
		List<Object[]> list = repo.findFolderListByUserIdAndFavor(171, true);
		list.stream().forEach(o->{
			System.out.println(o[0]+","+o[1]+","+o[2]+","+o[3]+","+o[4]);
		});
	}



}
