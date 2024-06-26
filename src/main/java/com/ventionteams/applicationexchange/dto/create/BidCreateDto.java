package com.ventionteams.applicationexchange.dto.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ventionteams.applicationexchange.entity.enumeration.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class BidCreateDto {
    @JsonIgnore
    private UUID userId;
    @JsonProperty("lot_id")
    @NotNull
    private Long lotId;
    @NotNull
    @Min(0)
    private Double amount;
    @NotNull
    private Currency currency;
}
