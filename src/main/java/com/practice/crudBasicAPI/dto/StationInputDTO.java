package com.practice.crudBasicAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationInputDTO {
    private String station_name;
    private String station_alias;
    private int is_last_station;
    private int status;
}
