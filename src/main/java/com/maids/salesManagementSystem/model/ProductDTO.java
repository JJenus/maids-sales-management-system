package com.maids.salesManagementSystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotBlank
    @Length(max = 50)
    private String category;

    @Min(value = 0)
    private int quantity;

    @DecimalMin(value = "0.0")
    private double price;

    @NotNull
    private LocalDateTime creationDate;

    @PrePersist
    public void onInsert(){
        creationDate = LocalDateTime.now();
    }
}
