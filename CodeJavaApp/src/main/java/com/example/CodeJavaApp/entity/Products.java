package com.example.CodeJavaApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
import java.text.NumberFormat;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prd;
    @Column(name = "name_prd")
    private String name_prd;
    @Column(name = "import_price")
    private Double import_price;
    @Column(name = "price")
    private Double price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "size")
    private Integer size;
    @Column(name = "type")
    private String type;
    @Column(name = "image")
    private String image;
    @Column(name = "details_prd")
    private String details_prd;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL) //Quen hệ 1-n với đối tượng BillDetali. 1 sp có thể có trong nhiều hóa đơn
    // MapopedBy trỏ tới tên products ở trong BillsDetail.
    private Collection<BillDetail> billDetails;

    public Products() {
    }

    public Products(Long id_prd, String name_prd, Double import_price, Double price, Integer quantity, Integer size, String type, String image, String details_prd) {
        this.id_prd = id_prd;
        this.name_prd = name_prd;
        this.import_price = import_price;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.type = type;
        this.image = image;
        this.details_prd = details_prd;
    }

    public Collection<BillDetail> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Collection<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public Long getId_prd() {
        return id_prd;
    }

    public void setId_prd(Long id_prd) {
        this.id_prd = id_prd;
    }

    public String getName_prd() {
        return name_prd;
    }

    public void setName_prd(String name_prd) {
        this.name_prd = name_prd;
    }

    public Double getImport_price() {
        return import_price;
    }

    public void setImport_price(Double import_price) {
        this.import_price = import_price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetails_prd() {
        return details_prd;
    }

    public void setDetails_prd(String details_prd) {
        this.details_prd = details_prd;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id_prd=" + id_prd +
                ", name_prd='" + name_prd + '\'' +
                ", import_price=" + import_price +
                ", price=" + price +
                ", quantity=" + quantity +
                ", size=" + size +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                ", details_prd='" + details_prd + '\'' +
                '}';
    }

    @Transient
    public String getImagePath(){
        if (image == null || id_prd == null) return null;
        return "/uploads-image/" + "null" +"/" +image;
    }

    @Transient
    public String convetPrice(double x){
        double vnd = x;
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(vnd);
        return price;
    }
}
