package com.ventionteams.applicationexchange.repository;

import com.ventionteams.applicationexchange.entity.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LotRepository extends
        JpaRepository<Lot, Long>,
        JpaSpecificationExecutor<Lot>
{
    Page<Lot> findAllByCategoryId(Integer id, Pageable pageable);

    @Modifying
    @Query(
            value = """
            UPDATE lots
            SET status = CASE
                             WHEN bid_quantity = 0 THEN 'EXPIRED'
                             WHEN bid_quantity > 0 THEN 'SOLD'
            ELSE status
            END
            WHERE expiration_date < NOW();
            """,
            nativeQuery = true
    )
    int updateExpiredLots();
}
