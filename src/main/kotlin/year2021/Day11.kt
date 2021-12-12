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
    val input = readInputAsString("d11.txt").let {
        val rtn = Array(10) { Array(10) { 0 } }
        for ((y, i) in it.withIndex())
            for ((x, j) in i.withIndex())
                rtn[y][x] = j.toString().toInt()
        rtn
    }
    println(ej11P1AndP2(input))
}

private fun ej11P1AndP2(input: Array<Array<Int>>): Pair<Long, Long>
{
    var highlighted = 0L
    var counter = 0L
    var counter2 = 0L
    while (true)
    {
        for (y in 0..9)
            for (x in 0..9)
                input[y][x] += 1
        var iterate = true
        val checked = arrayListOf<Pair<Int, Int>>()
        while (iterate)
        {
            var added = false
            for (y in 0..9)
            {
                for (x in 0..9)
                {
                    val pair = Pair(y, x)
                    if (input[y][x] > 9 && !checked.contains(pair))
                    {
                        checked.add(pair)
                        if (y - 1 >= 0)
                            input[y - 1][x] += 1
                        if (y + 1 < 10)
                            input[y + 1][x] += 1
                        if (x - 1 >= 0)
                            input[y][x - 1] += 1
                        if (x + 1 < 10)
                            input[y][x + 1] += 1
                        if (y - 1 >= 0 && x - 1 >= 0)
                            input[y - 1][x - 1] += 1
                        if (y - 1 >= 0 && x + 1 < 10)
                            input[y - 1][x + 1] += 1
                        if (y + 1 < 10 && x + 1 < 10)
                            input[y + 1][x + 1] += 1
                        if (y + 1 < 10 && x - 1 >= 0)
                            input[y + 1][x - 1] += 1
                        added = true
                    }
                    if (y == 9 && x == 9 && !added)
                        iterate = false
                }
            }
        }
        var ban = 0
        for (y in 0..9)
            for (x in 0..9)
                if (input[y][x] > 9)
                {
                    input[y][x] = 0
                    ban++
                    if (counter < 100)
                        highlighted++
                }
        counter2++
        counter++
        if (ban == 100)
            break
    }
    return Pair(highlighted, counter2)
}
