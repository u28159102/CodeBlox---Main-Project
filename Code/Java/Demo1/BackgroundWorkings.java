import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;

import javax.swing.JOptionPane;


public class BackgroundWorkings {
int hiddenPin;
	public BackgroundWorkings() {
		generatePin();
		String[] cmd = {  "python redon.py"};
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		server();
		
	}

	private void server() {//Set up web server
			try {
				ServerSocket listener = new ServerSocket(55555);
				Thread thread = new Thread(){
				    public void run(){
				    	while (true)try {
							Socket client = listener.accept();
							OutputStream streamOut = client.getOutputStream();
							String httpResponse="HTTP/1.1 200 OK\r\n\r\n<!DOCTYPE html><html><head><title> "
							+ "Get Pin</title>   </head><body><div> "+hiddenPin+ "</div></body></html>";
							streamOut.write(httpResponse.getBytes("UTF-8"));
							if (client != null)    client.close();//Close all open things
							if (streamOut != null)  streamOut.close();
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				    }
				  };

				  thread.start();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
	}

	private void generatePin() {
		hiddenPin=(int) Math.round((Math.random()*999885));
	}

	public void submitPin(int pin) {
		// TODO Auto-generated method stub
		System.out.println(pin);
		if(pin==hiddenPin)
		{gateActivate();}
	}

	private void gateActivate() {
		//JOptionPane.showMessageDialog(null, "Gate OPEN!");
		String[] cmd = {  "python redoffgreenfor5Sec.py"};
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		generatePin();
		try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
		//JOptionPane.showMessageDialog(null, "Gate Closed!");
	}
	
	
	
}
