package com.cookingflamingoz.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

public class Quiz {

    public static class Question {
        public Integer id;
        public String type;
        public String value;
    }

    public static class Option {
        public Integer questonId;
        public String value;
        public boolean correct;
    }

    public Set<Question> questions;
    public Set<Option> options;
}
