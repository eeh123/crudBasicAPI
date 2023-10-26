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
public class LineDisplayDTO {
    @JsonIgnore
    private int id;
//    @JsonIgnore
    private int status;
    private String line_name, line_status;

}
