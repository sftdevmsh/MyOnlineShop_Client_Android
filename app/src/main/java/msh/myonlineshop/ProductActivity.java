package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.List;

import msh.myonlineshop.adapters.ProductAdapter;
import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.ProductService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    TextView tvProductCategoryTitle;
    ImageView imgProductCategory;
    ProgressBar progressbarProductCategory;
    RecyclerView rvProducts;
    private int pageNumber = 0 , pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        init();
    }

    void init(){
        bindViews();
        ProductCategory pc = (ProductCategory) getIntent().getExtras().get("data");
        tvProductCategoryTitle.setText(pc.getTitle());
        //
        String url = ApiAddresses.getFileUrl(pc.getImage());
        Picasso.get().load(url)
                .error(R.drawable.img_broken)
                .placeholder(R.drawable.img_loading)
                .into(imgProductCategory);

        fillProductDataListForThisCategory(pc);
    }

    private void fillProductDataListForThisCategory(ProductCategory pc) {
        ProductService.getByCategory(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        List<Product> lst = response.body().getDataList();
                        //
                        rvProducts.setAdapter(new ProductAdapter(ProductActivity.this, lst));
                        //
                        GridLayoutManager lyo = new GridLayoutManager(ProductActivity.this, 2, RecyclerView.VERTICAL, false);
                        rvProducts.setLayoutManager(lyo);
                        //
                        rvProducts.setVisibility(View.VISIBLE);
                        progressbarProductCategory.setVisibility(View.INVISIBLE);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                MsgUtility.showMsgShort(findViewById(R.id.bottomNav)
                        ,"Server Failure: get new products");
            }
        }, pc.getId(), pageNumber, pageSize);

    }

    void bindViews(){
        tvProductCategoryTitle = findViewById(R.id.tvProductCategoryTitle);
        imgProductCategory = findViewById(R.id.imgProductCategory);
        progressbarProductCategory = findViewById(R.id.progressbarProductCategory);
        rvProducts = findViewById(R.id.rvProducts);
    }
}