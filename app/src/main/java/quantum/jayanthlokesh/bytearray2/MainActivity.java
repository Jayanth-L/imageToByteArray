package quantum.jayanthlokesh.bytearray2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    File file;
    private File[] listfiles;
    private String[] filepaths;
    private String[] filenames;
    int i;
    int j;
    int count=0;
    String byteArray = "";
    String encodedbase64value = "";
    String decodearray = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this,"The Sd card is not mounted",Toast.LENGTH_SHORT).show();
        }
        else
            file = new File(Environment.getExternalStorageDirectory() + File.separator + "samp_image");
            file.mkdirs();
        if(file.isDirectory()) {
            listfiles = file.listFiles();
            Log.i( "this","list = " + listfiles);
            //Toast.makeText(this, "Hello " + listfiles.length, Toast.LENGTH_SHORT).show();
            filepaths = new String[listfiles.length];
            filenames = new String[listfiles.length];
            filepaths[0] = listfiles[0].getAbsolutePath();
            filenames[0] = listfiles[0].getName();
            try {
                FileInputStream fis = new FileInputStream(filepaths[0]);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                        //Writes to this byte array output stream
                        bos.write(buf, 0, readNum);
                        System.out.println("read " + readNum + " bytes,");
                    }
                } catch (IOException ex) {
                    Toast.makeText(this, "I/O Exception", Toast.LENGTH_SHORT).show();
                }
                byte[] bytes = bos.toByteArray();
                String encodedString = Base64.encodeToString(bytes, Base64.URL_SAFE | Base64.NO_WRAP);
                byte[] decodedArray = Base64.decode(encodedString, Base64.URL_SAFE | Base64.NO_WRAP);
                if (bytes.equals(decodedArray)) {
                    //Toast.makeText(this, "True", Toast.LENGTH_LONG).show();
                }
                for (i=0;i<bytes.length;i++) {
                    byteArray = byteArray + ", " + bytes[i];
                    decodearray = decodearray + ", " + decodedArray[i];

                    Log.i("Now","value :" + i);
                }

                //-----------------------------------------------------------------
                for (i=0; i<bytes.length; i++) {
                    for (j=0;j<bytes.length; j++) {
                        if(bytes[i] == decodedArray[j]) {
                            if(i==j) {
                                count++;
                            }
                        }
                    }
                }
                if(count == bytes.length) {
                    Toast.makeText(this, bytes.length + "&" + count, Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this,byteArray.length() + "||" + decodearray.length(),Toast.LENGTH_SHORT).show();
                if(byteArray.equals(decodearray)) {
                    //Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
                }

            } catch (FileNotFoundException ex) {
                Toast.makeText(this, "File Not Found", Toast.LENGTH_LONG).show();
            }
/*            if (bmp3.equals(bmp4)) {
                Toast.makeText(this, "2nd True", Toast.LENGTH_SHORT).show();
            }

            if (!bmp.equals(bmp3)) {
                Toast.makeText(this,"Both are not equal", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "bmp :" + filepaths.length, Toast.LENGTH_SHORT).show();
*/

        }
    }

}
