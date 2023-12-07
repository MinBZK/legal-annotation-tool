package com.LAT.backend.repository;

import com.LAT.backend.model.Annotation;
import com.LAT.backend.model.LawClass;
import com.LAT.backend.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
}