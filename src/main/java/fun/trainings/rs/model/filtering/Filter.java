package fun.trainings.rs.model.filtering;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Абстрактный класс {@link Filter}, основа для фильтрации.
 */
public abstract class Filter {
    /**
     * Хранилище для эталонов фильтрации по их ключам.
     */
    protected Map<String, Object> filterStandards = new HashMap<>();
    ;

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
        if (value == null ||
                (value instanceof String && !StringUtils.hasText((String) value))) {
            return;
        }
        filterStandards.put(standardKey, value);
    }
}
