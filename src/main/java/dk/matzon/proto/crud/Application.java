package dk.matzon.proto.crud;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Application {
    public static void main(String... args) {
        Quarkus.run(args);
    }
}
