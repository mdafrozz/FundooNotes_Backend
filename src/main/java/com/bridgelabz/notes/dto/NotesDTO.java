package com.bridgelabz.notes.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotesDTO {

	@NotNull
    private String title;
    @NotNull
    private String description;
    private String color;
    @NotNull(message = "UserID cannot be NULL")
	private int user_id;
}
