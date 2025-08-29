package learningtest.archunit.domain;

import java.util.ArrayList;
import java.util.List;

public class MyDomain {
    List<Object> myField;

    void run() {
        myField = new ArrayList<>();
        System.out.println(myField);
    }
}
