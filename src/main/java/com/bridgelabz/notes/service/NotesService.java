package com.bridgelabz.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.notes.dto.NotesDTO;
import com.bridgelabz.notes.dto.ReminderDTO;
import com.bridgelabz.notes.exception.NotesException;
import com.bridgelabz.notes.model.NotesModel;
import com.bridgelabz.notes.repository.NotesRepository;
import com.bridgelabz.notes.util.TokenUtil;

@Service
public class NotesService implements INotesService {

	@Autowired
	NotesRepository notesRepo;

	@Autowired
	TokenUtil tokenUtil;

	@Autowired
	UserService userService;

	// create new note
	@Override
	public NotesModel Create(NotesDTO notesDTO, String token) throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = new NotesModel(notesDTO);
		notesRepo.save(notesModel);
		return notesModel;
	}

	// edit update note
	@Override
	public NotesModel update(NotesDTO notesDTO, String token, int id) throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesModel.setTitle(notesDTO.getTitle());
			notesModel.setDescription(notesDTO.getDescription());
			notesModel.setColor(notesDTO.getColor());
			return notesRepo.save(notesModel);
		} else
			throw new NotesException("Invalid NoteID: " + id);
	}

	// delete note
	public String deleteNote(int id, String token) {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesRepo.deleteById(id);
		} else
			throw new NotesException("Invalid NoteID");
		return notesModel.getTitle();
	}

	// get all notes
	public List<NotesModel> getAll(String token) {
		userService.validateUser(token);
		List<NotesModel> NotesList = notesRepo.findAll();
		if (NotesList.isEmpty()) {
			throw new NotesException("No Notes Added yet!");
		} else
			return NotesList;
	}

	// get note by id
	public NotesModel getById(int id) {
		NotesModel noteModel = notesRepo.findById(id).orElse(null);
		if (noteModel != null) {
			return noteModel;
		} else
			throw new NotesException("Invalid NoteID");
	}

	// pin note
	@Override
	public NotesModel pinNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesModel.setArchived(!notesModel.isPinned());
			return notesRepo.save(notesModel);
		} else
			throw new NotesException("Invalid NoteID: " + id);
	}

	// trash note
	@Override
	public NotesModel trashNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesModel.setTrashed(!notesModel.isTrashed());
			return notesRepo.save(notesModel);
		} else
			throw new NotesException("Invalid NoteID: " + id);
	}

	//archive note
	@Override
	public NotesModel archiveNote(String token, int id) throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesModel.setArchived(!notesModel.isArchived());
			return notesRepo.save(notesModel);
		} else
			throw new NotesException("Invalid NoteID: " + id);
	}
	
	//add reminder
	@Override
	public NotesModel addReminder(String token, int id, ReminderDTO reminderDto)throws NotesException {
		userService.validateUser(token);
		NotesModel notesModel = notesRepo.findById(id).orElse(null);
		if (notesModel != null) {
			notesModel.setReminder(reminderDto.getReminder());
			notesModel.setRepeatReminder(reminderDto.getRepeatReminder());
			return notesRepo.save(notesModel);
		} else
			throw new NotesException("Invalid NoteID: " + id);
	}
	
	//add reminder
		@Override
		public NotesModel removeReminder(String token, int id)throws NotesException {
			userService.validateUser(token);
			NotesModel notesModel = notesRepo.findById(id).orElse(null);
			if (notesModel != null) {
				notesModel.setReminder(null);
				notesModel.setRepeatReminder(null);
				return notesRepo.save(notesModel);
			} else
				throw new NotesException("Invalid NoteID: " + id);
		}
	
	
}
