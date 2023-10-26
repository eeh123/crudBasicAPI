package com.practice.crudBasicAPI.repository;

import com.practice.sb_finalActivity.entity.Lines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LineRepository extends JpaRepository<Lines, Integer> {
    @Modifying
    @Query(value = "UPDATE tbl_lines SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteLine(int id);

    @Modifying
    @Query(value = "UPDATE tbl_lines tl " +
            "JOIN tbl_stations ts ON tl.id = ts.line_id " +
            "JOIN tbl_checklists tc ON ts.id = tc.station_id " +
            "JOIN tbl_ngrecords tn ON tc.id = tn.checklist_id " +
            "SET tl.status = 0, ts.status = 0, tc.status = 0, tn.status = 0 " +
            "WHERE tl.id = ?1",
            nativeQuery = true)
    //works only if every parent has child, otherwise wont cascade properly
    void softDeleteLineCascade(int id);


}
