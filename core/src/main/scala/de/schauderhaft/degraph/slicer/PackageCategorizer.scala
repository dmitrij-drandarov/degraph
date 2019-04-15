package de.schauderhaft.degraph.slicer

import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.SimpleNode._
import de.schauderhaft.degraph.model.Node

/**
 * categorizes a java node as member of the matching package node
 */
object PackageCategorizer extends Function1[AnyRef, Node] {
    def apply(value: AnyRef) = {
        value match {
            case SimpleNode(t, n) if (t == classType) => packageNode(packagePart(n))
            case _ => value.asInstanceOf[Node]
        }
    }

    def packagePart(name: String) = {
        val lastDotIndex = name.lastIndexOf('.')
        if (lastDotIndex >= 0)
            name.substring(0, lastDotIndex)
        else
            name

//        val lastJarIndex = name.lastIndexOf("(")
//        var lastDotIndex = -1
//
//        if (lastJarIndex > 0)
//            lastDotIndex = name.substring(0, lastJarIndex).lastIndexOf('.')
//        else
//            lastDotIndex = name.lastIndexOf('.')
//
//        if (lastDotIndex >= 0 & lastJarIndex > 0)
//            name.substring(0, lastDotIndex) + " " + name.substring(lastJarIndex, name.length)
//        else if (lastDotIndex >= 0)
//            name.substring(0, lastDotIndex)
//        else
//            name
    }
}
