package com.sogood.core.base.biz.controller;


import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sogood.core.base.biz.util.BaseUtils;
import com.sogood.core.base.biz.vo.FileInfo;
import com.sogood.core.config.util.PropertyUtil;
import com.sogood.core.constants.CoreConstant;
import com.sogood.core.constants.ResMediaType;
import com.sogood.core.error.ErrorCode;
import com.sogood.core.exception.CustomException;
import com.sogood.core.util.IoUtils;
import com.sogood.core.web.util.FileUploader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/** 공통 파일 업로드/다운로드 컨트롤러이다. */
@Controller 
public class FileController {

  /**
   * 블로그 이미지를 다운로드 받는다. 
   * @param request  웹요청 
   * @param response 웹응답 
   * @param fileName 다운로드 받을 파일 이름 
   * @return
   * @throws IOException
   */
  @GetMapping(value = "/get-blog-image", produces = ResMediaType.OCTET_STREAM)
	public @ResponseBody byte[] getImage2(HttpServletRequest request, HttpServletResponse response, @RequestParam("blogId") String blogId, @RequestParam("fileName") String fileName) throws IOException {
    //get an user ID , 세션 개발이 완료되면 수정할 것 
    String uploadBaseDir = PropertyUtil.getProperty(CoreConstant.YAML_KEY_UPLOAD_DIR);
    //String blogId = "a";
		String uploadDir = uploadBaseDir + blogId + "/" + CoreConstant.POST_IMAGE_DIR_NAME + "/"; 
		String filePath = uploadDir + fileName; 
		File f = new File(filePath);
		return IoUtils.readFileToByteArray(f);
	}//:

  /**
   * 블로그 이미지를 업로드한다. 
   * @param request 웹요청
   * @param response 웹응답
   * @param file 업로드 파일
   * @return
   *   업로드된 이미지 정보
   */
  @PostMapping(value = "/put-blog-image", produces = ResMediaType.JSON)
	public @ResponseBody FileInfo uploadFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("blogId") String blogId,  @RequestParam("uploadFile") MultipartFile file) {
    String uploadBaseDir = PropertyUtil.getProperty(CoreConstant.YAML_KEY_UPLOAD_DIR);
    //String blogId = "a";
		String uploadDir = uploadBaseDir + blogId + "/" + CoreConstant.POST_IMAGE_DIR_NAME + "/"; 

    String fileName =  BaseUtils.createFileName(file.getOriginalFilename());  
		String imagePath = uploadDir + fileName; 

    try { 
			FileUploader.upload(uploadDir, fileName, file);
		}catch(Exception e) {
			throw new CustomException(ErrorCode.INTERAL_SERVER_ERROR);
		}
		FileInfo imgPath = new FileInfo();
		imgPath.setAbsolutePath(imagePath);
		imgPath.setName(fileName);
		return imgPath; 
	}//

}///~
