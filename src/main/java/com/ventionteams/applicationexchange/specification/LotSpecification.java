package com.ventionteams.applicationexchange.specification;

import com.ventionteams.applicationexchange.dto.create.LotFilterDTO;
import com.ventionteams.applicationexchange.entity.Lot;
import com.ventionteams.applicationexchange.entity.enumeration.LotStatus;
import com.ventionteams.applicationexchange.entity.enumeration.Packaging;
import com.ventionteams.applicationexchange.entity.enumeration.Weight;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class LotSpecification {

    private LotSpecification() {

    }

    public static Specification<Lot> getFilterSpecification(LotFilterDTO filter) {
        return Specification.where(filter.category() == null ? null : inCategories(filter.category()))
                .or(filter.category() == null ? null : inParentCategories(filter.category()))
                .and(isEmpty(filter.packaging()) ? null : inPackaging(filter.packaging()))
                .and(isEmpty(filter.weights()) ? null : inWeight(filter.weights()))
                .and(isEmpty(filter.countries()) ? null : inCountries(filter.countries()))
                .and(isEmpty(filter.cities()) ? null : inCities(filter.cities()))
                .and(isEmpty(filter.varieties()) ? null : inVarieties(filter.varieties()))
                .and(filter.fromQuantity() == null ? null : fromQuantity(filter.fromQuantity()))
                .and(filter.toQuantity() == null ? null : toQuantity(filter.toQuantity()))
                .and(filter.fromSize() == null ? null : fromSize(filter.fromSize()))
                .and(filter.toSize() == null ? null : toSize(filter.toSize()))
                .and(filter.fromPrice() == null ? null : fromPrice(filter.fromPrice()))
                .and(filter.toPrice() == null ? null : toPrice(filter.toPrice()))
                .and(filter.lotStatus() == null ? null : inLotStatus(getStatusList(filter.lotStatus())))
                .and(filter.keyword() == null ? null : byKeyword(filter.keyword()));
    }

    public static Specification<Lot> inCategories(Integer category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), category);
    }

    public static Specification<Lot> inParentCategories(Integer category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("parent").get("id"), category);
    }

    private static Specification<Lot> inPackaging(List<Packaging> packaging) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<Packaging> inClause = criteriaBuilder.in(root.get("packaging"));
            for (Packaging pack : packaging) {
                inClause.value(pack);
            }
            return inClause;
        };
    }

    private static Specification<Lot> inWeight(List<Weight> weights) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<Weight> inClause = criteriaBuilder.in(root.get("weight"));
            for (Weight weight : weights) {
                inClause.value(weight);
            }
            return inClause;
        };
    }

    private static Specification<Lot> inCountries(List<String> countries) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("location").get("country"));
            for (String country : countries) {
                inClause.value(country);
            }
            return inClause;
        };
    }

    private static Specification<Lot> inCities(List<String> cities) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("location").get("region"));
            for (String city : cities) {
                inClause.value(city);
            }
            return inClause;
        };
    }

    private static Specification<Lot> inVarieties(List<Integer> varieties) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<Integer> inClause = criteriaBuilder.in(root.get("category").get("id"));
            for (Integer variety : varieties) {
                inClause.value(variety);
            }
            return inClause;
        };
    }

    private static Specification<Lot> fromQuantity(Long fromQuantity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), fromQuantity);
    }

    private static Specification<Lot> toQuantity(Long toQuantity) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("quantity"), toQuantity);
    }

    private static Specification<Lot> fromSize(Integer fromSize) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("fromSize"), fromSize);
    }

    private static Specification<Lot> toSize(Integer toSize) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("toSize"), toSize);
    }

    private static Specification<Lot> inLotStatus(List<LotStatus> lotStatuses) {
        return (root, query, criteriaBuilder) -> {
            CriteriaBuilder.In<LotStatus> inClause = criteriaBuilder.in(root.get("status"));
            lotStatuses.forEach(inClause::value);
            return inClause;
        };
    }

    private static Specification<Lot> fromPrice(Integer fromPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("totalPrice"), fromPrice);
    }

    private static Specification<Lot> toPrice(Integer toPrice) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("totalPrice"), toPrice);
    }

    private static Specification<Lot> byKeyword(String keyword) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.literal("KEYWORD"), criteriaBuilder.literal(keyword));
    }

    private static List<LotStatus> getStatusList(String statuses) {
        return Arrays.stream(statuses.split(","))
                .map(String::trim)
                .map(LotStatus::valueOf)
                .toList();
    }
}
