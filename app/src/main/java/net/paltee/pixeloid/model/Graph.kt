package net.paltee.pixeloid.model

import java.io.Serializable

data class Graph(var id: String,
                 var name: String,
                 var unit: String,
                 var timezone: String,
                 var type: String = "int",
                 var color: String = "shibafu"
) : Serializable
