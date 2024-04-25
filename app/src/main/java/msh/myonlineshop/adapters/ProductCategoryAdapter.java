package msh.myonlineshop.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.List;

import msh.myonlineshop.ProductActivity;
import msh.myonlineshop.R;
import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.models.ProductCategory;

public class ProductCategoryAdapter extends RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>{

    private List<ProductCategory> dataList;
    private Activity activity;

    public ProductCategoryAdapter(Activity activity, List<ProductCategory> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }



    @NonNull
    @Override
    public ProductCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //
        View view = inflater.inflate(R.layout.product_category_item_layout , parent, false);
        //
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductCategory pc = dataList.get(position);
        //
        holder.tvTitle.setText(pc.getTitle());
        //
//        holder.ivImage.setImageURI(pc.getImage());
        String strFileUrl = ApiAddresses.getFileUrl(pc.getImage());
        Picasso.get()
                .load(strFileUrl)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_broken)
                .into(holder.ivImage);
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductActivity.class);
                intent.putExtra("itemProductCategory", pc);
                ////
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair(holder.tvTitle, "tvTitle");
                pairs[1] = new Pair(holder.ivImage, "ivImage");
                //
                ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                ////
                activity.startActivity(intent, ao.toBundle());
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ivImage;
        public TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivProductCategoryImage);
            tvTitle = itemView.findViewById(R.id.tvProductCategoryTitle);
        }
    }
}
