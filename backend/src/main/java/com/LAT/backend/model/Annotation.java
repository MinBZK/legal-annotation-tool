package com.LAT.backend.model;

import com.LAT.backend.views.Views;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

@Entity
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
    @JoinColumn(name = "parent_annotation_id", nullable = true)
    @JsonView(Views.Extended.class)
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

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"annotations", "xml_content"})
    private Project project;

    @JsonIgnoreProperties({"annotations"})
    @ManyToOne
    @JoinColumn(name = "term_id")
    private Term term;

//    @JsonIgnoreProperties({"annotations"})
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User created_by;

    @JsonView(Views.Basic.class)
    private long created_at;

//    @JsonView(Views.Basic.class)
//    private long updated_at;

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

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
