package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


/**
 * @author 33455
 */
public class ListDialogFragment extends DialogFragment {
    RecyclerView recyclerViews;
    private View rootView;
    private MusicListAdapter listAdapter;

    public ListDialogFragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        ListDialogFragment.newInstance();
    }


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(true);
         rootView = inflater.inflate(R.layout.fragment_list_dialog, container, false);
        recyclerViews=rootView.findViewById(R.id.listview_music);
        listAdapter=new MusicListAdapter(getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerViews.setLayoutManager(linearLayoutManager);
        recyclerViews.setAdapter(listAdapter);

   /*     //Do something
        // 设置宽度为屏宽、靠近屏幕底部。
        final Window window = getDialog().getWindow();
        //这步是必须的
        if (window != null) {
            window.setBackgroundDrawableResource(androidx.cardview.R.color.cardview_shadow_end_color);
        }
        //必要，设置 padding，这一步也是必须的，内容不能填充全部宽度和高度
        window.getDecorView().setPadding(50, 50, 50, 50);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wlp);*/
        ListDialogFragment.newInstance();
        return rootView;
    }
    public static ListDialogFragment newInstance() {
        ListDialogFragment dialog = new ListDialogFragment();
        // 设置主题，这里只能通过xml方式设置主题，不能通过Java代码处理，因为这是getWindow还是null，
        // 而且window的几乎所有属性，都可以通过xml设置
        dialog.setStyle(STYLE_NORMAL, R.style.FullSreenDialogTheme);
        // 设置触摸、点击弹窗外部不可关闭
        dialog.setCancelable(true);
        // 对于DialogFragment，设置外部传的参数，通过bundle设置，然后在onCreateView读取
/*        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        bundle.putString("name", name);
        // 把外部传进的参数放到bundle里， 在onCreateView里通过继续getArguments()读取参数，
        // 通过bundle来处理，是因为就算DialogFragment被重建了，也能恢复回来并初始化
        dialog.setArguments(bundle);*/
        return dialog;
    }

}