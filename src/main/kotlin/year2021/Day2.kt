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
    val inputEj2 = computeInput(readInputAsString("d2.txt"))
    println(ej2P1(inputEj2))
    println(ej2P2(inputEj2))
}

private fun computeInput(it: Array<String>): Array<Pair<String, Int>>
{
    val rtn = arrayListOf<Pair<String, Int>>()
    for (i in it)
    {
        val split = i.split(" ")
        rtn.add(Pair(split[0], split[1].toInt()))
    }
    return rtn.toTypedArray()
}

private fun ej2P1(inputEj2: Array<Pair<String, Int>>): Long
{
    var depth = 0L
    var horizontal = 0L
    for (i in inputEj2)
    {
        when (i.first)
        {
            "forward" -> horizontal += i.second
            "down" -> depth += i.second
            "up" -> depth -= i.second
        }
    }
    return depth * horizontal
}

private fun ej2P2(inputEj2: Array<Pair<String, Int>>): Long
{
    var depth = 0L
    var horizontal = 0L
    var aim = 0L
    for (i in inputEj2)
    {
        when (i.first)
        {
            "forward" ->
            {
                horizontal += i.second
                depth += (aim * i.second)
            }
            "down" -> aim += i.second
            "up" -> aim -= i.second
        }
    }
    return depth * horizontal
}
