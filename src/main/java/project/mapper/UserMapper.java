package project.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import project.dto.UserDto;

@Mapper
public interface UserMapper {
	List<UserDto> select_user(HashMap<String, Object> params) throws Exception;
	List<UserDto> check_id(HashMap<String, Object> params) throws Exception;
	void insert_account(Map<String, Object> data) throws Exception;
	String getSalt(String id) throws Exception;
}
