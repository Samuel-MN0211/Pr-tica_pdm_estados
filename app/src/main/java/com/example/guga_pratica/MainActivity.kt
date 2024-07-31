package com.example.guga_pratica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var currentImageIndex by remember { mutableStateOf(0) }
    val images = listOf(
        Pair(R.drawable.guga_1, "Céu estrelado"),
        Pair(R.drawable.guga_2, "Imagem 2"),
        Pair(R.drawable.guga_3, "Imagem 3")
    )
    val artists = listOf(
        "Van Gogh",
        "Artista 2",
        "Artista 3"
    )

    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount < 0) {
                        if (currentImageIndex < images.size - 1) {
                            currentImageIndex++
                        } else {
                            currentImageIndex = 0
                        }
                    } else {
                        if (currentImageIndex > 0) {
                            currentImageIndex--
                        } else {
                            currentImageIndex = images.size - 1
                        }
                    }
                }
            },
        color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = Color.LightGray
            ) {
                Image(
                    painter = painterResource(id = images[currentImageIndex].first),
                    contentDescription = "Imagem",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${images[currentImageIndex].second}\n${artists[currentImageIndex]}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        if (currentImageIndex > 0) {
                            currentImageIndex--
                        } else {
                            currentImageIndex = images.size - 1
                        }
                    },
                ) {
                    Text(text = "Anterior")
                }
                Button(
                    onClick = {
                        if (currentImageIndex < images.size - 1) {
                            currentImageIndex++
                        } else {
                            currentImageIndex = 0
                        }
                    },

                ) {
                    Text(text = "Próximo")
                }
            }
        }
    }
}

@Composable
fun showHint(message: String) {
    LaunchedEffect(message) {
        SnackbarHostState().showSnackbar(message)
        delay(2000)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
