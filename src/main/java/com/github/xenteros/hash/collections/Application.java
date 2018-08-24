package com.github.xenteros.hash.collections;

import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        Optional<String> o1 = Optional.of("ABC");
        String str = o1.filter(s -> s.contains("d"))
                .map(String::toLowerCase)
                .orElse("Default String");
        System.out.println(str);

    }
}
