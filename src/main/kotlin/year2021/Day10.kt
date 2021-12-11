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
    val inputEj10 = readInputAsString("d10.txt")
    println(ej10P1AndP2(inputEj10))
}

private fun ej10P1AndP2(input: Array<String>): Pair<Long, Long>
{
    val pairs = hashMapOf(Pair('(', ')'), Pair('{', '}'), Pair('[', ']'), Pair('<', '>'))
    val pointsIllegalChars = hashMapOf(Pair(')', 3L),
                                       Pair(']', 57L),
                                       Pair('}', 1197L),
                                       Pair('>', 25137L))
    val pointsCompletionChars = hashMapOf(Pair(')', 1L),
                                          Pair(']', 2L),
                                          Pair('}', 3L),
                                          Pair('>', 4L))
    var sum = 0L
    val indexes = arrayListOf<Int>()
    indexes.addAll(input.indices.toList())
    for ((idx, i) in input.withIndex())
    {
        val stack = arrayListOf<Char>()
        for (j in i)
        {
            if ("([{<".contains(j))
                stack.add(j)
            else if (")]}>".contains(j))
            {
                val last = stack[stack.size - 1]
                if (j == pairs[last]!!)
                {
                    stack.removeAt(stack.size - 1)
                } else
                {
                    indexes.remove(idx)
                    sum += pointsIllegalChars[j]!!
                    break
                }
            }
        }
    }
    val scores = arrayListOf<Long>()
    for (i in indexes)
    {
        val stack = arrayListOf<Char>()
        for (j in input[i])
        {
            if ("([{<".contains(j))
                stack.add(j)
            else if (")]}>".contains(j))
                stack.removeAt(stack.size - 1)
        }
        var t = 0L
        while (stack.size > 0)
        {
            t *= 5
            t += pointsCompletionChars[pairs[stack[stack.size - 1]]]!!
            stack.removeAt(stack.size - 1)
        }
        scores.add(t)
    }
    scores.sort()
    return Pair(sum, scores[scores.size / 2])
}
