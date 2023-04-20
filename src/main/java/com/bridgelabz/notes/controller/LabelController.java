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

import com.bridgelabz.notes.dto.LabelDTO;
import com.bridgelabz.notes.dto.ResponseDTO;
import com.bridgelabz.notes.model.LabelModel;
import com.bridgelabz.notes.service.ILabelService;
import com.bridgelabz.notes.service.IUserService;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	ILabelService ilabelService;

	@Autowired
	IUserService iuserService;

	// create new label
	@PostMapping(value = { "/create" })
	public ResponseEntity<ResponseDTO> AddLabel(@Valid @RequestBody LabelDTO labelDTO, @RequestHeader String token) {
		LabelModel labelModel = ilabelService.addLabel(labelDTO, token);
		ResponseDTO responseDTO = new ResponseDTO("Label added Successfully..!!!", labelModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	// edit update existing label
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> update(@Valid @RequestBody LabelDTO labelDTO, @PathVariable int id,
			@RequestHeader String token) {
		LabelModel labelModel = ilabelService.update(labelDTO, id, token);
		ResponseDTO responseDTO = new ResponseDTO("Label updated Successfully..!!!", labelModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// delete label
	@DeleteMapping("/{noteId}/{labelId}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable int noteId, @PathVariable int labelId, @RequestHeader String token) {
		String label = ilabelService.delete(noteId,labelId, token);
		ResponseDTO responseDTO = new ResponseDTO(label, "Deleted Successfully");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get all labels
	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAll(@RequestHeader String token) {
		List<LabelModel> List = ilabelService.getAll(token);
		ResponseDTO responseDTO = new ResponseDTO("All labels, Number of labels: " + List.size(), List);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	// Get label by ID
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> getById(@PathVariable int id, @RequestHeader String token) {
		LabelModel labelModel = ilabelService.getById(id, token);
		ResponseDTO responseDTO = new ResponseDTO("label details with ID: " + id, labelModel);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
