package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import msh.myonlineshop.enums.PaymentType;
import msh.myonlineshop.localDbHandler.BasketItemDbHandler;
import msh.myonlineshop.localDbHandler.CurrentUserHandler;
import msh.myonlineshop.models.BasketItem;
import msh.myonlineshop.models.OrderItem;
import msh.myonlineshop.models.Payment;
import msh.myonlineshop.models.PaymentUserVM;
import msh.myonlineshop.models.StartPaymentVM;
import msh.myonlineshop.models.User;
import msh.myonlineshop.models.base.ServiceResponse;
import msh.myonlineshop.services.PaymentService;
import msh.myonlineshop.utlities.MsgUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {


    private TextInputEditText
            etFirstNamePayment,
            etLastNamePayment,
            etUsernamePayment,
            etPasswordPayment,
            etTelPayment,
            etAddressPayment,
            etMobilePayment,
            etEmailPayment,
            etPostalPayment;
    private Button btnPayment;
    private ProgressBar pbPayment;
    private LinearLayout lytPayment;
    private List<BasketItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
    }

    private void init(){
        bindView();
        fillBasketItemsDataList();
        fillUserData();
        setEvents();
    }

    private void setEvents() {
        btnPayment.setOnClickListener(x -> {
            //
            if (!checkFields()) {
                //Snackbar.make(lytPayment, "Please fill all of the fields", BaseTransientBottomBar.LENGTH_LONG).show();
                MsgUtility.showMsgLong(lytPayment, "Please fill all of the fields");
                return;
            }
            //
            pbPayment.setVisibility(View.VISIBLE);
            btnPayment.setVisibility(View.GONE);
            //
            PaymentUserVM userVM = new PaymentUserVM();
            userVM.setAddress(etAddressPayment.getText().toString());
            userVM.setEmail(etEmailPayment.getText().toString());
            userVM.setFirstName(etFirstNamePayment.getText().toString());
            userVM.setLastName(etLastNamePayment.getText().toString());
            userVM.setPassword(etPasswordPayment.getText().toString());
            userVM.setMobile(etMobilePayment.getText().toString());
            userVM.setPostalCode(etPostalPayment.getText().toString());
            userVM.setUsername(etUsernamePayment.getText().toString());
            userVM.setTel(etTelPayment.getText().toString());
            //
            if (CurrentUserHandler.IsLoggedIn())
                userVM.setCustomerId(CurrentUserHandler.getCurrentUser().getCustomerId());
            //
            List<OrderItem> orderItemList = new ArrayList<>();
            //
            dataList.forEach(i -> {
                OrderItem o = new OrderItem();
                if (i.getColor() != null) {
                    o.setColorId(i.getColor().getId());
                }
                if (i.getSize() != null) {
                    o.setSizeId(i.getSize().getId());
                }
                o.setProductId(i.getProduct().getId());
                o.setCount(i.getQuantity());
                orderItemList.add(o);
            });
            //
            Payment payment = new Payment();
            payment.setPaymentType(PaymentType.ZarinPal);
            payment.setCustomer(userVM);
            payment.setOrderItems(orderItemList);
            //
            PaymentService.addPayment(new Callback<ServiceResponse<StartPaymentVM>>() {
                @Override
                public void onResponse(Call<ServiceResponse<StartPaymentVM>> call, Response<ServiceResponse<StartPaymentVM>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        if (!response.body().isHasError()) {
                            StartPaymentVM data = response.body().getDataList().get(0);
                            if (data.getLocation() != null && !data.getLocation().equals("")) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getLocation()));
                                startActivity(browserIntent);
                                BasketItemDbHandler dbHandler = new BasketItemDbHandler(PaymentActivity.this);
                                dbHandler.deleteAllBasket();
                                finish();
                            }
                        } else if (response.body().isHasError()) {
                            MsgUtility.showMsgLong(lytPayment, response.body().getMessage());
                            return;
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse<StartPaymentVM>> call, Throwable t) {
                    MsgUtility.showMsgShort(lytPayment, "Error on payment method");
                }
            }, payment);
        });
    }

    private void fillBasketItemsDataList() {
        dataList = new ArrayList<>();
        BasketItemDbHandler dbHandler = new BasketItemDbHandler(this);
        dataList = dbHandler.getAllData();
    }


    private void fillUserData() {
        if (CurrentUserHandler.IsLoggedIn()) {
            User user = CurrentUserHandler.getCurrentUser();
//            firstName.getEditText().setText(user.getFirstName());
            etFirstNamePayment.setText(user.getFirstName());
            etLastNamePayment.setText(user.getLastName());
            etUsernamePayment.setText(user.getUsername());
            etEmailPayment.setText(user.getEmail());
            etPasswordPayment.setVisibility(View.GONE);
            if (user.getCustomer() != null) {
                etTelPayment.setText(user.getCustomer().getTel());
                etAddressPayment.setText(user.getCustomer().getAddress());
                etMobilePayment.setText(user.getCustomer().getMobile());
                etPostalPayment.setText(user.getCustomer().getPostalCode());
            }
        }
    }
    private boolean checkFields() {

        if (!CurrentUserHandler.IsLoggedIn())
            return false;
        if (etFirstNamePayment.getText().toString().equals(""))
            return false;
        if (etLastNamePayment.getText().toString().equals(""))
            return false;
        if (etUsernamePayment.getText().toString().equals(""))
            return false;
        if (etAddressPayment.getText().toString().equals(""))
            return false;
        if (etEmailPayment.getText().toString().equals(""))
            return false;
        if (etPostalPayment.getText().toString().equals(""))
            return false;
        if (etMobilePayment.getText().toString().equals(""))
            return false;
        if (etTelPayment.getText().toString().equals(""))
            return false;

        return true;
    }




    private void bindView(){
        etFirstNamePayment = findViewById(R.id.etFirstNamePayment);
        etLastNamePayment = findViewById(R.id.etLastNamePayment);
        etUsernamePayment = findViewById(R.id.etUsernamePayment);
        etPasswordPayment = findViewById(R.id.etPasswordPayment);
        etTelPayment = findViewById(R.id.etTelPayment);
        etAddressPayment = findViewById(R.id.etAddressPayment);
        etMobilePayment = findViewById(R.id.etMobilePayment);
        etEmailPayment = findViewById(R.id.etEmailPayment);
        etPostalPayment = findViewById(R.id.etPostalPayment);
        btnPayment = findViewById(R.id.btnPayment);
        pbPayment = findViewById(R.id.pbPayment);
        lytPayment = findViewById(R.id.lytPayment);
    }

}