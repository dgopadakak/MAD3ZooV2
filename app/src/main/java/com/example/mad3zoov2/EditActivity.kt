package com.example.mad3zoov2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast

class EditActivity : AppCompatActivity()
{
    private lateinit var editTextName: EditText
    private lateinit var editTextAviary: EditText
    private lateinit var editTextZoo: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextHeight: EditText
    private lateinit var editTextWeight: EditText
    private lateinit var editTextTailLength: EditText
    private lateinit var radioButtonSexM: RadioButton
    private lateinit var radioButtonSexW: RadioButton
    private lateinit var editTextDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val action = intent.getSerializableExtra("ACTION") as Int
        val indexA = intent.getSerializableExtra("INDEXA") as Int
        val indexZ = intent.getSerializableExtra("INDEXZ") as Int
        val index = intent.getSerializableExtra("INDEX") as Int

        editTextName = findViewById(R.id.editText1)
        editTextAviary = findViewById(R.id.editText2)
        editTextZoo = findViewById(R.id.editText3)
        editTextAge = findViewById(R.id.editText4)
        editTextHeight = findViewById(R.id.editText5)
        editTextWeight = findViewById(R.id.editText6)
        editTextTailLength = findViewById(R.id.editText7)
        radioButtonSexM = findViewById(R.id.radioButtonSexM)
        radioButtonSexW = findViewById(R.id.radioButtonSexW)
        editTextDescription = findViewById(R.id.editText8)

        findViewById<Button>(R.id.buttonConfirmChanges).setOnClickListener { confirmChanges(action,
            indexA, indexZ, index) }

        if (action == 2 || action == 4)
        {
            editTextName.setText(intent.getSerializableExtra("NAME") as String)
            editTextAviary.setText(intent.getSerializableExtra("AVIARY") as String)
            editTextZoo.setText(intent.getSerializableExtra("ZOO") as String)
            editTextAge.setText((intent.getSerializableExtra("AGE") as Int).toString())
            editTextHeight.setText((intent.getSerializableExtra("HEIGHT") as Double).toString())
            editTextWeight.setText((intent.getSerializableExtra("WEIGHT") as Double).toString())
            editTextTailLength.setText((intent.getSerializableExtra("TAIL") as Double).toString())
            if (intent.getSerializableExtra("SEX") as Boolean)
            {
                radioButtonSexM.isChecked = true
            }
            else
            {
                radioButtonSexW.isChecked = true
            }
            editTextDescription.setText(intent.getSerializableExtra("DESC") as String)
        }
    }

    private fun confirmChanges(action: Int, indexA: Int, indexZ: Int, index: Int)
    {
        if (editTextName.text.toString() != "" && editTextAviary.text.toString() != ""
            && editTextAge.text.toString() != "" && editTextHeight.text.toString() != ""
            && editTextWeight.text.toString() != "" && editTextTailLength.text.toString() != "")
        {
            if (editTextAge.text.toString().toInt() >= 0 && editTextHeight.text.toString().toDouble() > 0
                && editTextWeight.text.toString().toDouble() > 0 && editTextTailLength.text.toString().toDouble() >= 0)
            {
                val intent = Intent(this@EditActivity, MainActivity::class.java)
                intent.putExtra("ACTION", action)
                intent.putExtra("INDEXA", indexA)
                intent.putExtra("INDEXZ", indexZ)
                intent.putExtra("INDEX", index)
                intent.putExtra("NAME", editTextName.text.toString().trim())
                intent.putExtra("AVIARY", editTextAviary.text.toString().trim())
                intent.putExtra("ZOO", editTextZoo.text.toString().trim())
                intent.putExtra("AGE", editTextAge.text.toString().trim().toInt())
                intent.putExtra("HEIGHT", editTextHeight.text.toString().trim().toDouble())
                intent.putExtra("WEIGHT", editTextWeight.text.toString().trim().toDouble())
                intent.putExtra("TAIL", editTextTailLength.text.toString().trim().toDouble())
                if (radioButtonSexM.isChecked)
                {
                    intent.putExtra("SEX", true)
                }
                else
                {
                    intent.putExtra("SEX", false)
                }
                intent.putExtra("DESC", editTextDescription.text.toString().trim())
                setResult(RESULT_OK, intent)
                finish()
            }
            else
            {
                val toast = Toast.makeText(
                    applicationContext,
                    "Введите корректные значения!",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }
        }
        else
        {
            val toast = Toast.makeText(
                applicationContext,
                "Заполните обязательные поля!",
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }
}