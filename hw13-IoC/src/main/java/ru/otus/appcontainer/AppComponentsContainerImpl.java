package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.*;
import lombok.Builder;
import lombok.SneakyThrows;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<AppComponentDefinition> defs = processDefinitions(configClass);
        for (AppComponentDefinition def : defs) {
            Object component = createAppComponent(def, appComponents);
            appComponents.add(component);
            if (appComponentsByName.get(def.componentName) != null) {
                throw new IllegalArgumentException("Duplicate component name: " + def.componentName);
            } else {
                appComponentsByName.put(def.componentName, component);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        long count = appComponents.stream().filter(componentClass::isInstance).count();
        if (count > 1) {
            throw new IllegalArgumentException(
                    String.format("Given component class %s is already registered", componentClass.getName()));
        }
        Object component = appComponents.stream()
                .filter(componentClass::isInstance)
                .findFirst()
                .orElse(null);
        if (component == null) {
            throw new IllegalArgumentException(
                    String.format("Given component class %s is not found", componentClass.getName()));
        } else {
            return componentClass.cast(component);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        Object component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new IllegalArgumentException(String.format("Given component name %s is not found", componentName));
        }
        return (C) component;
    }

    @SneakyThrows
    private List<AppComponentDefinition> processDefinitions(Class<?> configClass) {
        List<AppComponentDefinition> defs = new ArrayList<>();
        Object o = configClass.getConstructor().newInstance();
        for (Method method : configClass.getMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                AppComponentDefinition def = AppComponentDefinition.builder()
                        .configClass(o)
                        .componentName(method.getAnnotation(AppComponent.class).name())
                        .method(method)
                        .parameterTypes(method.getParameterTypes())
                        .order(method.getAnnotation(AppComponent.class).order())
                        .build();
                defs.add(def);
            }
        }
        Collections.sort(defs);
        return defs;
    }

    @SneakyThrows
    private Object createAppComponent(AppComponentDefinition def, List<Object> components) {
        Method method = def.method;
        Class<?>[] parameterTypes = def.parameterTypes;
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            for (Object component : components) {
                if (parameterTypes[i].isInstance(component)) {
                    args[i] = component;
                }
            }
        }
        return method.invoke(def.configClass, args);
    }

    @Builder
    private static class AppComponentDefinition implements Comparable<AppComponentDefinition> {
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
}
