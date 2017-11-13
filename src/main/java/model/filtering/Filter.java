package model.filtering;

import java.util.HashMap;
import java.util.Map;

public abstract class Filter {
    private Map<String, Object> filterAttributes;

    protected Object getFilterAttribute(String attributeKey) {
        return filterAttributes.get(attributeKey);
    }

    public void putFilterAttribute(String attributeKey, Object value) {
        if (filterAttributes == null) {
            filterAttributes = new HashMap<>();
        }
        filterAttributes.put(attributeKey, value);
    }
}
