package com.hnucm.javaee_task;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hnucm.javaee_task.Fragment.PersonalFragment;
import com.hnucm.javaee_task.entity.User;
import com.hnucm.javaee_task.tools.Result;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.regex.Pattern;

public class Updatepersonalinfo extends AppCompatActivity {


    private EditText edit_NewPassword, edit_NewUsername, edit_NewTelephone, edit_NewNickname;
    String  username, password ,telephone,nickname= null;
    String format = "[^[a-zA-Z]\\w{5,11}$]";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updatepersonalinfo);


        edit_NewUsername = this.findViewById(R.id.newUsername);
        edit_NewPassword = this.findViewById(R.id.newPassword);
        edit_NewTelephone = this.findViewById(R.id.newTelephone);
        edit_NewNickname = this.findViewById(R.id.newNickname);
    }

    public void updatePersonalInfo(View view) {

        telephone = edit_NewTelephone.getText().toString();
        username = edit_NewUsername.getText().toString();
        password = edit_NewPassword.getText().toString();
        nickname = edit_NewNickname.getText().toString();

        Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Updatepersonalinfo.this, PersonalFragment.class);
        startActivity(intent);

        /*x.Ext.init(getApplication());
        RequestParams requestParams = new RequestParams("http://10.136.10.249:8090/user/update");
        requestParams.setAsJsonContent(true);

        String data = "{\"username\":" + username + ",\"password\":" + password + ",\"telephone\":" + telephone + ",\"nickname\":" + nickname + "}";//"password":"test"
        requestParams.setBodyContent(data);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MainActivity", "result" + result);
                Gson gson = new Gson();
                Result result1 = gson.fromJson(result, Result.class);
                if (result1.getCode() == 1) {
                    Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Updatepersonalinfo.this, PersonalFragment.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "修改失败", Toast.LENGTH_LONG).show();
                    edit_NewPassword.setText("");
                    edit_NewNickname.setText("");
                    edit_NewTelephone.setText("");
                    edit_NewUsername.setText("");
                }
                //主线程
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
        });*/
    }
}
