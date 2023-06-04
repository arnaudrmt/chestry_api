package bungee.fr.arnaud.chestry.backend.docker;

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

import java.util.Date;
import java.util.UUID;

public class DockerUtils {

    private final DockerClient dockerClient;
    private final ChestryBungee chestryBungee = ChestryBungee.getInstance();

    public DockerUtils() {
        dockerClient = DockerClientBuilder.getInstance().build();
    }

    public void createContainer(String img, String versionType, String uuid, String customIP) {

        chestryBungee.getMultiThreading().getExecutorService().submit(() -> {

            // Getting a random port available in the range 26000 - 50000
            int availablePort = chestryBungee.getServerUtils().getRandomAvailablePort(26000, 50000);

            // Setting a random uuid for the container
            String name = UUID.randomUUID().toString();

            // Exposing the container 25565 port
            ExposedPort tcp25565 = ExposedPort.tcp(25565);
            Ports portBindings = new Ports();
            portBindings.bind(tcp25565, Ports.Binding.bindPort(availablePort));

            // Creating the container with default settings
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

            // Starting the container
            dockerClient.startContainerCmd(containerResponse.getId()).exec();

            // Putting the info into MongoDB
            ChestryBungee.getInstance().getMongoDB().addServer(name, customIP, availablePort, versionType, new Date(),
                    UUID.fromString(uuid), ImmutableMap.of(UUID.fromString(uuid), "admin"));
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
            e.printStackTrace();
        }
    }
}
