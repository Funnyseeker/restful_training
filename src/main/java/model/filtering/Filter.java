package model.filtering;

import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс {@link Filter}, основа для фильтрации.
 */
public abstract class Filter {
    /**
     * Хранилище для эталонов фильтрации по их ключам.
     */
    private Map<String, Object> filterStandards;

    /**
     * Полученине эталона для фильтрации по ключу.
     *
     * @param standardKey ключ эталона для фильтрации
     *
     * @return эталон для фильтрации
     */
    protected Object getFilterAttribute(String standardKey) {
        return filterStandards.get(standardKey);
    }

    /**
     * Задание эталона для фильтрации и его ключа.
     *
     * @param standardKey ключ эталона
     * @param value       эталон
     */
    public void putFilterAttribute(String standardKey, Object value) {
        if (filterStandards == null) {
            filterStandards = new HashMap<>();
        }
        filterStandards.put(standardKey, value);
    }
}
