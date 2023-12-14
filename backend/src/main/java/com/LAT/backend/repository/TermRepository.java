package com.LAT.backend.repository;

import com.LAT.backend.model.Annotation;
import com.LAT.backend.model.LawClass;
import com.LAT.backend.model.Term;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TermRepository extends CrudRepository<Term, Integer> {
    Optional<Term> findByName(String name);
}
