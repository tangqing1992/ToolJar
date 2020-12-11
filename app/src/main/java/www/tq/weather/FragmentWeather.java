package www.tq.weather;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import www.tq.weather.model.DbUnit;
import www.tq.weather.model.MediaFile;
import www.tq.weather.presenter.FileSearch;
import www.tq.weather.presenter.LineChartUnit;
import www.tq.weather.views.PieView;

public class FragmentWeather extends Fragment {
    private View view = null;
    private Context context = null;
    private SmartRefreshLayout smartreshlayout = null;
    private LineChart linerchart = null;
    private List<Float> floats = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private PieChart piechart = null;
    private PieView pieView = null;
    private String tag = "FragmentWeather";

    public FragmentWeather(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fgweather, null);
            initView();
        }
        return view;
    }

    private void initView() {
        smartreshlayout = view.findViewById(R.id.smartreshlayout);
        smartreshlayout.finishLoadmoreWithNoMoreData();
        smartreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
        linerchart = view.findViewById(R.id.linerchart);

        floats.add(18f);
        floats.add(100.6f);
        floats.add(60.4f);
        floats.add(80f);
        floats.add(150f);
        floats.add(120f);
        floats.add(70f);
        stringList.add("11-28");
        stringList.add("11-29");
        stringList.add("11-30");
        stringList.add("12-01");
        stringList.add("12-02");
        stringList.add("12-03");
        stringList.add("12-04");

        LineChartUnit.changelinearChart(linerchart, context.getResources().getColor(R.color.txt_blue), "", floats, stringList);
        pieView = view.findViewById(R.id.pieview);


    }

}
