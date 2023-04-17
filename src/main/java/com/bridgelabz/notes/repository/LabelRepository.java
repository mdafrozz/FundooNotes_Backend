package com.bridgelabz.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.notes.model.LabelModel;

public interface LabelRepository extends JpaRepository<LabelModel, Integer> {

}
