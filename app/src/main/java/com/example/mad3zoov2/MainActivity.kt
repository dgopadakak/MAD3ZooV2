package com.example.mad3zoov2

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mad3zoov2.zoos.Animal
import com.example.mad3zoov2.zoos.ZoosOperator

class MainActivity : AppCompatActivity()
{
    private val zo: ZoosOperator = ZoosOperator()

    private var currentZooIndex: Int = -1
    private var currentAviaryIndex: Int = -1

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
                            }
                        }))
                }
            }))
    }
}