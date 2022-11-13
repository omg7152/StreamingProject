package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.service.NoticeService;

@Controller
// @Controller : View를 반환하기 위해 사용
// @RestController : @Controller에 @ResponseBody가 추가된 것
public class testController {

    @Autowired
    private NoticeService boardService;

    @RequestMapping("test") // localhost:8080/test 형태로 매핑 됨
    // @RequestMapping : 요청 주소(url) 설정, 요청 방식(GET, POST, DELETE, PATCH) 설정
    //                  @RequestMapping(value = "/test", method = {RequestMethod.GET})
    public String test() throws Exception {
        return "test";
    }
}
