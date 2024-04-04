package br.com.example.spotify.ui.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class UtilsViews() {

    companion object {

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun TopBar(
            onBackPressed: (() -> Unit)?,
            title: String,
            content: @Composable (PaddingValues) -> Unit
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(title) },
                        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                        navigationIcon = {
                            if (onBackPressed != null) {
                                IconButton(onClick = {
                                    onBackPressed()
                                }) {
                                    Icon(Icons.Filled.ArrowBack, "Voltar")
                                }
                            }
                        }
                    )
                }) {
                content(it)
            }
        }

    }
}
