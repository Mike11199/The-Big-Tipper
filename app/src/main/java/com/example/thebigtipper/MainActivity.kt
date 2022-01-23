package com.example.thebigtipper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

//When you have tagging, the tag should be the name of the class name
private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {
    private lateinit var Base_Amount: EditText
    private lateinit var Tip_Seek_Bar: SeekBar
    private lateinit var Percent_Label: TextView
    private lateinit var Tip_Amount: TextView
    private lateinit var Total_Amount: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Base_Amount=findViewById(R.id.Base_Amount)
        Tip_Seek_Bar=findViewById(R.id.Tip_Seek_Bar)
        Percent_Label=findViewById(R.id.Percent_Label)
        Tip_Amount=findViewById(R.id.Tip_Amount)
        Total_Amount=findViewById(R.id.Total_Amount)

        Tip_Seek_Bar.progress= INITIAL_TIP_PERCENT
        Percent_Label.text = "$INITIAL_TIP_PERCENT%"


//        add a listener here. An event listener is a procedure or function in a computer program that waits for an event to occur.
        Tip_Seek_Bar.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener 
        {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               Log.i(TAG,"onProgressChanged $progress")
                Percent_Label.text= "$progress%"
                computeTipAndTotal()
//                Every time progress is changed on the seekbar you print out the current value shown in logcat
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


}
