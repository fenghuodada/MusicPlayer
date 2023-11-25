package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHoder> {


//    声明一个接口对象
 private onItemClickListeners onItemClickListenerd;

/**
 * 声明一个上下文
 */
    private Context context;
    /**
     * 创建一个传接口的函数
     */
    public void setOnItemClickListener(onItemClickListeners onItemClickListener) {
        this.onItemClickListenerd = onItemClickListener;
    }
    /**
     * 创建一个接口
     */
    public interface onItemClickListeners{
        public void onItemListenerd(View view,int position);
    }

/**
 * 创建一个无参构造函数
 */
    public MusicListAdapter() {
    }

/**
 * 创建一个有参构造函数
 */
    public MusicListAdapter(Context context) {
        this.context = context;
    }
/**
 * 创建viewholder，主要是创建一个视图
 */
    @NonNull
    @Override
    public MusicViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_list_layout,parent,false);
        return new MusicViewHoder(view);
    }

/**
 * 绑定viewholder，主要是适配数据源
 */
    @Override
    public void onBindViewHolder(@NonNull MusicViewHoder holder, @SuppressLint("RecyclerView") int position) {
//        设置数据源
        MusicBean itemBean=Myapplication.getDataMusicList().get(position);
        holder.song.setText(itemBean.getSong());
        holder.singer.setText(itemBean.getSinger());
        holder.time.setText(itemBean.getTime());
//        设置视图点击事件
/*        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerd.onItemListenerd(view,position);
                //Myapplication.setMusicPosition(position);
            }
        });*/
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
    ImageView imageView;
    TextView song,singer,time;
    public MusicViewHoder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.item_list_iamge);
        song=itemView.findViewById(R.id.item_list_song);
        singer=itemView.findViewById(R.id.item_list_singer);
        time=itemView.findViewById(R.id.item_list_time);

    }
}
}
