// BoardService.java
package project.service;

import project.dto.NoticeDto;
import java.util.*;

public interface NoticeService {

	//조회
    List<NoticeDto> select_notices(String sort, int size) throws Exception;
    //작성
    void insert_notice(NoticeDto notice) throws Exception;
    //상세조회 
    NoticeDto select_notice(int noticeIdx) throws Exception;
    //수정 
    void update_notice(NoticeDto notice) throws Exception;
    //삭제 
    void delete_notice(int noticeIdx) throws Exception;
}