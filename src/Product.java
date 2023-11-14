public class Product {
    private final String ID;
    private String name;
    private String description;
    private double cost;

    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    // Getters
    public String getID() { return ID.trim(); }
    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public double getCost() { return cost; }

    // Utility method to pad strings
    private String padString(String input, int length) {
        return String.format("%-" + length + "s", input);
    }

    // Method to create a fixed-length string representation for random access file
    public String toFixedLengthString() {
        return padString(ID, 6) + padString(name, 35) + padString(description, 75) + String.format("%-8.2f", cost);
    }

    // Static method to create a Product instance from a fixed-length string
    public static Product fromFixedLengthString(String record) {
        String id = record.substring(0, 6);
        String name = record.substring(6, 41);
        String description = record.substring(41, 116);
        double cost = Double.parseDouble(record.substring(116).trim());
        return new Product(id, name, description, cost);
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
