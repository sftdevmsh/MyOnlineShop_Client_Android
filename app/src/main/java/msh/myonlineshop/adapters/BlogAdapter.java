package msh.myonlineshop.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.BlogDetailActivity;
import msh.myonlineshop.R;
import msh.myonlineshop.clientHandler.base.ApiAddresses;
import msh.myonlineshop.models.Blog;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder>{

    private List<Blog> lst = new ArrayList<>();;
    private Activity activity;

    public BlogAdapter(Activity activity, List<Blog> lst) {
        this.lst = lst;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View v = layoutInflater.inflate(R.layout.layout_item_blog, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Blog blog = lst.get(position);
        //
        holder.tvTitle.setText(blog.getTitle());
        holder.tvSubtitle.setText(blog.getSubtitle());
        //
        String url = ApiAddresses.getFileUrl(blog.getImage());
        Picasso.get().load(url)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_loading)
                .into(holder.iv);
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BlogDetailActivity.class);
                intent.putExtra("data", blog);
                ////
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair(holder.iv, "trImage");
                pairs[1] = new Pair(holder.tvTitle, "trTitle");
                pairs[2] = new Pair(holder.tvSubtitle, "trSubtitle");
                //
                ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                ////
                activity.startActivity(intent, ao.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView iv;
        public TextView tvTitle, tvSubtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
        }
    }
}
