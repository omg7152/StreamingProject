package project.service;

import java.util.*;

public interface UserService {
	Boolean select_user(String user_id, String user_password) throws Exception;
	Boolean check_id(String user_id) throws Exception;
	void insert_account(Map<String, Object> data) throws Exception;
}
