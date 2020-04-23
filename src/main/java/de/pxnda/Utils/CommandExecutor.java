package de.pxnda.Utils;

public class CommandExecutor {

    public ICommand command;

    public CommandExecutor(ICommand command){
        this.command = command;
    }

    public void execute() {
        command.execute();
    }


}
