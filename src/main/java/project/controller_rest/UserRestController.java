package project.controller_rest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import project.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login_check")
    public ResponseEntity login_check(@RequestBody Map<String, Object> data, UriComponentsBuilder uriBuilder){
        JSONObject resultJobj = new JSONObject();
        try {
            String id = (String) data.get("id");
            String password = (String) data.get("password");

            if (userService.select_user(id, password)) {
                UriComponents uriComponents = uriBuilder.path("/main").build();
                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(uriComponents.toUri());

                resultJobj.put("status", "SUCCESS");
                resultJobj.put("redirect", "/main");
                return new ResponseEntity(resultJobj.toJSONString(), headers, HttpStatus.OK);
            }

            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.UNAUTHORIZED);
        }
        catch(Exception e){
            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", e.getMessage());
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/check_id")
    public ResponseEntity check_id(@RequestBody Map<String, Object> data){
        JSONObject resultJobj = new JSONObject();
        try {
            String id = (String) data.get("id");

            if (userService.check_id(id)) {
                resultJobj.put("status", "SUCCESS");
                return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.OK);
            }

            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", "중복된 아이디 입니다.");
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.OK);
        }
        catch (Exception e){
            resultJobj.put("status", "ERROR");
            resultJobj.put("error_message", e.getMessage());
            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/insert_account")
    public ResponseEntity insert_account(@RequestBody Map<String, Object> data, UriComponentsBuilder uriBuilder){

        JSONObject resultJobj = new JSONObject();
        try {
            userService.insert_account(data);

            UriComponents uriComponents = uriBuilder.path("/login").build();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriComponents.toUri());

            resultJobj.put("status", "SUCCESS");
            resultJobj.put("redirect", "/login");

            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.OK);
        }
        catch(Exception e){

            resultJobj.put("status", "ERROR");
            resultJobj.put("error_massage", e.getMessage());

            return new ResponseEntity(resultJobj.toJSONString(), HttpStatus.BAD_REQUEST);
        }
    }
}
