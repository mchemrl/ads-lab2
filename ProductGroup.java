import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProductGroup {
    private String name;
    private String description;
    private static ArrayList<ProductGroup> groups = new ArrayList<>();

    public ProductGroup(String name, String description) {
        this.name = name;
        this.description = description;
        groups.add(this);
        writeGroupNamesToFile();
    }

    public void writeGroupNamesToFile() {
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

    @Override
    public String toString() {
        return name;
    }
}