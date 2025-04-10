package co.edu.uniquindio.seci_proyect.mappers;

import co.edu.uniquindio.seci_proyect.Model.Report;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReportMapper {
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "votes", expression = "java(new ArrayList<>())")
    @Mapping(target = "comments", expression = "java(new ArrayList<>())")
    Report parseOf(ReportRequest reportRequest);

    ReportResponse toReportResponse(Report report);

}
