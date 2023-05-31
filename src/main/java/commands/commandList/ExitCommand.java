package commands.commandList;

import commands.Command;

import java.io.IOException;

public class ExitCommand implements Command {
    @Override
    public String execute(String[] args) throws IOException {
        return null;
    }

    @Override
    public String getCommandName() {
        return "exit";
    }

    @Override
    public String getCommandDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}
