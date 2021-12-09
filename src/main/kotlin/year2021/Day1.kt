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
    val inputEj1 = readInputAsNum("d1.txt")
    println(ej1P1(inputEj1))
    println(ej1P2(inputEj1))
}

private fun ej1P1(inputEj1: Array<Int>): Int
{
    var last = inputEj1[0]
    var counter = 0
    for (i in 1 until inputEj1.size)
    {
        if (inputEj1[i] > last)
            counter ++
        last = inputEj1[i]
    }
    return counter
}

private fun ej1P2(inputEj1: Array<Int>): Int
{
    val newInput = inputEj1.let {
        val sums = arrayListOf<Int>()
        var index = 1
        while (index + 1 < it.size)
        {
            val item = it[index - 1] + it[index] + it[index + 1]
            sums.add(item)
            index ++
        }
        sums.toTypedArray()
    }
    return ej1P1(newInput)
}
