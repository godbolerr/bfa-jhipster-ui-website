package com.bfa.app.repository;

import com.bfa.app.domain.CheckInRecord;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CheckInRecord entity.
 */
@SuppressWarnings("unused")
public interface CheckInRecordRepository extends JpaRepository<CheckInRecord,Long> {

}
