package com.learning.notebook.tips.basic.collections;


public class ClassB {

    private String name;

    private String age;

    public ClassB(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ClassB{" +
            "name='" + name + '\'' +
            ", age='" + age + '\'' +
            '}';
    }
}
