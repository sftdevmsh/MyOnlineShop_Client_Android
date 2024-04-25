package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;

import msh.myonlineshop.models.Color;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.Size;


public class ProductDetailsActivity extends AppCompatActivity {

    private Product product;
    private TextView tvPrice, tvTitle, tvDescription;
    private ImageView img;
    private ChipGroup chpSizes, chpColors;
    private Button btnAddToCard;
    private LinearLayout lyo;

    private Size selectedSize = null;
    private Color selectedColor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
    }

    void init(){
        bindViews();
        //
        Product p = (Product) getIntent().getExtras().get("data");
        //
        fillPageWithData(p);

    }


    private void fillPageWithData(Product p) {
        //
        tvTitle.setText(p.getTitle());
        tvPrice.setText(p.getPrice()+"$");
        //
        //tvDescription.setText(p.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(product.getDescription()));
        }
        //
        String url = p.getImage();
//        Picasso.get().load(url)
//                .error(R.drawable.img_broken)
//                .placeholder(R.drawable.img_loading)
//                .into(img);
        //
        fillSizeChips(p);
        //
        fillSizeChips(p);
    }

    private void fillSizeChips(Product p) {
        for(Size s:p.getSizesList())
        {
            Chip chp = new Chip(this);
            chp.setText(s.getTitle());
            chp.setCheckable(true);
            chp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(chp.isChecked())
                        selectedSize = s;
                    else
                        selectedSize= null;
                }
            });
            chpSizes.addView(chp);
        }
    }

    private void fillColorChips(Product p) {
        for(Color c:p.getColorsList())
        {
            Chip chp = new Chip(this);
            chp.setText(c.getName());
            chp.setCheckable(true);
            //
            int iColor = android.graphics.Color.parseColor(c.getValue());
            chp.setTextColor(iColor);
            //
            chp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(chp.isChecked())
                        selectedColor = c;
                    else
                        selectedColor= null;
                }
            });
            chpSizes.addView(chp);
        }
    }

    void bindViews(){
        img =  findViewById(R.id.img);
        tvTitle =  findViewById(R.id.tvTitle);
        tvPrice =  findViewById(R.id.tvPrice);
        tvDescription =  findViewById(R.id.tvDescription);
        chpSizes = findViewById(R.id.chpSizes);
        chpColors = findViewById(R.id.chpColors);
    }
}