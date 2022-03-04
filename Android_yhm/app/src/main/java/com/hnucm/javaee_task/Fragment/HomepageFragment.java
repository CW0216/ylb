package com.hnucm.javaee_task.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hnucm.javaee_task.tools.GlideImageLoader;
import com.hnucm.javaee_task.tools.OnItemClickListener;
import com.hnucm.javaee_task.R;
import com.hnucm.javaee_task.tools.MedicineResult;
import com.hnucm.javaee_task.tools.Result;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    MedicineResult medicineResult;

    private Banner banner;
    private GlideImageLoader glideImageLoader;
    private List<Integer> imagePath;//存放图片

    long uid3;
    //private static final long uid3 = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_homepage, container, false);
        banner = mview.findViewById(R.id.banner);

        Intent intent = getActivity().getIntent();
        uid3 = intent.getLongExtra("uid3", 0);//Activity间传值
        System.out.println(uid3);

        initImage();
        initView();

        return mview;
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter();



        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        x.Ext.init(getActivity().getApplication());
        RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/medicine/findAll?page=1&limit=100");
        requestParams.setAsJsonContent(true);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity", "result" + result);

                Gson gson = new Gson();
                medicineResult = gson.fromJson(result, MedicineResult.class);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("MainActivity", "ex" + ex.getMessage());
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

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener clickListener) {
            this.mOnItemClickListener = clickListener;
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
            String mtype = null;

            if (medicineResult.data.get(position).getM_type() == 1) {
                mtype = "家庭常备";
            } else if (medicineResult.data.get(position).getM_type() == 2) {
                mtype = "感冒用药";
            } else if (medicineResult.data.get(position).getM_type() == 3) {
                mtype = "清热解毒";
            }

            Long mid = medicineResult.data.get(position).getId();
            holder.m_name.setText(medicineResult.data.get(position).getM_name());
            holder.price.setText(medicineResult.data.get(position).getPrice() + "");
            holder.sales.setText(medicineResult.data.get(position).getSales() + "");
            holder.company.setText(medicineResult.data.get(position).getCompany());
            holder.m_type.setText(mtype);
            Glide.with(getActivity()).load(medicineResult.data.get(position).getImg()).into(holder.img);


            holder.imageButton_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    x.Ext.init(getActivity().getApplication());
                    RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/cart/save");
                    requestParams.setAsJsonContent(true);

                    String data="{\"mid\":"+mid+",\"uid\":"+uid3+"}";
                    requestParams.setBodyContent(data);

                    x.http().post(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("MainActivity","result"+result);
                            Gson gson = new Gson();
                            Result result1 = gson.fromJson(result,Result.class);

                            if (result1.getCode() == 0 ){
                                Toast.makeText(getActivity().getApplicationContext(),"加入购物车成功",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getActivity().getApplicationContext(),"加入购物车失败",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.i("MainActivity","error"+ex.getMessage());
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

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.medicine_list, parent, false);
            final MyViewHolder myViewHolder = new MyViewHolder(view, mOnItemClickListener);
            return myViewHolder;
        }

        @Override
        public int getItemCount() {
            return medicineResult.data.size();
        }


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView m_name, price, sales, company, m_type;
        ImageView img;
        ImageButton imageButton_cart;

        public MyViewHolder(View itemView, OnItemClickListener onClickListener) {
            super(itemView);
            m_name = itemView.findViewById(R.id.textView_medicinename);
            price = itemView.findViewById(R.id.textView_medicineprice);
            sales = itemView.findViewById(R.id.textView_medicinesales);
            company = itemView.findViewById(R.id.textView_medicinecompany);
            m_type = itemView.findViewById(R.id.textView_medicinetype);
            img = itemView.findViewById(R.id.imageview_medicine);
            imageButton_cart = itemView.findViewById(R.id.imageButton_cart);

            imageButton_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("ylb");
                    if (onClickListener != null) {
                        int position = getAdapterPosition();
                        //确保position值有效
                        if (position != RecyclerView.NO_POSITION) {
                            onClickListener.onItemClick(view, position);
                        }
                    }

                }
            });
        }

    }

    private void initView() {


        //实例化，绑定组件
        glideImageLoader = new GlideImageLoader();
        //Banner相应的设置，共5种样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

        //加载器
        banner.setImageLoader(glideImageLoader);
        //间隔时间
        banner.setDelayTime(8000);
        //自动轮播
        banner.isAutoPlay(true);
        //小圆点位置
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //放图片路径
        banner.setImages(imagePath);
        banner.start();

    }

    private void initImage() {
        imagePath = new ArrayList<>();
        imagePath.add(R.drawable.a);
        imagePath.add(R.drawable.b);
        imagePath.add(R.drawable.c);
    }
}