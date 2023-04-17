package com.bridgelabz.notes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.notes.dto.LoginDTO;
import com.bridgelabz.notes.dto.ResponseDTO;
import com.bridgelabz.notes.dto.UserDTO;
import com.bridgelabz.notes.model.UserModel;
import com.bridgelabz.notes.service.IUserService;
import com.bridgelabz.notes.util.TokenUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService iuserService;

	@Autowired
	TokenUtil tokenUtil;

	// Welcome message
	@GetMapping(value = { "/", "" })
	public String welcome() {
		return "Welcome to Notes App..!!";
	}

	// Register user and generate Token
	@PostMapping(value = { "/register" })
	public ResponseEntity<ResponseDTO> AddUser(@Valid @RequestBody UserDTO userdto) {
		String token = iuserService.addUser(userdto);
		ResponseDTO responseDTO = new ResponseDTO("User Registered Successfully..!!!", token);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// Get all data
	@GetMapping("/all")
	public List<UserModel> getAllUserDetails() {
		List<UserModel> userList = iuserService.getAllUsersData();
		return userList;
	}

	// Get User Data by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> getUserById(@PathVariable int id) {
		UserModel userModel = iuserService.getUserDataById(id);
		ResponseDTO responseDTO = new ResponseDTO("User Details with ID: " + id, userModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Forgot Password
	@PostMapping("/forgotpassword/{email}/{newPassword}")
	public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email, @PathVariable String newPassword) {
		String response = iuserService.forgotPassword(email, newPassword);
		ResponseDTO responseDTO = new ResponseDTO("Password Reset Successfully", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Change password
	@PostMapping("/changepassword/{newPassword}")
	public ResponseEntity<ResponseDTO> changePassword(@RequestBody LoginDTO loginDTO,
			@PathVariable String newPassword) {
		String response = iuserService.changePassword(loginDTO, newPassword);
		ResponseDTO responseDTO = new ResponseDTO("Password Status:", response);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Login check
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
		String token = iuserService.login(loginDTO);
		ResponseDTO responseDTO = new ResponseDTO("Login Success", token);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Delete User by User ID
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ResponseDTO> deleteUserDataByID(@PathVariable int id) {
		int response = iuserService.deleteUser(id);
		ResponseDTO respDTO = new ResponseDTO("Deleted Successfully Id:", response);
		return new ResponseEntity<>(respDTO, HttpStatus.OK);
	}

}
