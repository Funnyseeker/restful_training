package model.filtering;

import java.util.Map;

public abstract class Filter {
    private Map<String, Object> filterAttributes;

    protected Map<String, Object> getFilterAttributes() {
        return filterAttributes;
    }
}
