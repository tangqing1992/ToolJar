package www.tq.weather.presenter;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import www.tq.weather.unit.DataUnit;

public class LineChartUnit {


    /*改变曲线图数据*/
    public static void changelinearChart(LineChart linerchart, int linearcolor,String tabname,List<Float> floats,List<String> txtlist) {

        if (floats.size() == 0)
            return;
        float maxY = 0;
        List<Entry> setin = new ArrayList<Entry>();
        for (int i = 0; i < floats.size(); i++) {
            float data = floats.get(i);
            Entry xin = new Entry(i, data);
            setin.add(xin);
            if (data > maxY) {
                maxY = data;
            }
        }
        if (maxY == 0) {
            maxY = 1000;
        } else
            maxY = maxY + maxY / 5;
        //数据源
        //创建LineDataSet对象
        LineDataSet lineDataSet1 = new LineDataSet(setin, tabname);
        lineDataSet1.setCircleSize(3f);// 图标上的数据点小圆圈的圆形大小
        lineDataSet1.setCircleColor(Color.WHITE);// 图标上的数据点小圆圈的圆形的颜色
        lineDataSet1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);//曲线风格
        lineDataSet1.setCubicIntensity(1f);//设置曲线的平滑度
        lineDataSet1.setDrawFilled(true);//设置允许填充
        lineDataSet1.setDrawCircles(false);//图标上的数据点小圆圈不显示
        lineDataSet1.setFillColor(linearcolor);//填充颜色
        lineDataSet1.setLineWidth(1f);//曲线宽度
        lineDataSet1.setColor(linearcolor);//曲线颜色
        lineDataSet1.setValueTextSize(0f);

        /*右边标签*/
        YAxis rightAxis = linerchart.getAxisRight();
        rightAxis.setAxisMinValue(0f);
        rightAxis.setEnabled(false);
        rightAxis.setDrawGridLines(false);

        /*X轴标签*/
        XAxis xAxis = linerchart.getXAxis();
        xAxis.setAxisMinValue(0f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisLineWidth(0f);
        xAxis.setAxisMaxValue(txtlist.size()-1);
        xAxis.setDrawGridLines(false);

        AxisValueFormatter dateaxisvalue = new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return txtlist.get((int)value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };

        xAxis.setValueFormatter(dateaxisvalue);
        /*左边标签*/
        YAxis left = linerchart.getAxisLeft();
        left.setDrawGridLines(true);
        left.setDrawAxisLine(false);
        left.setAxisMinValue(0f);
        left.setAxisMaxValue(maxY);

        AxisValueFormatter AxisValueFormatter = new AxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DataUnit.getvalueCeil(value,2);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        };

        left.setValueFormatter(AxisValueFormatter);
        Legend legend = linerchart.getLegend();
        legend.setEnabled(false);
        linerchart.setDescription("");
        linerchart.setScaleXEnabled(false);//启用/禁用缩放在X轴上。
        linerchart.setScaleYEnabled(false);//启用/禁用缩放在Y轴上。
        //List<ILineDataSet> 对象
        List<ILineDataSet> list = new ArrayList<ILineDataSet>();
        //将数据添加进集合
        list.add(lineDataSet1);
        //数据对象,封装了所有的数据
        LineData lineData = new LineData(list);
        //为图表设置新的数据对象。数据对象包含所有值和信息。
        linerchart.setData(lineData);
        //设置动画时间
        linerchart.animateX(0);
        //刷新
        linerchart.invalidate();
        linerchart.setEnabled(false);

    }



}
