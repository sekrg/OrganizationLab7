package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import model.OrganizationType;
import services.OrganizationController;

import java.util.stream.Collectors;

/**
 * The type Show command.
 * Выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class ShowCommand implements Command {
    private final OrganizationController controller;

    public ShowCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        controller.showOrganizations().forEach(System.out::println);
        return "Успешно выведено " + controller.showOrganizations().size() + " элементов.";
    }
    @Override
    public String getCommandName() {
        return "show";
    }
    @Override
    public String getCommandDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}