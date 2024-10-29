package com.example.listacompra_rodrigorojasredondo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var repository: ShoppingListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ShoppingListRepository(this)

        // Add items to the list
        repository.addItem(ShoppingItem(0, "Apples", 5, 1.0))
        repository.addItem(ShoppingItem(0, "Bananas", 3, 0.5))

        setContent {
            ShoppingListScreen(repository)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(repository: ShoppingListRepository) {
    val context = LocalContext.current
    var items by remember { mutableStateOf(repository.getItems()) }
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Productos: ${items.size} productos") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                LazyColumn {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "${item.name} - ${item.quantity} - ${item.price}")
                                IconButton(onClick = {
                                    repository.removeItem(item.id)
                                    items = repository.getItems()
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
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
                    label = { Text("Nombre") }
                )
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") }
                )
                TextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Precio") }
                )
                if (isError) {
                    Text(
                        text = "Por favor, introduce datos válidos para nombre, cantidad y precio",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Button(
                    onClick = {
                        val quantityInt = quantity.toIntOrNull() ?: -1
                        val priceDouble = price.toDoubleOrNull() ?: -1.0

                        if (name.isNotBlank() && quantityInt > 0 && priceDouble > 0.0) {
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
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Añadir")
                }
            }
        }
    }
}
