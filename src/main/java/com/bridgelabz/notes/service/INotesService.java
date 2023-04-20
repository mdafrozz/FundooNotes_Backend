package com.bridgelabz.notes.service;

import java.util.List;

import com.bridgelabz.notes.dto.NotesDTO;
import com.bridgelabz.notes.dto.ReminderDTO;
import com.bridgelabz.notes.model.NotesModel;

public interface INotesService {

	public NotesModel Create(NotesDTO notesDTO, String token);
	public NotesModel update(NotesDTO notesDTO, String token, int id);
	public String deleteNote(int id, String token);
	public List<NotesModel> getAll(String token);
	public NotesModel getById(int id, String token);
	public NotesModel pinNote(String token, int id);
	public NotesModel trashNote(String token, int id);
	public NotesModel archiveNote(String token, int id);
	public NotesModel addReminder(String token, int id, ReminderDTO reminderDto);
	public NotesModel removeReminder(String token, int id);
}
