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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.notes.dto.NotesDTO;
import com.bridgelabz.notes.dto.ReminderDTO;
import com.bridgelabz.notes.dto.ResponseDTO;
import com.bridgelabz.notes.model.NotesModel;
import com.bridgelabz.notes.service.INotesService;
import com.bridgelabz.notes.service.IUserService;

@RestController
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	INotesService inoteService;

	@Autowired
	IUserService iuserService;

	// create new note
	@PostMapping(value = { "/create" })
	public ResponseEntity<ResponseDTO> Add(@Valid @RequestBody NotesDTO noteDTO,@RequestHeader String token) {
		NotesModel notesModel = inoteService.Create(noteDTO, token);
		ResponseDTO responseDTO = new ResponseDTO("Notes created Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// edit update existing note
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> update(@Valid @RequestBody NotesDTO noteDTO, @RequestHeader String token,
			@PathVariable int id) {
		NotesModel notesModel = inoteService.update(noteDTO, token, id);
		ResponseDTO responseDTO = new ResponseDTO("Notes updated Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// delete note
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable int id, @RequestHeader String token) {
			String note = inoteService.deleteNote(id,token);
			ResponseDTO responseDTO = new ResponseDTO(note, "Deleted Successfully");
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get all Notes
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAll(@RequestHeader String token) {
		List<NotesModel> noteList = inoteService.getAll(token);
		ResponseDTO responseDTO = new ResponseDTO("Number of notes: " + noteList.size(), noteList);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get note by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> getById(@PathVariable int id, @RequestHeader String token) {
		NotesModel notesModel = inoteService.getById(id, token);
		ResponseDTO responseDTO = new ResponseDTO("Note details with ID: " + id, notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Pin note
	@PutMapping("/pin/{id}")
	public ResponseEntity<ResponseDTO> pinNote(@RequestHeader String token, @PathVariable int id) {
		NotesModel notesModel = inoteService.pinNote(token, id);
		ResponseDTO responseDTO = new ResponseDTO("Notes pinned Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Trash note
	@PutMapping("/trash/{id}")
	public ResponseEntity<ResponseDTO> trashNote(@RequestHeader String token, @PathVariable int id) {
		NotesModel notesModel = inoteService.trashNote(token, id);
		ResponseDTO responseDTO = new ResponseDTO("Notes sent to trash Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Archive note
	@PutMapping("/archive/{id}")
	public ResponseEntity<ResponseDTO> archiveNote(@RequestHeader String token, @PathVariable int id) {
		NotesModel notesModel = inoteService.archiveNote(token, id);
		ResponseDTO responseDTO = new ResponseDTO("Notes archived Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// add reminder
	@PostMapping("/reminder/{id}")
	public ResponseEntity<ResponseDTO> addReminder(@RequestHeader String token, @PathVariable int id,
			@RequestBody ReminderDTO reminderDto) {
		NotesModel notesModel = inoteService.addReminder(token, id, reminderDto);
		ResponseDTO responseDTO = new ResponseDTO("Reminder added Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// remove reminder
	@PutMapping("/reminder/{id}")
	public ResponseEntity<ResponseDTO> removeReminder(@RequestHeader String token, @PathVariable int id) {
		NotesModel notesModel = inoteService.removeReminder(token, id);
		ResponseDTO responseDTO = new ResponseDTO("Reminder removed Successfully..!!!", notesModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

}
