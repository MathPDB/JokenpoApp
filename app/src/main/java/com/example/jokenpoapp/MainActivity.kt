package com.example.jokenpoapp

import android.R.attr.shape
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jokenpoapp.ui.theme.JokenpoAppTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokenpoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    JokenpoApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun getMaos(mao: Int): Int {
    return when (mao) {
        1 -> R.drawable.pedra
        2 -> R.drawable.papel
        3 -> R.drawable.tesoura
        else -> R.drawable.ppt
    }
}

@Composable
fun JokenpoApp(modifier: Modifier = Modifier) {

    val scope = rememberCoroutineScope()

    var jogadorWin by remember {
        mutableStateOf(0)
    }
    var ursinhoWin by remember {
        mutableStateOf(0)
    }
    var jogadorMao by remember {
        mutableStateOf(0)
    }
    var ursinhoMao by remember {
        mutableStateOf(0)
    }

    fun partida(mao: Int) {
        jogadorMao = mao

        scope.launch {
            //sorteio??
            delay(150)
            ursinhoMao = 1
            delay(150)
            ursinhoMao = 2
            delay(150)
            ursinhoMao = 3
            delay(150)
            val maoRandom = (1..3).random()
            ursinhoMao = maoRandom
            //sorteio??

            when {
                jogadorMao == ursinhoMao -> {}
                (jogadorMao == 1 && ursinhoMao == 3) ||
                        (jogadorMao == 2 && ursinhoMao == 1) ||
                        (jogadorMao == 3 && ursinhoMao == 2) -> jogadorWin++
                else -> ursinhoWin++
            }
        }
    }

    fun restartGame() {
        jogadorMao = 0
        jogadorWin = 0
        ursinhoMao = 0
        ursinhoWin = 0
    }


    // TelaInicial(modifier = modifier)
    TelaInicial(
        modifier = Modifier,
        jogadorWin = jogadorWin,
        ursinhoWin = ursinhoWin,
        jogadorMao = jogadorMao,
        ursinhoMao = ursinhoMao,
        onPartida = { mao -> partida(mao) },
        onReset = { restartGame() }
        //lambda

        //onPartida = ::partida,
        //onReset = ::restartGame
        //operador :: para passar a função como parâmetro sem executar
    )
}

@Composable
fun TelaInicial(
    modifier: Modifier = Modifier,
    jogadorWin: Int,
    ursinhoWin: Int,
    jogadorMao: Int,
    ursinhoMao: Int,
    onPartida: (Int) -> Unit, //recebe Int e não retorna nada
    onReset: () -> Unit //não recebe e não retorna nada (tipo void)
) {
    Image(
        painterResource(R.drawable.bg_jokenpo),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxSize()
    )

    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Placar(modifier = modifier, jogadorWin, ursinhoWin)
        Column {
            Mao(maos = listOf(ursinhoMao), jogador = false, onEscolher = {})
            Spacer(modifier = Modifier.height(70.dp))
            Mao(maos = listOf(jogadorMao), jogador = false, onEscolher = {})
            Spacer(modifier = Modifier.height(50.dp))
            Mao(maos = listOf(1, 2, 3), jogador = true, onEscolher = onPartida)
        }
        RestartGame(onReset = onReset)
    }
}

@Composable
fun Placar(
    modifier: Modifier = Modifier,
    jogadorWin: Int,
    ursinhoWin: Int

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 75.dp, bottom = 70.dp)
            .height(60.dp)
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row(
            modifier = modifier
                //.padding(10.dp)
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    BorderStroke(width = 2.dp, color = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Jogador",
                modifier = Modifier
                    .width(90.dp)
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = jogadorWin.toString(),
                modifier = Modifier
                    .width(40.dp)
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
        Row(
            modifier = modifier
                //.padding(10.dp)
                .background(
                    Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    BorderStroke(width = 2.dp, color = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Ursinho",
                modifier = Modifier
                    .width(90.dp)
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = ursinhoWin.toString(),
                modifier = Modifier
                    .width(40.dp)
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun Mao(
    modifier: Modifier = Modifier,
    maos: List<Int> = listOf<Int>(1, 2, 3),
    jogador: Boolean = true,
    onEscolher: ((Int) -> Unit)

) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        maos.forEachIndexed { i, mao ->
            val maoImg = getMaos(mao)
            Image(
                painterResource(maoImg),
                contentDescription = "Mão escolhida: $mao",
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clickable(
                        enabled = jogador,
                        onClick = { onEscolher(mao) }
                    )

            )

        }

    }
}

@Composable
fun RestartGame(
    modifier: Modifier = Modifier,
    onReset: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = modifier
                .border(
                    BorderStroke(width = 2.dp, color = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = onReset,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                text = "Restart",
                modifier = Modifier
                    .padding(8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JokenpoAppTheme {
        TelaInicial(
            jogadorWin = 87,
            ursinhoWin = 2,
            jogadorMao = 1,
            ursinhoMao = 2,
            onPartida = {},
            onReset = {})
        //Placar()
        //Mao()

    }
}