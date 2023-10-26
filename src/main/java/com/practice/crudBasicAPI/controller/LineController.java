package com.practice.crudBasicAPI.controller;

import com.practice.sb_finalActivity.dto.*;
import com.practice.sb_finalActivity.service.ILineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LineController {
    @Autowired
    private ILineService iLineService;

    public LineController(ILineService iLineService) {
        super();
        this.iLineService = iLineService;
    }

    //---------------------------------Lines---------------------------------
    @GetMapping("/getLines") //[WORKING]
    public ResponseEntity<List<LineDisplayDTO>> getAllLines(){
        List<LineDisplayDTO> line = iLineService.getAllLines();
        if (line.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(line, HttpStatus.OK);
    }
    @GetMapping("/getLine/{id}") //[WORKING]
    public ResponseEntity<LineDisplayDTO> getLineById(@PathVariable Integer id){
        LineDisplayDTO line = iLineService.getLineById(id);
        return new ResponseEntity<>(line, HttpStatus.OK);
    }
    @PostMapping("/addLine") //[WORKING]
    public ResponseEntity<LineDisplayDTO> addNewLine(@RequestBody LineStationDTO lineStationDTO){
        return new ResponseEntity<LineDisplayDTO>(iLineService.addLine(lineStationDTO), HttpStatus.CREATED);
    }
    @PutMapping("/updateLine/{id}") //[WORKING]
    public ResponseEntity<LineDisplayDTO> updateLine(@PathVariable Integer id, @RequestBody LineInputDTO lineInputDTO){
        LineDisplayDTO update_line = iLineService.updateLine(id, lineInputDTO);
        return new ResponseEntity<>(update_line, HttpStatus.OK);
    }
    @PutMapping("/softDeleteLine/{id}") //[WORKING]
    public ResponseEntity<LineDisplayDTO> softDeleteLineStations(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.softDeleteLine(id), HttpStatus.OK);
    }
    @DeleteMapping("/hardDeleteLine/{id}") //[WORKING]
    public ResponseEntity<String> hardDeleteLine(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.hardDeleteLine(id), HttpStatus.OK);
    }




    //---------------------------------Stations---------------------------------
    @GetMapping("/getStations") //[WORKING]
    public ResponseEntity<List<StationDisplayDTO>> getAllStations(){
        List<StationDisplayDTO> stationsDTO = iLineService.getAllStations();
        if (stationsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stationsDTO, HttpStatus.OK);
    }
    @GetMapping("/getStationsByLineId/{id}") //[WORKING]
    public ResponseEntity<List<StationDisplayDTO>> getAllStationsByLineId(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getAllStationsByLineId(id), HttpStatus.OK);
    }
    @GetMapping("/getActiveStationsByLineId/{id}") //[WORKING]
    public ResponseEntity<List<StationInputDTO>> getAllActiveStationsByLineId(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getAllActiveStationsByLineId(id), HttpStatus.OK);
    }
    @GetMapping("/getStationById/{id}") //[WORKING]
    public ResponseEntity<StationDisplayDTO>getStationById(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getStationById(id), HttpStatus.OK);
    }
    @PostMapping("/addStationByLineId/{line_id}") //[WORKING]
    public ResponseEntity<StationDisplayDTO> addNewStation(@PathVariable Integer line_id, @RequestBody StationInputDTO stationInputDTO){
        return new ResponseEntity<>(iLineService.addStationByLineId(line_id, stationInputDTO), HttpStatus.CREATED);
    }
    @PutMapping("/updateStation/{id}") //[WORKING]
    public ResponseEntity<StationDisplayDTO> updateStation(@PathVariable Integer id, @RequestBody StationInputDTO stationInputDTO){
        StationDisplayDTO update_station = iLineService.updateStation(id, stationInputDTO);
        return new ResponseEntity<>(update_station, HttpStatus.OK);
    }
    @PutMapping("/softDeleteStation/{id}") //[WORKING]
    public ResponseEntity<StationDisplayDTO> softDeleteStation(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.softDeleteStation(id), HttpStatus.OK);
    }
    @DeleteMapping("/hardDeleteStation/{id}") //[WORKING]
    public ResponseEntity<String> hardDeleteStation(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.hardDeleteStation(id), HttpStatus.OK);
    }




    //---------------------------------Checklists---------------------------------
    @PostMapping("/addChecklistByStationId/{station_id}") //[WORKING]
    public ResponseEntity<ChecklistDisplayDTO> addNewChecklist(@PathVariable Integer station_id, @RequestBody ChecklistInputDTO checklistInputDTO){
        return new ResponseEntity<>(iLineService.addChecklistByStationId(station_id, checklistInputDTO), HttpStatus.CREATED);
    }
    @GetMapping("/getChecklists") //[WORKING]
    public ResponseEntity<List<ChecklistDisplayDTO>> getAllChecklists(){
        List<ChecklistDisplayDTO> checklistDTO = iLineService.getAllChecklist();
        if (checklistDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(checklistDTO, HttpStatus.OK);
    }
    @GetMapping("/getChecklistsByStationId/{id}") //[WORKING]
    public ResponseEntity<List<ChecklistDisplayDTO>> getAllChecklistsByStationId(@PathVariable Integer station_id){
        return new ResponseEntity<>(iLineService.getAllChecklistsByStationId(station_id), HttpStatus.OK);
    }
    @GetMapping("/getChecklistById/{id}") //[WORKING]
    public ResponseEntity<ChecklistDisplayDTO>getChecklistById(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getChecklistById(id), HttpStatus.OK);
    }
    @PutMapping("/updateChecklist/{id}") //[WORKING]
    public ResponseEntity<ChecklistDisplayDTO> updateChecklist(@PathVariable Integer id, @RequestBody ChecklistInputDTO checklistInputDTO){
        ChecklistDisplayDTO update_checklist = iLineService.updateChecklist(id, checklistInputDTO);
        return new ResponseEntity<>(update_checklist, HttpStatus.OK);
    }
    @PutMapping("/softDeleteChecklist/{id}") //[WORKING]
    public ResponseEntity<ChecklistDisplayDTO> softDeleteChecklist(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.softDeleteChecklist(id), HttpStatus.OK);
    }
    @DeleteMapping("/hardDeleteChecklist/{id}") //[WORKING]
    public ResponseEntity<String> hardDeleteChecklist(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.hardDeleteChecklist(id), HttpStatus.OK);
    }




    //---------------------------------NgRecords---------------------------------
    @PostMapping("/addNgrecordByChecklistId/{checklist_id}") //[WORKING]
    public ResponseEntity<NgrecordDisplayDTO> addNewNgrecord(@PathVariable Integer checklist_id, @RequestBody NgrecordInputDTO ngrecordInputDTO){
        return new ResponseEntity<>(iLineService.addNgrecordByChecklistId(checklist_id, ngrecordInputDTO), HttpStatus.CREATED);
    }
    @GetMapping("/getNgrecords") //[WORKING]
    public ResponseEntity<List<NgrecordDisplayDTO>> getAllNgrecords(){
        List<NgrecordDisplayDTO> pNgrecordDTOs = iLineService.getAllNgrecords();
        if (pNgrecordDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pNgrecordDTOs, HttpStatus.OK);
    }
    @GetMapping("/getNgrecordsByChecklistId/{id}") //[WORKING]
    public ResponseEntity<List<NgrecordDisplayDTO>> getAllNgrecordsByChecklistId(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getAllNgrecordsByChecklistId(id), HttpStatus.OK);
    }
    @GetMapping("/getNgrecordById/{id}") //[WORKING]
    public ResponseEntity<NgrecordDisplayDTO>getNgrecordById(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.getNgrecordById(id), HttpStatus.OK);
    }
    @PutMapping("/updateNgrecord/{id}") //[WORKING]
    public ResponseEntity<NgrecordDisplayDTO> updateNgrecord(@PathVariable Integer id, @RequestBody NgrecordInputDTO ngrecordInputDTO){
        NgrecordDisplayDTO update_ngrecord = iLineService.updateNgrecord(id, ngrecordInputDTO);
        return new ResponseEntity<>(update_ngrecord, HttpStatus.OK);
    }
    @PutMapping("/softDeleteNgrecord/{id}") //[WORKING]
    public ResponseEntity<NgrecordDisplayDTO> softDeleteNgrecord(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.softDeleteNgrecord(id), HttpStatus.OK);
    }
    @DeleteMapping("/hardDeleteNgrecord/{id}") //[WORKING]
    public ResponseEntity<String> hardDeleteNgrecord(@PathVariable Integer id){
        return new ResponseEntity<>(iLineService.hardDeleteNgrecord(id), HttpStatus.OK);
    }
}
