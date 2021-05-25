package com.mercadolibre.mutantverifier.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stats_error")
public class StatsError {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name="book_generator", sequenceName = "seq_stats_error", allocationSize=1)
    @Column(name = "id")
    private Integer id;

    private String dna;

    private Boolean isMutant;

    private String errorMessage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_error")
    private Date dateErrror;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public Boolean getMutant() {
        return isMutant;
    }

    public void setMutant(Boolean mutant) {
        isMutant = mutant;
    }

    public Date getDateErrror() {
        return dateErrror;
    }

    public void setDateErrror(Date dateErrror) {
        this.dateErrror = dateErrror;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
