package com.example.famousblog.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.famousblog.databinding.PostItemLayoutBinding;
import com.example.famousblog.interfaces.RecyclerViewItemClickListener;
import com.example.famousblog.models.Post;
import com.example.famousblog.models.User;
import com.example.famousblog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context context;
    private RecyclerViewItemClickListener<Post> listener;
    private List<Post> posts = new ArrayList<>();

    public PostAdapter(Context context, RecyclerViewItemClickListener<Post> listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemLayoutBinding mBinding = PostItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User loggedInUser = Utils.getPersistedUser(context);
        final Post post = posts.get(position);
        holder.mBinding.tvTitle.setText(post.getTitle());
        holder.mBinding.tvBody.setText(post.getBody());
        holder.mBinding.tvDatePosted.setText(post.getDate());
        holder.mBinding.postedBy.setText(post.getPostedBy().getName());
        if (loggedInUser.getUsername().equals(post.getPostedBy().getUsername())) {
            holder.mBinding.btnDelete.setVisibility(View.VISIBLE);
        } else {
            holder.mBinding.btnDelete.setVisibility(View.GONE);

        }
        holder.mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(post);
            }
        });
        holder.mBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(context, "Could have deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder{
        PostItemLayoutBinding mBinding;
        ViewHolder(@NonNull PostItemLayoutBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

        }
    }

    public void setData(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }
}
