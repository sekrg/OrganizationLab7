package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import services.OrganizationController;

import java.util.Comparator;
import java.util.Optional;

import static utils.Utilities.readOrganization;

/**
 * The type Add if min command.
 */
public class AddIfMinCommand implements Command {
    private final OrganizationController controller;

    public AddIfMinCommand(OrganizationController controller) {
        this.controller = controller;
    }

    /**
     * Добавляет элемент если значение годового оборота у введенной организации < чем у существующих.
     */
    @Override
    public String execute(String[] args) {
        Organization organization = readOrganization();
        if (controller.addOrganizationifMin(organization) < 0){
            return "Элемент не минимальный.";
        }
        return "Элемент добавлен.";
    }

    @Override
    public String getCommandName() {
        return "add_if_min";
    }

    @Override
    public String getCommandDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }
}

