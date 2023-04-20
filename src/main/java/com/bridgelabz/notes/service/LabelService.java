package com.bridgelabz.notes.service;

import java.time.LocalDateTime;
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
	public LabelModel addLabel(LabelDTO labelDTO, String token) throws NotesException {
		userService.validateUser(token);
		if (labelDTO.getName() == "") {
			throw new NotesException("Label name cannot be blank");
		} else {
			int id = tokenUtil.decodeToken(token);
			Optional<UserModel> userModel = userRepo.findById(id);
			Optional<NotesModel> notesModel = notesRepo.findById(labelDTO.getNote_id());
			if (userModel.isPresent() && notesModel.isPresent() && notesModel.get().getUser() == userModel.get()) {
				LabelModel labelModel = new LabelModel(labelDTO);
				labelModel.setUser(userModel.get());
				labelModel.setNote(notesModel.get());
				labelModel.getNotes().add(notesModel.get());
				labelRepo.save(labelModel);
				return labelModel;
			} else
				throw new NotesException("User is not associated with the note");
		}
	}

	// update label
	@Override
	public LabelModel update(LabelDTO labelDTO, int id, String token) throws NotesException {
		userService.validateUser(token);
		if (labelDTO.getName() == "") {
			throw new NotesException("Label name cannot be blank");
		} else {
			int userId = tokenUtil.decodeToken(token);
			Optional<UserModel> userModel = userRepo.findById(userId);
			Optional<NotesModel> notesModel = notesRepo.findById(labelDTO.getNote_id());
			LabelModel labelModel = labelRepo.findById(id).orElse(null);
			if (notesModel.get().getUser() == userModel.get())
				if (notesModel.isPresent())
					if (labelModel.getLabelId() == id) {
						labelModel.setName(labelDTO.getName());
						labelModel.setModifiedDate(LocalDateTime.now());
						return labelRepo.save(labelModel);
					} else
						throw new NotesException("Invalid LabelID");
				else
					throw new NotesException("Invalid NoteID");
			else
				throw new NotesException("Invalid User Token");
		}
	}

	// Delete Label
	@Override
	public String delete(int noteId, int labelId, String token) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		Optional<UserModel> userModel = userRepo.findById(userId);
		LabelModel labelModel = labelRepo.findById(labelId).orElse(null);
		Optional<NotesModel> notesModel = notesRepo.findById(noteId);
		if (notesModel.get().getUser() == userModel.get())
			if (notesModel.isPresent())
				if (labelModel != null)

				{
					labelModel.getNotes().remove(notesModel.get());
					labelRepo.deleteById(labelId);
				}

				else
					throw new NotesException("Invalid LabelID");
			else
				throw new NotesException("Invalid NoteID");
		else
			throw new NotesException("Invalid User Token");
		return labelModel.getName();
	}

	// get all labels
	@Override
	public List<LabelModel> getAll(String token) {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		Optional<UserModel> userModel = userRepo.findById(userId);
		UserModel user = userModel.get();
		List<LabelModel> List = labelRepo.findByUser(user);
		if (List.isEmpty()) {
			throw new NotesException("No label Added yet!");
		} else
			return List;
	}

	// get label by id
	@Override
	public LabelModel getById(int id, String token) {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		LabelModel labelModel = labelRepo.findById(id).orElse(null);
		if (labelModel != null)
			if (labelModel.getUser().getUserId() == userId) {
			return labelModel;
		}  else
			throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid LabelID");
	}
}
