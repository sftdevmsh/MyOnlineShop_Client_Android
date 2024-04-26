package msh.myonlineshop.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.util.Pair;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.squareup.picasso.Picasso;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import msh.myonlineshop.ProductActivity;
import msh.myonlineshop.ProductDetailsActivity;
import msh.myonlineshop.R;
import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.base.ServiceResponse;
import retrofit2.Callback;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    List<Product> lst;
    Activity activity;

    public ProductAdapter(Activity activity, List<Product> lst) {
        this.lst = lst;
        this.activity =  activity;
    }

    @NonNull
    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);
        View v = layoutInflater.inflate(R.layout.product_item_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = lst.get(position);
        //
        holder.tvProductTitle.setText(p.getTitle());
        holder.tvProductPrice.setText(String.valueOf(p.getPrice()));
        //
        String url = ApiAddresses.getFileUrl(p.getImage());
        System.out.println("url: "+url);
        Picasso.get().load(url)
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_broken)
                .into(holder.imgProduct);
        ////
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetailsActivity.class);
                intent.putExtra("data",p);
                //
                Pair[] pairs = new Pair[3];
                pairs[0] = new Pair(holder.tvProductTitle, "trTitle");
                pairs[1] = new Pair(holder.tvProductPrice, "trPrice");
                pairs[2] = new Pair(holder.imgProduct, "trImage");
                //
                ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation(activity, pairs);
                //
                activity.startActivity(intent, ao.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvProductPrice, tvProductTitle;
        ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductTitle = itemView.findViewById(R.id.tvProductTitle);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }
}
