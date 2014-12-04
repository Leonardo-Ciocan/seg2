package team2j.com.seg2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class ChartCardView extends FrameLayout {
    public ChartCardView(Context context) {
        super(context);
        invalidate();
    }

    public ChartCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        invalidate();
    }

    public ChartCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        invalidate();
    }

    ArrayList<PointF> data = new ArrayList<PointF>();
    public void drawData(ArrayList<DataPoint> points){
        data.clear();
        float dx = getWidth() / points.size();

        float max  = 0;
        float min = Integer.MAX_VALUE;

        for(DataPoint point : points){
            if(point.getValue() > max){
                max = point.getValue();
            }
            if(point.getValue() < min){
                min = point.getValue();
            }
        }

        float h = max - min;

        for(int r = 0; r < points.size();r++){
            float x = r * dx;
            float y = ((points.get(r).getValue() - min)/h) * getHeight();
            data.add(new PointF(x,y));
        }

        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        setAlpha(0.075f);
        super.onDraw(canvas);
        Paint wallpaint = new Paint();
        wallpaint.setColor(Color.DKGRAY);
        wallpaint.setStyle(Paint.Style.FILL);

        Path wallpath = new Path();
        wallpath.reset();
        if(data.size() == 0 ) return;
        wallpath.moveTo(getWidth() - data.get(0).x , getHeight() ); // used for first point
        PointF last = new PointF(0,0);
        for(PointF pt : data){
            wallpath.lineTo(getWidth() - pt.x, getHeight() - pt.y);
            last = pt;
        }
        wallpath.lineTo( getWidth() - last.x,getHeight());

        canvas.drawPath(wallpath, wallpaint);
    }



}
