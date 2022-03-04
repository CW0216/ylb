package com.hnucm.javaee_task;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



import com.hnucm.javaee_task.Fragment.HomepageFragment;
import com.hnucm.javaee_task.Fragment.PersonalFragment;
import com.hnucm.javaee_task.Fragment.ShoppingCartFragment;

public class HomepageActivity extends AppCompatActivity {


    HomepageFragment homepageFragment = new HomepageFragment();
    ShoppingCartFragment shoppingCartFragment = new ShoppingCartFragment();
    PersonalFragment personalFragment = new PersonalFragment();

    long uid,uid2,uid3;
    String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        ConstraintLayout op1 = findViewById(R.id.constraintLayout);
        ConstraintLayout op2 = findViewById(R.id.constraintLayout2);
        ConstraintLayout op3 = findViewById(R.id.constraintLayout3);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homepageFragment).commit();//默认在首页界面
        op1.setSelected(true);
        op2.setSelected(false);
        op3.setSelected(false);


        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, homepageFragment).commit();
                op1.setSelected(true);
                op2.setSelected(false);
                op3.setSelected(false);

                Intent intent = getIntent();
                uid3 = intent.getLongExtra("uid3", 0);//Activity间传值


                System.out.println(uid3);
                // 获取FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // 获取FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // 创建Bundle对象
                // 作用:存储数据，并传递到Fragment中
                Bundle bundle = new Bundle();

                // 往bundle中添加数据
                bundle.putLong("uid3",uid3);

                // 把数据设置到Fragment中
                homepageFragment.setArguments(bundle);
                System.out.println(bundle.getLong("uid3"));

            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, shoppingCartFragment).commit();
                op1.setSelected(false);
                op2.setSelected(true);
                op3.setSelected(false);

                Intent intent = getIntent();
                uid = intent.getLongExtra("uid", 0);//Activity间传值


                // 获取FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // 获取FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // 创建Bundle对象
                // 作用:存储数据，并传递到Fragment中
                Bundle bundle = new Bundle();

                // 往bundle中添加数据
                bundle.putLong("uid", uid);

                // 把数据设置到Fragment中
                shoppingCartFragment.setArguments(bundle);

            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, personalFragment).commit();
                op1.setSelected(false);
                op2.setSelected(false);
                op3.setSelected(true);

                Intent intent = getIntent();
                nickname = intent.getStringExtra("nickname");//Activity间传值
                uid2 = intent.getLongExtra("uid2", 0);//Activity间传值

                // 获取FragmentManager
                FragmentManager fragmentManager = getFragmentManager();

                // 获取FragmentTransaction
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // 创建Bundle对象
                // 作用:存储数据，并传递到Fragment中
                Bundle bundle = new Bundle();

                // 往bundle中添加数据
                bundle.putString("nickname", nickname);
                bundle.putLong("uid2",uid2);

                // 把数据设置到Fragment中
                personalFragment.setArguments(bundle);

            }
        });
    }
}

