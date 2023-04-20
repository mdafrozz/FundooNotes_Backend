package com.bridgelabz.notes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dto.NotesDTO;
import com.bridgelabz.notes.dto.ReminderDTO;
import com.bridgelabz.notes.exception.NotesException;
import com.bridgelabz.notes.model.NotesModel;
import com.bridgelabz.notes.model.UserModel;
import com.bridgelabz.notes.repository.NotesRepository;
import com.bridgelabz.notes.repository.UserRepository;
import com.bridgelabz.notes.util.TokenUtil;

@Service
public class NotesService implements INotesService {

	@Autowired
	NotesRepository notesRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepo;

	// create new note
	@Override
	public NotesModel Create(NotesDTO notesDTO, String token) throws NotesException {
		if (token == "") {
			throw new NotesException("token cannot be empty");
		} else {
			userService.validateUser(token);
			int id = tokenUtil.decodeToken(token);
			Optional<UserModel> userModel = userRepo.findById(id);
			if (notesDTO.getTitle() == "" && notesDTO.getDescription() == "") {
				throw new NotesException("Please enter either title or description to create a note ");
			} else {
				NotesModel notesModel = new NotesModel(notesDTO);
				notesModel.setUser(userModel.get());
				notesModel.getUsers().add(userModel.get());
				notesRepo.save(notesModel);
				return notesModel;
			}
		}
	}

	// edit update note
	@Override
	public NotesModel update(NotesDTO notesDTO, String token, int id) throws NotesException {
		userService.validateUser(token);
		if (notesDTO.getTitle() == "" && notesDTO.getDescription() == "") {
			throw new NotesException("Please enter either title or description to create a note ");
		} else {
			NotesModel notesModel = notesRepo.findById(id).orElse(null);
			int userId = tokenUtil.decodeToken(token);
			if (notesModel != null)
				if (notesModel.getUser().getUserId() == userId) {
					notesModel.setTitle(notesDTO.getTitle());
					notesModel.setDescription(notesDTO.getDescription());
					notesModel.setColor(notesDTO.getColor());
					notesModel.setModifiedDate(LocalDateTime.now());
					return notesRepo.save(notesModel);
				} else
					throw new NotesException("User not associated with note");
			else
				throw new NotesException("Invalid NoteID");
		}
	}

	// delete note
	public String deleteNote(int noteId, String token) {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		Optional<UserModel> userModel = userRepo.findById(userId);
		NotesModel notesModel = notesRepo.findById(noteId).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.getUsers().remove(userModel.get());
				notesRepo.deleteById(noteId);
				return notesModel.getTitle();
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// get all notes
	public List<NotesModel> getAll(String token) {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		Optional<UserModel> userModel = userRepo.findById(userId);
		UserModel user = userModel.get();
		List<NotesModel> NotesList = notesRepo.findByUser(user).stream().filter(u -> !(u.isArchived() || u.isTrashed()))
				.collect(Collectors.toList());
		if (NotesList.isEmpty()) {
			throw new NotesException("No Notes Added yet!");
		} else
			return NotesList;
	}

	// get note by id
	public NotesModel getById(int id, String token) {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				return notesModel;
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// pin note
	@Override
	public NotesModel pinNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.setArchived(!notesModel.isPinned());
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// trash note
	@Override
	public NotesModel trashNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.setTrashed(!notesModel.isTrashed());
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// archive note
	@Override
	public NotesModel archiveNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.setArchived(!notesModel.isArchived());
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// add reminder
	@Override
	public NotesModel addReminder(String token, int id, ReminderDTO reminderDto) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.setReminder(reminderDto.getReminder());
				notesModel.setRepeatReminder(reminderDto.getRepeatReminder());
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

	// Remove reminder
	@Override
	public NotesModel removeReminder(String token, int id) throws NotesException {
		userService.validateUser(token);
		int userId = tokenUtil.decodeToken(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null)
			if (notesModel.getUser().getUserId() == userId) {
				notesModel.setReminder(null);
				notesModel.setRepeatReminder(null);
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("User not associated with note");
		else
			throw new NotesException("Invalid NoteID");
	}

}
