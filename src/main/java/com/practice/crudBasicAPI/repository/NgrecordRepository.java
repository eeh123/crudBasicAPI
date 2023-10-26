package com.practice.crudBasicAPI.repository;

import com.practice.sb_finalActivity.entity.Ngrecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NgrecordRepository extends JpaRepository<Ngrecords,Integer> {
    List<Ngrecords> findAllNgrecordsByChecklistsId(int id);

    @Query(
            value = "SELECT r.checklist_id FROM tbl_ngrecords r " +
                    "JOIN tbl_checklists c ON c.id = r.checklist_id " +
                    "WHERE c.station_id = ?1",
            nativeQuery = true)
    int[] selectChecklistIDByStationId(int station_id);
    @Modifying
    @Query(
            value = "UPDATE tbl_ngrecords SET status = 0 WHERE checklist_id = ?1",
            nativeQuery = true)
    void softDeleteNgrecordsByChecklist(int checklist_id);
    @Modifying
    @Query(
            value = "UPDATE tbl_ngrecords SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteNgrecord(int id);
}
