package lll.com.cardstackviewpager;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class CardStackAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> mData;

    CardStackAdapter(FragmentManager fm) {
        super(fm);
        mData = new ArrayList<>();
    }

    @Override
    public CardFragment getItem(int position) {
        return CardFragment.newInstance(mData.get(position), position);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    public void setData(ArrayList<String> bean) {
        mData.clear();
        if (bean != null) mData.addAll(bean);
        notifyDataSetChanged();
    }


    public static class CardFragment extends Fragment {

        private ImageView ivImage;

        public static CardFragment newInstance(String url, int position) {
            CardFragment cardFragment = new CardFragment();
            Bundle args = new Bundle();
            args.putString("url", url);
            args.putInt("position", position);
            cardFragment.setArguments(args);
            return cardFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.d("CardFragment", "onCreateView : " + getArguments().getInt("position"));
            return inflater.inflate(R.layout.stack_card_item, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Log.d("CardFragment", "onViewCreated : " + getArguments().getInt("position"));
            ivImage = view.findViewById(R.id.ivImage);
            Glide.with(getContext()).load(getArguments().getString("url")).error(R.mipmap.act_background).into(ivImage);
        }

        public void showGrayView() {
            Log.d("CardFragment", "showGrayView :" + getArguments().getInt("position"));
            if (ivImage != null) {
                Glide.with(getContext()).load(R.mipmap.act_background).into(ivImage);
            }
        }

        public void showActView() {
            Log.d("CardFragment", "showActView :" + getArguments().getInt("position"));
            if (ivImage != null) {
                Glide.with(getContext()).load(getArguments().getString("url")).error(R.mipmap.act_background).into(ivImage);
            }
        }
    }


}
