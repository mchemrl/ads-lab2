package structure;

import java.io.*;
import java.util.ArrayList;

public class ProductGroup {
    private String name;
    private String description;
    public static ArrayList <ProductGroup> groups = new ArrayList < >();
    public static ArrayList <Product> products = new ArrayList<>();

    public ProductGroup(String name, String description) {
        this.name = name;
        this.description = description;
        groups.add(this);
        writeGroupNamesToFile();
    }

    public static void deleteGroup(ProductGroup group) {
        groups.remove(group);
        writeGroupNamesToFile();
    }

    public static void editGroup(ProductGroup group, String name, String description) {
        group.name = name;
        group.description = description;
        writeGroupNamesToFile();
    }

    public static void writeGroupNamesToFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("group_names.txt"));
            for (ProductGroup group : groups) {
                writer.write(group.getName());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
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
    public void deleteProduct(Product newProduct){
        products.remove(newProduct);

    }
    @Override
    public String toString() {
        return name;
    }
}
