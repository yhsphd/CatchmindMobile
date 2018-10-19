package tk.yhsphd.catchmindmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
	int player1score, player2score;
	int turn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1score = 0;
        player2score = 0;
        turn = 1;

		findViewById(R.id.start).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View v) {
						Intent intent=new Intent(getApplicationContext(),MakeQuestion.class);
						intent.putExtra("player1score",player1score);
						intent.putExtra("player2score",player2score);
						intent.putExtra("turn",turn);
						startActivity(intent);
						finish();
					}
				}
		);

	}
}
