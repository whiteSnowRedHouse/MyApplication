package com.example.myapplication.ui.SaveFiles


import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.FileProvider.getUriForFile
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.R.drawable.ic_baseline_rename_24
import com.example.myapplication.ui.MyApplication
import java.io.File


/**
 * @author  白雪红房子
 * @date  2021/2/19 15:00
 * @version 1.0
 */
class filesAdapter(var fileList: Array<File>) :
    RecyclerView.Adapter<filesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val excelImage: ImageView = view.findViewById(R.id.excel_image)
        val fileName: TextView = view.findViewById(R.id.file_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cell_files, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var file = fileList[position]
        if (file.name.contains(".xls")) {
            holder.excelImage.setImageResource(R.drawable.excel_1)
        } else if (file.name.contains(".zip")) {
            holder.excelImage.setImageResource(R.drawable.compress_file)
        } else {
            holder.excelImage.setImageResource(R.drawable.file)
        }
        holder.fileName.setText(file.name)
//点击打开Excel文件
        holder.fileName.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val fileUri: Uri =
                getUriForFile(MyApplication.context, "com.example.myapplication.fileprovider", file)
            intent.setDataAndType(fileUri, "application/vnd.ms-excel")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.context.startActivity(intent)
            Log.d("fileList", file.name + "点击了")

        }

        )
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            creatPopupWindow(it,holder,file)
            return@OnLongClickListener true
        }
        )

    }

    fun creatPopupWindow(v: View,holder:ViewHolder,file: File) {
        val view = LayoutInflater.from(MyApplication.context)
            .inflate(R.layout.file_popupwindow, null, false)
        val renameImageView: ImageView = view.findViewById(R.id.rename_image)
        val shareImageView: ImageView = view.findViewById(R.id.share_image)
        val deleteImageView: ImageView = view.findViewById(R.id.delete_image)
        val cancelImageView: ImageView = view.findViewById(R.id.cancel_image)
        val rename: TextView = view.findViewById(R.id.rename)
        val share: TextView = view.findViewById(R.id.share)
        val delete: TextView = view.findViewById(R.id.delete)
        val cancel: TextView = view.findViewById(R.id.cancel)
        renameImageView.setImageResource(ic_baseline_rename_24)
        shareImageView.setImageResource(R.drawable.ic_baseline_share_24)
        deleteImageView.setImageResource(R.drawable.ic_baseline_delete_24)
        cancelImageView.setImageResource(R.drawable.ic_baseline_cancel_24)
        //PopupWindow(
        //    contentView: View!,
        //    width: Int,
        //    height: Int,
        //    focusable: Boolean)
        val popupWindow: PopupWindow = PopupWindow(
            view, ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, true
        )
        popupWindow.setOutsideTouchable(true)
        popupWindow.setTouchable(true);
        popupWindow.animationStyle = R.anim.anim
        //点击空白区域，popupwindow消失
        popupWindow.setTouchInterceptor { v, event ->
            false
        }
        popupWindow.showAsDropDown(v, 0, 0)
        //重命名点击事件
        rename.setOnClickListener(View.OnClickListener {
            CreatAlertDialog(v,holder,file)
            Toast.makeText(it.context, "rename 被点击了", Toast.LENGTH_SHORT).show()
        })
        //分享点击事件
        share.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val fileUri: Uri =
                getUriForFile(MyApplication.context, "com.example.myapplication.fileprovider", file)
            intent.putExtra(Intent.EXTRA_STREAM,fileUri) //添加附件，附件为文件
            intent.setDataAndType(fileUri, "application/vnd.ms-excel")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            MyApplication.context.startActivity(intent)
        })
        //删除点击事件
        delete.setOnClickListener(View.OnClickListener {
           file.delete()
           Toast.makeText(it.context, file.name+"已删除", Toast.LENGTH_SHORT).show()
        })
        //取消事件
        cancel.setOnClickListener(View.OnClickListener {
                popupWindow.dismiss()
        })
    }

    private fun CreatAlertDialog(v: View,holder: ViewHolder,file:File) {
        val builder = AlertDialog.Builder(v.context)
        val alertDialogView = LayoutInflater.from(MyApplication.context)
            .inflate(R.layout.rename_alertdialog,null)
        val editText:EditText=alertDialogView.findViewById(R.id.editFileName)
        builder.setCancelable(true)
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(alertDialogView)
            // Add action buttons
            .setPositiveButton(R.string.confirm,
                DialogInterface.OnClickListener { dialog, id ->
                    // sign in the user ...
                    val fileName=editText.text
                    val SavePath =
                        MyApplication.context.getExternalFilesDir(null)?.path + "/" + fileName + ".xls"
                    file.renameTo(File(SavePath))
                    holder.fileName.setText(fileName)
                })
            .setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                           dialog.cancel()
                })
        builder!!.create()
        builder.show()
    }

}



