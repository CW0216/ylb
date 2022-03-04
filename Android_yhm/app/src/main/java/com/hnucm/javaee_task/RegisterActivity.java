package com.hnucm.javaee_task;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hnucm.javaee_task.tools.Result;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    EditText cusername = null;
    EditText cnickname = null;
    EditText cpassword = null;
    EditText ctelephone = null;
    String username,nickname,password,telephone=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cusername = findViewById(R.id.username);
        cnickname = findViewById(R.id.nickname);
        cpassword = findViewById(R.id.password);
        ctelephone = findViewById(R.id.telephone);
    }
    public void register(View view){

        username = cusername.getText().toString();
        nickname = cnickname.getText().toString();
        password = cpassword.getText().toString();
        telephone = ctelephone.getText().toString();

        int flag = 0;
        String format = "[^[a-zA-Z]\\w{5,11}$]";
        String fusername = username.trim();
        String fpassword = password.trim();
        boolean res = Pattern.matches(format,fusername);
        boolean res2 = Pattern.matches(format,fpassword);
        if(res){
            flag++;
        }else{
            Toast.makeText(getApplicationContext(),"帐号需以字母开头，长度在6~12之间，只能包含字母、数字和下划线",Toast.LENGTH_LONG).show();
        }
        if(res2){
            flag++;
        }else{
            Toast.makeText(getApplicationContext(),"密码需以字母开头，长度在6~12之间，只能包含字母、数字和下划线",Toast.LENGTH_LONG).show();
        }

        if (flag==2){
            x.Ext.init(getApplication());
            RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/user/save");
            requestParams.setAsJsonContent(true);

            String data="{\"username\":"+username+",\"password\":"+password+",\"nickname\":"+nickname+",\"telephone\":"+telephone+"}";//"password":"test"
            requestParams.setBodyContent(data);

            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("MainActivity","result"+result);
                    Gson gson = new Gson();
                    Result result1 = gson.fromJson(result,Result.class);
                    if (result1.getCode() == 1 ){
                        Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
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
}




