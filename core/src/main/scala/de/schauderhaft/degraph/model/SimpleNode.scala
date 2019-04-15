package de.schauderhaft.degraph.model

/**
 * companion object providing factory methods for often needed kinds of Node instances.
 */
object SimpleNode {
    final val classType = "Class"
    final val packageType = "Package"
    def classNode(name: String) = SimpleNode(classType, name)
    def packageNode(name: String) = SimpleNode(packageType, name)
}

sealed trait Node {
    def contains(n: Node): Boolean
    def types: Set[String]
    var name: String
}

/**
 * represents a node in the dependency graph.
 */
case class SimpleNode(
    nodeType: String,
    var name: String) extends Node {

    def contains(n: Node) = (this == n)
    def types = Set(nodeType)
}

case class ParentAwareNode(vals: Node*) extends Node {
    def prune = if (vals.size == 1) vals.head else this
    def next = if (vals.size > 1) new ParentAwareNode(vals.tail: _*) else this
    def head = vals.head

    def contains(n: Node) = (vals.exists(_.contains(n)))
    def types = vals.flatMap(_.types).toSet
    var name = vals.map(_.name).mkString(" x ")
}
