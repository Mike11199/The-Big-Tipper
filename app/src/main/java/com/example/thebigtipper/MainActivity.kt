package com.example.thebigtipper

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.animation.Animation
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

//When you have tagging, the tag should be the name of the class name
private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var Base_Amount: EditText
    private lateinit var Tip_Seek_Bar: SeekBar
    private lateinit var Percent_Label: TextView
    private lateinit var Tip_Amount: TextView
    private lateinit var Total_Amount: TextView
    private lateinit var Mood_Image: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Base_Amount=findViewById(R.id.Base_Amount)
        Tip_Seek_Bar=findViewById(R.id.Tip_Seek_Bar)
        Percent_Label=findViewById(R.id.Percent_Label)
        Tip_Amount=findViewById(R.id.Tip_Amount)
        Total_Amount=findViewById(R.id.Total_Amount)
        Mood_Image=findViewById(R.id.FaceMood)


        Tip_Seek_Bar.progress= INITIAL_TIP_PERCENT
        Percent_Label.text = "$INITIAL_TIP_PERCENT%"

        Tip_Seek_Bar.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        Tip_Seek_Bar.getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)


//        This takes the main layout defined in activity main and runs an animation.
//        Followed animated background tutorial here from practical coding in Java and then wrote down in Kotlin


//        This was for adding animated gradient backgrounds but was crashing the app for some reason; worked on emulated app
//        val ConstraintLayout: ConstraintLayout = findViewById(R.id.mainLayout)
//        val AnimationDrawable: AnimationDrawable= ConstraintLayout.background as AnimationDrawable
//        AnimationDrawable.setEnterFadeDuration(2500)
//        AnimationDrawable.setExitFadeDuration(5000)
//        AnimationDrawable.start()




//        This sections adds a listener here. An event listener is a procedure or function in a computer program that waits for an event to occur.
//        This does a task when the seekbar changes


        Tip_Seek_Bar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener 
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               Log.i(TAG,"onProgressChanged $progress")
                Percent_Label.text= "$progress%"
                computeTipAndTotal()
                Update_Mood()
//                Every time the progress is changed on the seekbar, you print out the current value shown in the logcat pane
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        }
        )
//the object keyword is how to create anonymous one-time use classes used to implement interfaces
        Base_Amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
                Update_Mood()
            }

        })
            
        }

    private fun computeTipAndTotal() {
        if (Base_Amount.text.isEmpty()) {
            Tip_Amount.text = ""
            Total_Amount.text = ""
            return
        }

        //1. Get the value of the base and tip percent
        val etBase_Amount = Base_Amount.text.toString().toDouble()
        val tipPercent = Tip_Seek_Bar.progress
        //2. Get the tip and total
        val tipAmount = etBase_Amount * tipPercent / 100
        val totalAmount = etBase_Amount + tipAmount
        //3. Update the UI
        Tip_Amount.text = "%.2f".format(tipAmount)
        Total_Amount.text="%.2f".format(totalAmount)

    }


    private fun Update_Mood() {
        //1. Get the value of the tip percent
        val tipPercent = Tip_Seek_Bar.progress
        //3. Update the image (R.java is an auto-generated file which contains resource IDs)
        if (tipPercent<1)
            {
            Mood_Image.setImageResource(R.drawable.madface)
            }
        else if (tipPercent<5)
            {
            Mood_Image.setImageResource(R.drawable.slightlyfrowny)
            }
        else if (tipPercent<10)
        {
            Mood_Image.setImageResource(R.drawable.pureneutral)
        }
        else if (tipPercent<20)
        {
            Mood_Image.setImageResource(R.drawable.slightlyhappy)
        }
         else
            {
            Mood_Image.setImageResource(R.drawable.vhappy)
                        }
                             }
    //app:srcCompat="@drawable/happy_face"

}
