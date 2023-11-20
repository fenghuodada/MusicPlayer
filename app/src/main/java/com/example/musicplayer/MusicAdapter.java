package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHoder> {


    onItemClickListener onItemClickListenerd;

    //创建一个传接口的函数
    public void setOnItemClickListener(MusicAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListenerd = onItemClickListener;
    }
    //创建一个接口
    public interface onItemClickListener{
        public void onItemListener(View view,int position);

    }
    public MusicAdapter(Context context, List<MusicBean> adapterDate) {
        this.context = context;
        this.adapterDate = adapterDate;
    }

    Context context;
    List<MusicBean> adapterDate;

    @NonNull
    @Override
    public MusicViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_music,parent,false);
        return new MusicViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHoder holder, @SuppressLint("RecyclerView") int position) {
//        设置数据源
        MusicBean itemBean=adapterDate.get(position);
        holder.itemSong.setText(itemBean.getSong());
        holder.itemSinger.setText(itemBean.getSinger());
        holder.itemNum.setText(itemBean.getNum());
        holder.itemAlbum.setText(itemBean.getAlbum());
        holder.itemTime.setText(itemBean.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerd.onItemListener(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return adapterDate.size();
    }
//    创建一个viewhoder类
/**
 * 创建viewhoder
 */
 class MusicViewHoder extends RecyclerView.ViewHolder{
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
