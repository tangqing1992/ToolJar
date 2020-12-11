package www.tq.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import www.tq.weather.model.DbUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerview_bottom = null;
    private BottomRecyAdapter bottomRecyAdapter = null;
    private MyViewPager viewpager ;
    private ViewpagerAdapter viewpagerAdapter = null;
    private FragmentWeather fragmentWeather = null;
    private FragmentMap fragmentMap = null;
    private FragmentMe fragmentMe = null;
    private List<Fragment> fragments = new ArrayList<>();
    private String tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerview_bottom = findViewById(R.id.recyclerview_bottom);
        recyclerview_bottom.setLayoutManager(linearLayoutManager);
        bottomRecyAdapter = new BottomRecyAdapter(this, new BottomRecyAdapter.OnClickItem() {
            @Override
            public void callback(int position) {
                viewpager.setCurrentItem(position);
            }
        });
        recyclerview_bottom.setAdapter(bottomRecyAdapter);
        viewpager = findViewById(R.id.viewpager);

        for (int i=0;i<1;i++){
            FragmentWeather   fragmentWeather = new FragmentWeather(this);
            FragmentMap fragmentMap = new FragmentMap(this);
            FragmentMe fragmentMe = new FragmentMe(this);
            fragments.add(fragmentWeather);
            fragments.add(fragmentMap);
            fragments.add(fragmentMe);
        }
        viewpagerAdapter = new ViewpagerAdapter(getSupportFragmentManager(),fragments);
        viewpager.setAdapter(viewpagerAdapter);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                DbUnit.logd(tag,"----position="+position);
               bottomRecyAdapter.setPosition(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}