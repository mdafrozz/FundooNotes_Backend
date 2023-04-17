package com.bridgelabz.notes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dto.LabelDTO;
import com.bridgelabz.notes.exception.NotesException;
import com.bridgelabz.notes.model.LabelModel;
import com.bridgelabz.notes.model.NotesModel;
import com.bridgelabz.notes.model.UserModel;
import com.bridgelabz.notes.repository.LabelRepository;
import com.bridgelabz.notes.repository.NotesRepository;
import com.bridgelabz.notes.repository.UserRepository;
import com.bridgelabz.notes.util.TokenUtil;

@Service
public class LabelService implements ILabelService {

	@Autowired
	LabelRepository labelRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	UserRepository userRepo;

	@Autowired
	UserService userService;

	@Autowired
	NotesRepository notesRepo;
	
	// create label
	@Override
	public LabelModel addLabel(LabelDTO labelDTO) throws NotesException {
		Optional<UserModel> userModel = userRepo.findById(labelDTO.getUser_id());
		Optional<NotesModel> notesModel = notesRepo.findById(labelDTO.getNote_id());
		if (userModel.isPresent() && notesModel.isPresent()) {
			LabelModel labelModel = new LabelModel(labelDTO, userModel.get(), notesModel.get());
			labelModel.setName(labelDTO.getName());
			labelRepo.save(labelModel);
			return labelModel;
		} else
			throw new NotesException("User not found");
	}

	// update label
	@Override
	public LabelModel update(LabelDTO labelDTO, int id) throws NotesException {
		Optional<UserModel> userModel = userRepo.findById(labelDTO.getUser_id());
		Optional<NotesModel> notesModel = notesRepo.findById(labelDTO.getNote_id());
		if (userModel.isPresent() && notesModel.isPresent()) {
			LabelModel labelModel = new LabelModel(labelDTO, userModel.get(), notesModel.get());
			labelModel.setName(labelDTO.getName());
			return labelRepo.save(labelModel);
		} else
			throw new NotesException("Invalid LabelID: " + id);
	}

	// Delete Label
	@Override
	public String delete(int id, String token) throws NotesException {
		userService.validateUser(token);
		LabelModel labelModel = labelRepo.findById(id).orElse(null);
		if (labelModel != null) {
			labelRepo.deleteById(id);
		} else
			throw new NotesException("Invalid LabelID");
		return labelModel.getName();
	}

	// get all labels
	@Override
	public List<LabelModel> getAll() {
		List<LabelModel> List = labelRepo.findAll();
		if (List.isEmpty()) {
			throw new NotesException("No label Added yet!");
		} else
			return List;
	}

	// get label by id
	@Override
	public LabelModel getById(int id) {
		LabelModel labelModel = labelRepo.findById(id).orElse(null);
		if (labelModel != null) {
			return labelModel;
		} else
			throw new NotesException("Invalid LabelID");
	}
}
