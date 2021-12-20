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

private fun hexToBin(c: Char): String = when (c)
{
    '0' -> "0000"
    '1' -> "0001"
    '2' -> "0010"
    '3' -> "0011"
    '4' -> "0100"
    '5' -> "0101"
    '6' -> "0110"
    '7' -> "0111"
    '8' -> "1000"
    '9' -> "1001"
    'A' -> "1010"
    'B' -> "1011"
    'C' -> "1100"
    'D' -> "1101"
    'E' -> "1110"
    'F' -> "1111"
    else -> ""
}

fun main()
{
    val st = readInputAsString("d16.txt")[0]
    val input = st.let {
        val array = it.toCharArray()
        var rtn = ""
        for (i in array) rtn += hexToBin(i)
        rtn
    }
    println(ej16P1(input))
    println(ej16P2(input, 0).first)
}

private fun ej16P1(string: String): Int
{
    if (string == "" || binaryToDec(string).toLong() == 0L)
        return 0
    val versionSum = binaryToDec(string.substring(0, 3)).toInt()
    val id = binaryToDec(string.substring(3, 6)).toInt()
    if (id == 4)
    {
        var counter = 6
        var finished = false
        while (!finished)
        {
            if (string[counter] == '0')
                finished = true
            counter += 5
        }
        return versionSum + ej16P1(string.substring(counter, string.length))
    }
    val type = string.substring(6, 7).toInt()
    val length = 7 + if (type == 1) 11 else 15
    val number = binaryToDec(string.substring(7, length)).toInt()
    return if (type == 0) //The number of bits in sub-packets
    {
        val sub = string.substring(length, length + number)
        val sub2 = string.substring(length + number, string.length)
        versionSum + ej16P1(sub) + ej16P1(sub2)
    } else //The number of sub-packets
    {
        val sub = string.substring(length, string.length)
        versionSum + ej16P1(sub)
    }
}

private fun ej16P2(string: String, start: Int, end: Int = -1): Pair<Long, Int>
{
    if (start == end || start > string.length - 4)
        return Pair(-1, -1)
    val id = binaryToDec(string.substring(start + 3, start + 6)).toInt()
    if (id == 4)
    {
        var counter = start + 6
        var finished = false
        var number = ""
        while (!finished)
        {
            if (string[counter] == '0')
                finished = true
            number += string.substring(counter + 1, counter + 5)
            counter += 5
        }
        return Pair(binaryToDec(number).toLong(), counter)
    }
    val type = string.substring(start + 6, start + 7).toInt()
    val length = start + 7 + if (type == 1) 11 else 15
    val number = binaryToDec(string.substring(start + 7, length)).toInt()
    val packets = arrayListOf<Long>()
    val next: Int
    if (type == 0) //The number of bits in sub-packets
    {
        var counter = length
        var prev = -1
        while (counter != -1)
        {
            prev = counter
            val pair = ej16P2(string, counter, length + number)
            counter = pair.second
            packets.add(pair.first)
        }
        packets.removeAt(packets.size - 1)
        next = prev
    } else //The number of sub-packets
    {
        var rem = number
        var counter = length
        while (rem > 0)
        {
            val pair = ej16P2(string, counter)
            counter = pair.second
            rem--
            packets.add(pair.first)
        }
        next = counter
    }
    return Pair(handlePackets(id, packets), next)
}

private fun handlePackets(id: Int, packets: ArrayList<Long>): Long
{
    return when (id)
    {
        0 -> packets.let {
                var sum = 0L
                for (i in it)
                    sum += i
                sum
            }
        1 -> packets.let {
            var multi = 1L
            for (i in it)
                multi *= i
            multi
        }
        2 -> packets.let {
            var min = Long.MAX_VALUE
            for (i in it)
                if (i < min)
                    min = i
            min
        }
        3 -> packets.let {
            var max = Long.MIN_VALUE
            for (i in it)
                if (i > max)
                    max = i
            max
        }
        5 -> if (packets.size == 2 && packets[0] > packets[1]) 1 else 0
        6 -> if (packets.size == 2 && packets[0] < packets[1]) 1 else 0
        7 -> if (packets.size == 2 && packets[0] == packets[1]) 1 else 0
        else -> 0
    }
}
