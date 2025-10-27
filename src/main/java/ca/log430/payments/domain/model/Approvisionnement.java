package ca.log430.payments.domain.model;

// Entity :

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
@Table(name = "approvisionnements")
public class Approvisionnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    private Integer userId;

    private Integer amount;

    @Pattern(regexp = "^\\d{16}$", message = "Le numéro de carte doit contenir exactement 16 chiffres.")
    private String numeroCarte;

    @Pattern(regexp = "^\\d{3,4}$", message = "Le CVV doit contenir exactement 3 ou 4 chiffres.")
    private String cvv;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/(\\d{2})$", message = "La date d'expiration doit être au format MM/AA.")
    private String expirationDate;


    public Approvisionnement() {

    }

    public String getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private ApprovisionnementType status = ApprovisionnementType.PENDING;
    public ApprovisionnementType getStatus() {
        return status;
    }

    public void setStatus(ApprovisionnementType status) {
        this.status = status;
    }

    public Approvisionnement(LocalDateTime createdAt, String cvv, String expirationDate) {
        this.createdAt = createdAt;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
    }
    public Approvisionnement(String cvv, String expirationDate) {
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.createdAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
