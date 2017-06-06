package at.com.levelprogressbar;
/**
 * THIS CLASS WILL HELP TO CREATE PROGRESS BAR 4 LEVEL.YOU CAN USE 2 COLOR FOR DISPLAYING THE SOME 2 STATE.
 * PROGRESS BAR.eg progress bar use to display sound level exceeding the specific range.
 * <p>
 * <h>USE mileStone PROPERTY OR setMileStone METHOD  TO SET THE BENCHMARK FOR COLOR CHANGE</h>
 * USE isSingleColor PROPERTY or setSingleColor METHOD TO ENABLE OR DISABLE DUAL COLOR FOR PROGRESS BAR
 * USE nFirstColor PROPERTY or setnFirstColor METHOD TO SET COLOR TO PROGRESS BAR REACHING BEFORE IT REACHING TO MILESTONE
 * USE nSecondColor PROPERTY ot setnSecondColor METHOD TO SET COLOR TO PROGRESS BAR AFTER IT CROSSING THE MILESTONE
 *
 * @author Ankit Polekar.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class LevelProgressBar extends ProgressBar {
    Paint paint;
    private int progress;
    private int nSecondColor;
    private int nFirstColor;
    private float barThickness;
    private int unfilledSectionColor;
    private int mileStone;
    private int nSlot;
    private Paint whitePaint;
    private Paint grayPaint;
    private boolean isSingleColor;

    public LevelProgressBar(Context context) {
        super(context);
        init(null, 0);
    }

    public LevelProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LevelProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public void setSingleColor(boolean singleColor) {
        isSingleColor = singleColor;
    }

    public void setnSecondColor(int nSecondColor) {
        this.nSecondColor = nSecondColor;
    }

    public void setnFirstColor(int nFirstColor) {
        this.nFirstColor = nFirstColor;
    }

    public void setBarThickness(float barThickness) {
        this.barThickness = barThickness;
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.LevelProgressBar, defStyle, 0);

        barThickness = a.getFloat(R.styleable.LevelProgressBar_barThickness, 60);
        unfilledSectionColor = Color.parseColor("#34c5bb");
        nSecondColor = a.getColor(R.styleable.LevelProgressBar_secondColor, Color.RED);
        nFirstColor = a.getColor(R.styleable.LevelProgressBar_firstColor, Color.GREEN);
        isSingleColor = a.getBoolean(R.styleable.LevelProgressBar_singleColor, false);
        mileStone = a.getInt(R.styleable.LevelProgressBar_mileStone, getMax());
        paint = new Paint();
        whitePaint = new Paint();
        grayPaint = new Paint();


        ColorDrawable colorDrawable = (ColorDrawable) this.getBackground();
        int nBackGroundColor = colorDrawable.getColor();

        whitePaint.setColor(nBackGroundColor);
        grayPaint.setColor(Color.GRAY);
        whitePaint.setStrokeWidth(barThickness);
        grayPaint.setStrokeWidth(barThickness);


        a.recycle();
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int halfHeight = getHeight() / 2;
        int progressEndX = (int) (getWidth() * getProgress() / 100f);
        int progressStop = (int) ((getWidth() * getMax()) / 100f);

        nSlot = getMax() / 4;

        int firstStop = (int) ((getWidth() * nSlot) / 100f);
        int secondStop = (int) ((getWidth() * (nSlot * 2)) / 100f);
        int thirdStop = (int) ((getWidth() * (nSlot * 3)) / 100f);
        int lastStop = (int) ((getWidth() * (nSlot * 4)) / 100f);

        progress = getProgress();
        // draw the filled portion of the bar
        paint.setStrokeWidth(barThickness);

        int color = nFirstColor;
        if (!isSingleColor)
            color = (progress >= mileStone) ? nSecondColor : nFirstColor;

        paint.setColor(color);

        //BACK GROUND OF PROGRESS BAR
        canvas.drawLine(0, halfHeight, progressStop, halfHeight, grayPaint);

        //HERE DRAW FIRST SECTION OF PROGRESS BAR
        if (progress > nSlot)
            canvas.drawLine(0, halfHeight, firstStop, halfHeight, paint);
        else {
            canvas.drawLine(0, halfHeight, progressEndX, halfHeight, paint);
        }

        //DIVIDER BETWEEN TWO SECTION
        canvas.drawLine(firstStop, halfHeight, firstStop + 10, halfHeight, whitePaint);

        //DRAW SECOND SECTION OF PROGRESS BAR
        if (progress < (nSlot * 2) && progress > nSlot)
            canvas.drawLine(firstStop + 10, halfHeight, progressEndX, halfHeight, paint);
        else if (progress > nSlot) {
            canvas.drawLine(firstStop + 10, halfHeight, secondStop, halfHeight, paint);
        }

        //DIVIDER BETWEEN TWO SECTION
        canvas.drawLine(secondStop, halfHeight, secondStop + 10, halfHeight, whitePaint);

        //DRAW THIRD SECTION OF PROGRESS BAR
        if (progress < (nSlot * 3) && (progress > (nSlot * 2)))
            canvas.drawLine(secondStop + 10, halfHeight, progressEndX, halfHeight, paint);
        else if (progress > nSlot * 3)
            canvas.drawLine(secondStop + 10, halfHeight, thirdStop, halfHeight, paint);

        //DIVIDER BETWEEN TWO SECTION
        canvas.drawLine(thirdStop, halfHeight, thirdStop + 10, halfHeight, whitePaint);

        //DRAW FOURTH SECTION OF PROGRESS BAR
        if (progress > (nSlot * 3))
            canvas.drawLine(thirdStop + 10, halfHeight, progressStop, halfHeight, paint);

        //
        canvas.drawLine(progressStop, halfHeight, getWidth(), halfHeight, whitePaint);


    }


    public void setMileStone(int mileStone) {
        this.mileStone = mileStone;
    }
}
