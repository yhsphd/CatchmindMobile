package tk.yhsphd.catchmindmobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MakeQuestion extends AppCompatActivity
{
	MyView myView;
	TextView questionWord;
	TextView turnView;
	int player1score, player2score;
	int turn;

	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_question);
		checkpermission();

		Intent intent = getIntent();
		player1score = intent.getIntExtra("player1score", 0);
		player2score = intent.getIntExtra("player2score", 0);
		turn = intent.getIntExtra("turn", 0);

		turnView = findViewById(R.id.turnMQ);

		if(turn % 2 == 1)
		{
			turnView.setText("Player 1 | Round" + String.valueOf(turn/2 + 1));
		}
		else if(turn % 2 == 0)
		{
			turnView.setText("Player 2 | Round" + String.valueOf(turn/2));
		}

		//디렉토리 생성
		String path = getExternalPath();
		File file = new File(path + "CatchmindMobile");
		file.mkdir();

		myView = findViewById(R.id.canvas);

		questionWord = findViewById(R.id.word);
		String word = wordSelect();
        questionWord.setText(word);
    }

    public String wordSelect()
    {
        Workbook workbook = null;
        Sheet sheet = null;
        Random random = new Random();
        String word = new String();

        try
        {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("wordList.xls"); //엑셀파일 불러오기
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);

            int MaxColumn = 1, RowStart = 0, RowEnd = 5125, ColumnStart = 0, ColumnEnd = 0;
            Log.d("WordCount", String.valueOf(RowEnd));

            String[] words = new String[RowEnd];

            for(int row = RowStart;row < RowEnd;row++) //배열에 각 단어 저장
            {
                String excelload = sheet.getCell(ColumnStart, row).getContents();
                words[row] = excelload;
            }

            word = words[random.nextInt(5125)]; //word 변수에 임의의 단어 선택하여 저장

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (BiffException e)
        {
            e.printStackTrace();
        }
        finally
        {
            workbook.close();
            return word; //변수 반환
        }
    }

	public String getExternalPath()
	{
		String sdPath = "";
		String ext = Environment.getExternalStorageState();
		if(ext.equals(Environment.MEDIA_MOUNTED))
		{
			sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
			//sdPath = "mnt/sdcard/";
		}
		else
		{
			sdPath = getFilesDir() + "";
		}
//        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_SHORT).show();
		return sdPath;
	}

	public void checkpermission()
	{

		int permissioninfo = ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);
		if(permissioninfo == PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this,
//                    "SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
		}
		else {

			//사실 이프문 안써도되는데
			if(ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.WRITE_EXTERNAL_STORAGE)){
				Toast.makeText(this,
						"어플리케이션 설정에서 저장소 사용 권한을 허용해주세요",Toast.LENGTH_SHORT).show();

				//이밑에꺼 해야 권한허용 대화상자가 다시뜸
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						100);
			}
			else{
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
						100);  // 이 100은 리퀘스트코드다
			}
		}
	}

	public void erase(View v)
	{
		myView.erase();
	}

	public void confirm(View v)
	{
		Log.d("turn", String.valueOf(turn));
		myView.Save(getExternalPath() + "CatchmindMobile/img.jpg");
		Intent intent = new Intent(getApplicationContext(), Ready.class);
		intent.putExtra("word",questionWord.getText().toString());
		intent.putExtra("player1score",player1score);
		intent.putExtra("player2score",player2score);
		intent.putExtra("turn",turn);
		startActivity(intent);
		finish();
	}
}