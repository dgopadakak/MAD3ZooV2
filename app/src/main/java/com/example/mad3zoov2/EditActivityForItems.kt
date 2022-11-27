package com.example.mad3zoov2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditActivityForItems : AppCompatActivity()
{
    private lateinit var editTextItemName: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_for_items)

        editTextItemName = findViewById(R.id.editText1Item)

        val stage = intent.getSerializableExtra("STAGE") as Int
        val indexZ = intent.getSerializableExtra("INDEXZ") as Int
        val index = intent.getSerializableExtra("INDEX") as Int
        val oldName = intent.getSerializableExtra("NAME") as String

        editTextItemName.setText(oldName)

        findViewById<Button>(R.id.buttonItemConfirm).setOnClickListener { confirmChanges(stage,
            indexZ, index) }
    }

    private fun confirmChanges(stage: Int, indexZ: Int, index: Int)
    {
        if (editTextItemName.text.toString() != "")
        {
            val intent = Intent(this@EditActivityForItems, MainActivity::class.java)
            intent.putExtra("ACTION", 3)
            intent.putExtra("STAGE", stage)
            intent.putExtra("INDEXZ", indexZ)
            intent.putExtra("INDEX", index)
            intent.putExtra("NAME", editTextItemName.text.toString().trim())
            setResult(RESULT_OK, intent)
            finish()
        }
        else
        {
            val toast = Toast.makeText(
                applicationContext,
                "Заполните поле!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}