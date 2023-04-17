package com.bridgelabz.notes.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReminderDTO {
	
	private String reminder;
    private String repeatReminder;
}
