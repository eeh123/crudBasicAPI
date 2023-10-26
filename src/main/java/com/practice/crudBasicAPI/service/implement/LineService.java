package com.practice.crudBasicAPI.service.implement;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.entity.*;
import com.practice.crudBasicAPI.exceptionhandler.ResourceNotFoundException;
import com.practice.crudBasicAPI.repository.*;
import com.practice.crudBasicAPI.service.ILineService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
@Slf4j //used for logging
@Service
public class LineService implements ILineService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ILineService iLineService;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private ChecklistRepository checklistRepository;
    @Autowired
    private NgrecordRepository ngrecordRepository;
    @Autowired
    private ModelMapper modelMapper;


//---------------------------------Lines---------------------------------
    @Override //[WORKING]
    public List<LineDisplayDTO> getAllLines() {
        LineDisplayDTO lineDisplayDTO = new LineDisplayDTO();
        List<LineDisplayDTO> allLines = new ArrayList<>();
        List<Lines> lines = new ArrayList<>(lineRepository.findAll());
        for (Lines l : lines) {
            lineDisplayDTO = modelMapper.map(l, LineDisplayDTO.class);
            lineDisplayDTO.setLine_status(boolToStatus(l.getStatus()));
            allLines.add(lineDisplayDTO);
        }
        return allLines;
    }
    @Override //[WORKING]
    public LineDisplayDTO getLineById(int id) {
        LineDisplayDTO lineDisplayDTO = new LineDisplayDTO();
        Optional<Lines> existingLine = lineRepository.findById(id);
        if(existingLine.isPresent()) {
            lineDisplayDTO = modelMapper.map(existingLine.get(), LineDisplayDTO.class);
            lineDisplayDTO.setLine_status(boolToStatus(existingLine.get().getStatus()));
            return lineDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing line with id: " + id + " found");
    }
    @Transactional
    @Override //[WORKING]
    public LineDisplayDTO addLine(LineStationDTO lineStationDTO) {
        Lines line = new Lines();
        line.setLine_name(lineStationDTO.getLine_name());
        line.setStatus(lineStationDTO.getStatus());
        var lineDetails = lineRepository.save(line);
        LineDisplayDTO lineDisplayDTO = modelMapper.map(lineDetails, LineDisplayDTO.class);
        lineDisplayDTO.setLine_status(boolToStatus(line.getStatus()));

        Stations station = new Stations();
        station.setStation_name(getStationName(lineStationDTO.getStations()));
        station.setStation_alias(getStationAlias(lineStationDTO.getStations()));
        station.setIs_last_station(isLastStation(lineStationDTO.getStations()));
        station.setStatus(getStatus(lineStationDTO.getStations()));
        station.setLines(lineDetails);
        stationRepository.save(station);

        //multiple station initialization on req body? not working (Stations not inserted in the db)
//        Set<Stations> s = lineStationDTO.getStations();
//        for (Stations station : s) {
//            station.setStation_name(getStationName(lineStationDTO.getStations()));
//            station.setStation_alias(getStationAlias(lineStationDTO.getStations()));
//            station.setIs_last_station(isLastStation(lineStationDTO.getStations()));
//            station.setStatus(getStatus(lineStationDTO.getStations()));
//            station.setLines(lineDetails);
//            stationRepository.save(station);
//        }

        return lineDisplayDTO;
    }
    @Override //[WORKING]
    public LineDisplayDTO updateLine(int id, LineInputDTO lineInputDTO) {
        LineDisplayDTO lineDisplayDTO = new LineDisplayDTO();
        Optional<Lines> existingLine = lineRepository.findById(id);
        if (existingLine.isPresent()){
            Lines lineDetails = existingLine.get();
            lineDetails.setLine_name(lineInputDTO.getLine_name());
            lineDetails.setStatus(lineInputDTO.getStatus());
            lineRepository.save(lineDetails);
            lineDisplayDTO = modelMapper.map(lineDetails, LineDisplayDTO.class);
            lineDisplayDTO.setLine_status(boolToStatus(lineDetails.getStatus()));
            return lineDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing line with id: " + id + " found");
    }
    @Override //[WORKING]
    public LineDisplayDTO softDeleteLine(int id){
        //cascades soft delete till ngrecords due to parent-child relationship
        lineRepository.softDeleteLineCascade(id);
        //works only if every parent has a child record, otherwise wont cascade properly
        //every station must have at least one checklist and every checklist of that station
        //must have at least one ngrecord for the query to work as intended
        return getLineById(id);
    }
    @Override //[WORKING]
    public String hardDeleteLine(int id){
        Optional<Lines> line = lineRepository.findById(id);
        if (line.isPresent()){
            Lines l = line.get();
            lineRepository.delete(l);
            if (!(stationRepository.findAllStationsByLinesId(id).isEmpty())){
                List<StationDisplayDTO> stations = getAllStationsByLineId(id);
                for (StationDisplayDTO s : stations) {
                    Stations station = modelMapper.map(s,Stations.class);
                    stationRepository.delete(station);
                }
            }
            return "Deleted " + l.getLine_name() + " Line";
        }
        throw new ResourceNotFoundException("No existing line with id: " + id + " found");
    }




//---------------------------------Stations---------------------------------
    @Override //[WORKING]
    public List<StationDisplayDTO> getAllStations(){
        StationDisplayDTO stationDisplayDTO = new StationDisplayDTO();
        List<StationDisplayDTO> allStations = new ArrayList<>();
        List<Stations> stations = new ArrayList<>(stationRepository.findAll());
        if (!(stations.isEmpty())) {
            for (Stations s : stations) {
                stationDisplayDTO = modelMapper.map(s, StationDisplayDTO.class);
                stationDisplayDTO.setStation_status(boolToStatus(s.getStatus()));
                stationDisplayDTO.setStation_isLast(boolLastStation(s.getIs_last_station()));
                allStations.add(stationDisplayDTO);
            }
            return allStations;
        }
        throw new ResourceNotFoundException("No stations found");
    }
    @Override //[WIP]
    public List<StationDisplayDTO> getAllStationsByLineId(int id){
        List<StationDisplayDTO> pStationsDTO = new ArrayList<>();
        StationDisplayDTO stationDetails = new StationDisplayDTO();
        List<Stations> stations = stationRepository.findAllStationsByLinesId(id);
        if (!(stations.isEmpty())){
            for (Stations s : stations) {
                stationDetails = modelMapper.map(s, StationDisplayDTO.class);
                stationDetails.setStation_isLast(boolLastStation(s.getIs_last_station()));
                stationDetails.setStation_status(boolToStatus(s.getStatus()));
                pStationsDTO.add(stationDetails);
            }
            return pStationsDTO;
        }
        throw new ResourceNotFoundException("No existing station/s with line id: " + id + " found");
    }
    @Override //[WORKING]
    public List<StationInputDTO> getAllActiveStationsByLineId(int id){
        List<StationInputDTO> stationInputDTO = new ArrayList<>();
        List<Stations> stations = stationRepository.findAllActiveStationsByLineId(id);
        if (!(stations.isEmpty())){
            for (Stations s : stations) {
                stationInputDTO.add(modelMapper.map(s, StationInputDTO.class));
            }
            return stationInputDTO;
        }
        throw new ResourceNotFoundException("Inactive or Non-existent station/s with line id: " + id);
    }
    @Override //[WORKING]
    public StationDisplayDTO getStationById(int id){
        StationDisplayDTO stationDisplayDTO = new StationDisplayDTO();
        Optional<Stations> existingStation = stationRepository.findById(id);
        if(existingStation.isPresent()) {
            stationDisplayDTO = modelMapper.map(existingStation.get(), StationDisplayDTO.class);
            stationDisplayDTO.setStation_status(boolToStatus(existingStation.get().getStatus()));
            stationDisplayDTO.setStation_isLast(boolLastStation(existingStation.get().getIs_last_station()));
            return stationDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing Station with id: " + id + " found");
    }
    @Override //[WORKING]
    public StationDisplayDTO addStationByLineId(int id, StationInputDTO stationDTO){
        Stations station = new Stations();
        station.setStation_name(stationDTO.getStation_name());
        station.setStation_alias(stationDTO.getStation_alias());
        station.setIs_last_station(stationDTO.getIs_last_station());
        station.setStatus(stationDTO.getStatus());
        station.setLines(getLine(id));
        StationDisplayDTO stationDetails =modelMapper.map(stationRepository.save(station), StationDisplayDTO.class);
        stationDetails.setStation_status(boolToStatus(station.getStatus()));
        stationDetails.setStation_isLast(boolLastStation(station.getIs_last_station()));
        return stationDetails;

        //can add constraints
        //ex.cant add station to an inactive Line
    }
    @Override //[WORKING]
    public StationDisplayDTO updateStation(int id, StationInputDTO stationInputDTO){
        StationDisplayDTO stationDisplayDTO = new StationDisplayDTO();
        Optional<Stations> existingStation = stationRepository.findById(id);
        if (existingStation.isPresent()){
            Stations station = existingStation.get();
            station.setStation_name(stationInputDTO.getStation_name());
            station.setStation_alias(stationInputDTO.getStation_alias());
            station.setIs_last_station(stationInputDTO.getIs_last_station());
            station.setStatus(stationInputDTO.getStatus());
            stationRepository.save(station);
            stationDisplayDTO = modelMapper.map(station, StationDisplayDTO.class);
            stationDisplayDTO.setStation_isLast(boolLastStation(station.getIs_last_station()));
            stationDisplayDTO.setStation_status(boolToStatus(station.getStatus()));
            return stationDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing station with id: " + id + " found");
    }

    @Override //[WORKING]
    public StationDisplayDTO softDeleteStation(int id){
        //cascading softDelete till ngrecords due to parent-child relationships
        stationRepository.softDeleteStationCascade(id);
        return getStationById(id);
    }
    @Override //[WORKING]
    public String hardDeleteStation(int id){
        Optional<Stations> station = stationRepository.findById(id);
        if (station.isPresent()){
            Stations s = station.get();
            stationRepository.delete(s);
            return "Deleted " + s.getStation_name() + " Station";
        }
        throw new ResourceNotFoundException("No existing station with id: " + id + " found");
    }




//---------------------------------Checklists---------------------------------
    @Override //[WORKING]
    public ChecklistDisplayDTO addChecklistByStationId(int id, ChecklistInputDTO checklistInputDTO){
        Checklists checklist = new Checklists();
        checklist.setChecklist_name(checklistInputDTO.getChecklist_name());
        checklist.setStatus(checklistInputDTO.getStatus());
        checklist.setStations(getStation(id));
        ChecklistDisplayDTO checklistDetails = modelMapper.map(checklistRepository.save(checklist), ChecklistDisplayDTO.class);
        checklistDetails.setChecklist_status(intToChecklistStatus(checklist.getStatus()));
        return checklistDetails;
    }
    @Override //[WORKING]
    public List<ChecklistDisplayDTO> getAllChecklist(){
        ChecklistDisplayDTO checklistDetails = new ChecklistDisplayDTO();
        List<ChecklistDisplayDTO> allChecklists = new ArrayList<>();
        List<Checklists> checklists = new ArrayList<>(checklistRepository.findAll());
        if (!(checklists.isEmpty())) {
            for (Checklists c : checklists) {
                checklistDetails = modelMapper.map(c, ChecklistDisplayDTO.class);
                checklistDetails.setChecklist_status(intToChecklistStatus(c.getStatus()));
                allChecklists.add(checklistDetails);
            }
            return allChecklists;
        }
        throw new ResourceNotFoundException("No checklists found");
    }
    @Override //[WORKING]
    public List<ChecklistDisplayDTO> getAllChecklistsByStationId(int id){
        List<ChecklistDisplayDTO> checklistDisplayDTO = new ArrayList<>();
        ChecklistDisplayDTO checklistDetails = new ChecklistDisplayDTO();
        List<Checklists> checklists = checklistRepository.findAllChecklistsByStationsId(id);
        if (!(checklists.isEmpty())){
            for (Checklists c : checklists) {
                checklistDetails = modelMapper.map(c, ChecklistDisplayDTO.class);
                checklistDetails.setChecklist_status(intToChecklistStatus(c.getStatus()));
                checklistDisplayDTO.add(checklistDetails);
            }
            return checklistDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing checklist/s with line id: " + id + " found");
    }
    @Override //[WORKING]
    public ChecklistDisplayDTO getChecklistById(int id){
        ChecklistDisplayDTO checklistDisplayDTO = new ChecklistDisplayDTO();
        Optional<Checklists> existingChecklist = checklistRepository.findById(id);
        if(existingChecklist.isPresent()) {
            checklistDisplayDTO = modelMapper.map(existingChecklist.get(), ChecklistDisplayDTO.class);
            checklistDisplayDTO.setChecklist_status(intToChecklistStatus(existingChecklist.get().getStatus()));
            return checklistDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing Checklist with id: " + id + " found");
    }
    @Override //[WORKING]
    public ChecklistDisplayDTO updateChecklist(int id, ChecklistInputDTO checklistInputDTO){
        ChecklistDisplayDTO checklistDetails = new ChecklistDisplayDTO();
        Optional<Checklists> existingChecklist = checklistRepository.findById(id);
        if (existingChecklist.isPresent()){
            Checklists checklist = existingChecklist.get();
            checklist.setChecklist_name(checklistInputDTO.getChecklist_name());
            checklist.setStatus(checklistInputDTO.getStatus());
            checklistRepository.save(checklist);
            checklistDetails = modelMapper.map(checklist, ChecklistDisplayDTO.class);
            checklistDetails.setChecklist_status(intToChecklistStatus(checklist.getStatus()));
            return checklistDetails;
        }
        throw new ResourceNotFoundException("No existing checklist with id: " + id + " found");
    }

    @Override //[WORKING]
    public ChecklistDisplayDTO softDeleteChecklist(int id){
        //cascading softDelete till ngrecords due to parent-child relationship
        checklistRepository.softDeleteChecklist(id);
        ngrecordRepository.softDeleteNgrecordsByChecklist(id);
        return getChecklistById(id);
    }

    @Override //[WORKING]
    public String hardDeleteChecklist(int id){
        Optional<Checklists> checklist = checklistRepository.findById(id);
        if (checklist.isPresent()){
            Checklists c = checklist.get();
            checklistRepository.deleteById(id);
            return "Deleted " + c.getChecklist_name() + " checklist";
        }
        throw new ResourceNotFoundException("No existing checklist with id: " + id + " found");
    }




//---------------------------------NgRecords---------------------------------
@Override //[WORKING]
public NgrecordDisplayDTO addNgrecordByChecklistId(int id, NgrecordInputDTO ngrecordInputDTO){
    Ngrecords ngrecord = new Ngrecords();
    ngrecord.setItem_name(ngrecordInputDTO.getItem_name());
    ngrecord.setIs_priority(ngrecordInputDTO.getIs_priority());
    ngrecord.setStatus(ngrecordInputDTO.getStatus());
    ngrecord.setChecklists(getChecklist(id));
    NgrecordDisplayDTO ngrecordDetails = modelMapper.map(ngrecordRepository.save(ngrecord), NgrecordDisplayDTO.class);
    ngrecordDetails.setItem_status(intToItemStatus(ngrecord.getStatus()));
    ngrecordDetails.setItem_priority(intToItemPriority(ngrecord.getIs_priority()));
    return ngrecordDetails;
}
    @Override //[WORKING]
    public List<NgrecordDisplayDTO> getAllNgrecords(){
        NgrecordDisplayDTO ngrecordDetails = new NgrecordDisplayDTO();
        List<NgrecordDisplayDTO> allNgrecords = new ArrayList<>();
        List<Ngrecords> ngrecords = new ArrayList<>(ngrecordRepository.findAll());
        if (!(ngrecords.isEmpty())) {
            for (Ngrecords n : ngrecords) {
                ngrecordDetails = modelMapper.map(n, NgrecordDisplayDTO.class);
                ngrecordDetails.setItem_priority(intToItemPriority(n.getIs_priority()));
                ngrecordDetails.setItem_status(intToItemStatus(n.getStatus()));
                allNgrecords.add(ngrecordDetails);
            }
            return allNgrecords;
        }
        throw new ResourceNotFoundException("No ngrecords found");
    }
    @Override //[WORKING]
    public List<NgrecordDisplayDTO> getAllNgrecordsByChecklistId(int id){
        List<NgrecordDisplayDTO> ngrecordDisplayDTO = new ArrayList<>();
        NgrecordDisplayDTO ngrecordDetails = new NgrecordDisplayDTO();
        List<Ngrecords> ngrecords = ngrecordRepository.findAllNgrecordsByChecklistsId(id);
        if (!(ngrecords.isEmpty())){
            for (Ngrecords n : ngrecords) {
                ngrecordDetails = modelMapper.map(n, NgrecordDisplayDTO.class);
                ngrecordDetails.setItem_status(intToItemStatus(n.getStatus()));
                ngrecordDetails.setItem_priority(intToItemPriority(n.getIs_priority()));
                ngrecordDisplayDTO.add(ngrecordDetails);
            }
            return ngrecordDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing ngrecord/s with checklist id: " + id + " found");
    }
    @Override //[WORKING]
    public NgrecordDisplayDTO getNgrecordById(int id){
        NgrecordDisplayDTO pNgrecordDetails = new NgrecordDisplayDTO();
        Optional<Ngrecords> existingNgrecord = ngrecordRepository.findById(id);
        if(existingNgrecord.isPresent()) {
            Ngrecords ngrecords = existingNgrecord.get();
            pNgrecordDetails = modelMapper.map(ngrecords, NgrecordDisplayDTO.class);
            pNgrecordDetails.setItem_priority(intToItemPriority(ngrecords.getIs_priority()));
            pNgrecordDetails.setItem_status(intToItemStatus(ngrecords.getStatus()));
            return pNgrecordDetails;
        }
        throw new ResourceNotFoundException("No existing ngrecord with id: " + id + " found");
    }
    @Override //[WORKING]
    public NgrecordDisplayDTO updateNgrecord(int id, NgrecordInputDTO ngrecordInputDTO){
        NgrecordDisplayDTO ngrecordDetails = new NgrecordDisplayDTO();
        Optional<Ngrecords> existingNgrecord = ngrecordRepository.findById(id);
        if (existingNgrecord.isPresent()){
            Ngrecords ngrecords = existingNgrecord.get();
            ngrecords.setItem_name(ngrecordInputDTO.getItem_name());
            ngrecords.setIs_priority(ngrecordInputDTO.getIs_priority());
            ngrecords.setStatus(ngrecordInputDTO.getStatus());
            ngrecordDetails = modelMapper.map(ngrecordRepository.save(ngrecords), NgrecordDisplayDTO.class);
            ngrecordDetails.setItem_status(intToItemStatus(ngrecords.getStatus()));
            ngrecordDetails.setItem_priority(intToItemPriority(ngrecords.getIs_priority()));
            return ngrecordDetails;
        }
        throw new ResourceNotFoundException("No existing ngrecord with id: " + id + " found");
    }

    @Override //[WORKING]
    public NgrecordDisplayDTO softDeleteNgrecord(int id){
        ngrecordRepository.softDeleteNgrecord(id);
        return getNgrecordById(id);
    }

    @Override //[WORKING]
    public String hardDeleteNgrecord(int id){
        Optional<Ngrecords> ngrecords = ngrecordRepository.findById(id);
        if (ngrecords.isPresent()){
            Ngrecords n = ngrecords.get();
            ngrecordRepository.deleteById(id);
            return "Deleted ngrecord with item name: " + n.getItem_name();
        }
        throw new ResourceNotFoundException("No existing ngrecord with id: " + id + " found");
    }





//----------------------------Protected Methods----------------------------
    protected Lines getLine(int id) {
        Optional<Lines> existingLine = lineRepository.findById(id);
        if(existingLine.isPresent()) {
            return existingLine.get();
        }
        else {
            throw new ResourceNotFoundException("Non-existent line with id: " + id);
        }
    }
    protected Stations getStation(int id) {
        Optional<Stations> existingStation = stationRepository.findById(id);
        if(existingStation.isPresent()) {
            return existingStation.get();
        }
        else {
            throw new ResourceNotFoundException("Non-existent station with id: " + id);
        }
    }
    protected Checklists getChecklist(int id) {
        Optional<Checklists> existingChecklist = checklistRepository.findById(id);
        if(existingChecklist.isPresent()) {
            return existingChecklist.get();
        }
        else {
            throw new ResourceNotFoundException("Non-existent checklist with id: " + id);
        }
    }
    protected Ngrecords getNgrecord(int id) {
        Optional<Ngrecords> existingNgrecord = ngrecordRepository.findById(id);
        if(existingNgrecord.isPresent()) {
            return existingNgrecord.get();
        }
        else {
            throw new ResourceNotFoundException("Non-existent ngrecord with id: " + id);
        }
    }
    protected Stations addStation(StationInputDTO stationDTO){
        Stations station = new Stations();
        station.setStation_name(stationDTO.getStation_name());
        station.setStation_alias(stationDTO.getStation_alias());
        station.setIs_last_station(stationDTO.getIs_last_station());
        station.setStatus(stationDTO.getStatus());
        return stationRepository.save(station);
    }
    protected Set<Stations> setStations(Set<Stations> set){
        if (set == null){
            return null;
        }

        Set<Stations> stationsSet = new HashSet<Stations>();
        for (Stations station : set) {
            StationInputDTO stationInputDTO = modelMapper.map(station, StationInputDTO.class);
            stationsSet.add(addStation(stationInputDTO));
        }
        return stationsSet;
    }
    protected String getStationName(Set<Stations> set){
        String sName = "";
        if (set == null){
            return null;
        }

        for (Stations station : set) {
            sName = station.getStation_name();
        }
        return sName;
    }
    protected String getStationAlias(Set<Stations> set){
        String sAlias = "";
        if (set == null){
            return null;
        }

        for (Stations station : set) {
            sAlias = station.getStation_alias();
        }
        return sAlias;
    }
    protected int isLastStation(Set<Stations> set){
        int isLastStation = 0;
        if (set == null){
            return 0;
        }

        for (Stations station : set) {
            isLastStation = station.getIs_last_station();
        }
        return isLastStation;
    }
    protected int getStatus(Set<Stations> set){
        int status = 0;
        if (set == null){
            return 0;
        }

        for (Stations station : set) {
            status = station.getStatus();
        }
        return status;
    }

    protected String boolToStatus(int boolVal){
        if (boolVal == 1){
            return "Active";
        }
        else{
            return "Inactive";
        }
    }

    protected String intToChecklistStatus(int boolVal){
        if (boolVal == 1){
            return "Checked";
        }
        else if (boolVal == 2){
            return "In Progress";
        }
        else if (boolVal == 3){
            return "Unchecked";
        }
        else{
            return "Disabled";
        }
    }

    protected String intToItemStatus(int boolVal){
        if (boolVal == 1){
            return "Available";
        }
        else if (boolVal == 2){
            return "Damaged";
        }
        else if (boolVal == 3){
            return "In Stock";
        }
        else{
            return "Disabled";
        }
    }
    protected String intToItemPriority(int boolVal){
        if (boolVal == 1){
            return "High Priority";
        }
        else if (boolVal == 2){
            return "Mid Priority";
        }
        else if (boolVal == 3){
            return "Low Priority";
        }
        else{
            return "Disabled";
        }
    }

    protected String boolLastStation(int boolVal){
        if (boolVal == 1){
            return "Yes";
        }
        else{
            return "No";
        }
    }
}
