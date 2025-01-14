package com.crm.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "trainers")
@DynamicUpdate
public class Trainer extends User {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization", nullable = false)
    private TrainingType specialization;

    @ManyToMany(mappedBy = "trainers")
    @Builder.Default
    private Set<Trainee> trainees = new HashSet<>();
}
