package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class AppliCliReserv {
	private final static int PORT = 3000;
	private final static String HOST = "localhost";

	public static void main(String[] args) throws IOException {
		Socket socket = null;
		try {
			socket = new Socket(HOST, PORT);

			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Connecté au serveur " + socket.getInetAddress() + " : " + socket.getPort());

			String line;

			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);
			line = sin.readLine();
			System.out.println(line);

			line = clavier.readLine();
			sout.println(line);

			line = sin.readLine();
			System.out.println(line);

			line = clavier.readLine();
			sout.println(line);

			line = sin.readLine();// vous voulez réservez l'objet..
			System.out.println(line);

			line = sin.readLine();
			System.out.println(line);

			line = clavier.readLine();
			sout.println(line);

			line = sin.readLine();
			System.out.println(line);

			System.out.println("Fin du service");
			socket.close();
		} catch (IOException e) {
			System.err.println("Erreur");
		}

		try {
			if (socket != null)
				socket.close();
		} catch (IOException e2) {
			;
		}
	}
}
