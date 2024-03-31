package com.maids.salesManagementSystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(max = 255)
    private String name;

    @NotBlank
    @Length(max = 255)
    private String lastName;

    @NotBlank
    @Length(max = 20)
    private String mobile;

    @NotBlank
    @Email
    @Length(max = 255)
    private String email;

    @NotBlank
    @Length(max = 255)
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
