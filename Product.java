public class Product {

    String name;
    String description;
    String manufacturer;
    int quantity;
    double price;

    // constructor for product
    public Product(String name, String description, String manufacturer, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
