package org.SprintForge.platform.search.service;

import org.SprintForge.platform.search.domain.PlatformSearchIndex;
import org.SprintForge.platform.search.dto.SearchFilterRule;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("platformSpecificationBuilderService")
public class SpecificationBuilderService {

    public Specification<PlatformSearchIndex> buildSpecification(String query, String workspaceId, String projectId, List<SearchFilterRule> filters, String matchMode) {
        Specification<PlatformSearchIndex> spec = (root, q, cb) -> cb.conjunction();

        if (workspaceId != null && !workspaceId.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("workspaceId"), workspaceId));
        }

        if (projectId != null && !projectId.isBlank()) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("projectId"), projectId));
        }

        if (query != null && !query.isBlank()) {
            Specification<PlatformSearchIndex> querySpec = (root, q, cb) -> cb.or(
                    cb.like(cb.lower(root.get("title")), "%" + query.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("content")), "%" + query.toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("keywords")), "%" + query.toLowerCase() + "%")
            );
            spec = spec.and(querySpec);
        }

        if (filters != null && !filters.isEmpty()) {
            for (SearchFilterRule rule : filters) {
                Specification<PlatformSearchIndex> ruleSpec = buildRuleSpecification(rule);
                if (ruleSpec != null) {
                    if ("OR".equalsIgnoreCase(matchMode)) {
                        spec = spec.or(ruleSpec);
                    } else {
                        spec = spec.and(ruleSpec);
                    }
                }
            }
        }

        return spec;
    }

    private Specification<PlatformSearchIndex> buildRuleSpecification(SearchFilterRule rule) {
        if (rule.getField() == null || rule.getOperator() == null) return null;
        String field = rule.getField();
        String op = rule.getOperator().toUpperCase();

        return (root, q, cb) -> {
            switch (op) {
                case "EQUALS":
                    return cb.equal(root.get(field), rule.getValue());
                case "NOT_EQUALS":
                    return cb.notEqual(root.get(field), rule.getValue());
                case "LIKE":
                    return cb.like(cb.lower(root.get(field)), "%" + rule.getValue().toLowerCase() + "%");
                case "IN":
                    if (rule.getValues() != null && !rule.getValues().isEmpty()) {
                        return root.get(field).in(rule.getValues());
                    }
                    return null;
                default:
                    return null;
            }
        };
    }
}
