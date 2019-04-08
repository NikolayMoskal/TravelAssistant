package by.neon.travelassistant.activity.query;

import java.util.List;

import by.neon.travelassistant.activity.query.select.SelectQuery;
import by.neon.travelassistant.activity.query.select.WhereQuery;

public class QuerySet {
    private SelectQuery select;
    private List<WhereQuery> expressions;

    public QuerySet(SelectQuery select, List<WhereQuery> expressions) {
        this.select = select;
        this.expressions = expressions;
    }

    public String getSelectQuery() {
        return select.toString();
    }

    public String getWhereQuery() {
        return expressions.toString();
    }
}
