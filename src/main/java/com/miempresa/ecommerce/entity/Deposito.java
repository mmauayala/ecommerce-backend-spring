package com.miempresa.ecommerce.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad de depósito/almacén para gestión de stock por ubicación
 * 
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deposito")
public class Deposito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre de deposito es requerido")
    @Size(max = 100, message = "Nombre de deposi no debe exceder los 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Size(max = 255, message = "Address o debe exceder los 255 caracteres")
    @Column(name = "address")
    private String address;

    @Size(max = 100, message = "City no debe exceder los 100 caracteres")
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 100, message = "Country no debe exceder los 100 caracteres")
    @Column(name = "country", length = 100)
    private String country;

    @Size(max = 20, message = "Postal code o debe exceder los 20 caracteres")
    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Size(max = 20, message = "Phone o debe exceder los 20 caracteres")
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 100, message = "Email no debe exceder los 100 caracteres")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "is_main", nullable = false)
    private Boolean isMain = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "deposito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductStock> productStocks = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
