package ru.bvt.wf.wfmnotesengine.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Long.valueOf;

@Data
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    private String name;


    public Category(String name) {
        this.name = name;
    }

    ;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
