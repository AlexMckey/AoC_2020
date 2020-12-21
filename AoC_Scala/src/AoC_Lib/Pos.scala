package AoC_Lib

trait BoxPosOps[A <: BoxPosOps[A]] extends PosOps[A] {
  def <=(that: A): Boolean
  def min(that: A): A
  def max(that: A): A
}

trait PosOps[A <: PosOps[A]] {
  def +(that: A): A
  def *:(k: Int): A

  def unary_- : A = -1 *: this
  def -(that: A): A = this + (-that)

  def manhattanDistance(that: A): Int
}

trait PosFactory[A <: PosOps[A]] {
  val zero: A
}

case class Pos(x: Int, y: Int) extends BoxPosOps[Pos] {
  override def +(that: Pos): Pos =
    Pos(x + that.x, y + that.y)

  override def *:(k: Int): Pos =
    Pos(k * x, k * y)

  override def manhattanDistance(that: Pos): Int =
    (x - that.x).abs + (y - that.y).abs

  override def <=(that: Pos): Boolean =
    x <= that.x && y <= that.y

  override def min(that: Pos): Pos =
    Pos(x min that.x, y min that.y)

  override def max(that: Pos): Pos =
    Pos(x max that.x, y max that.y)
}

object Pos extends PosFactory[Pos] {
  override val zero: Pos = Pos(0, 0)

  val axisOffsets: Seq[Pos] = Seq(Pos(0, 1), Pos(-1, 0), Pos(1, 0), Pos(0, -1))
  val diagonalOffsets: Seq[Pos] = Seq(Pos(-1, 1), Pos(1, 1), Pos(-1, -1), Pos(1, -1))
  val allOffsets: Seq[Pos] = axisOffsets ++ diagonalOffsets
}