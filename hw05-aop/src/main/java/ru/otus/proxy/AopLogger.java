package ru.otus.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.otus.annotation.Log;

@Slf4j
public class AopLogger<T> implements InvocationHandler {
    private final T target;
    private final List<Method> logAnnotatedMethods;

    public AopLogger(T target) {
        this.target = target;
        logAnnotatedMethods = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .toList();
    }

    public static <T> Object addLogging(T target, Class<? super T> interfaceT) {
        return Proxy.newProxyInstance(
                interfaceT.getClassLoader(), new Class<?>[] {interfaceT}, new AopLogger<>(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logAnnotatedMethods.stream()
                .filter(m -> m.isAnnotationPresent(Log.class))
                .filter(m -> m.getName().equals(method.getName()))
                .filter(m -> argsAreIdentical(m.getParameterTypes(), method.getParameterTypes()))
                .findAny()
                .ifPresent(m -> log.info("Executed method {}, param(s): {}", method.getName(), Arrays.toString(args)));
        return method.invoke(target, args);
    }

    private boolean argsAreIdentical(Class<?>[] paramsA, Class<?>[] paramsB) {
        if (paramsA.length != paramsB.length) {
            return false;
        }
        for (int i = 0; i < paramsA.length; i++) {
            if (paramsA[i] == paramsB[i]) {
                return true;
            }
        }
        return false;
    }
}
