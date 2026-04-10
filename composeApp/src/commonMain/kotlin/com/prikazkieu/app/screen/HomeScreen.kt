package com.prikazkieu.app.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prikazkieu.app.customComponent.AgeControl
import com.prikazkieu.app.customComponent.WavesDivider
import org.jetbrains.compose.resources.painterResource
import prikazkieu.composeapp.generated.resources.*

@Composable
fun HomeScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(Res.drawable.night_sky),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            //INFO
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Избери си",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFFADB5BD),
                        fontFamily = FontFamily.Serif
                    )
                )

                Text(
                    text = "ПРИКАЗКА",
                    style = TextStyle(
                        fontSize = 56.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFB627),  // golden yellow
                        shadow = Shadow(
                            color = Color(0xFF5C3A00),  // dark brown shadow
                            offset = Offset(4f, 6f),
                            blurRadius = 2f
                        )
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                AgeControl()
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "1500+ прекрасни класически произведения за четене, слушане и гледане – за малки и пораснали любители на вълшебни истории от целия свят.",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp        // ← gives the airy spacing between lines
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(48.dp))
                WavesDivider(modifier = Modifier
                    .fillMaxWidth(),
                    wave1Color = Color(0xFF1B2E5E),
                    wave2Color = Color(0xFF2E6AAF),
                    wave3Color = Color(0xFFB8DFF0)
                )

                // Browse prikazki
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFB8DFF0),
                                    Color(0xFFDFF0F5),
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ornamental_title_big_yellow),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = "Избери си Приказка",
                                style = TextStyle(
                                    fontStyle = FontStyle.Italic,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    color = Color(0xFF2C1810),      // dark brown
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 32.dp, vertical = 32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(48.dp))

                        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(
                                modifier = Modifier.align(Alignment.Bottom),
                                shape = RectangleShape,
                                onClick = { },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp),
                                elevation = null
                            ) {
                                Text(
                                    text = "АВТОР",
                                    style = TextStyle(
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        shadow = Shadow(
                                            color = Color(0xFFB0B8C0),
                                            offset = Offset(6f, 8f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                            }
                            Image(
                                painter = painterResource(Res.drawable.jaba_pisatel),
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )
                        }

                        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Image(
                                painter = painterResource(Res.drawable.jaba_queen),
                                contentDescription = null,
                                contentScale = ContentScale.Fit
                            )
                            Button(
                                onClick = { },
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                ),
                                contentPadding = PaddingValues(0.dp),
                                elevation = null
                            ) {
                                Text(
                                    text = "ЦАРСТВО",
                                    style = TextStyle(
                                        fontSize = 48.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White,
                                        shadow = Shadow(
                                            color = Color(0xFFB0B8C0),
                                            offset = Offset(6f, 8f),
                                            blurRadius = 4f
                                        )
                                    )
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFDFF0F5))
                ) {
                    Column {
                        WavesDivider(modifier = Modifier
                            .fillMaxWidth(),
                            wave1Color = Color(0xFFB8DFF0),
                            wave2Color = Color(0xFF6BAEAD),
                            wave3Color = Color(0xFF3D8C8A)
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF3D8C8A))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    // Large background text — semi transparent
                                    Text(
                                        text = "ПРИКАЗКА",
                                        style = TextStyle(
                                            fontSize = 40.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White.copy(alpha = 0.25f)  // ← faded/watermark
                                        ),
                                        modifier = Modifier.align(Alignment.Center)
                                    )

                                    // Italic text on top — aligned to bottom left
                                    Text(
                                        text = "по пощата в сряда",
                                        style = TextStyle(
                                            fontStyle = FontStyle.Italic,
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        ),
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(bottom = 2.dp)
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    OutlinedTextField(
                                        value = "",
                                        onValueChange = {  },
                                        placeholder = {
                                            Text(
                                                text = "E-mail адрес",
                                                style = TextStyle(
                                                    fontStyle = FontStyle.Italic,
                                                    fontSize = 18.sp,
                                                    color = Color.Gray
                                                )
                                            )
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(56.dp),
                                        shape = RectangleShape,         // ← no rounded corners
                                        colors = OutlinedTextFieldDefaults.colors(
                                            unfocusedContainerColor = Color.White,
                                            focusedContainerColor = Color.White,
                                            unfocusedBorderColor = Color.White,
                                            focusedBorderColor = Color.Black
                                        ),
                                        singleLine = true
                                    )

                                    // OK Button
                                    Button(
                                        onClick = { },
                                        modifier = Modifier
                                            .height(56.dp)
                                            .width(72.dp)
                                            .padding(start = 4.dp),
                                        shape = RectangleShape,         // ← square corners
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFFE8C84A)  // golden yellow
                                        ),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Text(
                                            text = "ok",
                                            style = TextStyle(
                                                fontSize = 22.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF1A1A1A)
                                            )
                                        )
                                    }
                                }

                                Text(
                                    text = "С натискането на бутона \"OK\", се съгласявате с нашите условия.",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.White,
                                        lineHeight = 16.sp
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF3D8C8A))
                ) {
                    Image(
                        painter = painterResource(Res.drawable.messy_floral_wallpaper),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Column {
                        WavesDivider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            wave1Color = Color(0xFF3D8C8A),
                            wave2Color = Color(0xFFEDD82A),
                            wave3Color = Color(0xFFF2F2F2)
                        )
                    }
                }
            }
        }
    }
}