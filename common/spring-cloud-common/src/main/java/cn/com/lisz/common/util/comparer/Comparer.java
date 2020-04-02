package cn.com.lisz.common.util.comparer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


@FunctionalInterface
public interface Comparer<T> {
    boolean compare(T t1, T t2);

    static boolean invokeDefault(Object t1, Object t2) {
        Class<?> type;

        if (t1 != null && t2 != null && (type = t1.getClass()) == t2.getClass()) {
            if (t1 instanceof String) {
                return t1.equals(t2);
            }

            List<Field> fields = Stream.concat(Arrays.stream(type.getDeclaredFields()), Arrays.stream(type.getSuperclass().getDeclaredFields())).collect(toList());

            Optional<Field> optional;
            if ((optional = fields.stream().filter(a -> a.isAnnotationPresent(CompareKey.class)).findFirst()).isPresent()) {
                Field keyField = optional.get();
                try {
                    keyField.setAccessible(true);

                    Object key1, key2;
                    if ((key1 = keyField.get(t1)) != null && (key2 = keyField.get(t2)) != null) {
                        return key1.equals(key2);
                    }
                } catch (Exception ignored) {
                }
            }

            if ((optional = fields.stream().filter(a -> "id".equals(a.getName())).findFirst()).isPresent()) {
                Field idField = optional.get();
                try {
                    idField.setAccessible(true);

                    Object id1, id2;
                    if ((id1 = idField.get(t1)) != null && (id2 = idField.get(t2)) != null) {
                        return id1.equals(id2);
                    }
                } catch (Exception ignored) {
                }
            }
        }

        return t1 == t2;
    }
}
