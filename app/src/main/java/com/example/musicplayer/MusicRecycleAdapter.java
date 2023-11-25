package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicRecycleAdapter extends RecyclerView.Adapter<MusicRecycleAdapter.MusicViewHoder> {


//    声明一个接口对象
 private onItemClickListener onItemClickListenerd;
/**
 * 声明一个上下文
 */
    private Context context;
    /**
     * 创建一个传接口的函数
     */
    public void setOnItemClickListener(MusicRecycleAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListenerd = onItemClickListener;
    }
    /**
     * 创建一个接口
     */
    public interface onItemClickListener{
        public void onItemListener(View view,int position);
    }

/**
 * 创建一个无参构造函数
 */
    public MusicRecycleAdapter() {
    }

/**
 * 创建一个有参构造函数
 */
    public MusicRecycleAdapter(Context context) {
        this.context = context;
    }
/**
 * 创建viewholder，主要是创建一个视图
 */
    @NonNull
    @Override
    public MusicViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_recycle_music,parent,false);
        return new MusicViewHoder(view);
    }

/**
 * 绑定viewholder，主要是适配数据源
 */
    @Override
    public void onBindViewHolder(@NonNull MusicViewHoder holder, @SuppressLint("RecyclerView") int position) {
//        设置数据源
        MusicBean itemBean=Myapplication.getDataMusicList().get(position);
        holder.itemSong.setText(itemBean.getSong());
        holder.itemSinger.setText(itemBean.getSinger());
        holder.itemNum.setText(itemBean.getNum());
        holder.itemAlbum.setText(itemBean.getAlbum());
        holder.itemTime.setText(itemBean.getTime());
//        设置视图点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerd.onItemListener(view,position);
                //Myapplication.setMusicPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Myapplication.getDataMusicList().size();
    }
//    创建一个viewhoder类
/**
 * 定义viewholder
 */
static class MusicViewHoder extends RecyclerView.ViewHolder{
    TextView itemSong, itemSinger, itemTime, itemNum, itemAlbum;
    public MusicViewHoder(@NonNull View itemView) {
        super(itemView);
        itemSong=itemView.findViewById(R.id.music_item_song);
        itemSinger=itemView.findViewById(R.id.music_item_singer);
        itemTime=itemView.findViewById(R.id.music_item_time);
        itemNum=itemView.findViewById(R.id.music_item_num);
        itemAlbum=itemView.findViewById(R.id.music_item_album);

    }
}
}
