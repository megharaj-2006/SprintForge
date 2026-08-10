package org.SprintForge.platform.search.dto;

import java.util.List;

public class SearchFilterRule {
    private String field;
    private String operator;
    private String value;
    private List<String> values;

    public SearchFilterRule() {}

    public SearchFilterRule(String field, String operator, String value, List<String> values) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.values = values;
    }

    public static SearchFilterRuleBuilder builder() {
        return new SearchFilterRuleBuilder();
    }

    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public List<String> getValues() { return values; }
    public void setValues(List<String> values) { this.values = values; }

    public static class SearchFilterRuleBuilder {
        private String field;
        private String operator;
        private String value;
        private List<String> values;

        public SearchFilterRuleBuilder field(String field) { this.field = field; return this; }
        public SearchFilterRuleBuilder operator(String operator) { this.operator = operator; return this; }
        public SearchFilterRuleBuilder value(String value) { this.value = value; return this; }
        public SearchFilterRuleBuilder values(List<String> values) { this.values = values; return this; }

        public SearchFilterRule build() {
            return new SearchFilterRule(field, operator, value, values);
        }
    }
}
