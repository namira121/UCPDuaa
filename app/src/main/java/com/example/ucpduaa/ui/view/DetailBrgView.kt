package com.example.ucpduaa.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpduaa.data.entity.Barang
import com.example.ucpduaa.ui.customwidget.TopAppBar
import com.example.ucpduaa.ui.viewmodel.DetailBarangViewModel
import com.example.ucpduaa.ui.viewmodel.DetailBrgUIState
import com.example.ucpduaa.ui.viewmodel.PenyediaViewModel
import com.example.ucpduaa.ui.viewmodel.toBarangEntity

@Composable
fun DetailBrgView(
    modifier: Modifier = Modifier,
    viewModel: DetailBarangViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (Int) -> Unit = { },
    onDeleteClick: () -> Unit = { }
){
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Barang",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailbrgUIState.value.detailUiEvent.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Barang")

            }
        }
    ) { innerPadding ->
        val detailBrgUIState by viewModel.detailbrgUIState.collectAsState()

        BodyDetailBrg(
            modifier = Modifier.padding(innerPadding),
            detailBrgUIState = detailBrgUIState,
            onDeleteClick={
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailBrg(
    modifier: Modifier = Modifier,
    detailBrgUIState: DetailBrgUIState = DetailBrgUIState(),
    onDeleteClick: () -> Unit = { }
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailBrgUIState.isLoading->{
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }

        detailBrgUIState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailBrg(
                    barang = detailBrgUIState.detailUiEvent.toBarangEntity(),
                    modifier= Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier=Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }

                if(deleteConfirmationRequired){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {deleteConfirmationRequired = false},
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        detailBrgUIState.isUiEventEmpty ->{
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ItemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
){
    Card (
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            ComponentDetailBrg(judul = "Nama", isinya = barang.nama)
            Spacer(modifier= Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Deskripsi", isinya = barang.deskripsi)
            Spacer(modifier= Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Harga", isinya = barang.harga)
            Spacer(modifier= Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Stok", isinya = barang.stok)
            Spacer(modifier= Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Nama Suplier", isinya = barang.namaspl)
            Spacer(modifier= Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailBrg(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "$judul",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(onDismissRequest = { },
        title = { Text("Delete Data") },
        text={ Text("Apaka anda ingin manghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}