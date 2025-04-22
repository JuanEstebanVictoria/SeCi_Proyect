package co.edu.uniquindio.seci_proyect.dtos.category;

public record CategoryWithStatsResponse(
        CategoryResponse category,
        long reportCount
) {}