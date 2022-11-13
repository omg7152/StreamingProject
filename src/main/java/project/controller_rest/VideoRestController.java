package project.controller_rest;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/video")
public class VideoRestController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    private String path = "/Users/anse-ung/workspace/Project/src/main/resources/static/videos/";

    @GetMapping("/play/{name}")
    public ResponseEntity<ResourceRegion> getVideo(@RequestHeader HttpHeaders headers, @PathVariable String name) throws Exception {
        logger.info("VideoController.getVideo");

        UrlResource video = new UrlResource("file:" + path + name + ".mp4");
        ResourceRegion resourceRegion;

        final long chunkSize = 1000000L;
        long contentLength = video.contentLength();

        Optional<HttpRange> optional = headers.getRange().stream().findFirst();
        HttpRange httpRange;
        if (optional.isPresent()) {
            httpRange = optional.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);
            resourceRegion = new ResourceRegion(video, start, rangeLength);
        } else {
            long rangeLength = Long.min(chunkSize, contentLength);
            resourceRegion = new ResourceRegion(video, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile) throws Exception{
        logger.info("fileUpload");
        JSONObject resultJobj = new JSONObject();

        String originFileName = multipartFile.getOriginalFilename();

        if(!"mp4".equals(originFileName.substring(originFileName.lastIndexOf('.') + 1))){
            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", "mp4 파일만 업로드 가능합니다.");
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        originFileName = originFileName.substring(0, originFileName.lastIndexOf('.'));

        String fileName = getFileName(originFileName);

        File targetFile = new File(path + fileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            resultJobj.put("status", "SUCCESS");
            resultJobj.put("videoUrl", "/api/video/" + fileName);
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.OK);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);
            e.printStackTrace();

            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", e.getMessage());
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.BAD_REQUEST);
        }
    }

    private String getFileName(String originFileName) throws NoSuchAlgorithmException {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatedNow = now.format(formatter);

        String originFileNameAndDateTime = originFileName + formatedNow;

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // 암호화
        md.update(originFileNameAndDateTime.getBytes());
        return String.format("%064x", new BigInteger(1, md.digest())) + ".mp4";
    }
}
