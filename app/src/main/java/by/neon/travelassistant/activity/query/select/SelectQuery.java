package by.neon.travelassistant.activity.query.select;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery {
    private List<String> fields;

    public SelectQuery() {
        this.fields = new ArrayList<>(0);
    }

    public void addField(String fieldName) {
        fields.add(fieldName);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String field : fields) {
            builder.append(" ").append(field).append(",");
        }
        return builder.deleteCharAt(builder.length() - 1).toString();
    }
}
