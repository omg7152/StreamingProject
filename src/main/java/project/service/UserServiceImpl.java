package project.service;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserMapper userMapper;

    @Override
    public Boolean select_user(String id, String password) throws Exception {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	
    	String salt = userMapper.getSalt(id);
    	
    	if(salt == null || salt == "") {
    		return false;
    	}
    	
    	params.put("id", id);
    	params.put("password", getSecurePassword(password, salt));
    	
    	if(userMapper.select_user(params).size() > 0) {
    		return true;
    	}
    	
        return false;
    }
    
    @Override
    public Boolean check_id(String id) throws Exception {
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	
    	params.put("id", id);
    	
    	if (userMapper.check_id(params).size() > 0) {
    		return false;
    	}
    	
        return true;
    }
    
    @Override
    public void insert_account(Map<String, Object> data) throws Exception {
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    	byte[] bytes = new byte[16];
    	random.nextBytes(bytes);
    			
    	// SALT 생성
    	String salt = new String(Base64.getEncoder().encode(bytes));
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("id", data.get("id"));
    	params.put("password", getSecurePassword((String)data.get("password"), salt));
    	params.put("name", data.get("name"));
    	params.put("birth", data.get("birth"));
    	params.put("gender", data.get("gender"));
    	params.put("email", data.get("email"));
    	params.put("phone", data.get("phone"));
    	params.put("salt", salt);
    	
    	userMapper.insert_account(params);
    }
    
    public String getSecurePassword(String password, String salt) throws NoSuchAlgorithmException{
    			
    	
    	String passwordAndSalt = password + salt;
    			
    	MessageDigest md = MessageDigest.getInstance("SHA-256");
    			
    	// 암호화
    	md.update(passwordAndSalt.getBytes());
    	return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
