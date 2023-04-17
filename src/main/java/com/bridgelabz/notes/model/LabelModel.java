package com.bridgelabz.notes.model;

import java.time.LocalDateTime;

import com.bridgelabz.notes.dto.LabelDTO;

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
@Table(name="labels")
public class LabelModel {

	@Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    @ManyToOne
    @JoinColumn(name="user_id")
	private UserModel user; 
    @ManyToOne
    @JoinColumn(name="note_id")
    private NotesModel notes;
    
    public LabelModel(LabelDTO labeldto, UserModel user, NotesModel notes) {

		this.name = labeldto.getName();
		this.user = user;
		this.notes = notes;
	}
}
