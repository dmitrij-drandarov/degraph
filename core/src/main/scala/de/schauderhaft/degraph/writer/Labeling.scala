package de.schauderhaft.degraph.writer

import de.schauderhaft.degraph.analysis.asm.GraphBuildingClassVisitor
import de.schauderhaft.degraph.model.SimpleNode
import de.schauderhaft.degraph.model.ParentAwareNode

/**
 * creates a labels from an arbitrary node. It starts with the name of actual Nodes and to String if anything else ends up in the Graph.
 *
 * It then removes the part of the name that is identically with the label of the parent node if present.
 */
object Labeling {
    def apply(node: AnyRef, parent: Option[AnyRef] = None): String = {
        val parentLabel = parent match {
            case Some(p) =>
                apply(p)
            case _ =>
                baseLabel(node)
        }

        val nLabel = baseLabel(node)

        def removePrefix(label: String, delimiter: String) = {
            val prefix = parentLabel + delimiter
            if (prefix == label)
                label
            else if (label.startsWith(prefix))
                label.replace(prefix, "")
            else
                label
        }

        removePrefix(
            removePrefix(
                removePrefix(nLabel, "$$"),
                "$"),
            ".")
    }

    private def baseLabel(node: AnyRef): String = node match {
        case p: ParentAwareNode => baseLabel(p.head)
        case n: SimpleNode => {
            println(n.name)
            n.name + "(" + GraphBuildingClassVisitor.jarPaths.getOrElse(n.name, "") + ")"
        }
        case _ => node.toString
    }
}