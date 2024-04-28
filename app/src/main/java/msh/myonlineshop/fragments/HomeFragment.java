package msh.myonlineshop.fragments;

import android.app.Activity;
import android.icu.lang.UCharacter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.R;
import msh.myonlineshop.adapters.ProductAdapter;
import msh.myonlineshop.adapters.ProductCategoryAdapter;
import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.ProductCategory;
import msh.myonlineshop.models.SliderItem;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.ProductCategoryService;
import msh.myonlineshop.services.ProductService;
import msh.myonlineshop.services.SliderService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Activity activity;
    RecyclerView rvProductCategories, rvNewProducts, rvPopularProducts;
    ImageSlider imgSlider;

    public HomeFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        ViewGroup vgRootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        init(vgRootView);
        return vgRootView;
    }

    private void init(ViewGroup vg) {
        bindViews(vg);
        fillImgSlider();
        fillRvProductCategories();
        fillRvNewProducts();
        fillRvPopularProducts();
    }


    private void fillImgSlider() {
        SliderService.get(new Callback<ServiceResponse<SliderItem>>() {
            @Override
            public void onResponse(Call<ServiceResponse<SliderItem>> call, Response<ServiceResponse<SliderItem>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        ArrayList<SlideModel> sliderImageList = new ArrayList<>();
                        //
                        List<SliderItem> imageList = response.body().getDataList();
                        //
                        imageList.stream().forEach( s -> {
                            String url = ApiAddresses.getFileUrl(s.getImage());
                            String description = s.getDescription();
                            SlideModel sm = new SlideModel(url, description, ScaleTypes.FIT);
                            //
                            sliderImageList.add(sm);
                        });
                        //
                        imgSlider.setImageList(sliderImageList);
                        imgSlider.startSliding(5000);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<SliderItem>> call, Throwable t) {
                MsgUtility.showMsgShort(imgSlider, "Server Failure: could not get slider images");
            }
        });
    }
    private void fillImgSliderTemp() {
        ArrayList<SlideModel> imageList = new ArrayList<SlideModel>(); // Create image list
        //
        // imageList.add(SlideModel("String Url" or R.drawable)
        // imageList.add(SlideModel("String Url" or R.drawable, "title") You can add title
        //
        for(int i = 0;i<=4;i++) {
            String url = ApiAddresses.getFileUrl("shopping"+i+".jpg");
            String description = ("shopping"+(i+1)+": it is a good shopping time").trim();
            SlideModel sm = new SlideModel(url, description, ScaleTypes.FIT);
            imageList.add(sm);
        }
        imgSlider.setImageList(imageList);
        imgSlider.startSliding(5000);
    }


    private void fillRvProductCategories() {
        ProductCategoryService.getAll(new Callback<ServiceResponse<ProductCategory>>() {
            @Override
            public void onResponse(Call<ServiceResponse<ProductCategory>> call, Response<ServiceResponse<ProductCategory>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        List<ProductCategory> lst = response.body().getDataList();
                        ProductCategoryAdapter adp = new ProductCategoryAdapter(activity, lst);
                        rvProductCategories.setAdapter(adp);
                        LinearLayoutManager lyo = new LinearLayoutManager(
                                                                activity
                                                                , LinearLayoutManager.HORIZONTAL
                                                                , false);
                        rvProductCategories.setLayoutManager(lyo);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<ProductCategory>> call, Throwable t) {
                MsgUtility.showMsgShort(
                        activity.findViewById(R.id.bottomNav)
                        ,"Server Failure: fill product categories"
                       // , -130
                        );
            }
        });

    }

    private void fillRvNewProducts() {
        ProductService.getNew(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        List<Product> lst = response.body().getDataList();
                        ProductAdapter adp = new ProductAdapter(activity, lst);
                        rvNewProducts.setAdapter(adp);
                        LinearLayoutManager lyo = new LinearLayoutManager(
                                activity
                                , LinearLayoutManager.HORIZONTAL
                                , false);
                        rvNewProducts.setLayoutManager(lyo);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {

            }
        });
        
    }

    private void fillRvPopularProducts() {
        ProductService.getPopular(new Callback<ServiceResponse<Product>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Product>> call, Response<ServiceResponse<Product>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        List<Product> lst = response.body().getDataList();
                        ProductAdapter adp = new ProductAdapter(activity, lst);
                        rvPopularProducts.setAdapter(adp);
                        LinearLayoutManager lyo = new LinearLayoutManager(
                                activity
                                , LinearLayoutManager.HORIZONTAL
                                , false);
                        rvPopularProducts.setLayoutManager(lyo);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Product>> call, Throwable t) {

            }
        });
    }


    private void bindViews(ViewGroup vg) {
        rvProductCategories = vg.findViewById(R.id.rvProductCategories);
        rvNewProducts = vg.findViewById(R.id.rvNewProducts);
        rvPopularProducts = vg.findViewById(R.id.rvPopularProducts);
        imgSlider = vg.findViewById(R.id.imgSlider);
    }


}