package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.ProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    TextView tvProductCategoryTitle;
    ImageView imgProductCategory;
    ProgressBar progressbarProductCategory;
    RecyclerView rclrProductCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
    }

    void init(){
        bindViews();
        ProductCategory pc = (ProductCategory) getIntent().getExtras().get("itemProductCategory");
        tvProductCategoryTitle.setText(pc.getTitle());
        //
        String url = ApiAddresses.getFileUrl(pc.getImage());
        Picasso.get().load(url)
                .error(R.drawable.imgBoken)
                .placeholder(R.drawable.imgLoading)
                .into(imgProductCategory);
        //
        fillProductDataListForThisCategory(pc);
    }

    private void fillProductDataListForThisCategory(ProductCategory pc) {
        ProductService.getByCategory(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {

            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {

            }
        });

    }

    void bindViews(){
        tvProductCategoryTitle = findViewById(R.id.tvProductCategoryTitle);
        imgProductCategory = findViewById(R.id.imgProductCategory);
        progressbarProductCategory = findViewById(R.id.progressbarProductCategory);
        rclrProductCategory = findViewById(R.id.rclrProductCategory);
    }
}