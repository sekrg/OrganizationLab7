package commands.commandList;

import collection.DataBase;
import commands.Command;
import services.OrganizationController;

/**
 * The type Info command.
 * Выводит в стандартный поток вывода информацию о коллекции
 */
public class InfoCommand implements Command {
    private final OrganizationController controller;

    public InfoCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        return controller.info();
    }
    @Override
    public String getCommandName() {
        return "info";
    }
    @Override
    public String getCommandDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}