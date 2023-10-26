package com.practice.crudBasicAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDisplayDTO {
    @JsonIgnore
    private int is_last_station;
    @JsonIgnore
    private int status;
    private String station_name, station_alias, station_isLast, station_status;
}
