package com.example.musicroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.musicroom.ui.theme.MusicRoomTheme


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   AdaptiveMusicDashboard()
                }
            }
        }
    }
}

@Preview
@Composable
fun AdapPrev(){
    AdaptiveMusicDashboard()
}

@Composable
fun AdaptiveMusicDashboard() {
    // Color dinámico simulado (Ej: sacado de la carátula de Dua Lipa o The Weeknd)
    val dynamicColor = Color(0xFF8B0000) // Un rojo oscuro profundo

    // Fondo Adaptive Blur
    val backgroundBrush = Brush.verticalGradient(
        colors = listOf(
            dynamicColor.copy(alpha = 0.6f),
            Color(0xFF0A0A0A), // Negro casi puro
            Color(0xFF000000)
        )
    )

    Scaffold(
        bottomBar = { FloatingLiquidBottomBar() },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundBrush)
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp) // Espacio para la bottom bar y el mini player
            ) {
                // 1. Cabecera del Artista
                item { ArtistHeader() }

                // 2. Carrusel de Categorías (Top 100, Clips, etc.)
                item { CategoryCarousel() }

                // 3. Título de sección
                item {
                    Text(
                        text = "Popular",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                }

                // 4. Lista de Canciones (Con la animación de los Beats)
                items(5) { index ->
                    TrackListItem(
                        title = "Canción ${index + 1}",
                        artist = "Artista Famoso",
                        isPlaying = index == 1 // Simulamos que la segunda canción está sonando
                    )
                }
            }

            // Mini Reproductor Flotante (Justo encima de la bottom bar)
            MiniPlayer(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun ArtistHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Gray) // Placeholder para la foto grande
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Dua Lipa", fontSize = 32.sp, fontWeight = FontWeight.Black, color = Color.White)
        Text("88,789,451 monthly listeners", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
            shape = RoundedCornerShape(50)
        ) {
            Text("Follow", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}

@Composable
fun CategoryCarousel() {
    val categories = listOf("Top 100", "Clips", "Singles", "Albums", "Related")

    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        items(categories.size) { index ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.15f))
                    .clickable { }
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                Text(categories[index], color = Color.White, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun TrackListItem(title: String, artist: String, isPlaying: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(if (isPlaying) "" else "1", color = Color.Gray, modifier = Modifier.width(24.dp))

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = if (isPlaying) Color.White else Color.LightGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(artist, color = Color.Gray, fontSize = 14.sp)
        }

        // Si está sonando, mostramos los Beats en blanco puro
        if (isPlaying) {
            AudioBeatsVisualizer(color = Color.White)
        } else {
            Icon(Icons.Default.Search, contentDescription = "More", tint = Color.Gray) // Placeholder icon
        }
    }
}

@Composable
fun MiniPlayer(modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A).copy(alpha = 0.9f)),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .height(64.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(Color.Gray))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("French Exit", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text("Dua Lipa", color = Color.LightGray, fontSize = 12.sp)
            }
            // Aquí irían controles de play/pause
        }
    }
}

@Composable
fun FloatingLiquidBottomBar() {
    // Simulamos el estado de selección
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(Icons.Outlined.Home to Icons.Filled.Home, Icons.Outlined.Search to Icons.Filled.Search, Icons.Outlined.Person to Icons.Filled.Person)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(Color.White, RoundedCornerShape(50)) // Fondo blanco de la barra
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            tabs.forEachIndexed { index, (unselectedIcon, selectedIcon) ->
                val isSelected = selectedTab == index

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color.Black else Color.Transparent)
                        .clickable { selectedTab = index },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isSelected) selectedIcon else unselectedIcon,
                        contentDescription = null,
                        tint = if (isSelected) Color.White else Color.Black,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AudioBeatsVisualizer(color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "beats")
    val bar1 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(400, easing = LinearEasing), repeatMode = RepeatMode.Reverse), label = "b1"
    )
    val bar2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(animation = tween(300, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse), label = "b2"
    )
    val bar3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(animation = tween(500, easing = LinearOutSlowInEasing), repeatMode = RepeatMode.Reverse), label = "b3"
    )

    Canvas(modifier = Modifier.width(24.dp).height(24.dp)) {
        val barWidth = 4.dp.toPx()
        val spacing = 3.dp.toPx()
        val cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())

        listOf(bar1, bar2, bar3).forEachIndexed { index, multiplier ->
            val barHeight = size.height * multiplier
            drawRoundRect(
                color = color,
                topLeft = Offset(x = index * (barWidth + spacing), y = size.height - barHeight),
                size = Size(width = barWidth, height = barHeight),
                cornerRadius = cornerRadius
            )
        }
    }
}

/*
@Composable
fun MinimalistTrackCard() {
    // Fondo crema suave
    val offWhite = Color(0xFFF9F9F6)
    val cobaltBlue = Color(0xFF0047AB)

    Card(
        colors = CardDefaults.cardColors(containerColor = offWhite),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Sin sombras
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.LightGray) // Placeholder carátula
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Vroom Vroom", fontSize = 20.sp, fontWeight = FontWeight.Black, color = Color.Black)
                Text("Soolking", fontSize = 14.sp, color = Color.DarkGray)
            }
            // Botón de acento sólido
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = cobaltBlue)
            ) {
                Text("VOTAR", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun AuroraTrackCard() {
    // Simulación del fondo de la app (degradado pastel)
    val auroraBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFE0C3FC), Color(0xFF8EC5FC))
    )

    Box(modifier = Modifier.fillMaxWidth().background(auroraBrush).padding(16.dp)) {
        // Tarjeta Glassmorphism (Blanco translúcido)
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Vroom Vroom", fontWeight = FontWeight.Bold, color = Color(0xFF2D3436))
                    Text("Soolking", color = Color(0xFF636E72))
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Votar", color = Color(0xFF6C5CE7), fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun NeobrutalismTrackCard() {
    val brutalYellow = Color(0xFFFFD54F)
    val brutalPink = Color(0xFFFF8A80)

    Box(modifier = Modifier.padding(16.dp)) {
        // Sombra sólida (desplazada hacia abajo y la derecha)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 6.dp, y = 6.dp)
                .background(Color.Black)
        )
        // Tarjeta principal
        Card(
            colors = CardDefaults.cardColors(containerColor = brutalYellow),
            border = BorderStroke(3.dp, Color.Black),
            shape = RoundedCornerShape(0.dp) // Formas cuadradas
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .border(3.dp, Color.Black)
                        .background(Color.White)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("VROOM VROOM", fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color.Black)
                    Text("SOOLKING", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = brutalPink),
                    border = BorderStroke(3.dp, Color.Black),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Text("VOTAR", fontWeight = FontWeight.Black, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun MediterraneanTrackCard() {
    // Paleta cálida y luminosa
    val sandWhite = Color(0xFFFAF9F6)
    val ceruleanBlue = Color(0xFF0077B6)
    val terracotta = Color(0xFFE07A5F)

    Card(
        colors = CardDefaults.cardColors(containerColor = sandWhite),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Carátula con bordes muy redondeados
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Vroom Vroom", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF2B2D42))
                Text("Soolking", fontSize = 14.sp, color = Color(0xFF8D99AE))
            }
            // Botón de acento con color vibrante pero natural
            FilledTonalButton(
                onClick = { },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = ceruleanBlue.copy(alpha = 0.1f),
                    contentColor = ceruleanBlue
                )
            ) {
                Text("Votar", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MaterialYouTrackCard() {
    // Todo depende del esquema de colores del sistema (Tema dinámico)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(28.dp) // Curvas muy pronunciadas típicas de M3
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Vroom Vroom",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Soolking",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Votar")
            }
        }
    }
}

@Composable
fun RetroTrackCard() {
    // Paleta analógica
    val vintageCream = Color(0xFFF5EEDC)
    val darkBrown = Color(0xFF4A3F35)
    val fadedRed = Color(0xFFD46A6A)

    Card(
        colors = CardDefaults.cardColors(containerColor = vintageCream),
        border = BorderStroke(1.dp, darkBrown.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(8.dp), // Bordes menos redondeados, más clásicos
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Carátula cuadrada simulando la funda de un disco
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Vroom Vroom",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBrown,
                    fontFamily = FontFamily.Serif // Toque tipográfico clásico
                )
                Text(
                    text = "Soolking",
                    fontSize = 14.sp,
                    color = darkBrown.copy(alpha = 0.7f),
                    fontFamily = FontFamily.Monospace
                )
            }
            // Botón estilo interruptor/etiqueta
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = fadedRed),
                border = BorderStroke(2.dp, fadedRed),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("VOTAR", fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
fun DynamicAuraTrackCard(
    // Este color lo extraerías dinámicamente de la carátula usando Palette API,
    // o vendría de los ajustes del usuario.
    dynamicDominantColor: Color = Color(0xFF9C27B0) // Ejemplo: Un morado intenso
) {
    // 1. El fondo dinámico: El color dominante se difumina hacia un gris/negro muy oscuro
    val auraBrush = Brush.radialGradient(
        colors = listOf(
            dynamicDominantColor.copy(alpha = 0.45f), // Color de la canción con transparencia
            Color(0xFF121212) // Fondo oscuro base
        ),
        radius = 800f // Qué tan expansivo es el "brillo"
    )

    // Contenedor principal que simula la pantalla con el brillo de fondo
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(auraBrush)
            .padding(16.dp)
    ) {
        // 2. La tarjeta de la canción: Súper minimalista y translúcida para dejar ver el fondo
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.05f) // Apenas un cristal muy sutil
            ),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Carátula de la canción (De aquí saldría el color dinámico)
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(dynamicDominantColor) // Placeholder simulando la imagen
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Información de la pista
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Vroom Vroom",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Soolking",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.6f) // Gris clarito para subtítulos
                    )
                }

                // Botón de acción integrado sutilmente
                IconButton(
                    onClick = { },
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(50))
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Reproducir / Votar",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun BeatVisualizerTrackCard(isPlaying: Boolean = true) {
    // Paleta de producto profesional de audio
    val darkSurface = Color(0xFF1A1A24)
    val beatAccent = Color(0xFF00E5FF) // Cian eléctrico muy técnico

    Card(
        colors = CardDefaults.cardColors(containerColor = darkSurface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Carátula
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.DarkGray, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Info de la pista
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Vroom Vroom",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Soolking",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Aquí viene la magia: El visualizador de Beats
            if (isPlaying) {
                AudioBeatsVisualizer(color = beatAccent)
            } else {
                // Botón de play/votar estático si no está sonando
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = beatAccent.copy(alpha = 0.2f))
                ) {
                    Text("VOTAR", color = beatAccent, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AudioBeatsVisualizer(color: Color) {
    val infiniteTransition = rememberInfiniteTransition()

    // Animamos varias barras con distintos tiempos para simular frecuencias
    val bar1 by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(400, easing = LinearEasing), repeatMode = RepeatMode.Reverse)
    )
    val bar2 by infiniteTransition.animateFloat(
        initialValue = 0.8f, targetValue = 0.2f,
        animationSpec = infiniteRepeatable(animation = tween(300, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )
    val bar3 by infiniteTransition.animateFloat(
        initialValue = 0.4f, targetValue = 0.9f,
        animationSpec = infiniteRepeatable(animation = tween(500, easing = LinearOutSlowInEasing), repeatMode = RepeatMode.Reverse)
    )

    val multipliers = listOf(bar1, bar2, bar3)

    Canvas(modifier = Modifier
        .width(32.dp)
        .height(36.dp)
    ) {
        val barWidth = 6.dp.toPx()
        val spacing = 4.dp.toPx()
        val cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())

        multipliers.forEachIndexed { index, multiplier ->
            val barHeight = size.height * multiplier
            val xOffset = index * (barWidth + spacing)
            val yOffset = size.height - barHeight // Para que crezcan desde abajo

            drawRoundRect(
                color = color,
                topLeft = Offset(x = xOffset, y = yOffset),
                size = Size(width = barWidth, height = barHeight),
                cornerRadius = cornerRadius
            )
        }
    }
}

@Composable
@Preview()
fun MinimalistPrev(){
    MinimalistTrackCard()
}

@Composable
@Preview(showBackground = true)
fun AuroraPrev(){
    AuroraTrackCard()
}

@Composable
@Preview(showBackground = true)
fun NeoPrev(){
    NeobrutalismTrackCard()
}

@Composable
@Preview()
fun MediterraneanPrev(){
    MediterraneanTrackCard()
}

@Composable
@Preview()
fun MaterialPrev(){
    MaterialYouTrackCard()
}

@Composable
@Preview()
fun RetroPrev(){
    RetroTrackCard()
}

@Composable
@Preview()
fun DynamicPrev(){
    DynamicAuraTrackCard()
}

@Composable
@Preview(showBackground = true)
fun BitsPrev(){
    BeatVisualizerTrackCard()
} */

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
