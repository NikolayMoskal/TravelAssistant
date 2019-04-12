package by.neon.travelassistant.activity.query.select;

import java.util.ArrayList;
import java.util.List;

/**
 * Constructs a set of expressions used by SQL WHERE clause.
 */
public class WhereQuery {
    /**
     * Indicates all set of expressions as negative (with NOT operator).
     */
    private boolean isNegativeExpression;
    /**
     * The set of expressions.
     */
    private List<Expression> expressions;

    /**
     * Build a new instance of {@link WhereQuery}.
     *
     * @param isNegativeExpression {@link #isNegativeExpression}
     */
    public WhereQuery(boolean isNegativeExpression) {
        this.isNegativeExpression = isNegativeExpression;
        expressions = new ArrayList<>(0);
    }

    /**
     * Builds a string representation of expression set. The first expression is always uses as filter (AND operator). Other expressions can be used with AND/OR operators.
     *
     * @return the string contains a valid SQL boolean expression for WHERE clause
     */
    public String build() {
        StringBuilder builder = new StringBuilder(isNegativeExpression ? "NOT (" : "");
        for (Expression expression : expressions) {
            builder.append(" ").append(expression.getExpression());
        }
        if (isNegativeExpression) {
            builder.append(')');
        }

        return builder.toString().replaceFirst(" (AND|OR) ", "");
    }

    /**
     * Inserts a new expression to end of set using fluent syntax.
     *
     * @param expression the expression to insert
     * @return this instance of {@link WhereQuery}
     */
    public WhereQuery addExpression(Expression expression) {
        expressions.add(expression);
        return this;
    }

    /**
     * A single boolean expression.
     */
    public static class Expression {
        /**
         * Indicates which operator will be used. If <b>true</b> then uses AND operator and OR operator in otherwise.
         */
        private boolean isFilterRange;
        /**
         * String representation of operation. All supported operations listed in {@link WhereQuery.Operation} class.
         */
        private String operation;
        /**
         * The column that will be used in expression.
         */
        private String columnName;
        /**
         * The value that will be used as filter of column values. Should be a same type with used column.
         */
        private Object filterValue;

        /**
         * Builds a new instance of {@link WhereQuery.Expression}.
         *
         * @param operation {@link #operation}
         * @param columnName {@link #columnName}
         * @param filterValue {@link #filterValue}
         * @param isFilterRange {@link #isFilterRange}
         */
        public Expression(String operation, String columnName, Object filterValue, boolean isFilterRange) {
            this.operation = operation;
            this.columnName = columnName;
            this.filterValue = filterValue;
            this.isFilterRange = isFilterRange;
        }

        /**
         * Builds the current expression as a string.
         *
         * @return the string that contains a valid boolean SQL expression
         */
        public String getExpression() {
            return (isFilterRange ? "AND " : "OR ") + columnName + " " + operation + " " + filterValue;
        }
    }

    /**
     * Contains a string constants with SQL compare operations.
     */
    public static class Operation {
        public static final String GREATER = ">";
        public static final String LESS = "<";
        public static final String EQUALS = "=";
        public static final String NOT_EQUALS1 = "<>";
        public static final String NOT_EQUALS2 = "!=";
        public static final String GREATER_OR_EQUAL = ">=";
        public static final String LESS_OR_EQUAL = "<=";
    }
}
