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
         * Left operand in expression
         */
        private String leftValue;
        /**
         * Right operand in expression
         */
        private String rightValue;

        /**
         * Builds a new instance of {@link WhereQuery.Expression}
         *
         * @param operation {@link #operation}
         * @param leftValue {@link #leftValue}
         * @param rightValue {@link #rightValue}
         * @param isFilterRange {@link #isFilterRange}
         */
        public Expression(String operation, String leftValue, String rightValue, boolean isFilterRange) {
            this.operation = operation;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
            this.isFilterRange = isFilterRange;
        }

        /**
         * Builds the current expression as a string.
         *
         * @return the string that contains a valid boolean SQL expression
         */
        public String getExpression() {
            return (isFilterRange ? "AND " : "OR ") + leftValue + " " + operation + " " + rightValue;
        }
    }

    /**
     * Contains a string constants with SQL compare operations
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
