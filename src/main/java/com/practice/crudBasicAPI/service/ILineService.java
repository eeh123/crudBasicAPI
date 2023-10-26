package com.practice.crudBasicAPI.service;

import com.practice.sb_finalActivity.dto.*;

import java.util.List;
public interface ILineService {

//------------Lines------------
    List<LineDisplayDTO> getAllLines();
    LineDisplayDTO getLineById(int id);
    LineDisplayDTO addLine(LineStationDTO lineStationDTO);
    LineDisplayDTO updateLine(int id, LineInputDTO lineInputDTO);
    LineDisplayDTO softDeleteLine(int id);
    String hardDeleteLine(int id);

    //------------Stations------------
    List<StationDisplayDTO> getAllStations();
    List<StationDisplayDTO> getAllStationsByLineId(int id);
    List<StationInputDTO> getAllActiveStationsByLineId(int id);
    StationDisplayDTO getStationById(int id);
    StationDisplayDTO addStationByLineId(int id, StationInputDTO stationDTO);
    StationDisplayDTO updateStation(int id, StationInputDTO stationInputDTO);
    StationDisplayDTO softDeleteStation(int id);
    String hardDeleteStation(int id);

    //------------Checklists------------
    ChecklistDisplayDTO addChecklistByStationId(int id, ChecklistInputDTO checklistInputDTO);
    List<ChecklistDisplayDTO> getAllChecklist();
    List<ChecklistDisplayDTO> getAllChecklistsByStationId(int id);
    ChecklistDisplayDTO getChecklistById(int id);
    ChecklistDisplayDTO updateChecklist(int id, ChecklistInputDTO checklistInputDTO);
    ChecklistDisplayDTO softDeleteChecklist(int id);
    String hardDeleteChecklist(int id);

    //------------Checklists------------
    NgrecordDisplayDTO addNgrecordByChecklistId(int id, NgrecordInputDTO ngrecordInputDTO);
    List<NgrecordDisplayDTO> getAllNgrecords();
    List<NgrecordDisplayDTO> getAllNgrecordsByChecklistId(int id);
    NgrecordDisplayDTO getNgrecordById(int id);
    NgrecordDisplayDTO updateNgrecord(int id, NgrecordInputDTO ngrecordInputDTO);
    NgrecordDisplayDTO softDeleteNgrecord(int id);
    String hardDeleteNgrecord(int id);
}
