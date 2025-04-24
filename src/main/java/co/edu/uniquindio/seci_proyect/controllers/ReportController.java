package co.edu.uniquindio.seci_proyect.controllers;


import co.edu.uniquindio.seci_proyect.dtos.category.CategoryResponse;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportRequest;
import co.edu.uniquindio.seci_proyect.dtos.report.ReportResponse;
import co.edu.uniquindio.seci_proyect.services.interfaces.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import co.edu.uniquindio.seci_proyect.services.interfaces.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private ReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponse> createReport(@Valid @RequestBody ReportRequest  report) {
        var response = reportService.createReport(report);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable("id") String id) {
        return reportService.getReport(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }



}
