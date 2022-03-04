package com.hnucm.javaee_task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.gson.Gson;
import com.hnucm.javaee_task.tools.CartResult;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;

public class MyOrder extends AppCompatActivity {

    RecyclerView recyclerView;
    CartResult cartResult;
    MyAdapter myAdapter;
    Long uid2 = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        recyclerView = findViewById(R.id.recyclerView3);
        myAdapter = new MyAdapter();

        Intent intent = getIntent();
        uid2 = intent.getLongExtra("uid2",0);//Activity间传值
        System.out.println(uid2);

        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplication(), DividerItemDecoration.VERTICAL));

        x.Ext.init(getApplication());
        RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/cart/findAllById");
        requestParams.addParameter("page",1);
        requestParams.addParameter("limit",100);
        requestParams.addParameter("uid",uid2);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity","result"+result);
                Gson gson = new Gson();
                cartResult = gson.fromJson(result, CartResult.class);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("MainActivity","error "+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView m_name,price,sales,date,m_type,m_company;
        public MyViewHolder(View itemView) {
            super(itemView);
            m_name = itemView.findViewById(R.id.textView_medicinename);
            price = itemView.findViewById(R.id.textView_medicineprice);
            sales = itemView.findViewById(R.id.textView_medicinesales);
            date = itemView.findViewById(R.id.textView_medicinedate);
            m_type = itemView.findViewById(R.id.textView_medicinetype);
            m_company = itemView.findViewById(R.id.textView_medicinecompany);

        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyOrder.MyViewHolder> {

        @NonNull
        @Override
        public MyOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MyOrder.this).inflate(R.layout.order_list,parent,false);
            MyOrder.MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyOrder.MyViewHolder holder, int position) {
            String mtype = null;


            if (cartResult.data.get(position).getMedicine().getM_type() == 1) {
                mtype = "家庭常备";
            } else if (cartResult.data.get(position).getMedicine().getM_type() == 2) {
                mtype = "感冒用药";
            } else if (cartResult.data.get(position).getMedicine().getM_type() == 3) {
                mtype = "清热解毒";
            }
            holder.m_name.setText(cartResult.data.get(position).getMedicine().getM_name());
            holder.price.setText(cartResult.data.get(position).getMedicine().getPrice()+"");
            holder.sales.setText(cartResult.data.get(position).getMedicine().getSales()+"");
            Timestamp timestamp = new Timestamp(cartResult.data.get(position).getDate().getTime());
            holder.date.setText(timestamp+"");
            holder.m_company.setText(cartResult.data.get(position).getMedicine().getCompany()+"");
            holder.m_type.setText(mtype);

        }


        @Override
        public int getItemCount() {
            return cartResult.data.size();
        }
    }
}


