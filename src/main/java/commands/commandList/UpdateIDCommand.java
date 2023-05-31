package commands.commandList;

import collection.DataBase;
import commands.Command;
import model.Organization;
import services.OrganizationController;
import utils.InputType;
import utils.Utilities;

import java.util.Scanner;

/**
 * The type Update id command.
 * Обновляет значение элемента коллекции, id которого равен заданному
 */
public class UpdateIDCommand implements Command {
    private final OrganizationController controller;
    private InputType inputType;

    public UpdateIDCommand(OrganizationController controller, InputType inputType) {
        this.controller = controller;
        this.inputType = inputType;
    }

    @Override
    public String execute(String[] args) {
        try {
            int id = Integer.parseInt(args[1]);

            if (inputType.equals(InputType.CMD)){
                if (!controller.getAll().stream().map(Organization::getId).toList().contains(id)){
                    return "Организация не существует.";
                }
                if (controller.updateOrganizationById(Utilities.readOrganization(), id)){
                    return "Element updated";
                } else {
                    return "Организация не ваша.";
                }
            } else {
                String stringOrg = args[2];
                if (!controller.getAll().stream().map(Organization::getId).toList().contains(id)){
                    return "Организация не существует.";
                }
                if (controller.updateOrganizationById(Utilities.fromStringToOrganization(stringOrg), id)){
                    return "Element updated";
                } else {
                    return "Организация не ваша.";
                }
            }
        } catch (NumberFormatException e) {
            return "Id should be number";
        }
    }

    @Override
    public String getCommandName() {
        return "update";
    }
    @Override
    public String getCommandDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
