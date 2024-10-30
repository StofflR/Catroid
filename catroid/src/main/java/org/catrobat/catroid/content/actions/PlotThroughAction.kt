/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2022 The Catrobat Team
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
package org.catrobat.catroid.content.actions

import android.util.Log
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
import org.catrobat.catroid.content.Scope
import org.catrobat.catroid.content.bricks.PlotArcBrick
import org.catrobat.catroid.formulaeditor.Formula
import org.catrobat.catroid.formulaeditor.InterpretationException
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class PlotThroughAction : TemporalAction() {
    private var scope: Scope? = null
    private var x1: Formula? = null
    private var y1: Formula? = null
    private var x2: Formula? = null
    private var y2: Formula? = null

    private var x1Value: Int = 0
    private var y1Value: Int = 0
    private var x2Value: Int = 0
    private var y2Value: Int = 0

    override fun begin() {
        super.begin()
        if (scope == null) {
            return
        }
        try {
            x1Value = if (x1 == null) 0 else x1!!.interpretInteger(scope)
            y1Value = if (y1 == null) 0 else y1!!.interpretInteger(scope)
            x2Value = if (x2 == null) 0 else x2!!.interpretInteger(scope)
            y2Value = if (y2 == null) 0 else y2!!.interpretInteger(scope)
        } catch (interpretationException: InterpretationException) {
            Log.d(
                javaClass.simpleName,
                "Formula interpretation for this specific Brick failed.",
                interpretationException
            )
        }
    }

    override fun update(percent: Float) {
        try {
            // calculate slope from current sprite look position to x2, y2
            val spriteX = scope!!.sprite.look.xInUserInterfaceDimensionUnit
            val spriteY = scope!!.sprite.look.yInUserInterfaceDimensionUnit

            val steps = 100
            for (i in 0..steps) {
                val t = i.toDouble() / steps
                val x = (1 - t).pow(2) * spriteX + 2 * (1 - t) * t * x1Value + t.pow(2) * x2Value
                val y = (1 - t).pow(2) * spriteY + 2 * (1 - t) * t * y1Value + t.pow(2) * y2Value
                scope!!.sprite.look.setPositionInUserInterfaceDimensionUnit(x.toFloat(), y.toFloat())
            }
        } catch (interpretationException: InterpretationException) {
            Log.d(
                javaClass.simpleName,
                "Formula interpretation for this specific Brick failed.",
                interpretationException
            )
        }
    }

    fun setScope(scope: Scope?) {
        this.scope = scope
    }

    fun setTargetCoordinates(x1: Formula?, y1: Formula?, x2: Formula?, y2: Formula?) {
        this.x1 = x1
        this.y1 = y1
        this.x2 = x2
        this.y2 = y2
    }
}