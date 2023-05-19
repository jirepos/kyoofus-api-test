package com.jirepo.demo.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jirepo.core.bean.FileInfo;
import com.jirepo.core.constants.ResMediaType;
import com.jirepo.core.util.IdGenerator;
import com.jirepo.core.util.IoUtils;
import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;
import com.jirepo.core.web.util.FileUploader;

import lombok.extern.slf4j.Slf4j;

/** 파일 업로드 데모 컨트롤러이다. */
@Controller
@Slf4j
@RequestMapping("/demo/file")
public class FileUploadDemo {

    /** upload.ftl 페이지 표시 */
    @GetMapping("/")
    public String index() {
        return "request/upload";
    }

    /**
     * 
     * @param request  요청정보
     * @param response 응답정보
     * @param realName 실제 파일명
     * @param file     파일 정보
     * @return
     */
    @PostMapping(value = "/upload", produces = ResMediaType.JSON)
    public @ResponseBody FileInfo uploadFile(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("realName") String realName, @RequestParam("uploadFile") MultipartFile file) {

        String uploadDir = "f:/upload/";
        String guid = IdGenerator.randomUUID2();
        String ext = IoUtils.getFilenameExtension(file.getOriginalFilename());
        String imagePath = uploadDir + guid + "." + ext;
        String fileName = guid + "." + ext;
        try {
            FileUploader.upload(uploadDir, fileName, file);
        } catch (Exception e) {
            throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR);
        }
        FileInfo imgPath = new FileInfo();
        imgPath.setAbsolutePath(imagePath);
        imgPath.setName(fileName);
        return imgPath;
    }//


   /**
     * 여러개의 파일 업로드 
     * @param request  요청정보
     * @param response 응답정보
     * @param realName 실제 파일명
     * @param file     파일 정보
     * @return
     */
    @PostMapping(value = "/multi-upload")
    public ResponseEntity<String> multiFileUpload( @RequestParam("content") String content,  @RequestParam("file")  MultipartFile[] multiFiles) {
        
        for(MultipartFile f : multiFiles) {
            System.out.println(f.getOriginalFilename());
            System.out.println(f.getSize());
            System.out.println(f.getContentType());
            //f.getBytes()
        }
        return ResponseEntity.ok().body("OK");
        
    }//


}
