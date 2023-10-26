package com.practice.crudBasicAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_ngrecords")
public class Ngrecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String item_name;
    private int is_priority;
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id", referencedColumnName = "id")
    @JsonIgnore
    private Checklists checklists;

}
