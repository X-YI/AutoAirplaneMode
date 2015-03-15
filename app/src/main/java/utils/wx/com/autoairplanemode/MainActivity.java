package utils.wx.com.autoairplanemode;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    Switch aSwitch;
    TimePicker timePicker1;
    TimePicker timePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aSwitch = (Switch)findViewById(R.id.switch1);
        timePicker1 = (TimePicker)findViewById(R.id.timePicker);
        timePicker2 = (TimePicker)findViewById(R.id.timePicker2);

        boolean isEnabled = aSwitch.isChecked();
//        boolean isEnabled = Settings.System.getInt(
//                getContentResolver(),
//                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
//
//        Settings.System.putInt(
//                getContentResolver(),
//                Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
//
//        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
//        intent.putExtra("state", !isEnabled);
//        sendBroadcast(intent);


        Process process = null;
        try {
            if (isEnabled) {
                process = Runtime.getRuntime().exec("settings put global airplane_mode_on 1\n" +
                        "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true");
            } else {
                process = Runtime.getRuntime().exec("settings put global airplane_mode_on 0\n" +
                        "am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
