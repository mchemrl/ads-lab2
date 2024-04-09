public class ProductGroup {
    String name;
    String description;
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
