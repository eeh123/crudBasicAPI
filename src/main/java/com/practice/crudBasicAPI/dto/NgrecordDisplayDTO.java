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
public class NgrecordDisplayDTO {
    @JsonIgnore
    private int status;
    @JsonIgnore
    private int is_priority;
    private String item_name, item_priority, item_status;

}
