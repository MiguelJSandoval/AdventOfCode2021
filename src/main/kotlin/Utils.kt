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

import java.io.File
import java.io.IOException
import kotlin.system.exitProcess

/**
 * Advent of code 2021
 * https://adventofcode.com
 */

//Get path of project, and concat the package where is the file (need to be in the package 'inputs')
private val path = "${File("").absolutePath}/src/main/kotlin/inputs/"

fun readInputAsString(file: String): Array<String>
{
    val array = arrayListOf<String>()
    try
    {
        File("$path$file").forEachLine { array.add(it) }
    } catch (e: IOException)
    {
        println(e.message)
        exitProcess(-1)
    }
    return array.toTypedArray()
}

fun readInputAsNum(file: String, singleLine: Boolean = false): Array<Int>
{
    val array = arrayListOf<Int>()
    try
    {
        File("$path$file").forEachLine {
            if (singleLine)
                for (i in it.split(","))
                    array.add(i.toInt())
            else
                array.add(it.toInt())
        }
    } catch (e: IOException)
    {
        println(e.message)
        exitProcess(-1)
    }
    return array.toTypedArray()
}
