package example
import aecor.data.Action
import example.AnalysisState.Status.{Initialised, Finalized}

object AnalysisActions extends Analysis[Action[Option[AnalysisState], AnalysisEvent, ?]] {
  override def createAnalysis(name: String, clientModel: String, dataSpecification: String): Action[Option[AnalysisState], AnalysisEvent, Unit] =
    Action {
      case Some(_) =>
        List.empty -> ()
      case None =>
        List(AnalysisEvent.Created(name, clientModel, dataSpecification)) -> ()
    }
  override def addParameters(parameters: List[String]): Action[Option[AnalysisState], AnalysisEvent, Unit] =
    Action {
      case Some(state) if state.status == Initialised =>
        List(AnalysisEvent.ParametersAdded(parameters)) -> ()
      case _ =>
        List.empty -> ()
    }

  override def close: Action[Option[AnalysisState], AnalysisEvent, Unit] =
    Action {
      case Some(state) if state.status == Initialised =>
        List(AnalysisEvent.Finalized) -> ()
      case _ =>
        List.empty -> ()
    }
}