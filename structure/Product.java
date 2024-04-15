package structure;

import ui.Groups;
import ui.Items;

import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Product {
    String group;
    String name;
    String description;
    String manufacturer;
    int quantity;
    double price;
   static ArrayList<Product> products = new ArrayList<>();

    // constructor for product
    public Product(String name, String description, String manufacturer, int quantity, double price) {
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.price = price;
        products.add(this);
        writeItemsToFile();
    }

    public static void writeItemsToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("items.txt"));
            HashSet<String> itemNames = new HashSet<>();

            for (Product item : products) {
                if (!itemNames.contains(item.getName())) {
                    itemNames.add(item.getName());
                    writer.write(item.getGroup() + " - " + item.getName() + " - " + item.getDescription() + " - " + item.getManufacturer() + " - " + item.getQuantity() + " - " + item.getPrice());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadItemsFromFile() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("items.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length >= 6) {
                    String group = parts[0];
                    String name = parts[1];
                    String description = parts[2];
                    String manufacturer = parts[3];
                    int quantity = Integer.parseInt(parts[4]);
                    double price = Double.parseDouble(parts[5]);
                    Product newProduct = new Product(name, description, manufacturer, quantity, price);
                    newProduct.setGroup(group);
                    Product.getProducts().add(newProduct);
                    Items.updateProductTable(newProduct);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void editProduct(int index, Product product) {
        if (index >= 0 && index < this.products.size()) {
            this.products.set(index, product);
        }
    }

    public void deleteProduct(int index) {
        if (index >= 0 && index < this.products.size()) {
            this.products.remove(index);
        }
    }

    //get product data

    public static ArrayList<Product> getProducts() {
        return products;
    }
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
        return "structure.Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
