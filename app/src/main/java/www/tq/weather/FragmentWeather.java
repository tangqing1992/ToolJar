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

        PublicKey publicKey = UnitRsa.getPublicKeyFromAssets(context,"commonRsaPublic.key");
        PrivateKey privateKey = UnitRsa.getPrivateKeyFromAssets(context,"commonRsaPrivate.key");
        String txt = "this is test data";
        String txt1 = UnitRsa.getEncryData(context,txt,publicKey);
        String txt2 = UnitRsa.getdecryptData(context,txt1,privateKey);
        DbUnit.logd(tag,"----txt="+txt);
        DbUnit.logd(tag,"----txt1="+txt1);
        DbUnit.logd(tag,"----txt2="+txt2);
        String base64encode =  UnitBase64.getencodeWord(txt);
        String base64decode =  UnitBase64.getdecodeWord(base64encode);

        DbUnit.logd(tag,"----base64encode="+base64encode);
        DbUnit.logd(tag,"----base64decode="+base64decode);
        DbUnit.logd(tag,"----UnitMd51="+ UnitMd5.getMD5Code("6666"));
        DbUnit.logd(tag,"----UnitMd52="+ UnitMd5.getMD5Code("6666"));

        String password = "123";
        String content = "68995999";
        String aes1 = UnitAES.getEncryptData(content,password);
        String aes2 = UnitAES.getDecryptData(aes1,password);
        DbUnit.logd(tag,"----content="+content);
        DbUnit.logd(tag,"----aes1="+ aes1);
        DbUnit.logd(tag,"----aes2="+aes2);

        String fromat = "yyyy-MM-dd HH:mm:ss";
        final long time = 1607497712658l;
        String data1 = UnitDate.getTime(time,fromat);
        long time1 = UnitDate.getTime(data1,fromat);
        String data2 = UnitDate.getTime(time1,fromat);

        DbUnit.logd(tag,"----time="+time);
        DbUnit.logd(tag,"----time1="+time1);
        DbUnit.logd(tag,"----data1="+data1);
        DbUnit.logd(tag,"----data2="+data2);

        String str1 = "中华人民共和国";
        String str2 = UnitString.getBinFromStr(str1);
        String str3 =UnitString.getStrFrombinstr(str2);
        DbUnit.logd(tag,"----str1="+str1);
        DbUnit.logd(tag,"----str2="+str2);
        DbUnit.logd(tag,"----str3="+str3);
        String str11 = "长江黄河";
        String str16 = UnitString.getHexStr(str11);
        String str16tostr = UnitString.getString(str16.replace(" ",""));
        DbUnit.logd(tag,"----str11="+str11);
        DbUnit.logd(tag,"----str16="+str16);
        DbUnit.logd(tag,"----str16tostr="+str16tostr);

        DbUnit.logd(tag,"--d--getvalueCeil="+ UnitData.getvalueCeil(4.88d,5));
        DbUnit.logd(tag,"--d--getvalueFloor="+ UnitData.getvalueFloor(4.51d,5));

        DbUnit.logd(tag,"--f--getvalueCeil="+ UnitData.getvalueCeil(4.88f,5));
        DbUnit.logd(tag,"--f--getvalueFloor="+ UnitData.getvalueFloor(4.88f,5));

        FileSearch fileSearch = new FileSearch();
        fileSearch.getFiles(context, MediaFile.FileType.img, new FileSearch.OnCallBackMediaFile() {
            @Override
            public void callback(MediaFile.FileType fileType, ArrayList<MediaFile> mediaFiles) {
                DbUnit.logd(tag,fileType+"----img size =" +mediaFiles.size());

            }
        });
        fileSearch.getFiles(context, MediaFile.FileType.video, new FileSearch.OnCallBackMediaFile() {
            @Override
            public void callback(MediaFile.FileType fileType, ArrayList<MediaFile> mediaFiles) {
                DbUnit.logd(tag,fileType+"----video size =" +mediaFiles.size());

            }
        });
        fileSearch.getFiles(context, MediaFile.FileType.music, new FileSearch.OnCallBackMediaFile() {
            @Override
            public void callback(MediaFile.FileType fileType, ArrayList<MediaFile> mediaFiles) {
                DbUnit.logd(tag,fileType+"----music size =" +mediaFiles.size());

            }
        });
    }

}
