package AoC_Lib

trait BoxOps[A <: BoxPosOps[A], B <: BoxOps[A, B]] {
  val min: A
  val max: A

  require(min <= max)

  def factory: BoxFactory[A, B]

  def contains(pos: A): Boolean =
    min <= pos && pos <= max

  def union(that: B): B = {
    val unionMin = min min that.min
    val unionMax = max max that.max
    factory(unionMin, unionMax)
  }

  def intersect(that: B): Option[B] = {
    val intersectMin = min max that.min
    val intersectMax = max min that.max
    if (intersectMin <= intersectMax)
      Some(factory(intersectMin, intersectMax))
    else
      None
  }

  def iterator: Iterator[A]
}

trait BoxFactory[A <: BoxPosOps[A], B <: BoxOps[A, B]] {
  def apply(min: A, max: A): B

  def apply(pos: A): B = apply(pos, pos)

  def bounding(poss: IterableOnce[A]): B = {
    poss.iterator.map(apply).reduce(_ union _)
  }
}

case class Box(min: Pos, max: Pos) extends BoxOps[Pos, Box] {
  override def factory: BoxFactory[Pos, Box] = Box

  override def iterator: Iterator[Pos] = {
    for {
      x <- (min.x to max.x).iterator
      y <- (min.y to max.y).iterator
    } yield Pos(x, y)
  }
}

object Box extends BoxFactory[Pos, Box] {

}