package example

import aecor.macros.boopickleWireProtocol
import boopickle.Default._

import scala.language.higherKinds

@boopickleWireProtocol
trait Analysis[F[_]] {
  def createAnalysis(name: String, clientModel: String, dataSpecification: String): F[Unit]
  def addParameters(parameters: List[String]): F[Unit]
  def close: F[Unit]
}