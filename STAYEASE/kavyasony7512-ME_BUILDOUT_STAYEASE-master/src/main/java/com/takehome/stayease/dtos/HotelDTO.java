package com.takehome.stayease.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private long id;

    @NotBlank(message = "Hotel name cannot be empty")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    private String location;
    
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Total rooms cannot be null")
    @Min(value = 1, message = "Total rooms must be at least 1")
    private int totalRooms;

    @NotNull(message = "Available rooms cannot be null")
    @Min(value = 0, message = "Available rooms cannot be negative")
    private int availableRooms;

}