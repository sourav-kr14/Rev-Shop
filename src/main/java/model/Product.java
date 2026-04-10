package model;

public class Product
{
    private  int productId;
    private  String name;
    private String description;
    private  double price;
    private double mrp;
    private String category;
    private int stock;
    private int sellerId;
    private int threshold;
    public  Product(){};

    public Product(int productId, String name, String description,
                   double price, double mrp, String category,
                   int stock, int sellerId,int threshold) {

        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.mrp = mrp;
        this.category = category;
        this.stock = stock;
        this.sellerId = sellerId;
        this.threshold=threshold;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", MRP=" + mrp +
                ", category='" + category + '\'' +
                ", stock=" + stock +
                ", sellerId=" + sellerId + "Threshold:  "+threshold+
                '}';
    }
}
