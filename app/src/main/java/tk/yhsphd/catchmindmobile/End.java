package tk.yhsphd.catchmindmobile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class End extends AppCompatActivity
{
	TextView player1view, player2view, player1scoreView, player2scoreView, resultDescription;
	Button replayButton, finishButton;
	int player1, player2;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end);

		player1view = findViewById(R.id.player1result);
		player2view = findViewById(R.id.player2result);
		player1scoreView = findViewById(R.id.player1pointResult);
		player2scoreView = findViewById(R.id.player2pointResult);
		resultDescription = findViewById(R.id.resultDesc);
		replayButton = findViewById(R.id.replay);
		finishButton = findViewById(R.id.finishApp);

		Intent intent = getIntent();
		player1 = intent.getIntExtra("player1score", 0);
		player2 = intent.getIntExtra("player2score", 0);

		player1scoreView.setText(String.valueOf(player1));
		player2scoreView.setText(String.valueOf(player2));

		if(player1 > player2)
		{
			player1view.setTypeface(null, Typeface.BOLD);
			player1scoreView.setTypeface(null, Typeface.BOLD);
			resultDescription.setText("Player 1이 " + String.valueOf(player1-player2) + "p 차이로 승리하였습니다.");
		}
		else if(player1 < player2)
		{
			player2view.setTypeface(null, Typeface.BOLD);
			player2scoreView.setTypeface(null, Typeface.BOLD);
			resultDescription.setText("Player 2가 " + String.valueOf(player2-player1) + "p 차이로 승리하였습니다.");
		}
		else if(player1 == player2)
		{
			resultDescription.setText("player 1과 player 2가 " + String.valueOf(player1) + "p로 무승부입니다.");
		}

		replayButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(getApplicationContext(), MakeQuestion.class);
				intent.putExtra("player1score",0);
				intent.putExtra("player2score",0);
				intent.putExtra("turn",1);
				startActivity(intent);
				finish();
			}
		});

		finishButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				finish();
			}
		});
	}
}
