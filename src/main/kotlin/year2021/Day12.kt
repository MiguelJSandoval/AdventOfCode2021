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
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main()
{
    val inputEj12 = readInputAsString("d12.txt").let {
        val caves = hashMapOf<String, ArrayList<String>>()
        for (i in it)
        {
            val split = i.split('-')
            if (caves[split[0]] != null)
                caves[split[0]]?.add(split[1])
            else
                caves[split[0]] = arrayListOf(split[1])
            if (caves[split[1]] != null)
                caves[split[1]]?.add(split[0])
            else
                caves[split[1]] = arrayListOf(split[0])
        }
        caves
    }
    println(ej12P1O2(inputEj12, false))
    println(ej12P1O2(inputEj12, true))
}

private fun ej12P1O2(input: HashMap<String, ArrayList<String>>, part2: Boolean): Long
{
    var completeRoutes = 0L
    val start = Pair(arrayListOf("start"), "")
    val routes = arrayListOf(start)
    while (routes.size > 0)
    {
        val last = routes[0].first[routes[0].first.size - 1]
        val tail = routes[0].first
        val caveSmall = routes[0].second
        routes.removeAt(0)
        if (last == "end")
        {
            completeRoutes++
            continue
        }
        for (i in input[last]!!)
        {
            val isUpper = i.uppercase() == i
            val tailAux = arrayListOf<String>()
            tailAux.addAll(tail)
            tailAux.add(i)
            if (isUpper || !tail.contains(i))
                routes.add(Pair(tailAux, caveSmall))
            else if (part2 && caveSmall == "" && !arrayOf("start", "end").contains(i))
                routes.add(Pair(tailAux, i))
        }
    }
    return completeRoutes
}
