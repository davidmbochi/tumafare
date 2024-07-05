package com.tumafare.com.tumafare.stkpush;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MpesaStkPushRepository extends JpaRepository<MpesaStkPush, Integer> {
    @Query(
            """
            SELECT mpesa_stk_push
            FROM MpesaStkPush mpesa_stk_push
            WHERE mpesa_stk_push.owner.id = :ownerId
            """
    )
    List<MpesaStkPush> findByOwnerId(Integer ownerId);

    @Modifying
    @Query("""
            DELETE FROM MpesaStkPush mpesa_stk_push
            WHERE mpesa_stk_push.id = :paymentId
            """)
    void deleteById(Integer paymentId);
}
