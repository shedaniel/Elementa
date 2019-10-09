package club.sk1er.elementa.constraints

import club.sk1er.elementa.UIComponent

class FillConstraint @JvmOverloads constructor(private val value: Float = 0f) : SizeConstraint {
    override fun getXSize(component: UIComponent, parent: UIComponent): Float {
        return parent.getWidth() + value
    }

    override fun getYSize(component: UIComponent, parent: UIComponent): Float {
        return parent.getHeight() + value
    }
}