package com.practice.crudBasicAPI.dto;

import com.practice.sb_finalActivity.entity.Stations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LineStationDTO {
    private int id;
    private String line_name;
    private int status;
    private Set<Stations> stations;
}
