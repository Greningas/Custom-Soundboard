package com.example.customsoundboard

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.customsoundboard.ui.theme.CUstomSoundboardTheme
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private var mediaPlayer: MediaPlayer? = null
    private val mediaPlayerResources = HashMap<MediaPlayer, Int>() //
    private var lastBackPressedTime: Long = 0
    private val backPressThreshold = 2000



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CUstomSoundboardTheme {
                SoundboardApp(playSound = { playSound(it) })
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastBackPressedTime < backPressThreshold) {
                    finish()
                } else {
                    lastBackPressedTime = currentTime
                    Toast.makeText(this@MainActivity, "Na stole stała misa...", Toast.LENGTH_SHORT).show()}
            }
        })
    }

    private fun playSound(soundResId: Int) {
        val currentPlayer = mediaPlayer
        if (currentPlayer?.isPlaying == true && mediaPlayerResources[currentPlayer] == soundResId) {
            // Same sound is playing, stop it
            currentPlayer.stop()
            currentPlayer.release()
            mediaPlayer = null
            mediaPlayerResources.remove(currentPlayer)
        } else {// Play the new sound
            mediaPlayer?.release()
            mediaPlayerResources.remove(mediaPlayer) // Remove old mapping
            mediaPlayer = MediaPlayer.create(this, soundResId).apply {
                mediaPlayerResources[this] = soundResId // Store new mapping
                start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}

@Composable
fun SoundboardApp(playSound: (Int) -> Unit) {
    var showGreeting by remember { mutableStateOf(true) }

    if (showGreeting) {
        GreetingScreen { showGreeting = false }
    } else {
        SoundboardScreen(playSound = playSound)
    }
}

@Composable
fun GreetingScreen(onContinue: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
            .clickable { onContinue() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Polskie kurwy, w przemówieniu mym, chciałbym wam powiedzieć że, Polska to jebana dzicz, a polacy to smiecie i kurwy." +
                    "Jak mówił Jan Paweł Drugi Papież, z Rosjanami wypije, z Żydem pohandluje, ale na polaków pluję." +
                    "Moi rodzice to komuniści i jestem z tego dumny, że dziadek, razem z Janem Pawłem rozstrzelał polaków na wołyniu, a na zakończenie" +
                    "chuj wam w dupę.!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SoundboardScreen(playSound: (Int) -> Unit) {
    val buttonConfigs = listOf(
        ButtonConfig("Zle but glosno", R.raw.wrong, Color.Red),
        ButtonConfig("Dobrze ding", R.raw.correct, Color.Green),
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Gorp", R.raw.gorp),
        ButtonConfig("Tomek do wozu", R.raw.tomek),
        ButtonConfig("Sus", R.raw.impostor),
        ButtonConfig("Sona 2", R.raw.seraphine_r),
        ButtonConfig("Me when Koi Pond", R.raw.og_snore),
        ButtonConfig("O zapasy szykuj sie", R.raw.zapasy),
        ButtonConfig("Me when 29 HR quest", R.raw.bazel),
        ButtonConfig("Coca Cola", R.raw.coca_cola),
        ButtonConfig("Edging my Aneurysm", R.raw.edging_aneurysm),
        ButtonConfig("Gibby", R.raw.gibby),
        ButtonConfig("Edging my Augherysm", R.raw.edging_augherysm),
        ButtonConfig("Edging my Satan Aneurysm", R.raw.edging_satan_anerysm),
        ButtonConfig("Takie zero", R.raw.mati_i_domino),
        ButtonConfig("Bird", R.raw.bird_screaming_meme),
        ButtonConfig("Randy", R.raw.niggers),
        ButtonConfig("Spock 1", R.raw.kurwa_spock_1),
        ButtonConfig("Spock 2", R.raw.kurwa_spock_2),
        ButtonConfig("Plecy", R.raw.dobry_sprzet),
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO */ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Sound")
            }
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding)
        ) {
            IconButton(onClick = { /* TODO */ },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }

          /* LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(buttonConfigs.size) { index ->
                    val config = buttonConfigs[index]
                    SoundButton(config.text, config.soundResId, config.color, playSound) */

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Set 2 columns
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(buttonConfigs.size) { index -> // Directly iterate over buttonConfigs
                    val config = buttonConfigs[index]
                    SoundButton(config.text, config.soundResId, config.color, playSound)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CUstomSoundboardTheme {
        SoundboardApp(playSound = { })
    }
}

/*@Composable
fun SoundboardScreen(playSound: (Int) -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = { playSound(R.raw.wrong) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )) {
                Text("Zle but glosno")
            }

            Button(onClick = { playSound(R.raw.parrot) }) {
                Text("Suck")
            }

            Button(onClick = { playSound(R.raw.gorp) }) {
                Text("Gorp")
            }

            Button(onClick = { playSound(R.raw.tomek) }) {
                Text("Tomek do wozu")
            }
            // Add more buttons for other sounds here
            Button(onClick = { playSound(R.raw.impostor) }) {
                Text("Sus")
            }

            Button(onClick = { playSound(R.raw.seraphine_r) }) {
                Text("Sona 2")
            }

            Button(onClick = { playSound(R.raw.og_snore) }) {
                Text("Me when Koi Pond")
            }

            Button(onClick = { playSound(R.raw.zapasy) }) {
                Text("O zapasy szykuj sie")
            }

            Button(onClick = { playSound(R.raw.bazel) }) {
                Text("Me when 29 HR quest")
            }
        }
    }
}*/