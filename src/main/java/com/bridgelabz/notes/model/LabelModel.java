package com.bridgelabz.notes.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.bridgelabz.notes.dto.LabelDTO;

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
@Table(name="labels")
public class LabelModel {

	@Id
    @GeneratedValue
	@Column(name = "labelId")
    private int labelId;
    private String name;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    @ManyToOne
	@JoinColumn(name = "user_id")
    private UserModel user;
    @ManyToOne
	@JoinColumn(name = "note_id")
    private NotesModel note;
    @ManyToMany
    @JoinTable(name = "label_note", joinColumns = {@JoinColumn(name = "label_id") },
    inverseJoinColumns = { @JoinColumn(name = "note_id") })
    private Set<NotesModel> notes = new HashSet<>();
    
    public LabelModel(LabelDTO labeldto) {

		this.name = labeldto.getName();
		
	}
}
