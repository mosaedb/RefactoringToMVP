package com.mosaedb.refactoringtomvp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ReposAdapter extends ArrayAdapter<Repo> {

    ReposAdapter(Context context) {
        super(context, 0);
    }

    private static class ViewHolder {
        private TextView repoName;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.repo_list_item, parent, false);
            holder = new ViewHolder();
            holder.repoName = listItemView.findViewById(R.id.repo_name);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        Repo currentRepo = getItem(position);

        if (currentRepo != null) {
            holder.repoName.setText(currentRepo.getName());
        }

        return listItemView;
    }

}