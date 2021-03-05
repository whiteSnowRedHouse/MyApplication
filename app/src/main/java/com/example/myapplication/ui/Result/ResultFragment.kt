package com.example.myapplication.ui.Result

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.ui.MyApplication
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ResultFragment : Fragment() {

    private lateinit var resultViewModel: ResultViewModel
    var entries1 = ArrayList<Entry>() //标准曲线数据
    var entries2 = ArrayList<Entry>() //检测数据

    lateinit var lineDataSet1: LineDataSet //标准曲线图数据集
    lateinit var lineDataSet2: LineDataSet //检测数据图数据集

    lateinit var lineData: LineData//标准曲线图数据



    var startX1Value: Float? = null
    var startY1Value: Float? = null
    var startX2Value: Float? = null
    var startY2Value: Float? = null
    //y=A+B*log10X
    var linearAValue: Float? = null
    var linearBValue: Float? = null


    var x0Value: Float? = null
    var xValue: Float? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
//        resultViewModel =
//                ViewModelProvider(this).get(ResultViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_result, container, false)
        val addButton:Button=root.findViewById(R.id.button_add)
        val linechart:LineChart=root.findViewById(R.id.lineChart1)
        val resultButton:Button=root.findViewById(R.id.button_result)
        val result:TextView=root.findViewById(R.id.result)

        addButton.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(it.context)
            val alertDialogView = LayoutInflater.from(MyApplication.context)
                .inflate(R.layout.linearfit_alertdialog,null)
            val startX1:EditText=alertDialogView.findViewById(R.id.x0_textview)
            val startY1:EditText=alertDialogView.findViewById(R.id.start_point_y1)
            val startX2:EditText=alertDialogView.findViewById(R.id.x_textview)
            val startY2:EditText=alertDialogView.findViewById(R.id.start_point_y2)

            val linearfitA:EditText=alertDialogView.findViewById(R.id.linearfit_a)
            val linearfitB:EditText=alertDialogView.findViewById(R.id.linearfit_b)
            builder.setCancelable(true)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(alertDialogView)
                // Add action buttons
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                        startX1Value= startX1.text.toString().toFloat()
                        startY1Value=startY1.text.toString().toFloat()
                        startX2Value=startX2.text.toString().toFloat()
                        startY2Value=startY2.text.toString().toFloat()
                        Log.d("fileList",startX1Value.toString()+startY1Value.toString()+startX2Value.toString()+startY2Value.toString())
//                    val linearfitValue=linearfit.text.toString().toFloat()
                        entries1.add(Entry(startX1Value!!, startY1Value!!))
                        entries1.add(Entry(startX2Value!!, startY2Value!!))
                        linearAValue=linearfitA.text.toString().toFloat()
                        linearBValue=linearfitB.text.toString().toFloat()
                        var axisName: TextView =root.findViewById(R.id.textView4)
                        val source =
                            "Log<sub>10</sub>C<sub>cort</sub> /nM <br>"
                        axisName.setText(Html.fromHtml(source,1))


                        this.lineDataSet1 = LineDataSet(entries1, "calibraion") //entries添加到数据集
                        this.lineDataSet1.setValueTextSize(0f)
                        // black lines and points

                        // black lines and points
                        this.lineDataSet1.setColor(Color.BLACK)
                        this.lineDataSet1.setCircleColor(Color.BLACK)


                        // line thickness and point size
                        this.lineDataSet1.setLineWidth(2f)
                        this.lineDataSet1.setCircleRadius(0f)

                        // draw points as solid circles

                        // draw points as solid circles
                        this.lineDataSet1.setDrawCircleHole(false)
                        this.lineDataSet1.color = Color.BLACK//折线数据集
                        this.lineDataSet1.setLineWidth(5f)

                        lineData = LineData(lineDataSet1)
                        linechart.getXAxis().position= XAxis.XAxisPosition.BOTH_SIDED
                        linechart.xAxis.axisLineWidth=2f
                        linechart.axisLeft.axisLineWidth=2f
                        linechart.axisRight.axisLineWidth=2f
                        linechart.getDescription().setEnabled(false)


                        linechart.setData(lineData)
                        linechart.invalidate()



                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            builder!!.create()
            builder.show()

        })
        Log.d("TAG",entries1.toString())
        resultButton.setOnClickListener(View.OnClickListener{
            val resultBuilder = AlertDialog.Builder(it.context)
            val resultAlertDialogView = LayoutInflater.from(MyApplication.context)
                .inflate(R.layout.result_alertdialog,null)
            val X0:EditText=resultAlertDialogView.findViewById(R.id.x0_textview)
            val X:EditText=resultAlertDialogView.findViewById(R.id.x_textview)
                       resultBuilder.setCancelable(true)

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            resultBuilder.setView(resultAlertDialogView)
                // Add action buttons
                .setPositiveButton(R.string.confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                        x0Value=X0.text.toString().toFloat()
                        xValue=X.text.toString().toFloat()
                        val ratioResult:Float=(xValue!! - x0Value!!)/ x0Value!!
//                    val linearfitValue=linearfit.text.toString().toFloat()
                        entries2.add(Entry(startX1Value!!, ratioResult*100!!))
                        entries2.add(Entry(startX2Value!!, ratioResult*100!!))

                        var axisName: TextView =root.findViewById(R.id.textView4)
                        val source =
                            "Log<sub>10</sub>C<sub>cort</sub> /nM <br>"
                        axisName.setText(Html.fromHtml(source,1))


                        lineDataSet2 = LineDataSet(entries2, "responding for saliva sample") //entries添加到数据集
                        lineDataSet2.enableDashedLine(30f, 20f, 0f)
                        lineDataSet2.setValueTextSize(0f)
                        // black lines and points

                        // black lines and points
                        lineDataSet2.setColor(Color.RED)
                        lineDataSet2.setCircleColor(Color.RED)


                        // line thickness and point size
                        lineDataSet2.setLineWidth(5f)
                        lineDataSet2.setCircleRadius(0f)

                        // draw points as solid circles

                        // draw points as solid circles
                        lineDataSet2.setDrawCircleHole(false)

                        lineDataSet2.color = Color.RED//折线数据集
                        lineData.addDataSet(lineDataSet2)
                        linechart.setData(lineData)
                        linechart.invalidate()

                        result.setText(String.format("%.2f",Math.pow(10.0,(((ratioResult+ linearAValue!!)/ linearBValue!!).toDouble())))+" nM")
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            resultBuilder!!.create()
            resultBuilder.show()
        })



        return root
    }






}