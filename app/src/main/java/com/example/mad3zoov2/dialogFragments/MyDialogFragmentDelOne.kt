package com.example.mad3zoov2.dialogFragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.mad3zoov2.MainActivity

class MyDialogFragmentDelOne : DialogFragment()
{
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Вы уверены?")
            .setTitle("Внимание!")
            .setPositiveButton("OK"
            ) { _, _ -> (activity as MainActivity?)?.buttonDelListener() }
            .setNegativeButton("Отмена") { _, _ -> }
        return builder.create()
    }
}