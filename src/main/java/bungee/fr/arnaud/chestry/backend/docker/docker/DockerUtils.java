package bungee.fr.arnaud.chestry.backend.docker.docker;

import bungee.fr.arnaud.chestry.ChestryBungee;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.RemoveContainerCmd;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.command.StopContainerCmd;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

public class DockerUtils {

    DockerClient dockerClient;

    private ExecutorService executorService;

    public DockerUtils() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    public void createContainer(String img, String uuid, String customIP) {

        ChestryBungee.getInstance().getMultiThreading().getExecutorService().submit(() -> {

            int availablePort = getRandomPort();
            String name = img + "_" + availablePort;

            ExposedPort tcp25565 = ExposedPort.tcp(25565);
            Ports portBindings = new Ports();
            portBindings.bind(tcp25565, Ports.Binding.bindPort(availablePort));

            CreateContainerResponse containerResponse = dockerClient.createContainerCmd(img)
                    .withName(name)
                    .withExposedPorts(tcp25565)
                    .withPortBindings(portBindings)
                    .withTty(true)
                    .withStdinOpen(true)

                    .withEnv("nether=true",
                            "flight=false",
                            "seed=",
                            "npcs=true",
                            "whitelist=false",
                            "whitelistfile=",
                            "ops=",
                            "animals=true",
                            "onlinemode=true",
                            "pvp=true",
                            "difficulty=1",
                            "commandblock=false",
                            "gamemode=1",
                            "maxplayers=5",
                            "monsters=true",
                            "viewdistance=10",
                            "structures=true",
                            "motd=A New Minecraft Server")
                    .exec();

            dockerClient.startContainerCmd(containerResponse.getId()).exec();

            ChestryBungee.getInstance().getMongoDB().addServer(name, availablePort, uuid, customIP, ImmutableMap.of(uuid, "admin"));
        });
    }

    public void startContainer(String name) {

        StartContainerCmd startCmd = dockerClient.startContainerCmd(name);
        startCmd.exec();
    }

    public void stopContainer(String name) {

        StopContainerCmd stopCmd = dockerClient.stopContainerCmd(name);
        stopCmd.exec();
    }

    public void deleteContainer(String name) {

        stopContainer(name);

        try {
            RemoveContainerCmd removeCmd = dockerClient.removeContainerCmd(name);
            removeCmd.exec();
        } catch (NotFoundException e) {
            // The container does not exist
            System.out.println("Container not found: " + name);
        }
    }

    public static int getRandomPort() {
        for (int i = 0; i < 1000; i++) {
            int port = 26000 + (int) (Math.random() * (50000 - 26000 + 1));

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
