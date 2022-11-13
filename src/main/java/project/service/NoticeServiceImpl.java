// BoardServiceImpl.java
package project.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.dto.NoticeDto;
import project.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<NoticeDto> select_notices(String sort, int size) throws Exception {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("sort", sort);
    	params.put("size", size);
    	
        return noticeMapper.select_notices(params);
    }
    
    @Override
    public void insert_notice(NoticeDto notice) throws Exception {
    	noticeMapper.insert_notice(notice);
    }
    
    @Override
	public NoticeDto select_notice(int noticeIdx) throws Exception {
    	noticeMapper.update_views(noticeIdx);
		
		NoticeDto notice = noticeMapper.select_notice(noticeIdx);
		
		return notice;
	}
    
    @Override
    public void update_notice(NoticeDto notice) throws Exception {
    	noticeMapper.update_notice(notice);
    }

    @Override
    public void delete_notice(int noticeIdx) throws Exception {
    	noticeMapper.delete_notice(noticeIdx);
    }
}