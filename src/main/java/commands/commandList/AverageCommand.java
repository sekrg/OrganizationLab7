package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import services.OrganizationController;

/**
 * The type Average command.
 * Выводит среднее значение поля annualTurnover (Годовой оборот) для всех элементов коллекции
 */
public class AverageCommand implements Command {
    private final OrganizationController controller;

    public AverageCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        return "среднее значение поля annualTurnover для всех элементов коллекции: "+ controller.averageTurnover();
    }
    @Override
    public String getCommandName() {
        return "average";
    }

    @Override
    public String getCommandDescription() {
        return "вывести среднее значение поля annualTurnover для всех элементов коллекции";
    }
}
