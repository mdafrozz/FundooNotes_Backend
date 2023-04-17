package com.bridgelabz.notes.service;

import java.util.List;

import com.bridgelabz.notes.dto.LoginDTO;
import com.bridgelabz.notes.dto.UserDTO;
import com.bridgelabz.notes.model.UserModel;

public interface IUserService {

	public String addUser(UserDTO userDTO);

	public String login(LoginDTO loginDTO);

	public String changePassword(LoginDTO loginDTO, String newPassword);

	public String forgotPassword(String email, String password);

	public List<UserModel> getAllUsersData();

	public UserModel getUserDataById(int id);

	public int deleteUser(int id);

	public String generateToken(int id);

	public boolean validateUser(String token);
}
