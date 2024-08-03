package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import msh.myonlineshop.clientHandler.base.ApiAddresses;
import msh.myonlineshop.models.Blog;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.BlogService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends AppCompatActivity {

    Blog blog;
    TextView tvTitle, tvSubtitle, tvDescription, tvVisitCount, tvDate;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        increaseVisitCounts();
    }

    void init(){
        bindViews();
        //
        blog = (Blog) getIntent().getExtras().get("data");
        //
        fillPageWithData();
    }

    private void fillPageWithData() {
        tvTitle.setText(blog.getTitle());
        tvSubtitle.setText(blog.getSubtitle());
        //
        tvDate.setText(blog.getPublishDateStr());
        //
        tvVisitCount.setText(String.valueOf(blog.getVisitCount()));
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(blog.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(blog.getDescription()));
        }
        //
        Picasso.get().load(ApiAddresses.getFileUrl(blog.getImage()))
                .fit()
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_broken)
                .into(iv);
    }


    void increaseVisitCounts(){
        BlogService.increaseVisitCount(new Callback<ServiceResponse<Long>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Long>> call, Response<ServiceResponse<Long>> response) {
                System.out.println("BlogDetailActivity_increaseVisitCount_success");
                int cnt = 0;
                if (response.body() != null) {
                    cnt = response.body().getDataList().get(0).intValue();
                }
                tvVisitCount.setText(String.valueOf(cnt));
            }

            @Override
            public void onFailure(Call<ServiceResponse<Long>> call, Throwable t) {
                System.out.println("BlogDetailActivity_increaseVisitCount_failed");
            }
        }, blog.getId());
    }

//
    void bindViews(){
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvDate = findViewById(R.id.tvDate);
        tvVisitCount = findViewById(R.id.tvVisitCount);
        iv = findViewById(R.id.iv);
    }

}