package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Stations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StationRepository extends JpaRepository<Stations,Integer> {
    List<Stations> findByLinesId(int id);
    List<Stations> findAllStationsByLinesId(int id);
    @Query(
            value = "SELECT * FROM tbl_stations WHERE line_id = ?1 AND status = 1",
            nativeQuery = true)
    List<Stations> findAllActiveStationsByLineId(int id);
    @Modifying
    @Query(
            value = "UPDATE tbl_stations SET status = 0 WHERE line_id = ?1",
            nativeQuery = true)
    void softDeleteStationsByLine(int line_id);
    @Modifying
    @Query(
            value = "UPDATE tbl_stations SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteStation(int id);

    @Modifying
    @Query(value = "UPDATE tbl_stations ts " +
            "JOIN tbl_checklists tc ON ts.id = tc.station_id " +
            "JOIN tbl_ngrecords tn ON tc.id = tn.checklist_id " +
            "SET ts.status = 0, tc.status = 0, tn.status = 0 " +
            "WHERE ts.id = ?1",
            nativeQuery = true)
        //works only if every parent has child, otherwise wont cascade properly
    void softDeleteStationCascade(int id);
}
