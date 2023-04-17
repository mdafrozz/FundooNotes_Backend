package com.bridgelabz.notes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LabelDTO {

	@NotEmpty
	String name;
	@NotNull(message = "UserID cannot be NULL")
	private int user_id;
	@NotNull(message = "NoteID cannot be NULL")
	private int note_id;
}
