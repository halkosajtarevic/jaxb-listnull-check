package io.github.halko.jaxb.example;

import generated.Example;
import java.io.File;
import javax.xml.bind.JAXB;

public class Application {
    public static void main(String[] args) {
        Example val = JAXB.unmarshal(new File("src/main/resources/test.xml"), Example.class);
        System.out.println("one-null: "+val.isOneNull());
        System.out.println("two-null: "+val.isTwoNull());
    }
}
