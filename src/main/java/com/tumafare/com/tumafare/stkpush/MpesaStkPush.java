package com.tumafare.com.tumafare.stkpush;

import com.tumafare.com.tumafare.common.BaseEntity;
import com.tumafare.com.tumafare.stkpush.sale.Sale;
import com.tumafare.com.tumafare.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class MpesaStkPush extends BaseEntity {
    private String amount;

    private String userPhoneNumber;

    private String clientPhoneNumber;

    private String mpesaStatus;

    private boolean isPaymentApproved;

    private String paymentStatus;

    private String invoiceId;

    private String trackingId;

    @OneToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH
            }
    )
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
