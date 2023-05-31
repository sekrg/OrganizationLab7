package commands.commandList;

import collection.DataBase;
import commands.Command;
import services.OrganizationController;

/**
 * The type Remove by id command.
 * Удалить элемент из коллекции по его id
 */
public class RemoveByIdCommand implements Command {
    private final OrganizationController controller;

    public RemoveByIdCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        try {
            int id = Integer.parseInt(args[1]);
            if (controller.removeOrganizationById(id)){
                return "Element removed";
            } else {
                return "Not yours or not exist";
            }
        } catch (NumberFormatException e){
            return "Id should be number";
        }
    }
    @Override
    public String getCommandName() {
        return "remove_by_id";
    }
    @Override
    public String getCommandDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
