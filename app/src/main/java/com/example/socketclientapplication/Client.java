package com.example.socketclientapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AppCompatActivity {
    private Socket socket;

    private static final int SERVERPORT = 5000;
    private static final String SERVER_IP = "10.0.2.2";

    public static int[][] a1 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
    public static int[][] a2 = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
    public static int[][] a3 = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private int c = 0;

    TextView[] t3 = new TextView[9];
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView[] t1 = new TextView[9];
        TextView[] t2 = new TextView[9];


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //_________________بش يطبع المصفوفات على الواجهة _____________
                int m1 = getResources().getIdentifier("M0" + c,
                        "id", getPackageName());
                t1[c] = findViewById(m1);
                t1[c].setText(Integer.toString(Client.a1[i][j]));

                int m2 = getResources().getIdentifier("M1" + c,
                        "id", getPackageName());
                t2[c] = findViewById(m2);
                t2[c].setText(Integer.toString(a2[i][j]));
                c++;
            }
        }
        new Thread(new ClientThread()).start();
    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) throws Exception {
        try {

            // Create an output stream
            // sends output to the socket
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            PrintWriter out = new PrintWriter(new BufferedWriter(
//                    new OutputStreamWriter(socket.getOutputStream())),
//                    true);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    // Send data to the server
                    // Send the matrix
                    out.writeInt(a1[i][j]);
                    out.writeInt(a2[i][j]);
                }
                out.flush();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();

        }
        // Receiving reply from server
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());

            for (int i = 0; i < 3; i++){
                for (int j = 0; j < 3; j++){
                    a3[i][j] = in.readInt();
            // printing reply
            int result = getResources().getIdentifier("R" + c,
                    "id", getPackageName());
            t3[c] = findViewById(result);
            // a3 contain result
            t3[c].setText(Integer.toString(a3[i][j]));
            c++;}}
        } catch (IOException e) {
            e.printStackTrace();
        }

}
    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

}