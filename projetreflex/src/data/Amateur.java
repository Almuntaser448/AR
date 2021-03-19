package data;

import java.util.List;

public class Amateur {
	private String login;
	private String mdp;
	
	private List<String> Messagrie;
	public Amateur(String login, String mdp) {
		this.login = login;
		this.mdp = mdp;

	}
	public void AjoutMessagrie(String Login,String Message) {
		this.Messagrie.add("Message de "+ Login+"\n" +Message);
	}
	public List<String> getMessagrie() {
		return this.Messagrie;
	}
	public String getLogin() {
		return this.login;
	}
	public String getMdp() {
		return this.mdp;
	}

}
