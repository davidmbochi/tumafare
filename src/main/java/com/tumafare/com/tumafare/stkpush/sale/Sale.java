package com.tumafare.com.tumafare.stkpush.sale;

import com.tumafare.com.tumafare.common.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Sale extends BaseEntity {
    private String sale;
}
