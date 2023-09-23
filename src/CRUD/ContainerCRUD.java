package CRUD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.container.Container;
import views.menu.Login;

public class ContainerCRUD {

    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Container.txt";

    public List<Container> readAllContainers() {
        List<Container> containers = new ArrayList<>();

        try {
            File file = new File(FILE_PATH); // Replace with your file path
            Scanner scanner = new Scanner(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                // Skip comment lines and empty lines
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(", ");
                if (parts.length != 5) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }

                String id = parts[0];
                double weight = Double.parseDouble(parts[1]);
                // ContainerType type = ContainerType.valueOf(parts[2]);
                String type = parts[2];
                String status = parts[3];
                String locationId = parts[4];
                Container container = new Container(id, weight, type, status, locationId);
                containers.add(container);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return containers;
    }

    public void addContainer(Container container) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write("\n" + container.getId() + ", " + container.getWeight() + ", " + container.getType() + ", "
                    + container.getStatus() + ", " + container.getLocationId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateContainer(String id, Container updatedContainer) {
        List<Container> containers = readAllContainers();
        int index = -1;
        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            containers.set(index, updatedContainer);
            writeAllContainers(containers);
        }
    }

    public void deleteContainer(String id) {
        List<Container> containers = readAllContainers();
        containers.removeIf(container -> container.getId().equals(id));
        writeAllContainers(containers);
    }

    private void writeAllContainers(List<Container> containers) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("# Container File\n# Format: ContainerID, Weight, Type");
            for (Container container : containers) {
                bw.write("\n" + container.getId() + ", " + container.getWeight() + ", " + container.getType() + ", " + container.getStatus() + ", " + container.getLocationId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Container readContainerById(String containerId) {
        List<Container> containers = readAllContainers();
        for (Container container : containers) {
            if (container.getId().equals(containerId)) {
                return container;
            }
        }
        return null; // Return null if no container with the given ID is found
    }

}
