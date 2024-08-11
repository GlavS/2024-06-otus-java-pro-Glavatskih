package ru.otus.proxy;

import static java.lang.invoke.MethodType.methodType;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import ru.otus.annotation.Log;

@Slf4j
public class AopLogger<T> implements InvocationHandler {
    private final T target;
    private final Set<Method> logAnnotatedMethods;

    public AopLogger(T target, Class<? super T> targetInterfaceClass) {
        this.target = target;
        logAnnotatedMethods = Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(method -> Arrays.stream(targetInterfaceClass.getDeclaredMethods())
                        .filter(ifceMethod -> ifceMethod.getName().equals(method.getName()))
                        .filter(ifceMethod -> methodType(ifceMethod.getReturnType(), ifceMethod.getParameterTypes())
                                .equals(methodType(method.getReturnType(), method.getParameterTypes())))
                        .findFirst()
                        .orElse(null))
                .collect(Collectors.toSet());
    }

    public static <T> Object addLogging(T target, Class<? super T> interfaceT) {
        return Proxy.newProxyInstance(
                interfaceT.getClassLoader(), new Class<?>[] {interfaceT}, new AopLogger<>(target, interfaceT));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (logAnnotatedMethods.contains(method)) {
            log.info("Executed method {}, param(s): {}", method.getName(), Arrays.toString(args));
        }
        return method.invoke(target, args);
    }
}
