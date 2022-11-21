package com.example.mad3zoov2

import android.os.Bundle
import android.view.View
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

        val recyclerViewZoos: RecyclerView = findViewById(R.id.recyclerViewZoos)
        recyclerViewZoos.layoutManager = LinearLayoutManager(this)
        recyclerViewZoos.adapter = CustomRecyclerAdapterForZoos(zo.getZoosNames(), zo.getNumOfAnimalsInTheZoos())

        val recyclerViewAviaries: RecyclerView = findViewById(R.id.recyclerViewAviaries)
        recyclerViewAviaries.visibility = View.INVISIBLE
        recyclerViewAviaries.layoutManager = LinearLayoutManager(this)

        val recyclerViewAnimals: RecyclerView = findViewById(R.id.recyclerViewAnimals)
        recyclerViewAnimals.visibility = View.INVISIBLE
        recyclerViewAnimals.layoutManager = LinearLayoutManager(this)

        val layoutAnimalInfo: ConstraintLayout = findViewById(R.id.layoutAnimalInfo)
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

        recyclerViewZoos.addOnItemTouchListener(RecyclerItemClickListener(
            recyclerViewZoos,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    currentZooIndex = position
                    currentStage = 1
                    recyclerViewAviaries.adapter = CustomRecyclerAdapterForAviaries(
                        zo.getAviariesNames(currentZooIndex),
                        zo.getNumOfAnimalsInTheAviaries(currentZooIndex))
                    recyclerViewZoos.visibility = View.INVISIBLE
                    recyclerViewAviaries.visibility = View.VISIBLE

                    recyclerViewAviaries.addOnItemTouchListener(RecyclerItemClickListener(
                        recyclerViewAviaries,
                        object : RecyclerItemClickListener.OnItemClickListener
                        {
                            override fun onItemClick(view: View, position: Int)
                            {
                                currentAviaryIndex = position
                                currentStage = 2
                                recyclerViewAnimals.adapter = CustomRecyclerAdapterForAnimals(
                                    zo.getAnimalsNames(currentZooIndex, currentAviaryIndex))
                                recyclerViewAviaries.visibility = View.INVISIBLE
                                recyclerViewAnimals.visibility = View.VISIBLE

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
                            }
                        }))
                }
            }))
    }
}