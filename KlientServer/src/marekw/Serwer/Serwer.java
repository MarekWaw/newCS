package marekw.Serwer;
import java.net.*;
import java.io.*;
public class Serwer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
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
		try {
			socket = serverSocket.accept();
		}
		catch(IOException wyjatek) {
			outp.println(wyjatek);
			System.exit(-1);
	
		}
		/*
		try {
			serverSocket.close();
		}
		catch(IOException wyjatek) {
			System.out.println("B��d zamykania gnizada sieciowego");
		}
		*/
		System.out.println(socket);
		try {
			inp = socket.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
			out = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException wyjatek) {
			outp.println("B�ad podczas tworzenia strumieni...");
			System.exit(-1);
		}
		String line = null;
		outp.println("Rozpocz�cie p�tli g��wnej...");
		while(true){
			try {
				line = brinp.readLine();
				System.out.println("Odczytano: " + line);
				if (line == null || line.equals("quit")) {
					try {serverSocket.close();
					}
					catch(IOException wyjatek) {
						outp.println("B��d zamykania gnizada sieciowego");						
					}
					outp.println("Zako�czenie pracy...");
					System.exit(0);
				}
				out.writeBytes(line + "\n\r");
				outp.println("Wys�ano lini�: " + line);
			}
			catch(IOException wyjatek){
				outp.println("B�ad wyj�cia-wej�cia.");
				System.exit(-1);
			}
		}
	}
}
