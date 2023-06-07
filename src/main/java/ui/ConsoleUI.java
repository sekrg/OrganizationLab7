package ui;

import collection.DataBase;
import collection.ElementsReader;
import commands.Invoker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import services.Authentication;
import utils.InputType;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * The type Console ui.
 */
public class ConsoleUI {
    private final Scanner scanner;

    /**
     * Instantiates a new Console ui.
     *
     * @param scanner the scanner
     */
    public ConsoleUI(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Start.
     *
     * @throws IOException the io exception
     */
    public void start() throws IOException {
        String command;

        Authentication authentication = new Authentication();

        Invoker console = new Invoker(InputType.CMD, authentication.start());

        System.out.println("Введите help для ознакомления с командами");
        while (!Objects.equals(command = scanner.nextLine(), null) && !Objects.equals(command, "exit")) {
            System.out.println(console.execute(command));
        }
    }
}
