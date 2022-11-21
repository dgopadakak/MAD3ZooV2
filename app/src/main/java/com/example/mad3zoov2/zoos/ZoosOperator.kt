package com.example.mad3zoov2.zoos

class ZoosOperator
{
    private var listOfZoos: ArrayList<Zoo> = ArrayList()

    fun getZoos(): ArrayList<Zoo>
    {
        return listOfZoos
    }

    fun getZoosNames(): ArrayList<String>
    {
        val tempListOfZoosNames: ArrayList<String> = ArrayList()
        for (i in listOfZoos)
        {
            tempListOfZoosNames.add(i.name)
        }
        return tempListOfZoosNames
    }

    fun getAviariesNames(indexOfZoo: Int): ArrayList<String>
    {
        val tempListOfAviariesNames: ArrayList<String> = ArrayList()
        for (i in listOfZoos[indexOfZoo].listOfAviaries)
        {
            tempListOfAviariesNames.add(i.name)
        }
        return tempListOfAviariesNames
    }

    fun getAnimalsNames(indexOfZoo: Int, indexOfAviary: Int): ArrayList<String>
    {
        val tempListOfAnimalsNames:ArrayList<String> = ArrayList()
        for (i in listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals)
        {
            tempListOfAnimalsNames.add(i.name)
        }
        return tempListOfAnimalsNames
    }

    fun getNumOfAnimalsInTheZoos(): ArrayList<Int>
    {
        val tempArrayListOfNum: ArrayList<Int> = ArrayList()
        for (i in listOfZoos)
        {
            var num = 0
            for (j in i.listOfAviaries)
            {
                num += j.listOfAnimals.size
            }
            tempArrayListOfNum.add(num)
        }
        return tempArrayListOfNum
    }

    fun getNumOfAnimalsInTheAviaries(indexOfZoo: Int): ArrayList<Int>
    {
        val tempArrayListOfNum: ArrayList<Int> = ArrayList()
        for (i in listOfZoos[indexOfZoo].listOfAviaries)
        {
            tempArrayListOfNum.add(i.listOfAnimals.size)
        }
        return tempArrayListOfNum
    }

    fun addAnimal(animal: Animal)
    {
        var oldZooIndex: Int = -1
        var oldAviaryIndex: Int = -1
        for (i in 0 until listOfZoos.size)
        {
            if (listOfZoos[i].name == animal.zoo)
            {
                oldZooIndex = i
            }
        }
        if (oldZooIndex != -1)
        {
            for (i in 0 until  listOfZoos[oldZooIndex].listOfAviaries.size)
            {
                if (listOfZoos[oldZooIndex].listOfAviaries[i].name == animal.aviary)
                {
                    oldAviaryIndex = i
                }
            }
            if (oldAviaryIndex != -1)
            {
                listOfZoos[oldZooIndex].listOfAviaries[oldAviaryIndex].listOfAnimals.add(animal)
            }
            else
            {
                listOfZoos[oldZooIndex].listOfAviaries.add(Aviary(animal.aviary, arrayListOf(animal)))
            }
        }
        else
        {
            listOfZoos.add(Zoo(animal.zoo, arrayListOf(Aviary(animal.aviary, arrayListOf(animal)))))
        }
    }

    fun delAnimal(indexOfZoo: Int, indexOfAviary: Int, indexOfAnimal: Int)
    {
        listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals.removeAt(indexOfAnimal)
        if (listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals.isEmpty())
        {
            listOfZoos[indexOfZoo].listOfAviaries.removeAt(indexOfAviary)
        }
        if (listOfZoos[indexOfZoo].listOfAviaries.isEmpty())
        {
            listOfZoos.removeAt(indexOfZoo)
        }
    }

    fun delAviary(indexOfZoo: Int, indexOfAviary: Int)
    {
        listOfZoos[indexOfZoo].listOfAviaries.removeAt(indexOfAviary)
        if (listOfZoos[indexOfZoo].listOfAviaries.isEmpty())
        {
            listOfZoos.removeAt(indexOfZoo)
        }
    }

    fun delZoo(indexOfZoo: Int)
    {
        listOfZoos.removeAt(indexOfZoo)
    }

    fun editAnimal(indexOfZoo: Int, indexOfAviary: Int, indexOfAnimal: Int, animal: Animal): Boolean
    {
        return if (listOfZoos[indexOfZoo].name == animal.zoo
            && listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].name == animal.aviary)
        {
            listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[indexOfAnimal] = animal
            true
        }
        else
        {
            this.delAnimal(indexOfZoo, indexOfAviary, indexOfAnimal)
            this.addAnimal(animal)
            false
        }
    }

    fun editAviary(indexOfZoo: Int, indexOfAviary: Int, name: String)
    {
        val aviarySize = listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals.size
        for (i in 0 until aviarySize)
        {
            val tempAnimal = Animal(
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].name, name,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].zoo,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].age,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].height,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].weight,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].tailLength,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].sex,
                listOfZoos[indexOfZoo].listOfAviaries[indexOfAviary].listOfAnimals[0].description)
            this.editAnimal(indexOfZoo, indexOfAviary, 0, tempAnimal)
        }
    }

    fun editZoo(indexOfZoo: Int, name: String)
    {
        val zooSize = listOfZoos[indexOfZoo].listOfAviaries.size
        for (i in 0 until zooSize)
        {
            val aviarySize = listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals.size
            for (j in 0 until aviarySize)
            {
                val tempAnimal = Animal(
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].name,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].aviary, name,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].age,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].height,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].weight,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].tailLength,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].sex,
                    listOfZoos[indexOfZoo].listOfAviaries[0].listOfAnimals[0].description
                )
                this.editAnimal(indexOfZoo, 0, 0, tempAnimal)
            }
        }
    }
}