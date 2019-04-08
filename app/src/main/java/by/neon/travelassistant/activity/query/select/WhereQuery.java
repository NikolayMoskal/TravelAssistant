package by.neon.travelassistant.activity.query.select;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class WhereQuery {
    private List<Expression> expressions;

    public WhereQuery() {
        expressions = new ArrayList<>(0);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Expression expression : expressions) {
            builder.append(" ").append(expression.getExpression());
        }
        return builder.delete(builder.length() - 2, 2).toString();
    }

    public void addExpression(Expression expression) {
        expressions.add(expression);
    }

    public class Expression {
        private boolean isFilterRange;
        private Operation operation;
        private String leftValue;
        private String rightValue;

        public Expression(Operation operation, String leftValue, String rightValue, boolean isFilterRange) {
            this.operation = operation;
            this.leftValue = leftValue;
            this.rightValue = rightValue;
            this.isFilterRange = isFilterRange;
        }

        public String getExpression() {
            return leftValue + " " + operation + " " + rightValue + (isFilterRange ? " &&" : " ||");
        }
    }

    public static class Operation {
        public static final String GREATER = "<";
        public static final String LESS = ">";
        public static final String EQUALS = "=";
        public static final String NOT_EQUALS1 = "<>";
        public static final String NOT_EQUALS2 = "!=";
    }
}
