package com.bridgelabz.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.notes.model.LabelModel;
import com.bridgelabz.notes.model.UserModel;

public interface LabelRepository extends JpaRepository<LabelModel, Integer> {
    public List<LabelModel> findByUser(UserModel user);

}
