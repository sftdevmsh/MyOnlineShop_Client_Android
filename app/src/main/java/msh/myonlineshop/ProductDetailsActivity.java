package msh.myonlineshop;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Picasso;

import msh.myonlineshop.clients.base.ApiAddresses;
import msh.myonlineshop.handlers.CardDbHandler;
import msh.myonlineshop.models.CardItem;
import msh.myonlineshop.models.Color;
import msh.myonlineshop.models.Product;
import msh.myonlineshop.models.Size;
import msh.myonlineshop.utlities.MsgUtility;


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
        //
        bindViews();
        //
        btnAddToCard.setVisibility(View.VISIBLE);
        //
        product = (Product) getIntent().getExtras().get("data");
        //
        fillPageWithData();
        //
        btnAddToCardClickListener();
    }

    private void btnAddToCardClickListener() {
        btnAddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddToCard.setVisibility(View.GONE);
                //
                if(product.getSizesList().size()<=0 || selectedSize == null)
                    MsgUtility.showMsgShort(btnAddToCard, "Please select a Size option.");
                else if(product.getColorsList().size()<=0 || selectedColor == null)
                    MsgUtility.showMsgShort(btnAddToCard, "Please select a Color option.");
                //
                else {
                    try {
                        CardItem cardItem = new CardItem();
                        cardItem.setProduct(product);
                        cardItem.setColor(selectedColor);
                        cardItem.setSize(selectedSize);
                        cardItem.setQuantity(1);
                        //
                        CardDbHandler cardDbHandler = new CardDbHandler(ProductDetailsActivity.this);
                        cardDbHandler.addToBasket(cardItem);
                        MsgUtility.showMsgShort(btnAddToCard,"Successfully added");
                    }
                    catch (Exception e)
                    {
                        MsgUtility.showMsgShort(btnAddToCard,"Error adding order to the basket");
                    }
                    //
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            finish();
                            ProductDetailsActivity.this.overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
                        }
                    }, 2000);
                }
            }
        });
    }


    private void fillPageWithData() {
        //
        tvTitle.setText(product.getTitle());
        tvPrice.setText(product.getPrice()+"$");
        //
        //tvDescription.setText(p.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvDescription.setText(Html.fromHtml(product.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvDescription.setText(Html.fromHtml(product.getDescription()));
        }
        //
        String url = ApiAddresses.getFileUrl(product.getImage());
        Picasso.get().load(url)
                .error(R.drawable.img_broken)
                .placeholder(R.drawable.img_loading)
                .into(img);
        //
        fillColorChips();
        //
        fillSizeChips();
    }

    private void fillSizeChips() {
        for(Size s:product.getSizesList())
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

    private void fillColorChips() {
        System.out.println("p.getColors().size(): "+product.getColors().size());
        for(Color c:product.getColorsList())
        {
            Chip chp = new Chip(this);
            chp.setText(c.getTitle());
            chp.setCheckable(true);
            //
            int iColor = android.graphics.Color.parseColor(c.getValue());
            chp.setTextColor(iColor);
            chp.setBackgroundColor(iColor);
            //
            System.out.println("c.getTitle(): "+c.getTitle());
            System.out.println("iColor: "+iColor);
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
            chpColors.addView(chp);
        }
    }

    void bindViews(){
        img =  findViewById(R.id.img);
        tvTitle =  findViewById(R.id.tvTitle);
        tvPrice =  findViewById(R.id.tvPrice);
        tvDescription =  findViewById(R.id.tvDescription);
        chpSizes = findViewById(R.id.chpSizes);
        chpColors = findViewById(R.id.chpColors);
        btnAddToCard = findViewById(R.id.btnAddToCard);
    }
}