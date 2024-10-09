package ru.otus.appcontainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.api.AppContainerException;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components =
                appComponents.stream().filter(componentClass::isInstance).toList();
        if (components.size() > 1) {
            throw new AppContainerException(
                    String.format("Given component class %s is already registered", componentClass.getName()));
        }
        Object component = components.getFirst();
        if (component == null) {
            throw new AppContainerException(
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
            throw new AppContainerException(String.format("Given component name %s is not found", componentName));
        }
        return (C) component;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        List<AppComponentDefinition> defs = processDefinitions(configClass);
        for (AppComponentDefinition def : defs) {
            Object component = createAppComponent(def, appComponents);
            appComponents.add(component);
            if (appComponentsByName.get(def.getComponentName()) != null) {
                throw new AppContainerException("Duplicate component name: " + def.getComponentName());
            } else {
                appComponentsByName.put(def.getComponentName(), component);
            }
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new AppContainerException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private List<AppComponentDefinition> processDefinitions(Class<?> configClass) {
        List<AppComponentDefinition> defs = new ArrayList<>();
        Object o;
        try {
            o = configClass.getConstructor().newInstance();
        } catch (InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new AppContainerException("Cannot instantiate application config" + configClass.getName(), e);
        }
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

    private Object createAppComponent(AppComponentDefinition def, List<Object> components) {
        Method method = def.getMethod();
        Class<?>[] parameterTypes = def.getParameterTypes();
        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            for (Object component : components) {
                if (parameterTypes[i].isInstance(component)) {
                    args[i] = component;
                }
            }
        }
        try {
            return method.invoke(def.getConfigClass(), args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppContainerException("Cannot create app component " + def.getComponentName(), e);
        }
    }
}
