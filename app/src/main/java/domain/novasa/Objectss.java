package domain.novasa;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Objectss {

    @SerializedName("id")
    private  String id;

    @SerializedName("name")
    private  String name;

    @SerializedName("products")
    private List<Objectss> products;


    public Objectss(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Objectss> getProducts() {
        return products;
    }

}
