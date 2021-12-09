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

import readInputAsNum

fun main()
{
    val inputEj7 = readInputAsNum("d7.txt", true)
    println(ej7P1(inputEj7))
    println(ej7P2(inputEj7))
}

private fun ej7P1(inputEj7: Array<Int>): Long
{
    val costes = arrayListOf<Long>()
    val map = inputEj7.let {
        val map = sortedMapOf<Int, Int>()
        for (i in it)
            if (map[i] == null) map[i] = 1
            else map[i] = map[i]!! + 1
        map
    }

    for (i in map.keys)
    {
        var actualCost = 0L
        for (j in map.keys)
        {
            if (j != i)
            {
                val min = Math.min(i, j)
                val max = Math.max(i, j)
                actualCost += (max - min) * map[j]!!
            }
        }
        costes.add(actualCost)
    }
    costes.sort()
    return costes[0]
}

private fun ej7P2(inputEj7: Array<Int>): Long
{
    val costes = arrayListOf<Long>()
    var minVal = Int.MAX_VALUE
    var maxVal = -1
    val map = inputEj7.let {
        val map = hashMapOf<Int, Int>()
        for (i in it)
        {
            if (map[i] == null) map[i] = 1
            else map[i] = map[i]!! + 1
            if (i < minVal) minVal = i
            if (i > maxVal) maxVal = i
        }
        map
    }
    for (i in minVal..maxVal)
    {
        var actualCost = 0L
        for (j in map.keys)
        {
            val min = Math.min(i, j)
            val max = Math.max(i, j)
            var totalCost = 0
            var cost = 1
            for (k in min until max)
            {
                totalCost += cost
                cost++
            }
            actualCost += (totalCost * map[j]!!)
        }
        costes.add(actualCost)
    }
    costes.sort()
    return costes[0]
}
