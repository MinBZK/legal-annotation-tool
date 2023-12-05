package com.LAT.backend.rest;

import com.LAT.backend.model.Class;
import com.LAT.backend.repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ClassController {

    @Autowired
    private ClassRepository classRepository;

    @GetMapping("/classes")
    public Iterable<Class> getAllAnnotations() {
        return classRepository.findAll();
    }

}
