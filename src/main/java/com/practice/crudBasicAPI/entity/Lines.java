package com.practice.crudBasicAPI.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_lines")
public class Lines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String line_name;
    private int status;

    @JsonIgnore
    @CreationTimestamp
    private LocalDateTime date_created;
    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime date_modified;

    @OneToMany(mappedBy = "lines", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Stations> stations;
}
