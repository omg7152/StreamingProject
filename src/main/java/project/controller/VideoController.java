package project.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@Controller
public class VideoController {

    @RequestMapping("/upload_video")
    public String open_upload_video(){
        return "views/video/upload_video";
    }

    @RequestMapping("/upload_video_success")
    public String open_upload_video_success(){
        return "views/video/upload_video_success";
    }

    @RequestMapping("/watch/{id}")
    public ModelAndView view(@PathVariable String id) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("id", id);
        mv.setViewName("/views/video/view_video");
        return mv;
    }


}
