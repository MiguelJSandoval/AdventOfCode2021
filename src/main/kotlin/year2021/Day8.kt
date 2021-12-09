/*
 * Copyright (c) 2021, DevMJS. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this code.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Please contact DevMJS on contact.devmjs@gmail.com if you need
 * additional information or have any questions.
 */

package year2021

import readInputAsString

fun main()
{
    val inputEj8 = conputeInput(readInputAsString("d8.txt"))
    println(ej8P1(inputEj8))
    println(ej8P2(inputEj8))
}

private fun conputeInput(input: Array<String>): Array<Pair<String, String>>
{
    val rtn = Array(input.size) { Pair("", "") }
    for ((idx, i) in input.withIndex())
    {
        val split = i.split(" | ")
        rtn[idx] = Pair(split[0], split[1])
    }
    return rtn
}

private fun ej8P1(inputEj8: Array<Pair<String, String>>): Long
{
    var coun = 0L
    for (i in inputEj8)
    {
        val split = i.second.split(" ")
        for (j in split)
            if (j.length == 2 || j.length == 4 || j.length == 3 || j.length == 7)
                coun++
    }
    return coun
}

private fun ej8P2(inputEj8: Array<Pair<String, String>>): Long
{
    val newInput = inputEj8.let {
        val arr = Array(it.size) { Pair(arrayOf<Pair<String, Int>>(), arrayOf<Pair<String, Int>>()) }
        for ((x, i) in it.withIndex())
        {
            val split1 = i.first.split(" ")
            val arr1 = Array(10) { Pair("", -1) }
            for ((idx, j) in split1.withIndex())
                when (j.length)
                {
                    2 -> arr1[idx] = Pair(j, 1)
                    4 -> arr1[idx] = Pair(j, 4)
                    3 -> arr1[idx] = Pair(j, 7)
                    7 -> arr1[idx] = Pair(j, 8)
                    else -> arr1[idx] = Pair(j, -1)
                }
            val split = i.second.split(" ")
            val arr2 = Array(4) { Pair("", -1) }
            for ((idx, j) in split.withIndex())
                when (j.length)
                {
                    2 -> arr2[idx] = Pair(j, 1)
                    4 -> arr2[idx] = Pair(j, 4)
                    3 -> arr2[idx] = Pair(j, 7)
                    7 -> arr2[idx] = Pair(j, 8)
                    else -> arr2[idx] = Pair(j, -1)
                }
            arr[x] = Pair(arr1, arr2)
        }
        arr
    }
    val adding = arrayListOf<String>()
    val combinations = arrayOf(arrayOf(0, 1, 2, 3, 4, 5), arrayOf(1, 2), arrayOf(0, 1, 4, 3, 6), arrayOf(0, 1, 2, 3, 6),
                               arrayOf(1, 2, 5, 6), arrayOf(0, 2, 3, 5, 6), arrayOf(0, 2, 3, 4, 5, 6), arrayOf(0, 1, 2),
                               arrayOf(0, 1, 2, 3, 4, 5, 6), arrayOf(0, 1, 2, 3, 5, 6))
    for (i in newInput)
    {
        val all = Array(7) { "" }
        val numbers = arrayListOf<Pair<String, Int>>()
        val n235 = arrayListOf<Pair<String, Int>>()
        for (j in i.first)
            if (j.second != -1)
                numbers.add(j)
            else if (j.first.length == 5)
                n235.add(j)
        var findTop = false
        var one = Pair("", 1)
        var three = Pair("", 3)
        var four = Pair("", 4)
        var seven = Pair("", 7)
        for (j in numbers)
        {
            for (k in numbers)
                if (j.second == 1 && k.second == 7)
                {
                    one = j
                    seven = k
                    val indexes = arrayListOf(0, 1, 2)
                    indexes.remove(k.first.indexOf(j.first[0]))
                    indexes.remove(k.first.indexOf(j.first[1]))
                    all[0] = k.first[indexes[0]].toString()
                    findTop = true
                    break
                }
            if (findTop)
                break
        }
        for (j in n235)
            if (j.first.contains(one.first[0]) && j.first.contains(one.first[1]))
            {
                three = Pair(j.first, 3)
                n235.remove(j)
                break
            }
        var noCommon = ""
        for (j in numbers)
        {
            if (j.second == 4)
            {
                four = j
                for (k in four.first)
                    if (!three.first.contains(k))
                    {
                        noCommon = k.toString()
                        all[5] = k.toString()
                    }
                break
            }
        }
        for (j in n235)
            if (j.first.contains(noCommon))
            {
                n235.remove(j)
                for (k in seven.first)
                    if (!j.first.contains(k))
                    {
                        all[1] = k.toString()
                        for (l in one.first)
                            if (l != k)
                                all[2] = l.toString()
                    }
                break
            }
        val two: Pair<String, Int> = Pair(n235[0].first, 2)
        for (j in two.first)
            if (!three.first.contains(j))
            {
                all[4] = j.toString()
                break
            }
        for (j in four.first)
        {
            val aux = j.toString()
            if (aux != all[1] && aux != all[2] && aux != all[5])
            {
                all[6] = aux
                break
            }
        }
        for (j in three.first)
        {
            val aux = j.toString()
            if (aux != all[0] && aux != all[1] && aux != all[2] && aux != all[6])
            {
                all[3] = aux
                break
            }
        }
        var add = ""
        for (j in i.second)
        {
            for ((idx, k) in combinations.withIndex())
            {
                var c = 0
                if (j.first.length == k.size)
                {

                    for (l in k)
                        if (j.first.contains(all[l]))
                            c++
                        else break
                }
                if (c == k.size)
                {
                    add += idx.toString()
                    break
                }
            }
        }
        adding.add(add)
    }
    var sumOfAll = 0L
    for (i in adding)
        sumOfAll += i.toLong()
    return sumOfAll
}
