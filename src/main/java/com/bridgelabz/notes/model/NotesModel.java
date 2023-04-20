package com.bridgelabz.notes.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.bridgelabz.notes.dto.NotesDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private String reminder;
    private String repeatReminder;
    @ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;
    @ManyToMany
    @JoinTable(name = "note_user", joinColumns = {@JoinColumn(name = "note_id") },
    inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserModel> users = new HashSet<>();
    
    public NotesModel(NotesDTO notesdto) {

		this.title = notesdto.getTitle();
		this.description = notesdto.getDescription();
	}
}
