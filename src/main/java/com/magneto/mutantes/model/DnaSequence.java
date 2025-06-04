package com.magneto.mutantes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dna_sequences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DnaSequence {

    @Id
    private String dnaHash;

    @Column(nullable = false)
    private boolean isMutant;

}
