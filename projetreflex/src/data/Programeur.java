package data;

public class Programeur {
	private String login;
	private String mdp;
	private String ftp;

	public Programeur(String login, String mdp, String ftp) {
		this.login = login;
		this.mdp = mdp;
		this.ftp = ftp;
	}
	public void setFtp(String ftp) {
		this.ftp=ftp;
	}
	public String getLogin() {
		return this.login;
	}
	public String getMdp() {
		return this.mdp;
	}
	public String getFtp() {
		return this.ftp;
	}

}
