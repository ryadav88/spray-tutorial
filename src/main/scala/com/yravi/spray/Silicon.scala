package com.yravi.spray

import org.json4s.ShortTypeHints
import org.json4s.native.Serialization
import org.json4s.native.Serialization._

trait Silicon

  case class SingleCrystalSilicon(grainSize: Int) extends Silicon

  case class MultiCrystalSilicon(name: String, grainSize: Int) extends Silicon

  object Silicon {
    val silicons = List[Silicon](
    MultiCrystalSilicon(name = "MultiCrystalline", grainSize = 10),
    MultiCrystalSilicon(name = "PolyCrystalline", grainSize = 1),
    SingleCrystalSilicon(grainSize = 1000)
    )

    private implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[MultiCrystalSilicon], classOf[SingleCrystalSilicon])))
    def toJson(silicons: List[Silicon]): String = writePretty(silicons)
    def toJson(silicon: Silicon): String = writePretty(silicon)
  }

