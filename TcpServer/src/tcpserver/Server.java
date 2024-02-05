
package tcpserver;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create a server socket and bind it to a port number
        ServerSocket serverSocket = new ServerSocket(6789);

        // Accept a connection from a client
        Socket socket = serverSocket.accept();

        // Get the input and output streams from the socket
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Loop until the client sends Q
        while (true) {
            // Read a request from the client
            String request = in.readLine();

            // Check if the request is empty
            if (request == null || request.length() == 1) {
                // Send a 500 response
                out.println("500 Request is empty");// (5)
                continue;
            }

            // Split the request into letter and number
            String[] parts = request.split(" ");

            // Get the letter
            String letter = parts[0];

            // Check if the letter is B or H
            if (!letter.equals("B") && !letter.equals("H")) {
                // Send a 300 response
                out.println("300 Bad request");// (4)
                continue;
            }

            // Check if the number is valid
            try {

                if (parts.length != 2) {
                    // Send a 400 response
                    out.println("400 The number is missing");// (3)
                    continue;
                }

                // Get the number
                String number = parts[1];

                // Parse the number as an integer

                int num = Integer.parseInt(number);

                // Convert the number to binary or hexadecimal
                String result;
                if (letter.equals("B")) {
                    // Convert to binary
                    result = Integer.toBinaryString(num);
                } else {
                    // Convert to hexadecimal
                    result = Integer.toHexString(num).toUpperCase();
                }

                // Send a 200 response with the result
                out.println("200 " + result);
            } catch (NumberFormatException e) {
                // Send a 400 response
                out.println("400 The number is missing");
            }
        }
    }
}
