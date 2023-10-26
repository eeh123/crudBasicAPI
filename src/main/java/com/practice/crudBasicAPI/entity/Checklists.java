package com.practice.crudBasicAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_checklists")
public class Checklists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String checklist_name;
    private int status;

    //make ud query if all of checklist's ngrecords status is 1, make checklist status to 1, 0 otherwise
    @OneToMany(mappedBy = "checklists", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Ngrecords> checklistNgrecords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", referencedColumnName = "id")
    @JsonIgnore
    private Stations stations;
}
