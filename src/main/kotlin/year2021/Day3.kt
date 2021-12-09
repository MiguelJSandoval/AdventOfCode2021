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
import kotlin.math.pow

fun main()
{
    val inputEj3 = readInputAsString("d3.txt")
    println(ej3P1(inputEj3))
    println(ej3P2(inputEj3))
}

fun binaryToDec(binary: String): String
{
    var accum = 0L
    for ((index, value) in binary.reversed().withIndex())
        accum += value.toString().toInt() * (2.0.pow(index).toLong())
    return accum.toString()
}

private fun ej3P1(inputEj3: Array<String>): Long
{
    val newInputEj3 = inputEj3.let {
        val commons = Array(it[0].length) { Array(2) { 0 } }
        for (i in it)
        {
            for ((idx, j) in i.withIndex())
            {
                if (j == '0')
                    commons[idx][0] = commons[idx][0] + 1
                else
                    commons[idx][1] = commons[idx][1] + 1
            }
        }
        commons
    }
    var gamma = ""
    var epsilon = ""
    for (i in newInputEj3)
    {
        if (i[0] > i[1])
        {
            gamma += "0"
            epsilon += "1"
        } else
        {
            gamma += "1"
            epsilon += "0"
        }
    }
    val g = binaryToDec(gamma)
    val e = binaryToDec(epsilon)
    return g.toLong() * e.toLong()
}

private fun ej3P2(inputEj3: Array<String>): Long
{
    var oxygen = ""
    var co2 = ""
    var auxG = inputEj3
    var auxE = inputEj3
    for (i in 0 until inputEj3[0].length)
    {
        var contZeros = 0
        var contOnes = 0
        for (j in auxG)
        {
            if (j[i] == '0')
                contZeros++
            else
                contOnes++
        }
        var start = if (contOnes >= contZeros) '1' else '0'
        var aux1 = arrayListOf<String>()
        if (auxG.size > 1)
        {
            for (j in auxG)
            {
                if (j[i] == start)
                    aux1.add(j)
            }
            auxG = aux1.toTypedArray()
        }
        contZeros = 0
        contOnes = 0
        for (j in auxE)
        {
            if (j[i] == '0')
                contZeros++
            else
                contOnes++
        }
        start = if (contZeros <= contOnes) '0' else '1'
        aux1 = arrayListOf()
        if (auxE.size > 1)
        {
            for (j in auxE)
            {
                if (j[i] == start)
                    aux1.add(j)
            }
            auxE = aux1.toTypedArray()
        }
        oxygen = auxG[0]
        co2 = auxE[0]
    }
    val g = binaryToDec(oxygen)
    val e = binaryToDec(co2)
    return g.toLong() * e.toLong()
}
