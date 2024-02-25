package com.ventionteams.applicationexchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ventionteams.applicationexchange.entity.Location;
import com.ventionteams.applicationexchange.entity.enumeration.LengthUnit;
import com.ventionteams.applicationexchange.entity.enumeration.LotStatus;
import com.ventionteams.applicationexchange.entity.enumeration.Packaging;
import com.ventionteams.applicationexchange.entity.enumeration.Weight;

import java.time.Instant;

public record LotReadDTO(
        @JsonProperty("lot_id")
        Long id,
        String title,
        @JsonProperty("category_id")
        Integer categoryId,
        @JsonProperty("category_name")
        String category,
        Long quantity,
        Weight weight,
        @JsonProperty("price_per_unit")
        Double pricePerUnit,
        Location location,
        String description,
        LotStatus status,
//    @JsonProperty("image_url")
//    String imageUrl,
        @JsonProperty("expiration_date")
        Instant expirationDate,
        String variety,
        Integer size,
        Packaging packaging,
        @JsonProperty("created_at")
        Instant createdAt,
        @JsonProperty("total_price")
        Double totalPrice,
        @JsonProperty("length_unit")
        LengthUnit lengthUnit,
        BidLotReadDto bid) {
}