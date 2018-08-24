package beepbeep.accordionView.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.accordionView.DataModel
import beepbeep.accordionView.R
import kotlinx.android.synthetic.main.activity_random_fact.*

class RandomFactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_fact)
        val adapter = RandomAdapter(listOf(
                DataModel("Zombie Facts", "In Haitian folklore, a zombie (Haitian French: zombi, Haitian Creole: zonbi) is an animated corpse raised by magical means, such as witchcraft. The concept has been popularly associated with the religion of voodoo, but it plays no part in that faith's formal practices."),
                DataModel("Mariana Trench", "The Mariana Trench or Marianas Trench, located in the western Pacific Ocean approximately 200 kilometres east of the Mariana Islands, is the deepest point in the world's oceans."),
                DataModel("Broccoli Facts", "It has been eaten there since the time of the ancient Romans in the 6th Century BC. 2. Broccoli is a member of the cabbage family, making it a cruciferous vegetable. It's name is derived from the Italian word broccolo, meaning the flowering top of a cabbage."),
                DataModel("Llama Facts", "The llama is a domesticated South American camelid, widely used as a meat and pack animal by Andean cultures since the Pre-Columbian era."),
                DataModel("Turtle Facts", "Turtles have a hard shell that protects them like a shield, this upper shell is called a 'carapace'. Turtles also have a lower shell called a 'plastron'. Many turtle species (not all) can hide their heads inside their shells when attacked by predators. Turtles have existed for around 215 million years.")
        ))
        accordion_view.adapter = adapter
        accordion_view.updatePosition(3)
    }
}