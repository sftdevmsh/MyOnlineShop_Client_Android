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
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import msh.myonlineshop.R;
import msh.myonlineshop.adapters.ProductCategoryAdapter;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.ProductCategoryService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsFragment extends Fragment {

    Activity activity;
    RecyclerView rvFrmProductsFilteredList, rvFrmProductsCategoryList;
    TextView tvFiltered;
    Chip chpFrmProductsExpensive, chpFrmProductsCheap, chpFrmProductsPopular, chpFrmProductsNew;
    ChipGroup chpGrpFrmProducts;

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
        rvFrmProductsFilteredList.setLayoutManager(lyo);
        fillFilteredData();

    }

    private void fillFilteredData() {

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
                    rvFrmProductsCategoryList.setAdapter(adapter);
                    //
                    LinearLayoutManager lyo = new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false);
                    rvFrmProductsCategoryList.setLayoutManager(lyo);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<ProductCategory>> call, Throwable t) {
                MsgUtility.showMsgShort(rvFrmProductsCategoryList,"Server Failure");
            }
        });
    }

    private void bindView(ViewGroup vg) {
        rvFrmProductsFilteredList = vg.findViewById(R.id.rvFrmProductsFilteredList);
        rvFrmProductsCategoryList = vg.findViewById(R.id.rvFrmProductsCategoryList);
        tvFiltered = vg.findViewById(R.id.tvFiltered);
        chpFrmProductsExpensive = vg.findViewById(R.id.chpFrmProductsExpensive);
        chpFrmProductsCheap = vg.findViewById(R.id.chpFrmProductsCheap);
        chpFrmProductsPopular = vg.findViewById(R.id.chpFrmProductsPopular);
        chpFrmProductsNew = vg.findViewById(R.id.chpFrmProductsNew);
        chpGrpFrmProducts = vg.findViewById(R.id.chpGrpFrmProducts);
    }


}