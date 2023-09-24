package CRUD;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.port.Port;
import views.menu.Login;

public class PortCRUD {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/Port.txt";

    public List<Port> readAllPorts() {
        List<Port> ports = new ArrayList<>();
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

                String[] parts = line.split(",");
                if (parts.length != 6) {
                    System.err.println("Invalid line: " + line);
                    continue;
                }
                String id = parts[0].trim();
                String name = parts[1].trim();
                double latitude = Double.parseDouble(parts[2].trim());
                double longitude = Double.parseDouble(parts[3].trim());
                double storingCapacity = Double.parseDouble(parts[4].trim());
                boolean landingAbility = Boolean.parseBoolean(parts[5].trim());
                Port port = new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
                ports.add(port);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        return ports;
    }

    public void addPort(Port port) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            
            bw.write(
                    "\n" + port.getId() + ", " + port.getName() + ", " + port.getLatitude() + ", " + port.getLongitude()
                            + ", " + port.getStoringCapacity() + ", " + port.getLandingAbility());
          
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePort(String id, Port updatedPort) {
        List<Port> Ports = readAllPorts();
        int index = -1;
        for (int i = 0; i < Ports.size(); i++) {
            if (Ports.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Ports.set(index, updatedPort);
            writeAllPorts(Ports);
        }
    }
    public void deletePort(String id) {
        List<Port> ports = readAllPorts();
        ports.removeIf(port -> port.getId().equals(id));
        writeAllPorts(ports);
    }

    private void writeAllPorts(List<Port> Ports) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(
                    "# Port File\n# Format: PortID, PortName, PortLatitude, PortLongitude, PortStoringCapacity, PortLandingAbility ");
            for (Port port : Ports) {
                bw.write("\n" + port.getId() + ", " + port.getName() + ", " + port.getLatitude()+ ", " + port.getLongitude()
                        + ", " + port.getStoringCapacity() + ", " + port.getLandingAbility());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Port readPort(String portId) {
        List<Port> ports = readAllPorts();
        for (Port port : ports) {
            if (port.getId().equals(portId)) {
                return port;
            }
        }
        return null;
    }

}