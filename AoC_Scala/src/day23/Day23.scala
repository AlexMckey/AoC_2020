package day23

import scala.math.Ordering.Implicits._
import scala.math.Integral.Implicits._
import scala.collection.BuildFrom
import scala.collection.generic.IsSeq
import scala.language.implicitConversions

import IntegralImplicits._
import IteratorImplicits._
import SeqImplicits._
import IntegralImplicits._
import LazyListImplicits._

import scala.collection.{AbstractIterator, immutable}

object IteratorImplicits {

  implicit class IndexIteratorOps[A](it: Iterator[A]) {
    def headOption: Option[A] = if (it.nonEmpty) Some(it.next()) else None
    def head: A = headOption.get

    def lastOption: Option[A] = it.reduceOption((_, x) => x)
    def last: A = lastOption.get

    def apply(i: Int): A = it.drop(i).head
  }

  implicit class ZipIteratorOps[A](it: Iterator[A]) {
    def zipWithTail: Iterator[(A, A)] = {
      if (it.hasNext) {
        // TODO: can be done with unfold for Iterator?
        new AbstractIterator[(A, A)] {
          private var prev: A = it.next()

          override def hasNext: Boolean = it.hasNext

          override def next(): (A, A) = {
            val cur = it.next()
            val ret = (prev, cur)
            prev = cur
            ret
          }
        }
      }
      else
        Iterator.empty
    }

    /*def zipTail: Iterator[(A, A)] = {
      it.sliding(2).map({ case Seq(a, b) => (a, b) }) // simpler but slower (by ~3s)
    }*/

    def zipWithPrev: Iterator[(Option[A], A)] = new AbstractIterator[(Option[A], A)] {
      private var prevOption: Option[A] = None

      override def hasNext: Boolean = it.hasNext

      override def next(): (Option[A], A) = {
        val cur = it.next()
        val ret = (prevOption, cur)
        prevOption = Some(cur)
        ret
      }
    }
  }

  implicit class GroupIteratorOps[A](it: Iterator[A]) {
    // TODO: Remove if Scala gets groupMapReduce on Iterator
    // Signature copied from Iterable
    def groupMapReduce[K, B](key: A => K)(f: A => B)(reduce: (B, B) => B): immutable.Map[K, B] = {
      it.to(LazyList).groupMapReduce(key)(f)(reduce)
    }
  }

  implicit class IteratorUnfoldOps(iterator: Iterator.type) {
    // copied from LazyListImplicits
    def unfold0[A](a: A)(f: A => Option[A]): Iterator[A] =
      Iterator.unfold(a)(a => f(a).map(a => (a, a)))
  }
}

object IterableImplicits {

  implicit class CycleIterableOps[A](coll: Iterable[A]) {
    def cycle: Iterator[A] = {
      // https://stackoverflow.com/a/2099896
      Iterator.continually(coll).flatten
    }
  }

  implicit class GroupIterableOps[A](coll: Iterable[A]) {
    def groupCount[K](key: A => K): Map[K, Int] = {
      coll.groupMapReduce(key)(_ => 1)(_ + _)
    }
  }
}

object IntegralImplicits {

  implicit class ExtraDivModIntegralOps[A: Integral](n: A) {
    def %+(d: A): A = (n % d + d) % d

    def /!(d: A): Option[A] = if (n % d == 0) Some(n / d) else None // /% returns both at once but calculates still separately...
  }

  implicit class ModPowIntegralOps[A](a: A)(implicit aIntegral: Integral[A]) {
    def modPow(n: A, m: A): A = {
      // vals for reused constants instead of using Integral defs
      val zero = aIntegral.zero
      val one = aIntegral.one
      val two = aIntegral.fromInt(2)

      // TODO: optimize using tailrec or loop
      def helper(a: A, n: A, m: A): A = {
        if (n equiv zero)
          one
        else {
          val (q, r) = n /% two
          val half = helper(a, q, m)
          val halfSquare = (half * half) % m
          if (r equiv zero)
            halfSquare
          else
            (a * halfSquare) % m
        }
      }

      helper(a, n, m)
    }
  }

  implicit class ExtraDivIntOps(n: Int) {
    def floorDiv(d: Int): Int = Math.floorDiv(n, d)
  }
}

object SeqImplicits {

  class RotateSeqOps[Repr, S <: IsSeq[Repr]](coll: Repr, seq: S) {
    def rotateLeft[That](n: Int)(implicit bf: BuildFrom[Repr, seq.A, That]): That = {
      val seqOps = seq(coll)
      val realN = n %+ seqOps.length
      val (init, tail) = seqOps.view.splitAt(realN)
      bf.fromSpecific(coll)(tail ++ init)
    }

    def rotateRight[That](n: Int)(implicit bf: BuildFrom[Repr, seq.A, That]): That =
      rotateLeft(-n)
  }

  // alternate more limited view-less implementation by OlegYch|h on freenode#scala
  /*class RotateOps[Repr, S <: IsSeq[Repr]](coll: Repr, seq: S) {
    def rotateLeft[That](n: Int)(implicit bf: BuildFrom[Repr, seq.A, That], seqIsRepr: seq.C =:= Repr): That = {
      val seqOps = seq(coll)
      val realN = n %+ seqOps.length
      val (init, tail) = seqOps.splitAt(realN)
      bf.fromSpecific(coll)(seq(tail) ++ seq(init))
    }
     def rotateRight[That](n: Int)(implicit bf: BuildFrom[Repr, seq.A, That], seqIsRepr: seq.C =:= Repr): That =
       rotateLeft(-n)
  }*/

  implicit def RotateSeqOps[Repr](coll: Repr)(implicit seq: IsSeq[Repr]): RotateSeqOps[Repr, seq.type] =
    new RotateSeqOps(coll, seq)
}
object LazyListImplicits {

  implicit class LazyListUnfoldOps(lazyList: LazyList.type) {
    // https://github.com/tpolecat/examples/blob/ab444af9101b9049d6bd7ebf13ae583bc77ac60a/src/main/scala/eg/Unfold.scala
    // converted to Scala 2.13 LazyList, unfold now standard (but with return arguments swapped)
    def unfold0[A](a: A)(f: A => Option[A]): LazyList[A] =
      LazyList.unfold(a)(a => f(a).map(a => (a, a)))
  }
}

object Day23 {

  type Cups = Vector[Int]

  case class State(next: Map[Int, Int], current: Int, max: Int) {

    def toCups(from: Int): LazyList[Int] = {
      from #:: LazyList.unfold0(from)(i => Some(next(i))).takeWhile(_ != from)
    }

    override def toString: String = toCups(current).toString
  }

  def simulateMove(state: State): State = {
    val State(next, current, max) = state
    val pick1 = next(current)
    val pick2 = next(pick1)
    val pick3 = next(pick2)

    val destination =
      Iterator.iterate(current)(i => (i - 1 - 1) %+ max + 1)
        .find(i => i != current && i != pick1 && i != pick2 && i != pick3)
        .get

    val newNext = next + (current -> next(pick3)) + (destination -> pick1) + (pick3 -> next(destination))
    State(newNext, newNext(current), max)
  }

  def simulateMoves(state: State, moves: Int = 100): State = {
    Iterator.iterate(state)(simulateMove)(moves)
  }

  def cups2State(cups: Cups): State = {
    val next =
      cups
        .iterator
        .zipWithTail
        .toMap
    val nextLoop = next + (cups.last -> cups.head)
    State(nextLoop, cups.head, cups.max)
  }

  def simulateMovesLabels(cups: Cups, moves: Int = 100): String = {
    val finalState = simulateMoves(cups2State(cups), moves)
    finalState.toCups(1).tail.mkString("")
  }

  def simulateMovesPart2(cups: Cups, moves: Int = 10000000): State = {
    val newCups = cups ++ ((cups.max + 1) to 1000000)
    simulateMoves(cups2State(newCups), moves)
  }

  def simulateMovesLabelsPart2(cups: Cups, moves: Int = 10000000): Long = {
    val finalState = simulateMovesPart2(cups, moves)
    finalState.toCups(1).tail.take(2).map(_.toLong).tapEach(println).product
  }


  def parseCups(input: String): Cups = input.map(_.asDigit).toVector

  //lazy val input: String = io.Source.fromInputStream(getClass.getResourceAsStream("day23.txt")).mkString.trim
  val input: String = "123487596"

  def main(args: Array[String]): Unit = {
    println(simulateMovesLabels(parseCups(input)))
    println(simulateMovesLabelsPart2(parseCups(input)))
  }
}