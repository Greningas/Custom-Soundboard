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
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.customsoundboard.ui.theme.CUstomSoundboardTheme
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private var lastBackPressedTime: Long = 0
    private val backPressThreshold = 2000  // 2 seconds threshold

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CUstomSoundboardTheme {
                SoundboardApp(playSound = { playSound(it) })
            }
        }

        // Register the callback for the back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastBackPressedTime < backPressThreshold) {
                    finish()  // Close the activity (and app)
                } else {
                    lastBackPressedTime = currentTime
                    Toast.makeText(this@MainActivity, "Na stole stała misa...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun playSound(soundResId: Int) {
        if (this::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
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
            text = "Welcome to the Soundboard!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SoundboardScreen(playSound: (Int) -> Unit) {
    val buttonConfigs = listOf(
        ButtonConfig("Zle but glosno", R.raw.wrong, Color.Red),
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
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Suck", R.raw.parrot),
        ButtonConfig("Suck", R.raw.parrot),
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Implement add sound functionality */ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Sound")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp), // Adjust spacing between items
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(buttonConfigs.size) { index ->
                val config = buttonConfigs[index]
                SoundButton(config.text, config.soundResId, config.color, playSound)
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