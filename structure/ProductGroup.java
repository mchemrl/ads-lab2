package structure;
import java.util.HashSet;

import ui.Groups;
import ui.Items;

import java.io.*;
import java.util.ArrayList;

public class ProductGroup {
    private String name;
    private String description;
    public static ArrayList <ProductGroup> groups = new ArrayList < >();
    public static ArrayList <Product> products = new ArrayList<>();

    public ProductGroup(String name, String description, boolean writeToFile) {
        this.name = name;
        this.description = description;
        groups.add(this);
        if (writeToFile) {
            writeGroupsToFile();
        }
    }

    public static void deleteGroup(ProductGroup group) {
        groups.remove(group);
        writeGroupsToFile();
    }

    public static void editGroup(ProductGroup group, String name, String description) {
        group.name = name;
        group.description = description;
        writeGroupsToFile();
    }

    public static void writeGroupsToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("group_names.txt"));
            HashSet<String> groupNames = new HashSet<>();

            for (ProductGroup group : groups) {
                if (!groupNames.contains(group.getName())) {
                    groupNames.add(group.getName());
                    writer.write(group.getName());
                    writer.write(" - ");
                    writer.write(group.getDescription());
                    writer.newLine();
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGroupsFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("group_names.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ");
                if (parts.length >= 2) {
                    String name = parts[0];
                    String description = parts[1];
                    ProductGroup newGroup = new ProductGroup(name, description, false);
                   // groups.add(newGroup);
                    Groups.groupListModel.addElement(newGroup);
              //      Items.updateGroupComboBox();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product getProductByIndex(int index){
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        } else {
            return null; // or throw an exception
        }
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        return this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    public static ArrayList<ProductGroup> getExistingGroups() {
        return groups;
    }
    public ArrayList <Product> getProducts(){
        return products;
    }
    public void addProduct(Product newProduct){
        products.add(newProduct);

    }
    public void addAllProductsFromGroup(ProductGroup group){
        products.addAll(group.getProducts());

    }
    public void deleteProduct(Product newProduct){
        products.remove(newProduct);

    }
    public static ProductGroup findGroupByName(String name) {
    for (ProductGroup group : groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        return null;
    }

    //lalala
    public static void editProduct(Product product, String name, String description, String manufacturer, int quantity, double price) {
        product.setName(name);
        product.setDescription(description);
        product.setManufacturer(manufacturer);
        product.setQuantity(quantity);
        product.setPrice(price);

    }

    @Override
    public String toString() {
        return this.name; // Assuming 'name' is the group name
    }}
