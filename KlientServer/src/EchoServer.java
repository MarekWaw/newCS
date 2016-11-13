import java.net.*;
import java.io.*;
public class EchoServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		InputStream inp = null;
		BufferedReader brinp = null;
		DataOutputStream out = null;
		PrintWriter outp = null;
		try {
			outp = new PrintWriter(new OutputStreamWriter(System.out, "CP852"), true);
		}
		catch (UnsupportedEncodingException wyjatek) {
			System.out.println("Nie mo�na ustawi� strony kodowej 852");
			outp = new PrintWriter(new OutputStreamWriter(System.out), true);
		}
		try {
			serverSocket = new ServerSocket(6666);
		}
		catch(IOException wyjatek) {
			outp.println("B��d przy tworzeniu gniazda sieciowego");
			System.exit(-1);
		}
		outp.println("Inicjalizacja gnizada sieciowego SERWERA zako�czona");
		outp.println("Parametry gniazda SERWERA: " + serverSocket);
		while(true) {
			try {
				clientSocket = serverSocket.accept();
			}
			catch(IOException e){
				outp.println("B�ad wej�cia-wyj�cia: " + e);
			}
			outp.println("Nadesz�o po��czenie...");
			outp.println("Parametry po��czenia: " + clientSocket);
			new EchoServerThread(clientSocket).start();
		}
	}
}
