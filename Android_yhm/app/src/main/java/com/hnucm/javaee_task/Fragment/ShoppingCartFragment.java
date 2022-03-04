package com.hnucm.javaee_task.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hnucm.javaee_task.R;
import com.hnucm.javaee_task.tools.CartResult;
import com.hnucm.javaee_task.tools.Result;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;

public class ShoppingCartFragment extends Fragment {

        RecyclerView recyclerView;
        MyAdapter myAdapter;
        CartResult cartResult;
        long uid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout for this fragment

            Bundle bundle = this.getArguments();
            uid = bundle.getLong("uid");

            return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
        }

        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            recyclerView = getActivity().findViewById(R.id.recyclerView2);
            myAdapter = new MyAdapter();

            //添加分割线
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

            x.Ext.init(getActivity().getApplication());
            RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/cart/findAllByState");
            requestParams.addParameter("page",1);
            requestParams.addParameter("limit",100);
            requestParams.addParameter("uid",uid);
            requestParams.addParameter("state",0);

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("MainActivity","result"+result);
                    Gson gson = new Gson();
                    cartResult = gson.fromJson(result, CartResult.class);
                    recyclerView.setAdapter(myAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


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

        public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.cart_list,parent,false);
                MyViewHolder myViewHolder = new MyViewHolder(view);
                return myViewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                holder.m_type.setText(mtype);
                holder.cartid.setText(cartResult.data.get(position).getId()+"");

                Long cart_id = cartResult.data.get(position).getId();
                Glide.with(getActivity()).load(cartResult.data.get(position).getMedicine().getImg()).into(holder.imageView);

                holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        x.Ext.init(getActivity().getApplication());
                        RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/cart/updateState");
                        requestParams.setAsJsonContent(true);

                        String data="{\"id\":"+cart_id+",\"state\":\"1\"}";
                        requestParams.setBodyContent(data);
                        x.http().post(requestParams, new Callback.CommonCallback<String>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onSuccess(String result) {
                                Log.i("MainActivity","result"+result);
                                Gson gson = new Gson();
                                Result result1 = gson.fromJson(result,Result.class);
                                Toast.makeText(getActivity().getApplicationContext(),"结算成功",Toast.LENGTH_LONG).show();
                                //主线程

                                myAdapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onError(Throwable ex, boolean isOnCallback) {
                                Log.i("MainActivity","ex"+ex.getMessage());
                            }

                            @Override
                            public void onCancelled(CancelledException cex) {

                            }

                            @Override
                            public void onFinished() {

                            }
                        });
                    }
                });
            }

            @Override
            public int getItemCount() {
                return cartResult.data.size();
            }
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView m_name,price,sales,date,m_type,cartid;
            ImageView imageView;
            ConstraintLayout constraintLayout;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                m_name = itemView.findViewById(R.id.textView_medicinename);
                price = itemView.findViewById(R.id.textView_medicineprice);
                sales = itemView.findViewById(R.id.textView_medicinesales);
                date = itemView.findViewById(R.id.medicinedate);
                m_type = itemView.findViewById(R.id.textView_medicinetype);
                imageView = itemView.findViewById(R.id.imageView_cart);

                constraintLayout = itemView.findViewById(R.id.settlement);
                cartid = itemView.findViewById(R.id.cartid);
            }
        }


    }
