package commands;

import java.io.IOException;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute string.
     *
     * @return the string
     * @throws IOException the io exception
     */
    String execute(String[] args) throws IOException;

    /**
     * Gets command name.
     *
     * @return the command name
     */
    String getCommandName();

    String getCommandDescription();
}
