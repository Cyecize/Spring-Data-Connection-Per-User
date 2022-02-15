package com.cyecize.demo.api.task;

import com.cyecize.demo.util.sorting.Direction;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import java.util.Arrays;

public final class TaskSpecifications {

    public static Specification<Task> userIdEquals(Long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Task_.userId), userId);
    }

    public static Specification<Task> hasDueDate(Boolean hasDueDate) {
        if (hasDueDate == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> {
            var predicate = root.get(Task_.dueDate).isNull();
            if (hasDueDate) {
                predicate = criteriaBuilder.not(predicate);
            }

            return predicate;
        };
    }

    public static Specification<Task> isInProgress(Boolean inProgress) {
        if (inProgress == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.isTrue(root.get(Task_.inProgress));
            if (!inProgress) {
                predicate = criteriaBuilder.not(predicate);
            }

            return predicate;
        };
    }

    public static Specification<Task> descriptionContains(String description) {
        if (description == null) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root.get(Task_.description)),
                "%" + description.toLowerCase() + "%",
                '\\'
        );
    }

    public static Specification<Task> sort(String fieldName, Direction direction) {
        final boolean validField = Arrays.stream(Task_.class.getDeclaredFields())
                .anyMatch(f -> f.getName().equals(fieldName));

        if (!validField) {
            return Specification.where(null);
        }

        return (root, query, criteriaBuilder) -> {
            final Class<?> resultType = query.getResultType();

            //Do not sort count queries.
            if (resultType.equals(Long.class) || resultType.equals(long.class)) {
                return criteriaBuilder.conjunction();
            }

            final Path<?> field = root.get(fieldName);
            Order order = criteriaBuilder.asc(field);
            if (direction == Direction.DESC) {
                order = criteriaBuilder.desc(field);
            }

            query.orderBy(order);

            return criteriaBuilder.conjunction();
        };
    }
}
