package console;

public class MsgCommand implements ICommand{

	@Override
	public void doCommand(String[] args) {
		// TODO Auto-generated method stub
		if(args.length <=0){
			System.out.println("!! You have to give atleast one argument :'( !!");
		}else{
			String totalMessageGiven = "";
			for(String arg:args){
				totalMessageGiven+=arg+" ";
			}
			System.out.print("[@]: Message given: ");
			System.out.println(totalMessageGiven);
		}
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "msg";
	}

}
