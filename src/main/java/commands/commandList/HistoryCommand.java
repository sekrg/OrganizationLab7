package commands.commandList;

import commands.Command;
import utils.HistoryManager;

/**
 * The type History command.
 * Выводит последние 7 команд
 */
public class HistoryCommand implements Command {
    private final HistoryManager history;

    public HistoryCommand(HistoryManager history) {
        this.history = history;
    }

    @Override
    public String execute(String[] args) {
        System.out.println("Список последних 7 команд: ");
        return history.getHistoryListOfCommands().stream().toList().toString();
    }
    @Override
    public String getCommandName() {
        return "history";
    }
    @Override
    public String getCommandDescription() {
        return "вывести последние 7 команд (без их аргументов)";
    }
}
