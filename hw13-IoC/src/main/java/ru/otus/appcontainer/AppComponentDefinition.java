package ru.otus.appcontainer;

import java.lang.reflect.Method;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppComponentDefinition implements Comparable<AppComponentDefinition> {

    private final Object configClass;
    private final String componentName;
    private final Method method;
    private final Class<?>[] parameterTypes;
    private final int order;

    @Override
    public int compareTo(AppComponentDefinition o) {
        return this.order - o.order;
    }
}
