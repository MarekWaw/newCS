import java.net.*;
import java.io.*;
public class EchoServerThread extends Thread {
	protected Socket gniazdo;
	
	public EchoServerThread(Socket clientSocket) {
		this.gniazdo = clientSocket;
	}
	public void run(){
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
			inp = gniazdo.getInputStream();
			brinp = new BufferedReader(new InputStreamReader(inp));
			out = new DataOutputStream(gniazdo.getOutputStream());
		}
		catch(IOException wyjatek) {
			outp.println("B�ad podczas tworzenia strumieni...");
			System.exit(-1);
		}
		String line = null;
		while(true){
			try {
				line = brinp.readLine();
				System.out.println("Odczytano: " + line);
				if (line == null || line.equals("quit")) {
					try {gniazdo.close();
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
