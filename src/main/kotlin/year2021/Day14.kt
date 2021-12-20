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
    var initialStr: String
    val inputEj14 = readInputAsString("d14.txt").let {
        initialStr = it[0]
        val arr = hashMapOf<Pair<Char, Char>, Char>()
        for (i in 2 until it.size)
        {
            val split = it[i].split(" -> ")
            arr[Pair(split[0][0], split[0][1])] = split[1][0]
        }
        arr
    }
    println(ej14P1(initialStr, inputEj14))
    println(ej14P1(initialStr, inputEj14, 40))
}

private fun ej14P1(initialStr: String, input: HashMap<Pair<Char, Char>, Char>, iterations: Int = 10): Long
{
    var counters = hashMapOf<Pair<Char, Char>, Long>()
    var iter = 0
    for (i in 0 until initialStr.length - 1)
    {
        val pair = Pair(initialStr[i], initialStr[i + 1])
        if (counters[pair] != null)
            counters[pair] = counters[pair]!! + 1
        else
            counters[pair] = 1
    }
    while (iter < iterations)
    {
        val aux = hashMapOf<Pair<Char, Char>, Long>()
        for (i in counters)
        {
            val pair1 = Pair(i.key.first, input[i.key]!!)
            val pair2 = Pair(input[i.key]!!, i.key.second)
            if (aux[pair1] != null)
                aux[pair1] = aux[pair1]!! + counters[i.key]!!
            else
                aux[pair1] = counters[i.key]!!
            if (aux[pair2] != null)
                aux[pair2] = aux[pair2]!! + counters[i.key]!!
            else
                aux[pair2] = counters[i.key]!!
        }
        counters = aux
        iter++
    }
    var max = -1L
    var min = Long.MAX_VALUE
    val auxC = hashMapOf<Char, Long>()
    for (i in counters)
    {
        if (auxC[i.key.first] != null)
            auxC[i.key.first] = auxC[i.key.first]!! + i.value
        else
            auxC[i.key.first] = i.value
    }
    auxC[initialStr[initialStr.length - 1]] = auxC[initialStr[initialStr.length - 1]]!! + 1
    for (i in auxC)
        if (i.value > max)
            max = i.value
        else if (i.value < min)
            min = i.value
    return max - min
}
