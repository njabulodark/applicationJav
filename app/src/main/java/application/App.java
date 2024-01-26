package application;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

// import application.Universities.Cao;
import application.Universities.Nwu;

public class App {
    public String getGreeting() {
        return "Hello";
    }

    public static void main(String[] args) {
        // Cao cao = new Cao();
        // cao.run();

        Nwu nwu = new Nwu();
        nwu.run();
        // System.out.println( cao.application() );
    }
}
