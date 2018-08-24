package com.github.xenteros.hash.collections;

import com.github.xenteros.hash.collections.hashmap.MyRealHashMap;

import java.util.Map;
import java.util.Optional;

public class Application {

    public static void main(String[] args) {

        Optional<String> o1 = Optional.of("ABC");
        String str = o1.filter(s -> s.contains("d"))
                .map(String::toLowerCase)
                .orElse("Default String");
        System.out.println(str);

        Map<String, String> map = new MyRealHashMap<>();
        map.put("Jan", "Kowalski");
        map.put("Danuta", "Nowak");
        System.out.println(map);


    }
}
