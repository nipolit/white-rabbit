package com.politaev.whiterabbit.util;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnnotationValueRule<T extends Annotation, R> extends TestWatcher {
    private final Class<T> annotationType;
    private final R defaultValue;
    private R annotationValue;

    public AnnotationValueRule(Class<T> annotationType, R defaultValue) {
        this.annotationType = annotationType;
        this.defaultValue = defaultValue;
    }

    @Override
    protected void starting(Description description) {
        T annotation = description.getAnnotation(annotationType);
        if (annotation != null) {
            annotationValue = getAnnotationValue(annotation);
        } else {
            annotationValue = defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    private R getAnnotationValue(T annotation) {
        try {
            Method valueMethod = annotationType.getMethod("value");
            Object annotationValue = valueMethod.invoke(annotation);
            return (R) annotationValue;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public R getValue() {
        return annotationValue;
    }
}
