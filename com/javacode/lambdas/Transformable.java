package com.javacode.lambdas;
@FunctionalInterface
public interface Transformable<T> {
    T transform(T t);
}
