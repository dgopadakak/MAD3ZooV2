package com.example.mad3zoov2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.mad3zoov2.dialogFragments.MyDialogFragmentDelMany
import com.example.mad3zoov2.dialogFragments.MyDialogFragmentDelOne
import com.example.mad3zoov2.forRecyclerViews.CustomRecyclerAdapterForAnimals
import com.example.mad3zoov2.forRecyclerViews.CustomRecyclerAdapterForAviaries
import com.example.mad3zoov2.forRecyclerViews.CustomRecyclerAdapterForZoos
import com.example.mad3zoov2.forRecyclerViews.RecyclerItemClickListener
import com.example.mad3zoov2.zoos.Animal
import com.example.mad3zoov2.zoos.ZoosOperator
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class MainActivity : AppCompatActivity()
{
    private var zo: ZoosOperator = ZoosOperator()

    private val gsonBuilder = GsonBuilder()
    private val gson: Gson = gsonBuilder.create()

    private var zoJSON: String = ""

    private val appPreferences = "mysettings"
    private val appPreferencesZo = "zo"

    private lateinit var mSettings: SharedPreferences

    private var currentZooIndex: Int = -1
    private var currentAviaryIndex: Int = -1
    private var currentAnimalIndex: Int = -1

    private val highlightedItemsForCurrentRecyclerView: ArrayList<Int> = ArrayList()

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


//        zo.addAnimal(Animal(
//            "Лео",
//            "Вольер №1",
//            "Сафари-парк",
//            7,
//            150.5,
//            61.3,
//            100.5,
//            true,
//            "Очень красивый лев"
//        ))
//        zo.addAnimal(Animal(
//            "Тигря",
//            "Вольер №2",
//            "Сафари-парк",
//            9,
//            130.5,
//            50.3,
//            110.5,
//            true,
//            "Очень красивый тигр"
//        ))
//        zo.addAnimal(Animal(
//            "Тигря2",
//            "Вольер №3",
//            "Сафари-парк",
//            9,
//            130.5,
//            50.3,
//            110.5,
//            true,
//            "Очень красивый тигр"
//        ))
//        zo.addAnimal(Animal(
//            "Хитруня",
//            "Вольер для енотов",
//            "Городской зоопарк",
//            3,
//            30.0,
//            11.8,
//            29.0,
//            false,
//            "Очень хитрая енотиха"
//        ))

        mSettings = getSharedPreferences(appPreferences, Context.MODE_PRIVATE)

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
        findViewById<Button>(R.id.buttonDel).setOnClickListener { preparingBeforeDelOne(1) }
        findViewById<Button>(R.id.buttonBack).setOnClickListener { buttonBackListener() }
        findViewById<Button>(R.id.buttonDelAviary).setOnClickListener { preparingBeforeDelOne(2) }
        findViewById<Button>(R.id.buttonDelZoo).setOnClickListener { preparingBeforeDelOne(3) }

        recyclerViewZoos.addOnItemTouchListener(
            RecyclerItemClickListener(
            recyclerViewZoos,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.isEmpty())
                    {
                        currentZooIndex = position
                        currentStage = 1
                    }
                    else
                    {
                        if (highlightedItemsForCurrentRecyclerView.contains(position))
                        {
                            highlightedItemsForCurrentRecyclerView.remove(position)
                        }
                        else
                        {
                            highlightedItemsForCurrentRecyclerView.add(position)
                        }
                    }
                    refresh()
                }
                override fun onItemLongClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.contains(position))
                    {
                        highlightedItemsForCurrentRecyclerView.remove(position)
                    }
                    else
                    {
                        highlightedItemsForCurrentRecyclerView.add(position)
                    }
                    refresh()
                }
            })
        )

        recyclerViewAviaries.addOnItemTouchListener(
            RecyclerItemClickListener(
            recyclerViewAviaries,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.isEmpty())
                    {
                        currentAviaryIndex = position
                        currentStage = 2
                    }
                    else
                    {
                        if (highlightedItemsForCurrentRecyclerView.contains(position))
                        {
                            highlightedItemsForCurrentRecyclerView.remove(position)
                        }
                        else
                        {
                            highlightedItemsForCurrentRecyclerView.add(position)
                        }
                    }
                    refresh()
                }
                override fun onItemLongClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.contains(position))
                    {
                        highlightedItemsForCurrentRecyclerView.remove(position)
                    }
                    else
                    {
                        highlightedItemsForCurrentRecyclerView.add(position)
                    }
                    refresh()
                }
            })
        )

        recyclerViewAnimals.addOnItemTouchListener(
            RecyclerItemClickListener(
            recyclerViewAnimals,
            object : RecyclerItemClickListener.OnItemClickListener
            {
                override fun onItemClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.isEmpty())
                    {
                        currentAnimalIndex = position
                        currentStage = 3
                        refresh()
                    }
                    else
                    {
                        if (highlightedItemsForCurrentRecyclerView.contains(position))
                        {
                            highlightedItemsForCurrentRecyclerView.remove(position)
                        }
                        else
                        {
                            highlightedItemsForCurrentRecyclerView.add(position)
                        }
                    }
                    refresh()
                }
                override fun onItemLongClick(view: View, position: Int)
                {
                    if (highlightedItemsForCurrentRecyclerView.contains(position))
                    {
                        highlightedItemsForCurrentRecyclerView.remove(position)
                    }
                    else
                    {
                        highlightedItemsForCurrentRecyclerView.add(position)
                    }
                    refresh()
                }
            })
        )

        if (mSettings.contains(appPreferencesZo))
        {
            zoJSON = mSettings.getString(appPreferencesZo, "").toString()
            zo = gson.fromJson(zoJSON, ZoosOperator::class.java)
        }

        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu):Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        if (highlightedItemsForCurrentRecyclerView.isEmpty())
        {
            menu.getItem(1).isEnabled = false
            menu.getItem(2).isEnabled = false
        }
        else
        {
            menu.getItem(1).isEnabled = highlightedItemsForCurrentRecyclerView.size == 1
            menu.getItem(2).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean
    {
        val id = item.itemId
        if (id == R.id.actionPlus)
        {
            buttonsAddOrEditListener(1)
        }
        else if (id == R.id.actionDel)
        {
            delManyAnimalsPreparing()
        }
        else if (id == R.id.actionEdit)
        {
            if (currentStage < 2)
            {
                val intent = Intent()
                intent.setClass(this, EditActivityForItems::class.java)
                intent.putExtra("STAGE", currentStage)
                if (currentStage == 0)
                {
                    intent.putExtra("INDEXZ", highlightedItemsForCurrentRecyclerView[0])
                    intent.putExtra("INDEX", -1)
                }
                else
                {
                    intent.putExtra("INDEXZ", currentZooIndex)
                    intent.putExtra("INDEX", highlightedItemsForCurrentRecyclerView[0])
                }
                if (currentStage == 0)
                {
                    intent.putExtra("NAME", zo.getZoosNames()[highlightedItemsForCurrentRecyclerView[0]])
                }
                else
                {
                    intent.putExtra("NAME", zo.getAviariesNames(currentZooIndex)[highlightedItemsForCurrentRecyclerView[0]])
                }
                startActivityForResult(intent, 1)
            }
            else
            {
                val intent = Intent()
                intent.setClass(this, EditActivity::class.java)
                intent.putExtra("ACTION", 4)
                intent.putExtra("INDEXA", currentAviaryIndex)
                intent.putExtra("INDEXZ", currentZooIndex)
                intent.putExtra("INDEX", highlightedItemsForCurrentRecyclerView[0])
                val tempAnimal: Animal = zo.getAnimal(currentZooIndex,
                    currentAviaryIndex, highlightedItemsForCurrentRecyclerView[0])
                intent.putExtra("NAME", tempAnimal.name)
                intent.putExtra("AVIARY", tempAnimal.aviary)
                intent.putExtra("ZOO", tempAnimal.zoo)
                intent.putExtra("AGE", tempAnimal.age)
                intent.putExtra("HEIGHT", tempAnimal.height)
                intent.putExtra("WEIGHT", tempAnimal.weight)
                intent.putExtra("TAIL", tempAnimal.tailLength)
                intent.putExtra("SEX", tempAnimal.sex)
                intent.putExtra("DESC", tempAnimal.description)
                startActivityForResult(intent, 1)
            }
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

    private fun delManyAnimalsPreparing()
    {
        val manager: FragmentManager = supportFragmentManager
        val myDialogFragmentDelMany = MyDialogFragmentDelMany()
        myDialogFragmentDelMany.show(manager, "myDialog")
    }

    fun delManyAnimals()
    {
        currentDelAction = when (currentStage)
        {
            0 -> 3
            1 -> 5
            else -> 4
        }
        var iBack = 0
        for (i in highlightedItemsForCurrentRecyclerView)
        {
            /**
            when (currentStage)
            {
                0 -> {
                    currentZooIndex = i - iBack
                    iBack++
                }
                1 -> {
                    currentAviaryIndex = i - iBack
                    iBack++
                }
                else -> {
                    currentAnimalIndex = i - iBack
                    iBack++
                }
            }
            */

            if (currentStage == 0)
            {
                currentZooIndex = i - iBack
                iBack++
            }
            else if (currentStage == 1)
            {
                currentAviaryIndex = i - iBack
                iBack++
            }
            else
            {
                currentAnimalIndex = i - iBack
                iBack++
            }
            buttonDelListener()
        }
        highlightedItemsForCurrentRecyclerView.clear()
    }

    private fun preparingBeforeDelOne(action: Int)
    {
        currentDelAction = action
        val manager: FragmentManager = supportFragmentManager
        val myDialogFragmentDelOne = MyDialogFragmentDelOne()
        myDialogFragmentDelOne.show(manager, "myDialog")
    }

    fun buttonDelListener()
    {
        if (currentDelAction == 1 || currentDelAction == 4)      // Удаление животного
        {
            var stageBack: Int = if (currentDelAction == 1) {
                1
            } else {
                0
            }
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
        if (currentDelAction == 2 || currentDelAction == 5)      // Удаление вольера
        {
            var stageBack: Int = if (currentDelAction == 2) {
                2
            } else {
                0
            }
            if (zo.getNumOfAnimalsInTheZoos()[currentZooIndex] == 1)
            {
                stageBack++
            }
            zo.delAviary(currentZooIndex, currentAviaryIndex)
            currentStage -= stageBack
            refresh()
        }
        if (currentDelAction == 3)      // Удаление зоопарка
        {
            zo.delZoo(currentZooIndex)
            currentStage = 0
            refresh()
        }
    }

    private fun buttonBackListener()
    {
        if (highlightedItemsForCurrentRecyclerView.isNotEmpty())
        {
            highlightedItemsForCurrentRecyclerView.clear()
            refresh()
        }
        else if (currentStage > 0)
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
            recyclerViewZoos.adapter = CustomRecyclerAdapterForZoos(zo.getZoosNames(),
                zo.getNumOfAnimalsInTheZoos(), highlightedItemsForCurrentRecyclerView)
            recyclerViewZoos.visibility = View.VISIBLE
            recyclerViewAviaries.visibility = View.INVISIBLE
            recyclerViewAnimals.visibility = View.INVISIBLE
            layoutAnimalInfo.visibility = View.INVISIBLE
        }
        if (currentStage == 1)
        {
            recyclerViewAviaries.adapter = CustomRecyclerAdapterForAviaries(
                zo.getAviariesNames(currentZooIndex),
                zo.getNumOfAnimalsInTheAviaries(currentZooIndex),
                highlightedItemsForCurrentRecyclerView)
            recyclerViewZoos.visibility = View.INVISIBLE
            recyclerViewAviaries.visibility = View.VISIBLE
            recyclerViewAnimals.visibility = View.INVISIBLE
            layoutAnimalInfo.visibility = View.INVISIBLE
        }
        if (currentStage == 2)
        {
            recyclerViewAnimals.adapter = CustomRecyclerAdapterForAnimals(
                zo.getAnimalsNames(currentZooIndex, currentAviaryIndex),
                highlightedItemsForCurrentRecyclerView)
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
        invalidateOptionsMenu()

        zoJSON = gson.toJson(zo)
        val editor: SharedPreferences.Editor = mSettings.edit()
        editor.putString(appPreferencesZo, zoJSON)
        editor.apply()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK)
        {
            var action = data?.getSerializableExtra("ACTION")
            action = action as Int
            if (action == 1 || action == 2 || action == 4)
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
                    val numOfAviaries = zo.getNumOfAnimalsInTheAviaries(currentZooIndex).size
                    val numOfZoos = zo.getNumOfAnimalsInTheZoos().size
                    val tempArrayList: ArrayList<Boolean> =
                        zo.editAnimal(indexZ, indexA, index, tempAnimal)
                    if (!tempArrayList[0])
                    {
                        currentStage = if (numOfZoos > zo.getNumOfAnimalsInTheZoos().size) {
                            0
                        } else {
                            if (numOfAviaries > zo.getNumOfAnimalsInTheAviaries(currentZooIndex).size) {
                                1
                            } else {
                                2
                            }
                        }
                    }
                    highlightedItemsForCurrentRecyclerView.clear()
                    refresh()
                }
            }
            else if (action == 3)
            {
                val stage = data?.getSerializableExtra("STAGE") as Int
                val indexZ = data.getSerializableExtra("INDEXZ") as Int
                val index = data.getSerializableExtra("INDEX") as Int
                val name = data.getSerializableExtra("NAME") as String
                if (stage == 0)
                {
                    zo.editZoo(indexZ, name)
                }
                else
                {
                    zo.editAviary(indexZ, index, name)
                }
                highlightedItemsForCurrentRecyclerView.clear()
                refresh()
            }
        }
    }
}