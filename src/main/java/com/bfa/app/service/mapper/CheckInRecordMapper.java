package com.bfa.app.service.mapper;

import com.bfa.app.domain.*;
import com.bfa.app.service.dto.CheckInRecordDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CheckInRecord and its DTO CheckInRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CheckInRecordMapper {

    CheckInRecordDTO checkInRecordToCheckInRecordDTO(CheckInRecord checkInRecord);

    List<CheckInRecordDTO> checkInRecordsToCheckInRecordDTOs(List<CheckInRecord> checkInRecords);

    CheckInRecord checkInRecordDTOToCheckInRecord(CheckInRecordDTO checkInRecordDTO);

    List<CheckInRecord> checkInRecordDTOsToCheckInRecords(List<CheckInRecordDTO> checkInRecordDTOs);
}
