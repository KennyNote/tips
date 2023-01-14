package com.learning.notebook.tips.basic.collections;

import java.util.List;
public class ClassA {

    private String id;
    private List<ClassB> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ClassB> getList() {
        return list;
    }

    public void setList(List<ClassB> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ClassA{" +
            "id='" + id + '\'' +
            ", list=" + list +
            '}';
    }
}
