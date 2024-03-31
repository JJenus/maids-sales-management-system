package com.maids.salesManagementSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product implements Auditable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
