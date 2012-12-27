package console;

public interface ICommand {
	public void doCommand(String[] args);
	public String getCommandName();
}
