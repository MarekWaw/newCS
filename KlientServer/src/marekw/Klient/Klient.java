package marekw.Klient;
// Wrzucamy do Gita
// Do Gita lokalnego
// Poprawka
import java.net.*;
import java.io.*;
public class Klient {
	public static void main(String[] args) {
		String host = args[0];
		Socket clientSocket = null;
		PrintWriter outp = null;
		try {
			outp = new PrintWriter(new OutputStreamWriter(System.out, "CP852"), true);
		}
		catch (UnsupportedEncodingException wyjatek) {
			System.out.println("Nie mo�na ustawi� strony kodowej 852");
			outp = new PrintWriter(new OutputStreamWriter(System.out), true);
		}
		int port = 0;
		try {
			port = new Integer(args[1]).intValue();
		}
		catch(NumberFormatException wyjatek) {
			outp.println("Nieprawid�owy argument: port");
			System.exit(-1);
		}
		try {
			clientSocket = new Socket(host, port);
		}
		catch(UnknownHostException wyjatek) {
			outp.println("Nieznany host");
			System.exit(-1);
		}
		catch(ConnectException wyjatek){
			outp.println("Po��czenie odrzucono");
			System.exit(-1);
		}
		catch(IOException wyjatek){
			outp.println(wyjatek);
			System.exit(-1);
		}
		outp.println("Po��czono z: " + clientSocket);
		String line = null;
		BufferedReader brSocket = null;
		BufferedReader brInput = null;
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(clientSocket.getOutputStream());
			brSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			brInput = new BufferedReader(new InputStreamReader(System.in));
		}
		catch(IOException wyjatek){
			outp.println("B�ad przy tworzeniu strumieni klienta...");
			System.exit(-1);
		}
		while(true){
			try {
				line = brInput.readLine();
				outp.println("Wysy�am :" + line);
				out.writeBytes(line + '\n');
				out.flush();
				if(line.equals("quit")){
					outp.println("Koniec pracy");
					clientSocket.close();
					System.exit(0);
				}
				brSocket.readLine();
				outp.println("Otrzymano: " + line);
			}
			catch(IOException wyjatek){
				outp.println("B�ad wej�cia-wyj�cia");
				System.exit(-1);
			}
			catch(Exception wyjatek){
				outp.println("B�ad og�lny: " + wyjatek);
				System.exit(-1);
			}
							
		}
	}
}
