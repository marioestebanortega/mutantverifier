package com.mercadolibre.mutantverifier.entity;

import javax.persistence.*;

@Entity
@Table(name = "stats")
public class Stats {

    @Id
    @Column(name = "dna")
    private String dna;

    private Boolean isMutant;

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

}
