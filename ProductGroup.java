import Tabs.Groups;

import java.util.ArrayList;

public class ProductGroup {
    String name;
    String description;

    public void addGroup(Groups group) {
        this.groups.add(group);
    }

    public void editGroup(int index, Groups group) {
        if (index >= 0 && index < this.groups.size()) {
            this.groups.set(index, group);
        }
    }

    public void deleteGroup(int index) {
        if (index >= 0 && index < this.groups.size()) {
            this.groups.remove(index);
        }
    }
    ArrayList<Groups> groups = new ArrayList<>();
    Product[] products;
    public ProductGroup(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "ProductGroup{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
