package com.hnucm.javaee_task.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hnucm.javaee_task.HomepageActivity;
import com.hnucm.javaee_task.MainActivity;
import com.hnucm.javaee_task.MyOrder;
import com.hnucm.javaee_task.R;
import com.hnucm.javaee_task.Updatepersonalinfo;
import com.hnucm.javaee_task.tools.CircleImageView;
import com.hnucm.javaee_task.tools.GlideEngine;

import com.hnucm.javaee_task.tools.Result;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;


public class PersonalFragment extends Fragment {

    private Button btn_UpdatePersonal,btn_quit,btn_MyOrder;
    private TextView textView_nickname;
    private String nickname;
    private CircleImageView imageView;
    long uid2;

    View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_personal, container, false);
        initView();
        Bundle bundle = this.getArguments();
        nickname = bundle.getString("nickname");
        uid2 = bundle.getLong("uid2");
        textView_nickname.setText(nickname);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(PersonalFragment.this)
                        .openGallery(PictureMimeType.ofAll())
                        .imageEngine(GlideEngine.createGlideEngine())
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                // onResult Callback
                                for (LocalMedia localMedia : result){
                                    Log.i("MainActivity",localMedia.getPath());
                                }
                            }

                            @Override
                            public void onCancel() {
                                // onCancel Callback
                            }
                        });
            }
        });

        return mView;


    }
    private void initView() {
        btn_UpdatePersonal = (Button) mView.findViewById(R.id.btn_updatePersonalInfo);
        btn_MyOrder = mView.findViewById(R.id.btn_myorder);
        btn_quit = (Button) mView.findViewById(R.id.btn_quit);
        textView_nickname = mView.findViewById(R.id.nickname);
        imageView = mView.findViewById(R.id.img_headportrait);
        btn_UpdatePersonal.setOnClickListener(new UpdatePersonal());
        btn_MyOrder.setOnClickListener(new myorder());

        btn_quit.setOnClickListener(new quit());
    }

    public class quit implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                    .setTitle("是否确认退出？")
                    .setIcon(R.mipmap.ic_launcher)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent intent = new Intent(getActivity(),MainActivity.class);
                            startActivity(intent);
                        }
                    })

                    .setNegativeButton("否", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
            alertDialog.show();
        }
    }

    private class UpdatePersonal implements View.OnClickListener {
        public void onClick(View v) {
                    Intent intent= new Intent(getActivity(),Updatepersonalinfo.class);
                    startActivity(intent);
            }
        }

    private class myorder implements View.OnClickListener {
        public void onClick(View v) {
                Intent intent= new Intent(getActivity(), MyOrder.class);
                intent.putExtra("uid2",uid2);
                startActivity(intent);
        }
    }

}