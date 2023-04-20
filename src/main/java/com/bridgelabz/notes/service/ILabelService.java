package com.bridgelabz.notes.service;

import java.util.List;

import com.bridgelabz.notes.dto.LabelDTO;
import com.bridgelabz.notes.model.LabelModel;

public interface ILabelService {

	public LabelModel addLabel(LabelDTO labelDTO, String token);
	public LabelModel update(LabelDTO labelDTO, int id, String token);
	public String delete(int noteId, int labelId, String token);
	public List<LabelModel> getAll(String token);
	public LabelModel getById(int id, String token);
	}
