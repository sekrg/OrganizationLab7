package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import services.OrganizationController;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The type Filter command.
 * Выводит элементы, значение поля name которых начинается с заданной подстроки
 */
public class FilterCommand implements Command {
    private final OrganizationController controller;

    public FilterCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите подстроку");
        String sub = scanner.nextLine().toUpperCase();
        return controller.filter(sub);
    }
    @Override
    public String getCommandName() {
        return "filter";
    }

    @Override
    public String getCommandDescription() {
        return "вывести элементы, значение поля name которых начинается с заданной подстроки";
    }
}
