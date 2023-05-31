package commands.commandList;

import collection.DataBase;
import commands.Command;
import services.OrganizationController;

/**
 * The type Clear command.
 * Команда для очистки коллекции.
 */
public class ClearCommand implements Command {
    private final OrganizationController controller;

    public ClearCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        if (controller.clear()){
            return "Collection cleared";
        }
        return "Collection was not clearesd";
    }
    @Override
    public String getCommandName() {
        return "clear";
    }

    @Override
    public String getCommandDescription() {
        return "очистить коллекцию";
    }
}
