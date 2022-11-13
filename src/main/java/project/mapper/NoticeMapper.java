package project.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import project.dto.NoticeDto;

@Mapper
public interface NoticeMapper {
    List<NoticeDto> select_notices(HashMap<String, Object> params) throws Exception;
    void insert_notice(NoticeDto notice) throws Exception;
    void update_views(int noticeIdx) throws Exception;
    NoticeDto select_notice(int noticeIdx) throws Exception;
    void update_notice(NoticeDto notice) throws Exception;
    void delete_notice(int noticeIdx) throws Exception;
}