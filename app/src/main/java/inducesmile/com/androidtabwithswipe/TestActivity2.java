package inducesmile.com.androidtabwithswipe;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ComputerOuterClass;
import com.example.computerServiceGrpc;
import com.github.mikephil.charting.charts.Chart;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by Cube on 10/25/2016.
 */

public class TestActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity2);
        //((TextView)findViewById(R.id.testTextView)).setText(doRead());
        ((TextView)findViewById(R.id.testTextView)).setText(readFromPref());

        ((Button)findViewById(R.id.goFurther)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        ((Button)findViewById(R.id.goToTest)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChartTest.class);
                startActivity(intent);
            }
        });
        ((Button)findViewById(R.id.button3)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getNotification2();
                //getNotification();
            }
        });

        /*try{
            ManagedChannel channel = ManagedChannelBuilder.forAddress("158.129.25.160", 43432)
                    .usePlaintext(true)
                    .build();
            computerServiceGrpc.computerServiceBlockingStub stub = computerServiceGrpc.newBlockingStub(channel);
            ComputerOuterClass.ComputerListResponse response = stub.getComputerList(ComputerOuterClass.Empty.newBuilder().build());
            //ComputerOuterClass.Computer response = stub.getRealtimeComputer(ComputerOuterClass.Empty.newBuilder().build());
            System.out.print(response + "The count " + response.getComputerListCount() + '\n');
            Map<Integer, ComputerOuterClass.Computer> map = response.getComputerListMap();
            Log.e("LOG SIZE", String.valueOf(map.size()));
            for (int x = 0; x < map.size(); x++) {
                Log.i("MAP DATA",map.get(x).getCpuCoreTemp());
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR", e.toString());
        }*/

    }

    public void getNotification2()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
                .setContentTitle("Content Title")
                .setContentText("This is a content test")
                .setSmallIcon(R.drawable.temperature)
                //.setStyle(new NotificationCompat.BigTextStyle())
                ;
        // Setting notification style
        Intent intent = new Intent();


        PendingIntent contentIntent = PendingIntent.getActivity(TestActivity2.this, 0, intent, 0);
        builder.setContentIntent(contentIntent);

        Intent realtimeIntent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent realtime = PendingIntent.getActivity(this, 0, realtimeIntent, 0);
        builder.addAction(R.mipmap.ic_flash, "SEE REALTIME", realtime);

        Intent chartIntent = new Intent(getApplicationContext(), ChartTest.class);
        PendingIntent chartPendlingIntent = PendingIntent.getActivity(this, 0, chartIntent, 0);
        builder.addAction(R.mipmap.ic_chart, "SEE CHART", chartPendlingIntent);

        Notification notification = builder.build();
        NotificationManagerCompat.from(this).notify(0, notification);
    }

    public void getNotification ()
    {
        Intent intent = new Intent();
        PendingIntent pIntent = PendingIntent.getActivity(TestActivity2.this, 0, intent, 0);
        Notification notification = new Notification.Builder(TestActivity2.this)
                .setTicker("Ticker Title")
                .setContentTitle("Content Title")
                .setContentText("This is a content test")
                .setSmallIcon(R.drawable.temperature)
                .addAction(R.mipmap.ic_chart, "Ignore", pIntent)
                //.addAction(R.drawable.glass, "Investigate", pIntent)
                .setContentIntent(pIntent).getNotification();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    String doRead()
    {
        String filename = "myfile";
        String string = "Hello world!";
        FileInputStream inputStream;

        /*try {
            inputStream = openFileInput(filename, Context.MODE_PRIVATE);
            inputStream.read(string.getBytes());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            FileInputStream fis = this.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }
    String readFromPref()
    {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String name = settings.getString("name", "");
        return name;
    }
}
