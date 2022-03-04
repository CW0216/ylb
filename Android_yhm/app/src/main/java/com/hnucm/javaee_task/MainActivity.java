package com.hnucm.javaee_task;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hnucm.javaee_task.Fragment.HomepageFragment;
import com.hnucm.javaee_task.tools.GlideEngine;
import com.hnucm.javaee_task.tools.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    EditText cusername = null;
    EditText cpassword = null;
    String username,password=null;

    long uid,uid2,uid3;
    String nickname;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cusername = findViewById(R.id.username);
        cpassword = findViewById(R.id.password);

        AndPermission.with(this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.CAMERA)
                .onGranted(permissions -> {
                    Toast.makeText(MainActivity.this, "授权成功！", Toast.LENGTH_SHORT).show();
                })
                .onDenied(permissions -> {
                    //用户拒绝->强制退出app
                    Toast.makeText(MainActivity.this, "未获取权限不能使用本app！", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                })
                .start();


    }

    public void regist(View view){
        Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }


    public void login(View view){

        username = cusername.getText().toString();
        password = cpassword.getText().toString();

        x.Ext.init(getApplication());
        RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/admin/login");
        requestParams.setAsJsonContent(true);

        String data="{\"username\":"+username+",\"password\":"+password+",\"userType\":\"user\"}";//"password":"test"
        requestParams.setBodyContent(data);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity","result"+result);
                Gson gson = new Gson();
                Result result1 = gson.fromJson(result,Result.class);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    uid = jsonObject.getLong("uid");
                    uid2 = uid;
                    uid3 = uid;
                    JSONObject user = jsonObject.getJSONObject("user");
                    nickname = user.getString("nickname");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result1.getCode() == 1 ){
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,HomepageActivity.class);
                    intent.putExtra("uid",uid);
                    intent.putExtra("uid2",uid2);
                    intent.putExtra("uid3",uid3);
                    intent.putExtra("nickname",nickname);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"登录失败",Toast.LENGTH_LONG).show();
                }
                //主线程
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





}



