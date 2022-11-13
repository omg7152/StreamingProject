package project.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoticeDto {
    private int noticeIdx;
    private String title;
    private String contents;
    private int views;
    private String creatorId;
    private LocalDateTime createdDatetime;
    private String updaterId;
    private LocalDateTime updatedDatetime;
}