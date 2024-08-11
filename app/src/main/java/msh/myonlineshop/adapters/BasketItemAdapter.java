package msh.myonlineshop.adapters;

import android.app.Activity;
import android.content.Context;
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

import msh.myonlineshop.R;
import msh.myonlineshop.clientHandler.base.ApiAddresses;
import msh.myonlineshop.models.BasketItem;

public class BasketItemAdapter extends RecyclerView.Adapter<BasketItemAdapter.ViewHolder> {
    Activity activity;
    List<BasketItem> lst = new ArrayList<>();


    public BasketItemAdapter(Activity activity, List<BasketItem> lst)
    {
        this.activity = activity;
        this.lst = lst;
        System.out.println(lst.get(0).getSize().getTitle());
        System.out.println(lst.get(1).getSize().getTitle());
    }


    @NonNull
    @Override
    public BasketItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(ctx);

        View view = layoutInflater.inflate(R.layout.layout_item_basket, parent, false);
        ViewHolder viewHolder = new BasketItemAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BasketItemAdapter.ViewHolder holder, int position) {
        BasketItem bi = lst.get(position);
        System.out.println(bi.getColor().getTitle());
        System.out.println(bi.getSize().getTitle());
        //
        String name = bi.getProduct().getTitle()
                        + " _ " + bi.getSize().getTitle()
                        + " _ " + bi.getColor().getTitle();
        holder.tvNameBasketItem.setText(name);
        //
        holder.tvQuantityBasketItem.setText(bi.getQuantity()+" ");
        //
        holder.tvPriceBasketItem.setText(bi.getProduct().getPrice()+" $");
        //
        String imgUrl = bi.getProduct().getImage();
        Picasso.get()
                .load(ApiAddresses.getFileUrl(imgUrl))
                .placeholder(R.drawable.img_loading)
                .error(R.drawable.img_broken)
                .into(holder.imgBasketItem);
    }


    @Override
    public int getItemCount() {
        return lst.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imgBasketItem;
        public TextView tvNameBasketItem, tvQuantityBasketItem, tvPriceBasketItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBasketItem = itemView.findViewById(R.id.imgBasketItem);
            tvNameBasketItem = itemView.findViewById(R.id.tvNameBasketItem);
            tvQuantityBasketItem = itemView.findViewById(R.id.tvQuantityBasketItem);
            tvPriceBasketItem = itemView.findViewById(R.id.tvPriceBasketItem);
        }
    }

}
