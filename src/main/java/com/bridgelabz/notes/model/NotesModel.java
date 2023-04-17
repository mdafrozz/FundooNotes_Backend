package com.bridgelabz.notes.model;

import java.time.LocalDateTime;

import com.bridgelabz.notes.dto.NotesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="note")
public class NotesModel {

	@Id
    @GeneratedValue
	@Column(name = "noteId")
    private int noteId;
    private String title;
    private String description;
    private String color;
    private boolean isPinned;
    private boolean isArchived;
    private boolean isTrashed;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String reminder;
    private String repeatReminder;
    @ManyToOne
    @JoinColumn(name="user_id")
	private UserModel user; 
    
    public NotesModel(NotesDTO notesdto) {

		this.title = notesdto.getTitle();
		this.description = notesdto.getDescription();
	}
}
