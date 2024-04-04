package com.ardb.spring_test.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category {

    @Id
    private int id;

    @NotBlank(message = "Name is required")
    private String name;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.REMOVE)
    private List<Product> products;
}
