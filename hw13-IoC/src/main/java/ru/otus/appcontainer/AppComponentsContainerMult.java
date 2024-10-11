package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class AppComponentsContainerMult extends AppComponentsContainerImpl {
    public AppComponentsContainerMult(Class<?>... configClasses) {
        List<Class<?>> classes = new ArrayList<>(Arrays.asList(configClasses));
        classes.sort(Comparator.comparingInt(o -> o.getAnnotation(AppComponentsContainerConfig.class).order()));
        for (Class<?> configClass : classes) {
            processConfig(configClass);
        }
    }


}
