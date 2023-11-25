package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListingMusicFragment extends Fragment {
//    声明循环控件
    private RecyclerView musicReclcle;
//    声明适配器
    private MusicRecycleAdapter adapter;
//    声明布局管理器
    private LinearLayoutManager linearLayoutManager;
    private ClickItemListener itemClick;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_music_list, container, false);
//        实例化循环控件
        musicReclcle=view.findViewById(R.id.music_recycle);
//        对控件进行设置
        initsetAdapter();
        return view;
    }
    @SuppressLint("NotifyDataSetChanged")
    private void initsetAdapter() {
//        实例化适配器
        adapter=new MusicRecycleAdapter(getActivity());
//        确定布局管理器
        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        设置布局方式
        musicReclcle.setLayoutManager(linearLayoutManager);
//        适配数据源
        musicReclcle.setAdapter(adapter);
        adapter.setOnItemClickListener(new MusicRecycleAdapter.onItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                    itemClick.sendPosition(position);
            }
        });
    }
    public void setClickItemListener(ClickItemListener listener){
        this.itemClick=listener;
    }
    public interface ClickItemListener{
        public void sendPosition(int posion);
    }

}