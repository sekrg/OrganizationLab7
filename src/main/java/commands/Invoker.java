package commands;

import commands.commandList.*;
import db.DataBaseProvider;
import exceptions.RecursionException;
import services.CurrentUserManager;
import services.OrganizationController;
import utils.HistoryManager;
import utils.InputType;
import utils.ScriptManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Developer.
 */
public class Invoker {
    /**
     * история команд
     */
    private final HistoryManager history = new HistoryManager(7);
    private CurrentUserManager userManager;
    private ScriptManager scriptManager = new ScriptManager(null);
    private Map<String, Command> commandMap = new HashMap<>();
    private final OrganizationController controller;
    public Invoker(InputType inputType, CurrentUserManager userManager) {
        this.userManager = userManager;
        controller = new OrganizationController(new DataBaseProvider(userManager));
        initMap(inputType);
    }

    /**
     * Execute string.
     *
     * @param line the line
     * @return the string
     * @throws IOException the io exception
     */
    public String execute(String line) throws IOException {
        try {
            String command = line.trim().split(" ")[0];
            String[] input = line.trim().split(" ");
            if (!getCommandMap().containsKey(command)){
                return "Incorrect commandName!";
            }
            for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
                if (entry.getKey().equals(command)) {
                    try {
                        history.addCommandToHistory(entry.getKey());
                        return entry.getValue().execute(input);
                    } catch (RecursionException e){
                        System.out.println(e.getMessage());
                        scriptManager.clearScripts();
                    }
                }
            }
            return "WTF";
        } catch (NullPointerException e){
         return "Meet null string";
        }
    }
    public void initMap(InputType type){
        commandMap.clear();
        commandMap.put("add", new AddCommand(controller, type));
        commandMap.put("add_if_min", new AddIfMinCommand(controller));
        commandMap.put("average", new AverageCommand(controller) );
        commandMap.put("clear", new ClearCommand(controller));
        commandMap.put("count", new CountCommand(controller));
        commandMap.put("execute", new ExecuteCommand(scriptManager, userManager));
        commandMap.put("filter", new FilterCommand(controller));
        commandMap.put("help", new HelpCommand(this));
        commandMap.put("history", new HistoryCommand(history));
        commandMap.put("info", new InfoCommand(controller));
        commandMap.put("remove_by_id", new RemoveByIdCommand(controller));
        commandMap.put("show", new ShowCommand(controller));
        commandMap.put("shuffe", new ShuffleCommand(controller));
        commandMap.put("update_by_id", new UpdateIDCommand(controller, type));
        commandMap.put("save", new SaveCommand());
    }


    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }
}
