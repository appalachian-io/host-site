package io.appalachian.hostsite

import akka.Done
import akka.actor._
import akka.http.scaladsl._
import akka.stream.{ActorMaterializer, Materializer}
import java.nio.file.Paths
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Server {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("host-site")
    implicit val materializer: Materializer = ActorMaterializer()
    implicit val ec: ExecutionContext = system.dispatcher

    val cwd = Paths.get("").toAbsolutePath
    val settings = Settings(system)

    Http()
      .bindAndHandle(HttpServer.Route.route(cwd),
                     settings.bindHost,
                     settings.bindPort)
      .onComplete {
        case Success(binding) =>
          system.log.info("http: {}", binding)
          CoordinatedShutdown(system)
            .addTask(CoordinatedShutdown.PhaseServiceUnbind, "unbind") { () =>
              binding
                .terminate(hardDeadline = 10.seconds)
                .map(_ => Done)
            }
        case Failure(_) =>
          System.exit(1)
      }

  }
}
