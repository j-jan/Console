package console;

public class DummyCommand implements ICommand{

	@Override
	public void doCommand(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("DUMMY DUMMY DUMMY DUMMY DUMMY DUMMY");
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "dummy";
	}

	/**
	 * @param args
	 */


}
