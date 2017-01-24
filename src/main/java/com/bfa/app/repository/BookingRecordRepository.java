package com.bfa.app.repository;

import com.bfa.app.domain.BookingRecord;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BookingRecord entity.
 */
@SuppressWarnings("unused")
public interface BookingRecordRepository extends JpaRepository<BookingRecord,Long> {

}
