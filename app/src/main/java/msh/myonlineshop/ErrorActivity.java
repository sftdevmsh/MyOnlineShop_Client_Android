package msh.myonlineshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    TextView txtMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        init();
    }

    void init(){
        bindViews();

        Intent intentData = getIntent();
        String msg = intentData.getStringExtra("msg");
        txtMsg.setText(msg);
    }

    void bindViews()
    {
        txtMsg = findViewById(R.id.txtMsg);
    }
}