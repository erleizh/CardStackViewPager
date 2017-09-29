package lll.com.cardstackviewpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ArrayList<CardBean> dataList;
    private CardStackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDataList();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new CardStackAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setPageTransformer(true, new CardStackPageTransformer());
        mViewPager.setOffscreenPageLimit(3);
        mAdapter.setData(dataList);
    }

    /**
     * 从asset读取文件json数据
     */
    private void initDataList() {
        dataList = new ArrayList<>();
        try {
            InputStream in = getAssets().open("preset.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < len; i++) {
                        JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                        CardBean itemEntity = new CardBean(itemJsonObject);
                        dataList.add(itemEntity);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CardStackPageTransformer implements ViewPager.PageTransformer {
        private float mScaleOffset = 100;

        /**
         * @param page     view
         * @param position 0是当前页。1是右边的一个整页位置，而-1是左边的一页位置。（-1也可能是当前页）
         */
        public void transformPage(View page, float position) {
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
                } else if (position > 1.0 && position < 2.0) {     //0.7  - 0.5  || 0.5  - 0.7
                    page.setAlpha((float) (percent + 0.50));
                } else if (position > 2.0 && position < 3.0) {     //0.5  - 0.25 || 0.25 - 0.5
                    page.setAlpha((float) (percent + 0.25));
                } else if (position >= 3.0 && position < 4.0) {     //0.25 - 0　　 || 0    - 0.25
                    page.setAlpha((float) (percent));
                }
            }
        }
    }
}
