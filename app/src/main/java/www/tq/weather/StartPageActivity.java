package www.tq.weather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import www.tq.weather.model.PermissionsUnit;

public class StartPageActivity extends Activity {

    private int indexo = 0;
    private String tag = "StartPageActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PermissionsUnit.isMarshmallow())
            applyforPermission();
        else
            handler.sendEmptyMessageDelayed(0,2000);

    }

    private void applyforPermission() {
        DbUnit.logd(tag, "----applyforPermission-indexo=" + indexo);

        if (indexo < PermissionsUnit.basepermissions.length) {
            DbUnit.logd(tag, "-----checkoutPermission=" + PermissionsUnit.checkoutPermission(StartPageActivity.this, PermissionsUnit.basepermissions[indexo]));

            if (PermissionsUnit.checkoutPermission(StartPageActivity.this, PermissionsUnit.basepermissions[indexo])) {
                indexo++;
                applyforPermission();
            } else
                PermissionsUnit.applyforPermission(StartPageActivity.this, PermissionsUnit.basepermissions[indexo]);
        }
        else {
                handler.sendEmptyMessageDelayed(0,2000);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==PermissionsUnit.requestCode){
                indexo++;
                applyforPermission();
        }
        else
            finish();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    startActivity(new Intent(StartPageActivity.this,MainActivity.class));
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
