package commands.commandList;

import commands.Command;
import commands.Invoker;

import java.util.Map;

/**
 * The type Help command.
 * Вывести справку по доступным командам
 */
public class HelpCommand implements Command {
    /**
     * The constant ANSI_RESET.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * The constant ANSI_CYAN.
     */
    public static final String ANSI_CYAN = "\u001B[36m";

    private final Invoker invoker;

    public HelpCommand(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public String execute(String[] args) {
        for (Map.Entry<String, Command> entry : invoker.getCommandMap().entrySet()) {
            System.out.println(ANSI_CYAN + entry.getKey() + ANSI_RESET + " : " + entry.getValue().getCommandDescription());
        }
        return "";
    }
    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getCommandDescription() {
        return "вывести справку по доступным командам";
    }

}
