package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import services.OrganizationController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Shuffle command.
 * Перемещает элементы коллекции в случайном порядке
 */
public class ShuffleCommand implements Command {
    private final OrganizationController controller;

    public ShuffleCommand(OrganizationController controller) {
        this.controller = controller;
    }

    @Override
    public String execute(String[] args) {
        return controller.shuffleOrganizations().stream().map(Organization::toString).collect(Collectors.joining());
    }

    @Override
    public String getCommandName() {
        return "shuffle";
    }
    @Override
    public String getCommandDescription() {
        return "перемешать элементы коллекции в случайном порядке";
    }
}
