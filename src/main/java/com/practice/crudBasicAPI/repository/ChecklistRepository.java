package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Checklists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ChecklistRepository extends JpaRepository<Checklists,Integer> {
    List<Checklists> findByStationsId(int id);
    List<Checklists> findAllChecklistsByStationsId(int id);
    @Modifying
    @Query(
            value = "UPDATE tbl_checklists SET status = 0 WHERE station_id = ?1",
            nativeQuery = true)
    void softDeleteChecklistsByStation(int station_id);
    @Modifying
    @Query(
            value = "UPDATE tbl_checklists SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteChecklist(int id);
}
