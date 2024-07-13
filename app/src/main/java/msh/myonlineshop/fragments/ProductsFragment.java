package msh.myonlineshop.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;
import java.util.concurrent.Callable;

import msh.myonlineshop.R;
import msh.myonlineshop.adapters.ProductAdapter;
import msh.myonlineshop.adapters.ProductCategoryAdapter;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.ProductCategoryService;
import msh.myonlineshop.services.ProductService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsFragment extends Fragment {

    Activity activity;
    RecyclerView rvFilteredList, rvCategoryList;
    TextView tvFiltered;
    Chip chpExpensive, chpCheap, chpPopular, chpNew;
    ChipGroup chpGrp;

    public ProductsFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_products, container, false);
        init(vg);
        return vg;
    }

    private void init(ViewGroup vg) {
        bindView(vg);
        //
        fillProductCategoryData();
        //
        GridLayoutManager lyo = new GridLayoutManager(activity, 2, RecyclerView.VERTICAL, false);
        rvFilteredList.setLayoutManager(lyo);
        ////
        fillFilteredData();
    }

    private void fillProductCategoryData() {
        ProductCategoryService.getAll(new Callback<ServiceResponse<ProductCategory>>() {
            @Override
            public void onResponse(Call<ServiceResponse<ProductCategory>> call, Response<ServiceResponse<ProductCategory>> response) {
                if(response.isSuccessful() && response.body()!=null && !response.body().isHasError())
                {
                    List<ProductCategory> lst = response.body().getDataList();
                    ProductCategoryAdapter adapter = new ProductCategoryAdapter(activity, lst);
                    //
                    rvCategoryList.setAdapter(adapter);
                    //
                    LinearLayoutManager lyo = new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false);
                    rvCategoryList.setLayoutManager(lyo);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<ProductCategory>> call, Throwable t) {
                MsgUtility.showMsgShort(rvCategoryList,"Server Failure");
            }
        });
    }


    private void fillFilteredData() {
        chpNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    fillFilteredDataForNew();
            }
        });

        chpPopular.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    fillFilteredDataForPopular();
            }
        });

        chpCheap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    fillFilteredDataForCheap();
            }
        });

        chpExpensive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    fillFilteredDataForCheap();
            }
        });

    }

    private void fillFilteredDataForNew()
    {
        ProductService.getNew(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    rvFilteredList.setAdapter(new ProductAdapter(activity, dataList));
                    tvFiltered.setText(getResources().getText(R.string.new_products));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting products data from server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fillFilteredDataForCheap()
    {
    }

    private void fillFilteredDataForExpensive()
    {
    }

    private void fillFilteredDataForPopular()
    {
        ProductService.getPopular(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isHasError()) {
                    List<Product> dataList = response.body().getDataList();
                    rvFilteredList.setAdapter(new ProductAdapter(activity, dataList));
                    tvFiltered.setText(getResources().getText(R.string.popular_products));
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {
                Toast.makeText(activity, "Error on getting products data from server", Toast.LENGTH_LONG).show();
            }
        });
    }




    private void bindView(ViewGroup vg) {
        rvFilteredList = vg.findViewById(R.id.rvFilteredList);
        rvCategoryList = vg.findViewById(R.id.rvCategoryList);
        tvFiltered = vg.findViewById(R.id.tvFiltered);
        chpExpensive = vg.findViewById(R.id.chpExpensive);
        chpCheap = vg.findViewById(R.id.chpCheap);
        chpPopular = vg.findViewById(R.id.chpPopular);
        chpNew = vg.findViewById(R.id.chpNew);
        chpGrp = vg.findViewById(R.id.chpGrp);
    }

}