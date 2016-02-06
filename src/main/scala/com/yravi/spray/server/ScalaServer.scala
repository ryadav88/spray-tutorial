package com.yravi.spray.server

import akka.actor.ActorSystem
import com.yravi.spray.{MultiCrystalSilicon, Silicon}
import spray.http.MediaTypes
import spray.routing.{Route, SimpleRoutingApp}

object ScalaServer extends App with SimpleRoutingApp {
    implicit val actorSystem = ActorSystem()

    //changed val to var for now.
    var plentyOfSilicon = Silicon.silicons


    def getJson(route: Route): Route = {
    get{
        respondWithMediaType(MediaTypes.`application/json`) {
          route
        }
      }
    }



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
      } ~ // ~ operator takes 2 routes and combines them. tries to feed context in 1st route, otherwise 2nd
      getJson {
        path("list" / "all"){
            complete{
              Silicon.toJson(plentyOfSilicon)
            }
        }
      } ~
      get{
        path("silicon" / IntNumber / "details") { index =>
          complete{
            Silicon.toJson(plentyOfSilicon(index))
          }
        }
      } ~
      post{
        path("silicon" / "add"){
          // ?? means it is optional
          parameters("name"?, "grainSize".as[Int]) { (name, grainSize) =>
            val newSilicon = MultiCrystalSilicon(
              name.getOrElse("Microcrystalline"),
              grainSize)

            plentyOfSilicon = newSilicon :: plentyOfSilicon

            complete{
                "OK"
            }
          }
        }
      }

    }




  }
