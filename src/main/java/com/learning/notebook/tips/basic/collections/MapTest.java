package com.learning.notebook.tips.basic.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapTest {

    public static void main(String[] args) {

        List<ClassA> arrayList = new ArrayList<>();

        List<ClassB> a1ClassB = new ArrayList<>();
        ClassB classB1 = new ClassB("x","1");
        ClassB classB2 = new ClassB("y","2");
        a1ClassB.add(classB1);
        a1ClassB.add(classB2);
        ClassA classA1 = new ClassA();
        classA1.setId("1");
        classA1.setList(a1ClassB);

        List<ClassB> a2ClassB = new ArrayList<>();
        ClassB classB3 = new ClassB("z","3");
        a2ClassB.add(classB3);
        ClassA classA2 = new ClassA();
        classA2.setId("1");
        classA2.setList(a2ClassB);
        arrayList.add(classA1);
        arrayList.add(classA2);

        System.out.println(arrayList.stream()
            .collect(Collectors.toMap(ClassA::getId, Function.identity(), (v1, v2) -> {
                v2.getList().addAll(v1.getList());
                return v2;
            })).values());

//        List<ClassB> a1ClassB = new ArrayList<>();
//        ClassB classB1 = new ClassB("x", "1");
//        ClassB classB2 = new ClassB("y", "2");
//        ClassB classB3 = new ClassB("y", "3");
//        a1ClassB.add(classB1);
//        a1ClassB.add(classB2);
//        a1ClassB.add(classB3);
//        a1ClassB.stream().filter(item -> "y".equals(item.getName())).findAny().ifPresent(System.out::println);

    }

    static class Demo{
        private String id;
        private String name;

        public Demo(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
