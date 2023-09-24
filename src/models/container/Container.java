// package container;

// public class Container {
//     private String id;
//     protected double weight;
//     private String type; // DryStorage/OpenTop/OpenSide/Refrigerated/Liquid

//     public String getType() {
//         return type;
//     }

//     public String getId() {
//         return id;
//     }

//     public double getWeight() {
//         return weight;
//     }

//     public Container(String id, double weight, String type) {
//         this.id = id;
//         this.weight = weight;
//         this.type = type;
//     }

//     public double getFuelConsumptionForShip() {
//         switch (type) {
//             case "DryStorage":
//                 return weight * 3.5;
//             case "OpenTop":
//                 return weight * 2.8;
//             case "OpenSide":
//                 return weight * 2.7;
//             case "Refrigerated":
//                 return weight * 4.5;
//             case "Liquid":
//                 return weight * 4.8;
//             default:
//                 return 0.0;
//         }
//     }

//     public double getFuelConsumptionForTruck() {
//         switch (type) {
//             case "DryStorage":
//                 return weight * 4.6;
//             case "OpenTop":
//                 return weight * 3.2;
//             case "OpenSide":
//                 return weight * 3.2;
//             case "Refrigerated":
//                 return weight * 5.4;
//             case "Liquid":
//                 return weight * 5.3;
//             default:
//                 return 0.0;
//         }
//     }
// }

package models.container;

public class Container {
    private String id;
    protected double weight;
    protected String type;
    public String getStatus() {
        return status;
    }

    public String getLocationId() {
        return locationId;
    }

    private String status;
    private String locationId;
    public Container(String id, double weight, String type,String status, String locationId) {
        this.id = id;
        this.weight = weight;
        this.type = type;
        this.status = status;
        this.locationId = locationId;
    }
    
    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public double getFuelConsumptionForShip() {
        return 0.0; // Default implementation
    }

    public double getFuelConsumptionForTruck() {
        return 0.0; // Default implementation
    }
}
