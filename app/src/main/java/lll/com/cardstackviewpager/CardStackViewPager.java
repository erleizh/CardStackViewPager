package lll.com.cardstackviewpager;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CardStackViewPager extends ViewPager {

    private float mDownX;
    private boolean isCanScroll = false;
    private CardStackPageTransformer mPageTransformer;

    public CardStackViewPager(Context context) {
        super(context);
        init();
    }

    public CardStackViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
//        adapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                if (mPageTransformer != null) {
//                    final int scrollX = getScrollX();
//                    final int childCount = getChildCount();
//                    for (int i = 0; i < childCount; i++) {
//                        final View child = getChildAt(i);
//                        final LayoutParams lp = (LayoutParams) child.getLayoutParams();
//
//                        if (lp.isDecor) continue;
//                        final float transformPos = (float) (child.getLeft() - scrollX) / (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
//                        mPageTransformer.transformPage(child, transformPos);
//                    }
//                }
//            }
//        });
    }

    private void init() {
        mPageTransformer = new CardStackPageTransformer();
        setPageTransformer(true, mPageTransformer);
//        addOnPageChangeListener(new OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.d("onPageScrolled", "position = " + position);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if(getAdapter() instanceof FragmentStatePagerAdapter){
//                    FragmentStatePagerAdapter adapter = (FragmentStatePagerAdapter) getAdapter();
//                    Fragment item = adapter.getItem(position);
//
//                }
//                if (getAdapter().getCount() > position + getOffscreenPageLimit()) {
//                Object item = getAdapter().instantiateItem(CardStackViewPager.this, position + 3);
//                if (item instanceof Fragment) {
//                    ((Fragment) item).getFragmentManager().beginTransaction().hide((Fragment) item).commit();
//                }
//                }
//                item = getAdapter().instantiateItem(CardStackViewPager.this, position + 2);
//                if (item instanceof Fragment) {
//                    ((Fragment) item).getFragmentManager().beginTransaction().show((Fragment) item).commit();
//                }
//                item = getAdapter().instantiateItem(CardStackViewPager.this, position);
//                if (item instanceof Fragment) {
//                    ((Fragment) item).getFragmentManager().beginTransaction().show((Fragment) item).commit();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            return super.dispatchTouchEvent(ev);
        } else {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = ev.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (ev.getX() - mDownX < 0 && getAdapter() != null && getCurrentItem() == (getAdapter().getCount() - (getOffscreenPageLimit()))) {
                        return true;
                    }
                    mDownX = ev.getX();
                    break;
                default:
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

    }

    private class CardStackPageTransformer implements ViewPager.PageTransformer {
        private float mScaleOffset = 100;

        /**
         * @param page     view
         * @param position 0是当前页。1是右边的一个整页位置，而-1是左边的一页位置。（-1也可能是当前页）
         */
        public void transformPage(View page, float position) {
            Log.d("transformPage", "position :" + position);
            if (position <= 0.0) {
                page.setAlpha(1);
                page.setTranslationX((page.getWidth() / 3 * position));
            } else {
                //　设置缩放　，　使显示为层叠效果
                float scale = (page.getWidth() - mScaleOffset * position) / (float) (page.getWidth());
                page.setScaleX(scale);
                page.setScaleY(scale);

                //　设置重叠摆放
                page.setTranslationX((-page.getWidth() * position) + (mScaleOffset * 0.8f) * position);

                //　setAlpha
                double percent = 0.25 * (float) (Math.floor(position + 1) - position);
                if (position > 0.0 && position < 1.0) {            //1.0  - 0.7  || 0.7  - 1.0
                    page.setAlpha((float) (percent + 0.75));
                } else if (position >= 1.0 && position < 2.0) {     //0.7  - 0.5  || 0.5  - 0.7
                    page.setAlpha((float) (percent + 0.50));
                } else if (position >= 2.0 && position < 3.0) {     //0.5  - 0.25 || 0.25 - 0.5
                    page.setAlpha((float) (percent + 0.25));
                } else if (position >= 3.0 && position < 4.0) {     //0.25 - 0　　 || 0    - 0.25
                    page.setAlpha((float) (percent));
                }
            }
        }
    }
}
