package com.example.mad3zoov2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad3zoov2.zoos.Animal
import com.example.mad3zoov2.zoos.ZoosOperator

class MainActivity : AppCompatActivity()
{
    private val zo: ZoosOperator = ZoosOperator()

    private var currentZooIndex: Int = -1
    private var currentAviaryIndex: Int = -1
    private var currentAnimalIndex: Int = -1

    private var currentStage: Int = 0

    private lateinit var recyclerViewZoos: RecyclerView
    private lateinit var recyclerViewAviaries: RecyclerView
    private lateinit var recyclerViewAnimals: RecyclerView
    private lateinit var layoutAnimalInfo: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zo.addAnimal(Animal(
            "Лео",
            "Вольер №1",
            "Сафари-парк",
            7,
            150.5,
            61.3,
            100.5,
            true,
            "Очень красивый лев"
        ))
        zo.addAnimal(Animal(
            "Тигря",
            "Вольер №2",
            "Сафари-парк",
            9,
            130.5,
            50.3,
            110.5,
            true,
            "Очень красивый тигр"
        ))
        zo.addAnimal(Animal(
            "Хитруня",
            "Вольер для енотов",
            "Городской зоопарк",
            3,
            30.0,
            11.8,
            29.0,
            false,
            "Очень хитрая енотиха"
        ))

        recyclerViewZoos = findViewById(R.id.recyclerViewZoos)
        recyclerViewZoos.layoutManager = LinearLayoutManager(this)

        recyclerViewAviaries = findViewById(R.id.recyclerViewAviaries)
        recyclerViewAviaries.visibility = View.INVISIBLE
        recyclerViewAviaries.layoutManager = LinearLayoutManager(this)

        recyclerViewAnimals = findViewById(R.id.recyclerViewAnimals)
        recyclerViewAnimals.visibility = View.INVISIBLE
        recyclerViewAnimals.layoutManager = LinearLayoutManager(this)

        layoutAnimalInfo = findViewById(R.id.layoutAnimalInfo)
        layoutAnimalInfo.visibility = View.INVISIBLE

        val textViewName: TextView = findViewById(R.id.textViewAnimalName)
        val textViewAviary: TextView = findViewById(R.id.textViewAviary)
        val textViewZoo: TextView = findViewById(R.id.textViewZoo)
        val textViewAge: TextView = findViewById(R.id.textViewAge)
        val textViewHeight: TextView = findViewById(R.id.textViewHeight)
        val textViewWeight: TextView = findViewById(R.id.textViewWeight)
        val textViewTailLength: TextView = findViewById(R.id.textViewTailLength)
        val textViewSex: TextView = findViewById(R.id.textViewSex)
        val textViewDescription: TextView = findViewById(R.id.textViewDescription)

        findViewById<Button>(R.id.buttonEdit).setOnClickListener { buttonsAddOrEditListener(2) }
        findViewById<Button>(R.id.buttonDel).setOnClickListener { buttonDelListener() }
        findViewById<Button>(R.id.buttonBack).setOnClickListener { buttonBackListener() }

        recyclerViewZoos.addOnItemTouchListener(RecyclerItemClickListener(
            recyclerViewZoos,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    currentZooIndex = position
                    currentStage = 1
                    refresh()
                }
            }))

        recyclerViewAviaries.addOnItemTouchListener(RecyclerItemClickListener(
            recyclerViewAviaries,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    currentAviaryIndex = position
                    currentStage = 2
                    refresh()
                }
            }))

        recyclerViewAnimals.addOnItemTouchListener(RecyclerItemClickListener(
            recyclerViewAnimals,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    currentAnimalIndex = position
                    currentStage = 3
                    val tempAnimal: Animal = zo.getAnimal(currentZooIndex,
                        currentAviaryIndex, currentAnimalIndex)
                    textViewName.text = tempAnimal.name
                    textViewAviary.text = tempAnimal.aviary
                    textViewZoo.text = tempAnimal.zoo
                    textViewAge.text = tempAnimal.age.toString()
                    textViewHeight.text = tempAnimal.height.toString()
                    textViewWeight.text = tempAnimal.weight.toString()
                    textViewTailLength.text = tempAnimal.tailLength.toString()
                    if (tempAnimal.sex)
                    {
                        textViewSex.text = "Самец"
                    }
                    else
                    {
                        textViewSex.text = "Самка"
                    }
                    textViewDescription.text = tempAnimal.description
                    recyclerViewAnimals.visibility = View.INVISIBLE
                    layoutAnimalInfo.visibility = View.VISIBLE
                }
            }))

        refresh()
    }

    private fun buttonsAddOrEditListener(action: Int)
    {
        val tempAnimal: Animal = zo.getAnimal(currentZooIndex,
            currentAviaryIndex, currentAnimalIndex)
        val intent = Intent()
        intent.setClass(this, EditActivity::class.java)
        intent.putExtra("ACTION", action)
        intent.putExtra("INDEXA", currentAviaryIndex)
        intent.putExtra("INDEXZ", currentZooIndex)
        intent.putExtra("INDEX", currentAnimalIndex)
        if (action == 2)
        {
            intent.putExtra("NAME", tempAnimal.name)
            intent.putExtra("AVIARY", tempAnimal.aviary)
            intent.putExtra("ZOO", tempAnimal.zoo)
            intent.putExtra("AGE", tempAnimal.age)
            intent.putExtra("HEIGHT", tempAnimal.height)
            intent.putExtra("WEIGHT", tempAnimal.weight)
            intent.putExtra("TAIL", tempAnimal.tailLength)
            intent.putExtra("SEX", tempAnimal.sex)
            intent.putExtra("DESC", tempAnimal.description)
        }
        startActivityForResult(intent, 1)
    }

    private fun buttonDelListener()
    {

    }

    private fun buttonBackListener()
    {

    }

    private fun refresh()
    {
        if (currentStage == 0)
        {
            recyclerViewZoos.adapter = CustomRecyclerAdapterForZoos(zo.getZoosNames(), zo.getNumOfAnimalsInTheZoos())
            recyclerViewZoos.visibility = View.VISIBLE
            recyclerViewAviaries.visibility = View.INVISIBLE
            recyclerViewAnimals.visibility = View.INVISIBLE
            layoutAnimalInfo.visibility = View.INVISIBLE
        }
        if (currentStage == 1)
        {
            recyclerViewAviaries.adapter = CustomRecyclerAdapterForAviaries(
                zo.getAviariesNames(currentZooIndex),
                zo.getNumOfAnimalsInTheAviaries(currentZooIndex))
            recyclerViewZoos.visibility = View.INVISIBLE
            recyclerViewAviaries.visibility = View.VISIBLE
            recyclerViewAnimals.visibility = View.INVISIBLE
            layoutAnimalInfo.visibility = View.INVISIBLE
        }
        if (currentStage == 2)
        {
            recyclerViewAnimals.adapter = CustomRecyclerAdapterForAnimals(
                zo.getAnimalsNames(currentZooIndex, currentAviaryIndex))
            recyclerViewZoos.visibility = View.INVISIBLE
            recyclerViewAviaries.visibility = View.INVISIBLE
            recyclerViewAnimals.visibility = View.VISIBLE
            layoutAnimalInfo.visibility = View.INVISIBLE
        }
        if (currentStage == 3)
        {

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
        {
            var action = data?.getSerializableExtra("ACTION")
            var indexA = data?.getSerializableExtra("INDEXA")
            var indexZ = data?.getSerializableExtra("INDEXZ")
            var index = data?.getSerializableExtra("INDEX")
            var name = data?.getSerializableExtra("NAME")
            var aviary = data?.getSerializableExtra("AVIARY")
            var zoo = data?.getSerializableExtra("ZOO")
            var age = data?.getSerializableExtra("AGE")
            var height = data?.getSerializableExtra("HEIGHT")
            var weight = data?.getSerializableExtra("WEIGHT")
            var tailLength = data?.getSerializableExtra("TAIL")
            var sex = data?.getSerializableExtra("SEX")
            var description = data?.getSerializableExtra("DESC")
            action = action as Int
            indexA = indexA as Int
            indexZ = indexZ as Int
            index = index as Int
            name = name as String
            aviary = aviary as String
            zoo = zoo as String
            age = age as Int
            height = height as Double
            weight = weight as Double
            tailLength = tailLength as Double
            sex = sex as Boolean
            description = description as String
            val tempAnimal = Animal(name, aviary, zoo, age, height, weight, tailLength, sex, description)
            if (action == 1)
            {
                zo.addAnimal(tempAnimal)
                refresh()
            }
            else
            {
                zo.editAnimal(indexZ, indexA, index, tempAnimal)
                refresh()
            }
        }
    }
}