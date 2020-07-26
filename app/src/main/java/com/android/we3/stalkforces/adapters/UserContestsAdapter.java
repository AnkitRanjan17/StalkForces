package com.android.we3.stalkforces.adapters;


import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.Contests;


import java.util.ArrayList;
import java.util.Collections;

import static com.android.we3.stalkforces.activities.UserContestsActivity.cSize;


public class UserContestsAdapter extends RecyclerView.Adapter<UserContestsAdapter.UserContestsViewHolder>{
    private ArrayList<Contests> contestsList;
    public static Integer count=0;
    private OnContestsClickListener onContestsClickListener;

    public interface OnContestsClickListener {
        void onContestNameClick(int pos);
    }

    @NonNull
    @Override
    public UserContestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contest_view,parent,false);
        UserContestsViewHolder viewHolder = new UserContestsViewHolder(v,onContestsClickListener);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull UserContestsViewHolder holder, int position) {
        Contests contests = contestsList.get(position);

        Integer p=cSize-position;
        holder.text1.setText(p.toString());
        //holder.text2.setText(contests.getContestName());
        holder.text2.setText(Html.fromHtml("<u>"+contests.getContestName()+"<u>"));
        //holder.text2.setPaintFlags(text2.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        holder.text3.setText(contests.getRank().toString());
        Integer oldRating;
        oldRating=contests.getOldRating();
        Integer rating = contests.getNewRating();
        Integer c=rating-oldRating;
        if(rating<=1199 && oldRating>=1200)
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" Newbie ");
            holder.title.setTextColor(Color.GRAY);
            holder.arrow.setText(" → ");
//           holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.GRAY);
        }
        else if(rating>=1200 &&rating<=1399 && (oldRating>1399||oldRating<1200))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" Pupil ");
            holder.title.setTextColor(Color.rgb(34,139,34));
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.rgb(34,139,34));
        }
        else if(rating>=1400 &&rating<=1599 &&(oldRating>1599 || oldRating<1400))
        {

            holder.userHandle.setText("Became ");
            holder.title.setText(" Specialist ");
            holder.title.setTextColor(Color.rgb(102,205,170));
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.rgb(102,205,170));
        }
        else if(rating>=1600 && rating<=1899 && (oldRating>1899 || oldRating<1600))
        {

            holder.userHandle.setText("Became ");
            holder.title.setText(" Expert ");
            holder.title.setTextColor(Color.BLUE);
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.BLUE);
        }
        else if(rating>=1900 && rating<=2099 && (oldRating>2099 || oldRating<1900))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" Candidate Master");
            holder.title.setTextColor(Color.rgb(234,33,200));
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setTextColor(Color.rgb(234,33,200));
        }
        else if(rating>=2100 && rating<=2299 &&(oldRating>2299 || oldRating<2100))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" Master");
            holder.title.setTextColor(Color.rgb(255,165,0));
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.rgb(255,165,0));
        }
        else if(rating>=2300 && rating<=2399 &&(oldRating>2399 || oldRating<2300))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" International Master");
            holder.title.setTextColor(Color.rgb(255,165,0));
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.rgb(255,165,0));
        }
        else if(rating>=2400 && rating<=2599 && (oldRating>2599 || oldRating<2400))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" GrandMaster");
            holder.title.setTextColor(Color.RED);
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.RED);
        }
        else if(rating>=2600 && rating<=2999 && (oldRating>2999 || oldRating<2600))
        {
            holder.userHandle.setText("Became ");
            holder.title.setText(" International GrandMaster ");
            holder.title.setTextColor(Color.RED);
            holder.arrow.setText(" → ");
//            holder.oldHandle.setText(contests.getHandle());
            func(holder,oldRating,contests.getHandle());
            holder.newHandle.setText(contests.getHandle());
            holder.newHandle.setTextColor(Color.RED);
        }
        else if(rating>=3000 && oldRating<3000)
        {
            String rank = "Legendary Grandmaster";
            int l = rank.length();
            holder.userHandle.setText("Became ");
            Spannable word = new SpannableString(Character.toString(rank.charAt(0)));

            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.title.setText(word);
            Spannable wordTwo = new SpannableString(rank.substring(1,l));

            wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.title.append(wordTwo);
            rank = contests.getHandle();
            l = rank.length();
            word = new SpannableString(Character.toString(rank.charAt(0)));

            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.newHandle.setText(word);
            wordTwo = new SpannableString(rank.substring(1,l));

            wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.newHandle.append(wordTwo);
            holder.arrow.setText(" → ");
            func(holder,oldRating,contests.getHandle());
//            holder.oldHandle.setText(contests.getHandle());

        }
        else {
            holder.userHandle.setText("");
            holder.title.setText("");
            holder.arrow.setText("");
            holder.oldHandle.setText("");
            holder.newHandle.setText("");
        }
        if(p==1)
            holder.text4.setText("");
        else
        if(c>0)
            holder.text4.setText("+"+c.toString());
        else
            holder.text4.setText(c.toString());
        holder.text5.setText(contests.getNewRating().toString());
    }
    @Override
    public int getItemCount() {
        return contestsList.size();
    }
    public void func(@NonNull UserContestsViewHolder holder,Integer rating,String handle)
    {
        if(rating==0)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.BLACK);
        }
        else
        if(rating<=1199)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.GRAY);
        }
        else if(rating<=1399)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.rgb(34,139,34));
        }
        else if(rating<=1599)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.rgb(102,205,170));
        }
        else if(rating<=1899)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.BLUE);
        }
        else if(rating<=2099)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.rgb(234,33,200));
        }
        else if(rating<=2399)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.rgb(255,165,0));
        }
        else if(rating<=2999)
        {
            holder.oldHandle.setText(handle);
            holder.oldHandle.setTextColor(Color.RED);
        }
        else if(rating>=3000)
        {
            String rank =handle;
            int l = rank.length();
            Spannable word = new SpannableString(Character.toString(rank.charAt(0)));

            word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.oldHandle.setText(word);
            Spannable wordTwo = new SpannableString(rank.substring(1,l));

            wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.oldHandle.append(wordTwo);
        }
    }

    public  static  class UserContestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text1, text2, text3, text4, text5,userHandle,oldHandle,newHandle,title,arrow;
        OnContestsClickListener onContestsClickListener;

        public UserContestsViewHolder(View view,OnContestsClickListener onContestsClickListener) {
            super(view);
            text1 = (TextView)view.findViewById(R.id.contestCountTextView);
            text2 = (TextView)view.findViewById(R.id.contestNameTextView);
            text3 = (TextView)view.findViewById(R.id.contestRankTextView);
            text4 = (TextView)view.findViewById(R.id.contestRatingChangeTextView);
            text5 = (TextView)view.findViewById(R.id.contestNewRatingTextView);
            userHandle=(TextView)view.findViewById(R.id.contestTitleNowTextView);
            title=(TextView)view.findViewById(R.id.titleTextView);
            oldHandle=(TextView)view.findViewById(R.id.userOldHandleTextView);
            newHandle=(TextView)view.findViewById(R.id.userNewHandleTextView);
            arrow=(TextView)view.findViewById(R.id.arrowTextView);
            this.onContestsClickListener=onContestsClickListener;
            text2.setOnClickListener(this);
        }
        //            text6=(TextView)view.findViewById(R.id.contestCountTextView);
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.contestNameTextView)
                onContestsClickListener.onContestNameClick(getAdapterPosition());

        }
    }

    public UserContestsAdapter(ArrayList<Contests> list,OnContestsClickListener onContestsClickListener) {

        contestsList=list;
        this.onContestsClickListener=onContestsClickListener;
    }
}
