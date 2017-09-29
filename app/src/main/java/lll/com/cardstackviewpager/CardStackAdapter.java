package lll.com.cardstackviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class CardStackAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<CardBean> mData;

    CardStackAdapter(FragmentManager fm) {
        super(fm);
        mData = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(mData.get(position),position);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    public void setData(ArrayList<CardBean> bean) {
        mData.clear();
        if (bean != null) mData.addAll(bean);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public static class CardFragment extends Fragment {

        private ImageView ivImage;
        private TextView tvText;

        public static CardFragment newInstance(CardBean bean,int position) {
            CardFragment cardFragment = new CardFragment();
            Bundle args = new Bundle();
            args.putString("url", bean.getCoverImageUrl());
            args.putString("time", bean.getTime());
            args.putInt("position", position);
            cardFragment.setArguments(args);
            return cardFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.stack_card_item, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ivImage = view.findViewById(R.id.ivImage);
            tvText = view.findViewById(R.id.tvText);
            tvText.setText(String.valueOf(getArguments().getInt("position")));
            Glide.with(getContext()).load(getArguments().getString("url")).into(ivImage);
        }

        public int dip2px(float dpValue) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }


}
