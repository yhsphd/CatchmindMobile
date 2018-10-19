package tk.yhsphd.catchmindmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ready extends AppCompatActivity
{
	String answer;
	int player1score, player2score;
	int turn;

	TextView turnView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready);

		Intent intent = getIntent();
		answer = intent.getStringExtra("word");
		player1score = intent.getIntExtra("player1score", 0);
		player2score = intent.getIntExtra("player2score", 0);
		turn = intent.getIntExtra("turn", turn);

		turnView = findViewById(R.id.turnRD);
		if(turn % 2 == 1)
			turnView.setText("Player 1 -> Player 2");

		else if(turn % 2 == 0)
			turnView.setText("Player 1 <- Player 2");

		findViewById(R.id.go).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Log.d("turn", String.valueOf(turn));
				Intent intent = new Intent(getApplicationContext(), SolveQuestion.class);
				intent.putExtra("answer",answer);
				intent.putExtra("player1score",player1score);
				intent.putExtra("player2score",player2score);
				intent.putExtra("turn",turn);
				startActivity(intent);
				finish();
			}
		});
    }
}
