package com.example.project

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project.ui.theme.ProjectTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PasswordGeneratorScreen()
                }
            }
        }
    }
}

@Composable
fun PasswordGeneratorScreen() {
    var lengthText by remember { mutableStateOf("") }
    var includeDigits by remember { mutableStateOf(false) }
    var includeSpecial by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Генератор паролів",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = lengthText,
            onValueChange = { lengthText = it },
            label = { Text("Довжина пароля") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = includeDigits, onCheckedChange = { includeDigits = it })
            Text("Використовувати цифри")
        }

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Checkbox(checked = includeSpecial, onCheckedChange = { includeSpecial = it })
            Text("Використовувати спецсимволи")
        }

        Button(
            onClick = {
                val length = lengthText.toIntOrNull()
                if (length == null || length <= 0) {
                    Toast.makeText(context, "Невірна довжина!", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                val digits = "0123456789"
                val special = "!@#\$%^&*()-_=+[]{};:,.<>?/|"

                var charPool = letters
                if (includeDigits) charPool += digits
                if (includeSpecial) charPool += special

                password = (1..length).map { charPool.random() }.joinToString("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Згенерувати")
        }

        if (password.isNotEmpty()) {
            Text("Пароль: $password", fontSize = 18.sp)
        }
    }
}
