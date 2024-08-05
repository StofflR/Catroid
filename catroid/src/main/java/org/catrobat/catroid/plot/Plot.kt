/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2024 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.plot

import android.graphics.PointF

class Plot
{
    private var isPlotting = false
    private var dataPointLists = ArrayList<ArrayList<PointF>>()


    fun pause(){
        isPlotting = false;
    }

    fun resume(){
        isPlotting = true;
    }

    fun isPlotting(): Boolean {
        return isPlotting
    }

    fun startNewPlotLine(point : PointF){
        dataPointLists.add(arrayListOf(point))
    }

    fun addPoint(point : PointF){
        dataPointLists.last().add(point)
    }

    fun data() : List<List<PointF>>{
        return dataPointLists
    }
}