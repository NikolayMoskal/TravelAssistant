package by.neon.travelassistant.activity.query;

import by.neon.travelassistant.activity.query.select.WhereQuery;

public class QuerySet {
    private WhereQuery expression;

    public QuerySet(WhereQuery expression) {
        this.expression = expression;
    }

    public String getWhereQuery() {
        return expression != null
                ? expression.build()
                : null;
    }
}
