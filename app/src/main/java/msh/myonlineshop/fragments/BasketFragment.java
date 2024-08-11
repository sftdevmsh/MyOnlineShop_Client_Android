package msh.myonlineshop.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.MainActivity;
import msh.myonlineshop.PaymentActivity;
import msh.myonlineshop.R;
import msh.myonlineshop.adapters.BasketItemAdapter;
import msh.myonlineshop.adapters.BlogAdapter;
import msh.myonlineshop.localDbHandler.BasketItemDbHandler;
import msh.myonlineshop.localDbHandler.CurrentUserHandler;
import msh.myonlineshop.models.BasketItem;
import msh.myonlineshop.models.User;

public class BasketFragment extends Fragment {
    MainActivity mainActivity;
    List<BasketItem> dataList;
    RecyclerView rvBasket;
    LinearLayout lytEmptyBasket, lytSummaryBasket;
    TextView tvTotalCountBasket, tvTotalPriceBasket;
    Button btnPaymentBasket;

    private long totalCountValue = 0;
    private long totalPriceValue = 0;


    public BasketFragment(MainActivity activity) {
        this.mainActivity = activity;
    }


    @Override
    public void onResume() {
        super.onResume();
        lytSummaryBasket.setVisibility(View.GONE);
        fillData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup vg = (ViewGroup) inflater.inflate(R.layout.fragment_basket, container, false);
        //
        init(vg);
        //
        return vg;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void init(ViewGroup vg) {
        bindView(vg);
        //
        setEvents();
        //
        updateVisibility();
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(mainActivity, RecyclerView.VERTICAL, false);
        rvBasket.setLayoutManager(layoutManager);
    }

    private void setEvents() {
        btnPaymentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CurrentUserHandler.IsLoggedIn())
                {
                    //go to payment
                    btnPaymentBasket.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(mainActivity, PaymentActivity.class));

                                }
                            });
                }
                else
                {
                    //go to login
                    FragmentTransaction transaction = mainActivity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainFrameLayout, new LoginFragment(mainActivity));
                    transaction.commit();
                }
            }
        });
    }


    private void scrollChangedLoadingData(ViewGroup vg) {
    }


    private void fillData() {
        dataList = new ArrayList<>();
        BasketItemDbHandler dbHandler = new BasketItemDbHandler(mainActivity);
        dataList = dbHandler.getAllData();
        //
        dataList.stream().forEach(x -> {
            totalCountValue += x.getQuantity();
            totalPriceValue += (x.getQuantity() * x.getProduct().getPrice());
        });
        tvTotalCountBasket.setText(" " + totalCountValue + " ");
        tvTotalPriceBasket.setText(" " + totalPriceValue+" $");
        //
        BasketItemAdapter basketAdapter = new BasketItemAdapter(getActivity(), dataList);
        rvBasket.setAdapter(basketAdapter);
        //
        updateVisibility();
    }


    private void updateVisibility() {
        if(totalCountValue == 0)
        {
            lytSummaryBasket.setVisibility(View.GONE);
            lytEmptyBasket.setVisibility(View.VISIBLE);
        }
        else
        {
            btnPaymentBasket.setText(R.string.pay);
            if(!CurrentUserHandler.IsLoggedIn())
                btnPaymentBasket.setText(R.string.login_and_pay);
            //
            lytEmptyBasket.setVisibility(View.GONE);
            lytSummaryBasket.setVisibility(View.VISIBLE);
        }
    }


    private void bindView(ViewGroup vg) {
        rvBasket = vg.findViewById(R.id.rvBasket);
        lytEmptyBasket = vg.findViewById(R.id.lytEmptyBasket);
        lytSummaryBasket = vg.findViewById(R.id.lytSummaryBasket);
        tvTotalCountBasket = vg.findViewById(R.id.tvTotalCountBasket);
        tvTotalPriceBasket = vg.findViewById(R.id.tvTotalPriceBasket);
        btnPaymentBasket = vg.findViewById(R.id.btnPaymentBasket);
    }
}