package com.wemeow.web.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.wemeow.web.system.service.FileService;
import com.wemeow.web.util.file.FileFormat;
import com.wemeow.web.util.file.FileUpload;
import com.wemeow.web.util.state.Status;
import com.wemeow.web.util.state.StatusCode;
import com.wemeow.web.util.state.StatusException;

@Controller
@RequestMapping("file")
public class FileController {

	@Autowired
	private FileService fileService;
	
	
	
	
	
	@RequestMapping(path = "", method = RequestMethod.POST)
	@ResponseBody
	public Status upload(@RequestParam CommonsMultipartFile[] files) {
		try {
			for(CommonsMultipartFile file:files){
				//空文件判断
				if (file.isEmpty()) {
					return new Status(false, StatusCode.ERROR_DATA, 0, "文件不可为空");
				}
				//不支持图片格式判断
				if (!FileFormat.isImage(file.getInputStream())) {
					return new Status(false, StatusCode.UNSUPPORT_IMAGE_FORMAT, 0, "不支持的图片格式");
				}
				//文件大小判断
				if (file.getSize() > FileUpload.FILE_MAX_SIZE) {
					return new Status(false, StatusCode.FILE_TOO_LARGE, 0, "图片大小超过了上传限制");
				}
			}
			//TODO：上传文件
			//return new Status(false, StatusCode.FILEUPLOAD_ERROR, 0, "图片上传失败");
			return new Status(true, StatusCode.SUCCESS, fileService.uploadFiles(files), "上传成功");
		} catch (Exception e) {
			return StatusException.procExcp(e);
		}
	}

	// @RequestMapping(path = "")
	// @ResponseBody
	// public Status sortLsnPoi(){
	// return new Status(true, StatusCode.SUCCESS, null, null);
	// }

}
