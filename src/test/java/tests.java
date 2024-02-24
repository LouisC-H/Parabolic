import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class tests {

    @Test
    void tinyExample(){
        Assertions.assertEquals(21, Main.runDay14CodeP1("src/main/resources/tinyExample.txt"));
    }

    @Test
    void exampleP1(){
        Assertions.assertEquals(136, Main.runDay14CodeP1("src/main/resources/ExampleP1.txt"));
    }

    @Test
    void tinyExampleSpin(){
        Assertions.assertEquals(15, Main.runDay14CodeP2("src/main/resources/tinyExample.txt", 100000));
    }

    @Test
    void exampleP1BigSpin(){
        Assertions.assertEquals(64, Main.runDay14CodeP2("src/main/resources/ExampleP1.txt", 100050));
    }
}
