package com.jaskaran.project2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jaskaran.project2.DAO.UserDAO;
import com.jaskaran.project2.Domain.User;

@RestController
public class UserController 
{
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private User user;
	
	
	//	http://localhost:8080/collaborationRestService/
	@RequestMapping("/")
	public String testServer()
	{
		return "This is first web service";
	}
	
	
	//	http://localhost:8080/collaborationRestService/userList
	@RequestMapping("/userList")
	public ResponseEntity<List<User>> userList()
	{
		List<User> users = userDAO.userList();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	
	//	http://localhost:8080/collaborationRestService/getUser/{email}
	@RequestMapping("/getUser/{email}")
	public ResponseEntity<User> getUser(@PathVariable String email)
	{
		user = userDAO.getUser(email);
		if(user==null)
		{
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}
	
	
	//	http://localhost:8080/collaborationRestService/validate/{email}/{password}
	@PostMapping("/validate/{email}/{password}")
	public ResponseEntity<User> validate(@PathVariable String email, @PathVariable String password)
	{
		user = userDAO.validateUser(email, password);
		if(user==null)
		{
			return new ResponseEntity<User>(user, HttpStatus.UNAUTHORIZED);
		}
		else
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}
	
	
	//	http://localhost:8080/collaborationRestService/registerUser
	@PostMapping("/registerUser")
	public ResponseEntity<User> registerUser(@RequestBody User user)
	{
		if(userDAO.saveUser(user))
		{
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<User>(user, HttpStatus.BAD_REQUEST);
		}
	}
	
}
