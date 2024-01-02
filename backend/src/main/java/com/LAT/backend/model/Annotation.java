package com.LAT.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Annotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String selectedWord;

    private String text;


    @ManyToOne
    @JoinColumn(name = "parent_annotation_id")
    private Annotation parentAnnotation;
    public Annotation getParentAnnotation() {
        return parentAnnotation;
    }

    public void setParentAnnotation(Annotation parentAnnotation) {
        this.parentAnnotation = parentAnnotation;
    }


    @JsonIgnoreProperties({"annotations"})
    @ManyToOne
    @JoinColumn(name = "lawClass_id")
    private LawClass lawClass;

    @JsonIgnoreProperties({"annotations"})
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnoreProperties({"annotations"})
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    public void setLawClass(LawClass lawClass) {
        this.lawClass = lawClass;
    }

    public LawClass getLawClass() {
        return lawClass;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSelectedWord() {
        return selectedWord;
    }

    public void setSelectedWord(String selectedWord) {
        this.selectedWord = selectedWord;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }
}
