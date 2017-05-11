package com.wemeow.web.system.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wemeow.web.util.file.FileUpload;




@Service
public class FileService {
	
	
	public String uploadFile(CommonsMultipartFile file) throws IOException{
		String loc = FileUpload.save(file, FileUpload.Path.USER_HEAD, "123456");
		return loc;
	}

	public Object uploadFiles(CommonsMultipartFile[] files) throws IOException {
		// TODO Auto-generated method stub
		List<String> imageList = new ArrayList<>();
		
		for(CommonsMultipartFile file:files){
			imageList.add(uploadFile(file));
		}
		return imageList;
	}
	
	

}
