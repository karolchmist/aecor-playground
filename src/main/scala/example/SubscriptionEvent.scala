package example


import aecor.runtime.akkapersistence.serialization._
import aecor.data.Enriched
import aecor.runtime.akkapersistence.serialization.{PersistentDecoder, PersistentEncoder}
//import aecor.example.persistentEncoderUtil
//import aecor.example.domain.EventMeta
//import io.circe.generic.auto._
//import io.circe.{ Decoder, Encoder }


sealed abstract class SubscriptionEvent extends Product with Serializable
object SubscriptionEvent {
  final case class SubscriptionCreated(userId: String, productId: String, planId: String) extends SubscriptionEvent
  final case object SubscriptionPaused extends SubscriptionEvent
  final case object SubscriptionResumed extends SubscriptionEvent
  final case object SubscriptionCancelled extends SubscriptionEvent

  implicit val persistentEncoder: PersistentEncoder[SubscriptionEvent] =
    persistentEncoderUtil.circePersistentEncoder(io.circe.generic.semiauto.deriveEncoder)

  implicit val persistentDecoder: PersistentDecoder[SubscriptionEvent] =
    persistentEncoderUtil.circePersistentDecoder(io.circe.generic.semiauto.deriveDecoder)

}