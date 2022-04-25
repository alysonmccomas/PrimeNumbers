package application;

import java.net.*;
import java.io.*;
import java.nio.charset.*;

public class ServerConnect {

	public static Integer number;
	
	public static void main(String[] args) {
		ServerSocket server = null;
		boolean shutdown = false;
		
		try {
			server = new ServerSocket(1229);
			System.out.println("Port Bound. Accepting Connections.");
	
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		while(!shutdown) {
			Socket client = null;
			InputStream input = null;
			OutputStream output = null;
			
			try {
				client = server.accept();
				input = client.getInputStream();
				output = client.getOutputStream();
				
				int n = input.read();
				byte[] data = new byte[n];
				input.read(data);
				
				String clientInput = new String(data, StandardCharsets.UTF_8);
				System.out.println("Client input is " + clientInput);
				
				//String response = PrimeNumber.primeNumber(clientInput);
				
				int i, prime = 0;
				String result;

				
				try {
					number = Integer.parseInt(clientInput);
				} 
				catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
				
				//determine if prime
				if (number == 0 || number == 1) {
					prime = 1;
				}
				
				for (i = 2; i <= number / 2; ++i) {
					if (number % i == 0) {
						prime = 1;
						break;
					}
				}
				
				//return results
				if (prime == 0) {
					result = number.toString() + " is a prime number";
					System.out.println(result);
				}else {
					result = number.toString() + " is not a prime number";
					System.out.println(result);
				}
				
				String response = result;
				
				output.write(response.length());
				output.write(response.getBytes());
				
				client.close();
				if(clientInput.equalsIgnoreCase("shutdown")) {
					System.out.println("Shutting down...");
					shutdown = true;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
