package com.example.state

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val language = rememberSaveable { mutableStateOf(value = "en") }
            val currentContext = LocalContext.current
            val products = Product.products.map { product ->
                getStringResource(currentContext, product.titleID, language.value)
            }

            Column(Modifier.padding(4.dp)) {
                Header(currentContext, language)
                ProductsList(products)
                Switcher(currentContext, language)
            }
        }
    }

    @Composable
    private fun Switcher(
        currentContext: Context,
        language: MutableState<String>
    ) {
        Text(
            text = getStringResource(
                currentContext,
                R.string.switch_language,
                language.value
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    language.value = if (language.value == "en") "ru" else "en"
                },
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }

    @Composable
    private fun ProductsList(products: List<String>) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color.Gray)
        ) {
            items(products) { product ->
                Card(
                    shape = CardDefaults.elevatedShape,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = product,
                        modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),

                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                }

            }
        }
    }

    @Composable
    private fun Header(
        currentContext: Context,
        language: MutableState<String>
    ) {
        Text(
            text = getStringResource(
                currentContext,
                R.string.list_of_products,
                language.value
            ),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }

    private fun getStringResource(context: Context, resId: Int, language: String): String {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        val localizedContext = context.createConfigurationContext(config)
        return localizedContext.getString(resId)
    }
}