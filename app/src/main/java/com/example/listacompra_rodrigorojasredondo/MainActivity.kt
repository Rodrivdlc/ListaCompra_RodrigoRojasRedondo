package com.example.listacompra_rodrigorojasredondo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    private lateinit var repository: ShoppingListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ShoppingListRepository(this)

        

        setContent {
            ShoppingListScreen(repository)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(repository: ShoppingListRepository) {
    var items by remember { mutableStateOf(repository.getItems()) }
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    // Calcular el precio total de los productos
    val totalPrice = items.sumOf { it.price * it.quantity }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Productos: ${items.size} productos = $totalPrice €",
                        fontSize = 16.sp,
                        color = Color.White // Texto en blanco
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Black // Fondo del TopAppBar negro
                )
            )
        },
        containerColor = Color.Black // Fondo de la pantalla en negro
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .background(Color.Black) // Fondo de la columna en negro
        ) {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.DarkGray // Fondo de las tarjetas en gris oscuro
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${item.name} - ${item.quantity} - ${item.price}",
                                    color = Color.White // Texto en blanco
                                )
                                IconButton(onClick = {
                                    repository.removeItem(item.id)
                                    items = repository.getItems()
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre", color = Color.White) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.DarkGray,
                        cursorColor = Color.White,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    )
                )
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad (opcional)", color = Color.White) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.DarkGray,
                        cursorColor = Color.White,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    )
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio (opcional)", color = Color.White) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.DarkGray,
                        cursorColor = Color.White,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedIndicatorColor = Color.White,
                        unfocusedLabelColor = Color.LightGray,
                        focusedLabelColor = Color.White
                    )
                )
                if (isError) {
                    Text(
                        text = "Por favor, introduce un nombre válido",
                        color = Color.Red // Mensaje de error en rojo para destacar
                    )
                }
                Button(
                    onClick = {
                        val quantityInt = quantity.toIntOrNull() ?: 0
                        val priceDouble = price.toDoubleOrNull() ?: 0.0

                        if (name.isNotBlank()) {
                            repository.addItem(ShoppingItem(0, name, quantityInt, priceDouble))
                            items = repository.getItems()
                            name = ""
                            quantity = ""
                            price = ""
                            isError = false
                        } else {
                            isError = true
                        }
                    },
                    modifier = Modifier.padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Añadir")
                }
            }
        }
    }
}
