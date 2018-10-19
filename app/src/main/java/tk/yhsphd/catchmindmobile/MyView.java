package tk.yhsphd.catchmindmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyView extends View
{
	Paint paint = new Paint();
	Canvas canvas = new Canvas();
	Bitmap mbitmap;

	int oldX,oldY = -1;

	public MyView(Context context)
	{
		super(context);
		paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
		paint.setStrokeWidth(10f); // 선의 굵기 지정

		canvas = new Canvas();

		mbitmap = Bitmap.createBitmap(2000,2000, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(mbitmap);
		canvas.drawColor(Color.rgb(250, 250, 250));
	}

	public MyView(Context context, AttributeSet att)
	{
		super(context, att);
		paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
		paint.setStrokeWidth(10f); // 선의 굵기 지정

		canvas = new Canvas();

		mbitmap = Bitmap.createBitmap(2000,2000, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(mbitmap);
		canvas.drawColor(Color.rgb(250, 250, 250));
	}

	public MyView(Context context, AttributeSet att, int ref)
	{
		super(context, att, ref);
		paint.setStyle(Paint.Style.STROKE); // 선이 그려지도록
		paint.setStrokeWidth(10f); // 선의 굵기 지정

		canvas = new Canvas();

		mbitmap = Bitmap.createBitmap(2000,2000, Bitmap.Config.ARGB_8888);
		canvas.setBitmap(mbitmap);
		canvas.drawColor(Color.rgb(250, 250, 250));
	}

	@Override
	protected void onDraw(Canvas canvas) // 화면을 그려주는 메서드
	{
		super.onDraw(canvas);
		if(mbitmap != null)
			canvas.drawBitmap(mbitmap,0,0,null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int X = (int)event.getX();
		int Y = (int)event.getY();

		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN :
				oldX = X; oldY = Y;
				break;

			case MotionEvent.ACTION_MOVE :
				if(oldX != -1)
				{
					canvas.drawLine(oldX,oldY,X,Y,paint);
					invalidate();
					oldX = X; oldY = Y;
				}
				break;

			case MotionEvent.ACTION_UP :
				if(oldX != -1)
				{
					canvas.drawLine(oldX,oldY,X,Y,paint);
				}
				invalidate();
				oldX = -1; oldY = -1;
				break;
		}

		invalidate(); // 화면을 다시그려라
		return true;
	}

	public void Save(String filename)
	{
		try
		{
			FileOutputStream out = new FileOutputStream(filename);
			mbitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			Toast.makeText(getContext(),"File not found",Toast.LENGTH_SHORT).show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			Toast.makeText(getContext(),"IO Exception",Toast.LENGTH_SHORT).show();
		}
	}

	public void erase()
	{
		canvas.drawColor(Color.rgb(250, 250, 250));
	}
}