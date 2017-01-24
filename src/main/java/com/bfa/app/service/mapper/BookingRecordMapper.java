package com.bfa.app.service.mapper;

import com.bfa.app.domain.*;
import com.bfa.app.service.dto.BookingRecordDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity BookingRecord and its DTO BookingRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookingRecordMapper {

    BookingRecordDTO bookingRecordToBookingRecordDTO(BookingRecord bookingRecord);

    List<BookingRecordDTO> bookingRecordsToBookingRecordDTOs(List<BookingRecord> bookingRecords);

    @Mapping(target = "bookPsrs", ignore = true)
    BookingRecord bookingRecordDTOToBookingRecord(BookingRecordDTO bookingRecordDTO);

    List<BookingRecord> bookingRecordDTOsToBookingRecords(List<BookingRecordDTO> bookingRecordDTOs);
}
