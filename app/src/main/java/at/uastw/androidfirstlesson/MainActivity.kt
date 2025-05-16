package at.uastw.androidfirstlesson

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import at.uastw.androidfirstlesson.dto.PokemonDto
import at.uastw.androidfirstlesson.dto.PokemonTypeDto
import at.uastw.androidfirstlesson.magiccard.ui.MagicCardScreen
import at.uastw.androidfirstlesson.magiccard.ui.SettingsScreen
import at.uastw.androidfirstlesson.parser.PokemonParser
import at.uastw.androidfirstlesson.ui.theme.AndroidFirstLessonTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidFirstLessonTheme {

                var showSettings by rememberSaveable {
                    mutableStateOf(false)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Magic Cards") },
                            navigationIcon = {
                                if (showSettings) {
                                    IconButton(onClick = {
                                        showSettings = false
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = "Settings"
                                        )
                                    }
                                }
                            },
                            actions = {
                                IconButton(onClick = {
                                    showSettings = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Settings,
                                        contentDescription = "Settings"
                                    )
                                }
                            }
                        )
                    }

                ) { innerPadding ->
                    BackHandler(showSettings) {
                        showSettings = false
                    }
                    if (showSettings) {
                        SettingsScreen(modifier = Modifier.padding(innerPadding))
                    } else {
                        MagicCardScreen(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainActivity", "onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MainActivity", "onDestroy")
    }
}

fun getContentFromWeb(url: String): String {
    // https://pokeapi.co/api/v2/pokemon/
    return "notrelevant";

    /*
    val urlObject = URL(url)
    val con = urlObject.openConnection() as HttpURLConnection
    try {
        con.requestMethod = "GET"
        con.readTimeout = 5000
        con.connectTimeout = 5000

        val result = String(con.inputStream.readBytes())
        return result
    } catch (e: Exception) {
        Log.e("MainActivity", "Pokemon not found", e)
        return "Pokemon not found!"
    } */
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var inputText by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var result by rememberSaveable { mutableStateOf<PokemonDto?>(null) }


    Column {
        Text(
            text = stringResource(R.string.greeting),
            modifier = modifier
        )
        Row {
            OutlinedTextField(value = inputText, onValueChange = {
                inputText = it
            })
            Button(onClick = {
                coroutineScope.launch {
                    val pokemonParser = PokemonParser()

                    /*val webContent = async(Dispatchers.IO) {
                        getContentFromWeb("https://pokeapi.co/api/v2/pokemon/$inputText")
                    }.await()
                    val pokemon = withContext(Dispatchers.Default) {
                        pokemonParser.parseJson(webContent)
                    }*/
                    val pokemonTypeDto = PokemonTypeDto("flying")
                    val types = mutableListOf(pokemonTypeDto)

                    for (i in 1..50000) {
                        types.add(pokemonTypeDto)
                    }

                    val pokemon = PokemonDto(1, "pikachu", 2, 2, types)
                    result = pokemon
                }
            }) { Text("Search") }
        }
        if (result != null) {
            LazyColumn {
                items(result!!.types) {
                    Text(it.name)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidFirstLessonTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
private fun ColumnTest() {
    Column {
        Text("Hi", modifier = Modifier.background(Color.Blue))
        Text("Hello")
        Text("Hiho")
        Row {
            Text("Hello")
            Text("Hiho")
        }
    }
}