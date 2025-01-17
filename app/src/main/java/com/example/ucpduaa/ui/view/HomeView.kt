package com.example.ucpduaa.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.util.TableInfo

@Preview(showBackground = true)
@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    onAddBarang: () -> Unit = { },
    onAddSuplier: () -> Unit = { },
    onListBarang: () -> Unit = { },
    onListSuplier: () -> Unit = { },
){
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onAddBarang
        ) {
            Text(text = "Tambah Barang")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onListBarang
        ) {
            Text(text = "Daftar Barang")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onAddSuplier
        ) {
            Text(text = "Tambah Suplier")
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = onListSuplier
        ) {
            Text(text = "Daftar Suplier")
        }
    }
}