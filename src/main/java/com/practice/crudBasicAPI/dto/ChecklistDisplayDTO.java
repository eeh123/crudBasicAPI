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
public class ChecklistDisplayDTO {
    @JsonIgnore
    private int status;
    private String checklist_name, checklist_status;
}
