package example


import aecor.runtime.akkapersistence.serialization._
import aecor.data.Enriched
import aecor.runtime.akkapersistence.serialization.{PersistentDecoder, PersistentEncoder}
//import aecor.example.persistentEncoderUtil
//import aecor.example.domain.EventMeta
//import io.circe.generic.auto._
//import io.circe.{ Decoder, Encoder }


sealed abstract class AnalysisEvent extends Product with Serializable
object AnalysisEvent {
  final case class Created(name: String, clientModel: String, dataSpecification: String) extends AnalysisEvent
  final case class ParametersAdded(parameters: List[String]) extends AnalysisEvent
  final case object Finalized extends AnalysisEvent

  implicit val persistentEncoder: PersistentEncoder[AnalysisEvent] =
    persistentEncoderUtil.circePersistentEncoder(io.circe.generic.semiauto.deriveEncoder)

  implicit val persistentDecoder: PersistentDecoder[AnalysisEvent] =
    persistentEncoderUtil.circePersistentDecoder(io.circe.generic.semiauto.deriveDecoder)

}