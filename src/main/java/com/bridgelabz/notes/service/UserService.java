package com.bridgelabz.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dto.LoginDTO;
import com.bridgelabz.notes.dto.UserDTO;
import com.bridgelabz.notes.exception.NotesException;
import com.bridgelabz.notes.model.UserModel;
import com.bridgelabz.notes.repository.UserRepository;
import com.bridgelabz.notes.util.EmailSenderService;
import com.bridgelabz.notes.util.TokenUtil;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	EmailSenderService emailSender;

	// Generated token after saving data and sent email
	@Override
	public String addUser(UserDTO userDTO) throws NotesException {
		Optional<UserModel> model = Optional.ofNullable(userRepo.findByEmailAddress(userDTO.getEmail()));
		if (model.isPresent()) {
			return "Email already exists";
		} else {
			UserModel userModel = new UserModel(userDTO);
			userRepo.save(userModel);
			String token = tokenUtil.createToken(userModel.getUserId());
			// email body
			String data = "Hi " + userModel.getFirstName()
					+ " \uD83D\uDE00,\n\nThank you for choosing Notes App \uD83E\uDD1D\n\n"
					+ "Your account is successfully created. Please find the details below\n\n" + "USER DETAILS: \n"
					+ "\uD83D\uDC71 First Name: " + userModel.getFirstName() + ",\t" + "Last Name: "
					+ userModel.getLastName() + "\n\uD83C\uDFE0 Address: " + userModel.getAddress() + "\n"
					+ "\uD83D\uDCE7 Email Address: " + userModel.getEmail() + "\n\uD83D\uDCC6 DOB: "
					+ "\n\uD83D\uDD11 Password: " + userModel.getPassword() + "\n\uD83E\uDE99 Token :" + token
					+ "\n\nRegards \uD83D\uDE4F,\nNotes Team";
			// sending email
			// emailSender.sendEmail(userModel.getEmail(), "Registered Successfully", data);
			return token;
		}
	}

	// Get all User Details list
	@Override
	public List<UserModel> getAllUsersData() {
		List<UserModel> userList = userRepo.findAll();
		if (userList.isEmpty()) {
			throw new NotesException("No User Registered yet!!!!");
		} else
			return userList;
	}

	// Get user data by id
	@Override
	public UserModel getUserDataById(int id) {
		UserModel userModel = userRepo.findById(id).orElse(null);
		if (userModel != null) {
			return userModel;
		} else
			throw new NotesException("UserID: " + id + ", does not exist");
	}

	// forgot password
	@Override
	public String forgotPassword(String email, String newPassword) {
		UserModel userModel = userRepo.findByEmailAddress(email);
		if (userModel != null) {
			userModel.setPassword(newPassword);// changing password
			userRepo.save(userModel);
			return "Hi " + userModel.getFirstName() + ", your password has been successfully reset.";
		} else
			throw new NotesException("Invalid Email Address");
	}

	// change password
	@Override
	public String changePassword(LoginDTO loginDTO, String newPassword) {
		UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmail());
		if (userModel != null) {
			if (userModel.getPassword().equals(loginDTO.getPassword())) {
				userModel.setPassword(newPassword); // changing password
			} else {
				return "Incorrect old password!!";
			}
			// Sending Email
			// emailSender.sendEmail(userModel.getEmail(), "Password Change!", "Password
			// Changed Successfully!");
			userRepo.save(userModel);
			return "Password Changed Successfully!";
		} else
			return "Invalid Email Address";
	}

	// Login
	@Override
	public String login(LoginDTO loginDTO) {
		UserModel userModel = userRepo.findByEmailAddress(loginDTO.getEmail());
		if (userModel != null) {
			if (userModel.getPassword().equals(loginDTO.getPassword())) {
				int id = userModel.getUserId();
				String token = generateToken(id);
				return token;
			} else
				throw new NotesException("Login Failed, Wrong Password!!!");
		} else
			throw new NotesException("Login Failed, Invalid emailId or password!!!");
	}

	// Delete data by userId
	@Override
	public int deleteUser(int id) {
		UserModel userModel = userRepo.findById(id).orElse(null);
		if (userModel != null) {
			String msg = "Hey " + userModel.getFirstName()
					+ " \uD83D\uDE07,\n\n Your account has been successfully removed" + " from Notes App..!!\n"
					+ "Hope to see you again soon \uD83D\uDE1E!\n\n" + "Thank you,\n" + "Notes Team";
			// sending email
			// emailSender.sendEmail(userModel.getEmail(), "User Removed!!!", msg);
			userRepo.deleteById(id);
		} else
			throw new NotesException("Error: Cannot find User ID " + id);
		return id;
	}

	// Generate token using userID
	@Override
	public String generateToken(int id) {
		UserModel model = userRepo.findById(id).get();
		if (model != null) {
			String token = tokenUtil.createToken(model.getUserId());
			return token;
		} else
			throw new NotesException("Invalid ID");
	}

	// Validate User using user token
	@Override
	public boolean validateUser(String token) {
		try {
			int id = tokenUtil.decodeToken(token);
			do {
				return true;
			} while (userRepo.findById(id).isPresent());
		} catch (Exception e) {
			throw new NotesException("Invalid User/ Token");
		}
	}
}
