package de.pxnda.bot.util.commands;

public class CommandExecutor {

    public ICommand command;

    public CommandExecutor(ICommand command){
        this.command = command;
    }

    public void execute() {
        command.execute();
    }


}
