package com.bridgelabz.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.notes.model.NotesModel;
import com.bridgelabz.notes.model.UserModel;

@Repository
public interface NotesRepository extends JpaRepository<NotesModel, Integer> {
	
	    public List<NotesModel> findByUser(UserModel user);
}
