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
    val inputEj6 = arrayListOf<Int>()
    inputEj6.addAll(readInputAsNum("d6.txt", true))
    println(ej6P1(inputEj6))
    println(ej6P2(inputEj6))
}

private fun ej6P1(inputEj6: ArrayList<Int>): Long
{
    val aux = inputEj6.clone() as ArrayList<Int>
    for (i in 1..80)
    {
        val new = arrayListOf<Int>()
        for (j in aux.indices)
        {
            if (aux[j] == 0)
            {
                aux[j] = 6
                new.add(8)
            } else
                aux[j] = aux[j] - 1
        }
        aux.addAll(new)
    }
    return aux.size.toLong()
}

private fun ej6P2(inputEj6: ArrayList<Int>): Long
{
    val map = inputEj6.let {
        val map = hashMapOf<Int, Long>()
        for (i in 0..8)
            map[i] = 0
        for (i in it)
            map[i] = map[i]!! + 1
        map
    }
    for (i in 1..256)
    {
        val auxs = Array(9) { 0L }
        val auxs2 = Array(9) { 0L }
        for (j in map.keys)
        {
            auxs[j] = map[j]!!
            auxs2[j] = map[j]!!
        }
        var news = 0L
        for (j in map.keys)
        {
            if (j == 0)
                news = auxs2[0]
            else
                auxs2[j - 1] = auxs[j]
        }
        map.clear()
        auxs2[8] = news
        auxs2[6] += news
        for ((idx, j) in auxs2.withIndex())
            map[idx] = j
    }
    return map.let {
        var sum = 0L
        for (i in it)
            sum += i.value
        sum
    }
}
