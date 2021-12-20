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

import kotlin.math.abs

fun main()
{
    val input = "target area: x=192..251, y=-89..-59".let {
        val split = it.replace("target area: ", "").split(", ")
        val rangesX = split[0].replace("x=", "").split("..")
        val rangesY = split[1].replace("y=", "").split("..")
        Pair(rangesX[0].toInt()..rangesX[1].toInt(), rangesY[0].toInt()..rangesY[1].toInt())
    }
    println(ej17P1AndP2(input))
}

private fun ej17P1AndP2(input: Pair<IntRange, IntRange>): Pair<Int, Int>
{
    var maxY = 0
    val positiveY = Math.max(abs(input.second.first), abs(input.second.last))
    val positiveX = Math.max(abs(input.first.first), abs(input.first.last))
    var possibles = 0
    for (i in 0..positiveX)
    {
        for (j in -positiveY..positiveY)
        {
            var velocityX = i
            var velocityY = j
            var x = 0
            var y = 0
            var maxYAux = 0
            var isInRange = false
            while (true)
            {
                if (velocityX > 0)
                {
                    x += velocityX
                    velocityX--
                }
                if (velocityY != 0)
                    y += velocityY
                else
                    maxYAux = y
                velocityY--
                if (y in input.second && x in input.first)
                {
                    isInRange = true
                    possibles++
                    break
                } else if (y < Math.min(input.second.first, input.second.last))
                    break
            }
            if (isInRange && maxYAux > maxY)
                maxY = maxYAux
        }
    }
    return Pair(maxY, possibles)
}
