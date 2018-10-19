package tk.yhsphd.catchmindmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SolveQuestion extends AppCompatActivity
{
	ImageView imgview;
	RadioButton[] answer = new RadioButton[5];
	Button confirm;
	int choice;

	TextView turnView;

	int player1score, player2score;
	int turn;

	Random random = new Random();
	int rightAnswer = random.nextInt(5);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_solve_question);

		turnView = findViewById(R.id.turnSQ);

		answer[0] = findViewById(R.id.answer1);
		answer[1] = findViewById(R.id.answer2);
		answer[2] = findViewById(R.id.answer3);
		answer[3] = findViewById(R.id.answer4);
		answer[4] = findViewById(R.id.answer5);

		confirm = findViewById(R.id.confirm);

		choice = 5;

		answer[0].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				answer[0].setChecked(true);
				answer[1].setChecked(false);
				answer[2].setChecked(false);
				answer[3].setChecked(false);
				answer[4].setChecked(false);
				choice=0;
			}
		});
		answer[1].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				answer[0].setChecked(false);
				answer[1].setChecked(true);
				answer[2].setChecked(false);
				answer[3].setChecked(false);
				answer[4].setChecked(false);
				choice=1;
			}
		});
		answer[2].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				answer[0].setChecked(false);
				answer[1].setChecked(false);
				answer[2].setChecked(true);
				answer[3].setChecked(false);
				answer[4].setChecked(false);
				choice=2;
			}
		});
		answer[3].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				answer[0].setChecked(false);
				answer[1].setChecked(false);
				answer[2].setChecked(false);
				answer[3].setChecked(true);
				answer[4].setChecked(false);
				choice=3;
			}
		});
		answer[4].setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				answer[0].setChecked(false);
				answer[1].setChecked(false);
				answer[2].setChecked(false);
				answer[3].setChecked(false);
				answer[4].setChecked(true);
				choice=4;
			}
		});

		String words[];
		words = wordSelect();

		for(int i=0;i<5;i++)
		{

			answer[i].setText(words[i]);
		}

		Intent intent = getIntent();
		player1score = intent.getIntExtra("player1score", 0);
		player2score = intent.getIntExtra("player2score", 0);
		turn = intent.getIntExtra("turn", 0);
		answer[rightAnswer].setText(intent.getStringExtra("answer"));

		if(turn % 2 == 1)
		{
			turnView.setText("Player 2 | Round" + String.valueOf(turn/2 + 1));
		}
		else if(turn % 2 == 0)
		{
			turnView.setText("Player 1 | Round" + String.valueOf(turn/2));
		}

		confirm.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), result.class);

				if(choice == 5)
					intent.putExtra("choice", "");
				else if(choice != 5)
					intent.putExtra("choice",(String)answer[choice].getText());

				intent.putExtra("rightAnswer",(String)answer[rightAnswer].getText());
				intent.putExtra("player1score",player1score);
				intent.putExtra("player2score",player2score);
				intent.putExtra("turn",turn);
				startActivity(intent);
				finish();
			}
		});


		try
		{
			imgview = findViewById(R.id.imageView);
			Bitmap bm = BitmapFactory.decodeFile(getExternalPath() + "/CatchmindMobile/img.jpg");
			imgview.setImageBitmap(bm);
		}
		catch(Exception e)
		{
			Toast.makeText(getApplicationContext(), "load error", Toast.LENGTH_SHORT).show();
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

	public String[] wordSelect()
	{
		Workbook workbook = null;
		Sheet sheet = null;
		Random random = new Random();
		String[] word = new String[5];

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

			for(int i=0; i<5; i++)
				word[i] = words[random.nextInt(5125)];  //word 변수에 임의의 단어 선택하여 저장

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
}
