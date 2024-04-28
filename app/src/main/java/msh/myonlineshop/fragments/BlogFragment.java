package msh.myonlineshop.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;


import msh.myonlineshop.R;
import msh.myonlineshop.adapters.BlogAdapter;
import msh.myonlineshop.models.Blog;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.BlogService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BlogFragment extends Fragment {
    private Activity activity;
    private RecyclerView rvBlog;
    private ProgressBar pbBlog;
    private NestedScrollView nsvBlog;
    private List<Blog> lst = new ArrayList<>();;
    private int pageNumber = 0, pageSize = 10;

    public BlogFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_blog, container, false);
        init(vg);
        return vg;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void init(ViewGroup vg) {
        bindView(vg);
        //
        pbBlog.setVisibility(View.VISIBLE);
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        rvBlog.setLayoutManager(layoutManager);
        //
        fillData();
        //
        scrollChangedLoadingData(vg);
    }

    private void fillData() {
        BlogService.getAllData(new Callback<ServiceResponse<Blog>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Blog>> call, Response<ServiceResponse<Blog>> response) {
                if(response.isSuccessful() && response.body()!=null)
                    if(!response.body().isHasError())
                    {
                        pbBlog.setVisibility(View.GONE);
                        List<Blog> newLst = response.body().getDataList();
                        lst.addAll(newLst);
                        BlogAdapter blogAdapter = new BlogAdapter(getActivity(), lst);
                        rvBlog.setAdapter(blogAdapter);
                    }
            }

            @Override
            public void onFailure(Call<ServiceResponse<Blog>> call, Throwable t) {
                MsgUtility.showMsgShort(rvBlog,"Server Failure");
            }
        },pageNumber,pageSize);
    }



    private void scrollChangedLoadingData(ViewGroup vg) {
        nsvBlog.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    pbBlog.setVisibility(View.VISIBLE);
                    pageNumber++;
                    fillData();
                }
            }
        });
    }




    private void bindView(ViewGroup vg) {
        rvBlog = vg.findViewById(R.id.rvBlog);
        pbBlog = vg.findViewById(R.id.pbBlog);
        nsvBlog = vg.findViewById(R.id.nsvBlog);
    }

}