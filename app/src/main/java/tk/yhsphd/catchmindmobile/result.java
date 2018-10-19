package tk.yhsphd.catchmindmobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class result extends AppCompatActivity
{
	String userAnswer, answer;
	int player1score, player2score;
	int turn;

	TextView result;
	TextView gotPoint;
	TextView rightAnswer;
	TextView player1scoreText, player2scoreText;

	Button finishButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		result = findViewById(R.id.result);
		gotPoint = findViewById(R.id.point);
		rightAnswer = findViewById(R.id.answer);
		player1scoreText = findViewById(R.id.player1point);
		player2scoreText = findViewById(R.id.player2point);
		finishButton = findViewById(R.id.finish);

		finishButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent;

				if(turn <= 6)
				{
					intent = new Intent(getApplicationContext(), MakeQuestion.class);
					intent.putExtra("turn", turn);
				}
				else
					intent = new Intent(getApplicationContext(), End.class);

				intent.putExtra("player1score", player1score);
				intent.putExtra("player2score", player2score);
				startActivity(intent);
				finish();
			}
		});

		Intent intent = getIntent();
		userAnswer = intent.getStringExtra("choice");
		answer = intent.getStringExtra("rightAnswer");
		player1score = intent.getIntExtra("player1score", 0);
		player2score = intent.getIntExtra("player2score", 0);
		turn = intent.getIntExtra("turn", 0);

		Log.d("turn", String.valueOf(turn));

		if(userAnswer.equals(answer))
		{
			result.setText("정답입니다.");
			rightAnswer.setText("정답은 \"" + answer +"\" 입니다.");
			gotPoint.setText("+10p");

			if(turn%2 == 1)
			{
				player2score = player2score + 10;
				player2scoreText.setTypeface(null, Typeface.BOLD);
			}
			else if(turn%2 == 0)
			{
				player1score = player1score + 10;
				player1scoreText.setTypeface(null, Typeface.BOLD);
			}
		}
		else
		{
			result.setText("오답입니다.");
			rightAnswer.setText("정답은 \"" + answer +"\" 입니다.");
			gotPoint.setText("-5p");

			if(turn%2 == 1)
			{
				player2score = player2score - 5;
				player2scoreText.setTypeface(null, Typeface.BOLD);
			}
			else if(turn%2 == 0)
			{
				player1score = player1score - 5;
				player1scoreText.setTypeface(null, Typeface.BOLD);
			}
		}

		if(player1score > player2score)
		{
			player1scoreText.setTextSize(20);
		}
		else if(player1score < player2score)
		{
			player2scoreText.setTextSize(20);
		}

		player1scoreText.setText(String.valueOf(player1score) + "p");
		player2scoreText.setText(String.valueOf(player2score) + "p");
		turn++;
	}
}
