package com.banksolutions.ebank.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beneficiaries")
public class Beneficiary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    @Column(nullable = false)
    private Long number;
    private String bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @OneToMany(mappedBy = "beneficiary", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transfer> transfers;
}
