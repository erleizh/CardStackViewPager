package lll.com.cardstackviewpager;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<String> dataList;
    private CardStackAdapter mAdapter;
    private ViewPager.SimpleOnPageChangeListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataList();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new CardStackAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "position " + position);
                for (int i = 0; i < mViewPager.getOffscreenPageLimit() + 1; i++) {
                    if (position + i >= mAdapter.getCount()) return;
                    CardStackAdapter.CardFragment item = (CardStackAdapter.CardFragment) mAdapter.instantiateItem(mViewPager, position + i);
                    if (item == null) return;
                    FragmentTransaction fragmentTransaction = item.getFragmentManager().beginTransaction();

                    //将第 4 页隐藏
                    if (i == mViewPager.getOffscreenPageLimit()) {
                        fragmentTransaction.hide(item).commit();
                    } else {
                        fragmentTransaction.show(item).commit();
                    }

                    //将除第一页和第二页外的都显示成灰色的
                    if (i == 0) {
                        item.showActView();
                    } else {
                        item.showGrayView();
                    }
                }
            }
        };
        mViewPager.addOnPageChangeListener(mListener);
        mViewPager.setOffscreenPageLimit(3);
        mAdapter.setData(dataList);
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mListener.onPageSelected(0);
            }
        });

    }

    /**
     * 从asset读取文件json数据
     */
    private void initDataList() {
        dataList = new ArrayList<>();
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/33/200201222405_3323351_ios_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/33/200500902650_3323329_ios_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/33/200201226238_3323319_ios_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/27/200462583649_3322706_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/26/200501002356_3322680_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/26/200500997801_3322667_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/25/200201242345_3322557_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/23/200201219395_3322378_ios_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/23/200500893477_3322317_ios_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/21/200500910964_3322191_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/20/100200900374_3322071_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/17/500500888587_3321725_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/15/200500980931_3321532_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/14/200200906421_3321498_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/12/200500991546_3321292_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/13/200201214576_3321302_640.jpg");
        dataList.add("http://piccdn.xingyun.cn/media/users/xingyu/332/10/100200896458_3321013_640.jpg");
        dataList.add("");
        dataList.add("");
    }
}
