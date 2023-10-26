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
@Table(name = "tbl_stations")
public class Stations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String station_name;
    private String station_alias;
    private int is_last_station;
    private int status;

    @OneToMany(mappedBy = "stations", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Checklists> station_checklist;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    @JsonIgnore
    private Lines lines;


}
