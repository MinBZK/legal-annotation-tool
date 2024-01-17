package com.LAT.backend.model;

import com.LAT.backend.views.Views;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Annotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Basic.class)
    private int id;
    @JsonView(Views.Basic.class)
    private String selectedWord;
    @JsonView(Views.Basic.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "parent_annotation_id")
    private Annotation parentAnnotation;

    @JsonIgnore
    @OneToMany(mappedBy = "parentAnnotation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Annotation> childAnnotations;

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"annotations", "xml_content"})
    private Project project;

    @JsonIgnoreProperties({"annotations"})
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

    @JsonIgnoreProperties({"annotations", "subClass", "mainClass"})
    @ManyToOne
    @JoinColumn(name = "relation_id")
    private Relation relation;

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

    public List<Annotation> getChildAnnotations() {
        return childAnnotations;
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

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}
