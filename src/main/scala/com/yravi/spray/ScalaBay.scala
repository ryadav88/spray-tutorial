package com.yravi.spray

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object ScalaBay extends App with SimpleRoutingApp {
  implicit val actorSystem = ActorSystem()

  // starts a number of actors defined by spray
  // handle request routing and completing response
  // define a route, type Route = RequestContext => unit.
  // routes are built using Directives - build routes from other routes. Here 'get' is a directive. Similarly path.
  startServer(interface = "localhost", port = 8080){
    get{
      path("hello"){
        // Context can also be written as,  ctx => ctx.complete("Welcome to Silicon Valley!")
        complete{
          "Welcome to Silicon Valley!"
        }
      }
    }
  }

}
