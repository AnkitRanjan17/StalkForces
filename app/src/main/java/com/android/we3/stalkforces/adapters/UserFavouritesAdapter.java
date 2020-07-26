package com.android.we3.stalkforces.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.Favourites;

import java.util.ArrayList;


public class UserFavouritesAdapter extends RecyclerView.Adapter<UserFavouritesAdapter.UserFavouritesViewHolder> {

    private ArrayList<Favourites> listOfFavourites;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Favourites item);
    }

    public static class UserFavouritesViewHolder extends RecyclerView.ViewHolder {

        public TextView text1, text2,text3,text4,text5,text6;

        public UserFavouritesViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.handleValueFavouritesTextView);
            text2 = itemView.findViewById(R.id.nameValueFavouritesTextView);
            text3=itemView.findViewById(R.id.ratingValueFavouritesTextView);
            text4=itemView.findViewById(R.id.handleTitleFavouritesTextView);
            text5=itemView.findViewById(R.id.nameTitleFavouritesTextView);
            text6=itemView.findViewById(R.id.ratingTitleFavouritesTextView);
        }

        public void bind(final Favourites listOfFavourites, final OnItemClickListener listener) {
            text1.setText(listOfFavourites.getHandle());
            text2.setText(listOfFavourites.getName());
            text3.setText(listOfFavourites.getRating());
            text4.setText("Handle");
            text5.setText("Name");
            text6.setText("Rating");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(listOfFavourites);
                }
            });
        }
    }

    public UserFavouritesAdapter(ArrayList<Favourites> list,OnItemClickListener listener) {
        listOfFavourites = list;
        this.listener = listener;
    }
    public UserFavouritesAdapter(ArrayList<Favourites> list) {
        listOfFavourites = list;
        this.listener = null;
    }

    @Override
    public UserFavouritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_view, parent, false);
        UserFavouritesViewHolder viewHolder = new UserFavouritesViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserFavouritesViewHolder holder, int position) {
        holder.bind(listOfFavourites.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return listOfFavourites.size();
    }
}
