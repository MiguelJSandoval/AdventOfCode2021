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
    val input = readInputAsString("d18.txt").let {
        val rtn = arrayListOf<Any?>()
        for (i in it)
        {
            val array = arrayListOf<Any?>()
            for (j in i)
                array.add(j.toString())
            createEntry(array)
            rtn.add(array[0])
        }
        rtn
    }
    println(ej18P1(input))
    println(ej18P2(input))
}

private fun createEntry(line: ArrayList<Any?>)
{
    var adding = true
    var index = 0
    while (adding)
    {
        if (index < line.size && line.size > 1)
        {
            if (line[index] is String && line[index] == "[")
            {
                var next = false
                var indexNext = 0
                for (j in index + 1 until line.size)
                {
                    if (line[j] is String && line[j] != "[" || line[j] is ArrayList<*>)
                    {
                        if (line[j] == "]")
                        {
                            indexNext = j
                            next = true
                            break
                        }
                    } else
                        break
                }
                if (next)
                {
                    val node = arrayListOf<Any?>()
                    for (j in index + 1 until indexNext)
                        node.add(line[j])
                    set(line, createNode(node), index, indexNext)
                    if (line.size <= 1)
                        adding = false
                }
            }
            if (adding && line.size > 1)
                index++
            else
                adding = false
        } else if (index <= line.size)
        {
            if (line.size <= 1)
                adding = false
            else
                index = 0
        }
    }
}

private fun createNode(_node: ArrayList<Any?>): ArrayList<Any?>
{
    val rtn = arrayListOf<Any?>()
    var n1: Any? = _node[0]
    var n2: Any? = _node[2]
    if (_node[0] is String)
    {
        if (_node[1] is String && (_node[1] as String).matches(Regex("\\d+")))
        {
            n1 = "${n1 as String}${_node[1]}"
            if (_node[2] is String && (_node[2] as String) == ",")
            {
                n2 = _node[3]
                if (4 < _node.size && _node[4] is String && (_node[4] as String).matches(Regex("\\d+")))
                {
                    n2 = "${n2 as String}${_node[4]}"
                }
            }
        } else if (_node[1] is String && (_node[1] as String) == ",")
        {
            if (3 < _node.size && _node[3] is String && (_node[3] as String).matches(Regex("\\d+")))
            {
                n2 = "${n2 as String}${_node[3]}"
            }
        }
    } else if (_node[2] is String)
    {
        if (3 < _node.size && _node[3] is String && (_node[3] as String).matches(Regex("\\d+")))
        {
            n2 = "${n2 as String}${_node[3]}"
        }
    }
    rtn.add(n1)
    rtn.add(n2)
    return rtn
}

private fun set(_arr: ArrayList<Any?>, _node: ArrayList<Any?>, start: Int, end: Int)
{
    _arr[start] = _node
    for (i in start + 1..end)
        _arr[i] = null
    _arr.removeAll(arrayOfNulls(1))
}

private fun computeSum(line: Any?): Any?
{
    val pair = explode(line)
    if (pair.first)
    {
        ((line as ArrayList<Any?>)).clear()
        for (i in pair.second as ArrayList<Any?>)
            line.add(i)
        return computeSum(line)
    } else
    {
        val pair2 = split(line)
        return if (pair2.first)
        {
            ((line as ArrayList<Any?>)).clear()
            for (i in pair2.second as ArrayList<Any?>)
                line.add(i)
            computeSum(pair2.second)
        } else
            pair2.second
    }
}

private fun lineAsArray(st: String): ArrayList<Any?>
{
    val rtn = arrayListOf<Any?>()
    for (i in st)
        rtn.add(i.toString())
    return rtn
}

private fun explode(sumArray: Any?): Pair<Boolean, Any?>
{
    val line = lineAsArray(rebuildLine(sumArray))
    var depth = 0
    for ((i, v) in line.withIndex())
    {
        if (v == "[")
        {
            depth++
            if (depth == 5)
            {
                val left = line[i + 1] as String + line[i + 2] as String
                val right = line[i + 4] as String + line[i + 5] as String
                if (left.matches(Regex("\\d+")) && line[i + 3] == "," &&
                    right.matches(Regex("\\d+")))
                {
                    var leftA = -1
                    var rightA = -1
                    for ((i2, v2) in line.withIndex())
                    {
                        if ((v2 as String).matches(Regex("\\d+")) &&
                            (line[i2 + 1] as String).matches(Regex("\\d+")) && i2 < i)
                            leftA = i2
                        else if (rightA == -1 && v2.matches(Regex("\\d+")) && i2 > i + 5)
                            rightA = i2
                    }
                    if (rightA != -1 && rightA > i)
                    {
                        var rg = (line[rightA] as String + line[rightA + 1] as String).toInt()
                        rg += right.toInt()
                        val number = if (rg > 9) rg.toString() else "0$rg"
                        line[rightA] = number[0].toString()
                        line[rightA + 1] = number[1].toString()
                    }
                    if (leftA != -1)
                    {
                        var rg = (line[leftA] as String + line[leftA + 1] as String).toInt()
                        rg += left.toInt()
                        val number = if (rg > 9) rg.toString() else "0$rg"
                        line[leftA] = number[0].toString()
                        line[leftA + 1] = number[1].toString()
                    }
                    for ((idx, j) in line.withIndex())
                        if (j != null && (j as String) == "0" && (line[idx + 1] as String).matches(Regex("\\d+")))
                            line[idx] = null
                    for (j in i..i + 6)
                        line[j] = null
                    line[i] = "0"
                    line.removeAll(arrayOfNulls(1))
                    createEntry(line)
                    return Pair(true, line[0])
                }
            }
        } else if (v == "]")
            depth--
    }
    return Pair(false, sumArray)
}

private fun split(array: Any?): Pair<Boolean, Any?>
{
    val line = lineAsArray(rebuildLine(array))
    for ((i, v) in line.withIndex())
    {
        if (i < line.size - 1)
        {
            val num = v as String + line[i + 1] as String
            if (num.matches(Regex("\\d+")) && num.toInt() > 9)
            {
                val arr = arrayListOf<Any?>()
                for (j in 0 until i)
                    arr.add(line[j])
                arr.add("[")
                arr.add((num.toInt() / 2).toString())
                arr.add(",")
                arr.add(((num.toInt() + 1) / 2).toString())
                arr.add("]")
                for (j in i + 2 until line.size)
                    arr.add(line[j])
                createEntry(arr)
                return Pair(true, arr[0])
            }
        }
    }
    return Pair(false, array)
}

private fun rebuildLine(item: Any?): String
{
    return if (item is ArrayList<*>)
        "[${rebuildLine(item[0])},${rebuildLine(item[1])}]"
    else if ((item as String).length == 1)
        "0$item"
    else
        item
}

private fun getMagnitude(item: Any?): Int
{
    return if (item is ArrayList<*>)
        3 * getMagnitude(item[0]) + 2 * getMagnitude(item[1])
    else
        (item as String).toInt()
}

private fun ej18P1(input: ArrayList<Any?>): Int
{
    var index = 2
    var sumArray = computeSum(arrayListOf(input[0], input[1]))
    while (index < input.size)
    {
        val aux = sumArray
        sumArray = arrayListOf(aux, input[index])
        sumArray = computeSum(sumArray)
        index++
    }
    return getMagnitude(sumArray)
}

//Part two will take about 30s due to use of brute force.
private fun ej18P2(input: ArrayList<Any?>): Int
{
    var max = Int.MIN_VALUE
    for ((i, v1) in input.withIndex())
    {
        for ((j, v2) in input.withIndex())
        {
            if (j != i)
            {
                val arrSum1 = computeSum(arrayListOf(v1, v2))
                val arrSum2 = computeSum(arrayListOf(v2, v1))
                val m = Math.max(getMagnitude(arrSum1), getMagnitude(arrSum2))
                max = Math.max(max, m)
            }
        }
    }
    return max
}
