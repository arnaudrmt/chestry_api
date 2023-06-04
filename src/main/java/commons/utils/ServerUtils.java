package commons.utils;

import bungee.fr.arnaud.chestry.ChestryBungee;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerUtils {

    // Check for an available port in MongoDB and the Machine in specific a range

    public int getRandomAvailablePort(int startRangePort, int endRangePort) {
        for (int i = 0; i < 1000; i++) {
            int port = startRangePort + (int) (Math.random() * (endRangePort - startRangePort + 1));

            try {

                if(!ChestryBungee.getInstance().getMongoDB().portIsUsed(port)) {
                    // Check if the port is available for general use
                    ServerSocket serverSocket = new ServerSocket(port);
                    serverSocket.close();
                    return port;
                }

            } catch (IOException e) {
            }
        }

        throw new RuntimeException("Unable to find an available port within the specified range.");
    }
}
