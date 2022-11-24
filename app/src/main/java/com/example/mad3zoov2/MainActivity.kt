package com.example.mad3zoov2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
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
    private var currentDelAction: Int = 0

    private lateinit var recyclerViewZoos: RecyclerView
    private lateinit var recyclerViewAviaries: RecyclerView
    private lateinit var recyclerViewAnimals: RecyclerView
    private lateinit var layoutAnimalInfo: ConstraintLayout

    private lateinit var textViewName: TextView
    private lateinit var textViewAviary: TextView
    private lateinit var textViewZoo: TextView
    private lateinit var textViewAge: TextView
    private lateinit var textViewHeight: TextView
    private lateinit var textViewWeight: TextView
    private lateinit var textViewTailLength: TextView
    private lateinit var textViewSex: TextView
    private lateinit var textViewDescription: TextView

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

        textViewName = findViewById(R.id.textViewAnimalName)
        textViewAviary = findViewById(R.id.textViewAviary)
        textViewZoo = findViewById(R.id.textViewZoo)
        textViewAge = findViewById(R.id.textViewAge)
        textViewHeight = findViewById(R.id.textViewHeight)
        textViewWeight = findViewById(R.id.textViewWeight)
        textViewTailLength = findViewById(R.id.textViewTailLength)
        textViewSex = findViewById(R.id.textViewSex)
        textViewDescription = findViewById(R.id.textViewDescription)

        findViewById<Button>(R.id.buttonEdit).setOnClickListener { buttonsAddOrEditListener(2) }
        findViewById<Button>(R.id.buttonDel).setOnClickListener { preparingBeforeDel(1) }
        findViewById<Button>(R.id.buttonBack).setOnClickListener { buttonBackListener() }
        findViewById<Button>(R.id.buttonDelAviary).setOnClickListener { preparingBeforeDel(2) }
        findViewById<Button>(R.id.buttonDelZoo).setOnClickListener { preparingBeforeDel(3) }

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
                override fun onItemLongClick(view: View, position: Int)
                {

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
                override fun onItemLongClick(view: View, position: Int)
                {

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
                    refresh()
                }
                override fun onItemLongClick(view: View, position: Int)
                {

                }
            }))

        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean
    {
        val id = item.itemId
        if (id == R.id.actionPlus)
        {
            buttonsAddOrEditListener(1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buttonsAddOrEditListener(action: Int)
    {
        val intent = Intent()
        intent.setClass(this, EditActivity::class.java)
        intent.putExtra("ACTION", action)
        intent.putExtra("INDEXA", currentAviaryIndex)
        intent.putExtra("INDEXZ", currentZooIndex)
        intent.putExtra("INDEX", currentAnimalIndex)
        if (action == 2)
        {
            val tempAnimal: Animal = zo.getAnimal(currentZooIndex,
                currentAviaryIndex, currentAnimalIndex)
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

    private fun preparingBeforeDel(action: Int)
    {
        currentDelAction = action
        val manager: FragmentManager = supportFragmentManager
        val myDialogFragment = MyDialogFragment()
        myDialogFragment.show(manager, "myDialog")
    }

    fun buttonDelListener()
    {
        if (/*currentStage == 3 &&*/ currentDelAction == 1)
        {
            var stageBack = 1
            if (zo.getNumOfAnimalsInTheAviaries(currentZooIndex)[currentAviaryIndex] == 1)
            {
                stageBack++
                if (zo.getNumOfAnimalsInTheZoos()[currentZooIndex] == 1)
                {
                    stageBack++
                }
            }
            zo.delAnimal(currentZooIndex, currentAviaryIndex, currentAnimalIndex)
            currentStage -= stageBack
            refresh()
        }
        if (currentDelAction == 2)
        {
            var stageBack = 2
            if (zo.getNumOfAnimalsInTheZoos().size == 1)
            {
                stageBack++
            }
            zo.delAviary(currentZooIndex, currentAviaryIndex)
            currentStage -= stageBack
            refresh()
        }
        if (currentDelAction == 3)
        {
            zo.delZoo(currentZooIndex)
            currentStage = 0
            refresh()
        }
    }

    private fun buttonBackListener()
    {
        if (currentStage > 0)
        {
            currentStage--
            refresh()
        }
    }

    override fun onBackPressed()
    {
        buttonBackListener()
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
            recyclerViewZoos.visibility = View.INVISIBLE
            recyclerViewAviaries.visibility = View.INVISIBLE
            recyclerViewAnimals.visibility = View.INVISIBLE
            layoutAnimalInfo.visibility = View.VISIBLE
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
        {
            var action = data?.getSerializableExtra("ACTION")
            action = action as Int
            if (action == 1 || action == 2)
            {
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
                val tempAnimal =
                    Animal(name, aviary, zoo, age, height, weight, tailLength, sex, description)
                if (action == 1)
                {
                    zo.addAnimal(tempAnimal)
                    refresh()
                }
                else
                {
                    val tempArrayList: ArrayList<Boolean> =
                        zo.editAnimal(indexZ, indexA, index, tempAnimal)
                    if (!tempArrayList[0])
                    {
                        var stageBack = 1
                        if (zo.getNumOfAnimalsInTheAviaries(currentZooIndex)[currentAviaryIndex] == 1)
                        {
                            stageBack++
                            if (zo.getNumOfAnimalsInTheZoos()[currentZooIndex] == 1
                                && !tempArrayList[1]
                            )
                            {
                                stageBack++
                            }
                        }
                        currentStage -= stageBack
                    }
                    refresh()
                }
            }
            else if (action == 3)
            {

            }
        }
    }
}