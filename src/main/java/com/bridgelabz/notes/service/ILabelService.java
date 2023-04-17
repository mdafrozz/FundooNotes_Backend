package com.bridgelabz.notes.service;

import java.util.List;

import com.bridgelabz.notes.dto.LabelDTO;
import com.bridgelabz.notes.model.LabelModel;

public interface ILabelService {

	public LabelModel addLabel(LabelDTO labelDTO);
	public LabelModel update(LabelDTO labelDTO, int id);
	public String delete(int id, String token);
	public List<LabelModel> getAll();
	public LabelModel getById(int id);
}
