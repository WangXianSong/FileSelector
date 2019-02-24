package com.songsong.fileselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by SongSong on 2019/2/24
 */
public class FileAdapter extends ArrayAdapter<FileBean> {
    private int resourceId;

    public FileAdapter(Context context, int resource, List<FileBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FileBean fileBean = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.filetv = view.findViewById(R.id.file_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.filetv.setText(fileBean.getFilepath());
        return view;
    }

    class ViewHolder {
        TextView filetv;
    }
}
