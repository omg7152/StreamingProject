package project.controller;

import java.util.*;

import project.dto.NoticeDto;
import project.service.NoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoticeController {
        @Autowired
        private NoticeService boardService;

        @RequestMapping("notices")
        public ModelAndView select_notices(@RequestParam(value = "sort", required = false, defaultValue = "created_datetime") String sort, 
        		@RequestParam (value = "size", required = false, defaultValue = "5")int size) throws Exception {

            ModelAndView mv = new ModelAndView("/views/notice/select_notices");

            List<NoticeDto> list = boardService.select_notices(sort, size);
            mv.addObject("list", list);
            mv.addObject("sort", sort);
            mv.addObject("size", size);
            
            return mv;
        }
        
        @RequestMapping("new_notice")
        public String create_notice() throws Exception {
            return "/views/notice/new_notice";
        }

        @RequestMapping("insert_notice")
        public String insertBoard(NoticeDto notice) throws Exception {
            boardService.insert_notice(notice);
            return "redirect:/notices";
        }
        
        @RequestMapping("notice")
    	public ModelAndView openBoardDetail(@RequestParam int noticeIdx) throws Exception {
    		ModelAndView mv = new ModelAndView("/views/notice/notice");
    		
    		NoticeDto notice = boardService.select_notice(noticeIdx);
    		mv.addObject("notice", notice);
    		
    		return mv;
    	}
        
        @RequestMapping("update_notice")
        public String updateBoard(NoticeDto notice) throws Exception {
            boardService.update_notice(notice);
            return "redirect:/notices";
        }

        @RequestMapping("delete_notice")
        public String delete_notice(int noticeIdx) throws Exception {
            boardService.delete_notice(noticeIdx);
            return "redirect:/notices";
        }
}
