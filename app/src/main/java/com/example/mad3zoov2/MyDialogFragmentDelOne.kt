package com.example.mad3zoov2

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

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