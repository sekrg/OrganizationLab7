package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import model.OrganizationType;
import services.OrganizationController;

import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The type Count command.
 */
public class CountCommand implements Command {
    private final OrganizationController controller;

    public CountCommand(OrganizationController controller) {
        this.controller = controller;
    }

    /**
     * Нельзя конкретно реализовать т.к Type у организаций невозможно сравнить между собой по имени. (Введена цифровая int система для сравнения)
     *
     */

    @Override
    public String execute(String[] args) {
        System.out.println("Введите тип");
        Scanner scanner = new Scanner(System.in);
        OrganizationType type = getType(scanner);
        return "Меньше, чем *"+type.toString()+"* найдено: " + controller.countType(type) + " значений.";
    }
    private static OrganizationType getType(Scanner scanner) {
        while (true) {
            String s = scanner.nextLine();
            try{  if (s != null && s.length() > 0) {
                return OrganizationType.valueOf(s.toUpperCase(Locale.ENGLISH).trim());
            }}
            catch (IllegalArgumentException e) {
                System.out.println("Incorrect input!"); }
        }
    }
    @Override
    public String getCommandName() {
        return "count";
    }

    @Override
    public String getCommandDescription() {
        return "вывести количество элементов, значение поля type которых меньше заданного";
    }
}