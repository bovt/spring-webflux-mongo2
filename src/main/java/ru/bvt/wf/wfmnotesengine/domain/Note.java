package ru.bvt.wf.wfmnotesengine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    private String text;

    private String author;

    private Map<String, String> categories;

    public Note(String text) {
        this.text = text;
        categories = new HashMap<String, String>();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

}
