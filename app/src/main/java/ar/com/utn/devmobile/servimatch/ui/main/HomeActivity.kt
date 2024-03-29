package ar.com.utn.devmobile.servimatch.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.com.utn.devmobile.servimatch.R
import ar.com.utn.devmobile.servimatch.ui.theme.PurpleGrey40
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ar.com.utn.devmobile.servimatch.MyPreferences
import ar.com.utn.devmobile.servimatch.ui.model.ApiClient
import ar.com.utn.devmobile.servimatch.ui.model.ProviderInfo
import ar.com.utn.devmobile.servimatch.ui.theme.Purpura2
import ar.com.utn.devmobile.servimatch.ui.theme.Purpura3
import ar.com.utn.devmobile.servimatch.ui.theme.Turquesa1
import ar.com.utn.devmobile.servimatch.ui.theme.Turquesa2
import ar.com.utn.devmobile.servimatch.ui.theme.Turquesa3
import ar.com.utn.devmobile.servimatch.ui.theme.Turquesa4
import ar.com.utn.devmobile.servimatch.ui.theme.Turquesa5
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng



var paddingH = 16.dp
var paddingV = 8.dp

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomeScreen(navController: NavController, username: String) {

    //Cargo el viewModel que va a gestionar la lista de proveedores que se muestra.
    val listaDeProveedores = viewModel<ListaDeProveedores>()
    // Acceder a la variable isLoading
    val isLoading = listaDeProveedores.isLoading


    //Cargo las primeras listas, recomendados y general. Pegandole al back.
    LaunchedEffect(Unit) {
        val userLocation = MyPreferences.getInstance().get("latlong_user") as? LatLng
        if (userLocation != null) {
            listaDeProveedores.getProvedores(userLocation)
        }

    }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .background(Turquesa1)
                .padding(horizontal = paddingH, vertical = paddingV),
            verticalArrangement = Arrangement.Top
        )
        {

                Spacer(modifier = Modifier.height(0.dp))
                //Renderizo header.
                Header(navController, username, 0.dp, 0.dp)

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre header y filtros

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    ) // Muestra la animación de carga
                }
            } else {
                //Renderizo lista de filtros
                FilterList(listaDeProveedores)

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre filtros y recomendados

                //Renderizo lista de Proveedores. Inicialmente renderiza las listas recomendados y general.
                ProvidersList(navController, listaDeProveedores)
            }


    }
}

@Composable
fun ProvidersList(navController: NavController, listaProveedores: ListaDeProveedores) {
    val busqueda by remember { listaProveedores.busqueda }
    if (listaProveedores.general.value.isNotEmpty() && listaProveedores.recomendados.value.isNotEmpty()){
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            //.padding(horizontal = 8.dp, vertical = 4.dp),
            content = {
                if (busqueda.isNotEmpty()) {

                    item {
                        Text(
                            text = stringResource(id = R.string.resultados) + " (${busqueda.size})",
                            style = MaterialTheme.typography.titleLarge,
                            color = PurpleGrey40,
                        )
                    }
                    items(busqueda) { providerInfo ->
                         Provider(providerInfo.imageResource, providerInfo.name, providerInfo.apellido,providerInfo.rol, providerInfo.priceSimbol, providerInfo.location, navController,providerInfo.identificador)
                     }
                } else {
                    item {
                        Text(
                            text = stringResource(id = R.string.recomendados) + " (${listaProveedores.recomendados.value.size})",
                            style = MaterialTheme.typography.titleLarge,
                            color = Turquesa4,
                        )
                    }
                    items(listaProveedores.recomendados.value) { providerInfo ->
                         Provider(providerInfo.imageResource, providerInfo.name, providerInfo.apellido, providerInfo.rol,providerInfo.priceSimbol, providerInfo.location, navController,providerInfo.identificador)
                     }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.mas_resultados) + " (${listaProveedores.general.value.size})",
                            style = MaterialTheme.typography.titleLarge,
                            color = PurpleGrey40,
                        )
                    }
                    items(listaProveedores.general.value) { providerInfo ->
                        Provider(providerInfo.imageResource, providerInfo.name, providerInfo.apellido,providerInfo.rol,providerInfo.priceSimbol, providerInfo.location, navController,providerInfo.identificador)
                    }
                }
    }
    )
    }else{
          Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = stringResource(id = R.string.proveedores_empty),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Turquesa5,
                    textAlign = TextAlign.Center
                )
            }

        }

    }

@Composable
fun Provider(image: String,
             nombre: String,
             apellido: String,
             profesion: String,
             simboloPrecio: Int,
             ubicaciones: List<String>,
             navController: NavController,
             identificador: Number) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = paddingV),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Imagen
        AsyncImage(
            model = image,
            contentDescription = "Foto de perfil del ofertante",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Turquesa3, CircleShape)

        )

        // Espacio entre la imagen y la información
        Spacer(modifier = Modifier.width(16.dp))

        // Columna con información de la persona
        Column(
            modifier = Modifier.weight(1f) // Permite que la columna ocupe el espacio disponible
        ) {
            Text(
                text = "$nombre, $apellido",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = Turquesa5
            )
            Text(
                text = profesion,
                color = Turquesa4
            )
            Text(
                //text = "Desde $precio",
                text = buildString {repeat(simboloPrecio){append("$")}},
                fontSize = 16.sp,
                color = Purpura3
            )
            Text(
                text = ubicaciones.joinToString(", "),
                color = Purpura2
            )

        }

        // IconButton con icono
        IconButton(
            onClick = {
                // Acción a realizar cuando se hace clic en el icono
                navController.navigate("profile/$identificador") {
                    // Puedes pasar el ID del proveedor como argumento si es necesario
                    // arguments = bundleOf("idProveedor" to proveedor.id)
                }
            },
            modifier = Modifier
                .size(35.dp) // Ajusta el tamaño del icono
        ) {
            Icon(
                painter = painterResource(id = R.drawable.eye), // Reemplaza con el recurso de tu icono de ojo
                contentDescription = null,
                tint = Turquesa4 // Color del icono
            )
        }
        // Divider negro como separador

    }
    Divider(color = Color.Gray, thickness = 1.dp)
}

@Composable
fun FilterList(listaProveedores: ListaDeProveedores){
    var jobs by remember {mutableStateOf(emptyList<String>())}
    var rating by remember {mutableStateOf(emptyList<String>())}
    var filtersApplied by remember {mutableStateOf(false)} // Variable para controlar el get lista filtrada.
    var profesionSeleccionada by remember {mutableStateOf("")} //guarda la profesion seleccionada del dropdown
    var puntajeSeleccionado by remember {mutableStateOf("-1")} //guarda el puntaje selecionado del dropdown
    var clearRubro by remember {mutableStateOf(false)}
    var clearPuntaje by remember {mutableStateOf(false)}


    LaunchedEffect(Unit) {
        try {
            jobs = ApiClient.apiService.profesiones()
            rating = ApiClient.apiService.rating()
        } catch (e: Exception) {
            // Podríamos agregar una alerta de error si falla la carga.
            Log.d("ERROR", e.toString())
        }
    }

    LaunchedEffect(Unit) {
        jobs= ApiClient.apiService.profesiones()
        rating= ApiClient.apiService.rating()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Filter(
            modifier = Modifier.weight(0.6f),
            "Rubro",
            jobs,
            onItemSelected={value -> filtersApplied=true},
            onItemChange={value -> profesionSeleccionada=value},
            clearRubro,
            onChangeClear={value -> clearRubro=value}
        )

        Spacer(modifier = Modifier.width(8.dp))

        Filter(
            modifier = Modifier.weight(0.5f),
            "Valoracion",
            rating,
            onItemSelected={value -> filtersApplied=true},
            onItemChange={value -> puntajeSeleccionado=value},
            clearPuntaje,
            onChangeClear={value -> clearPuntaje=value}

        )


        // Icono de recarga
        IconButton(
            onClick = {
                listaProveedores.busqueda.value = mutableListOf()
                profesionSeleccionada = ""
                puntajeSeleccionado = "-1"
                // Realizar alguna acción cuando se presiona el icono de recarga
                Log.d("ReloadButton", "Botón presionado")
                clearPuntaje=true
                clearRubro=true
            }
        ) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Recargar")
        }
    }
    if (filtersApplied) {
        val listaConcatenada = listaProveedores.recomendados.value + listaProveedores.general.value
        listaProveedores.busqueda.value = buscarPorFiltro(listaConcatenada, profesionSeleccionada, puntajeSeleccionado.toFloat())
        if(listaProveedores.busqueda.value.isEmpty()){
            EmptySearch(){filtersApplied = false}
        }else{
            filtersApplied = false
        }
    }
}

@Composable
fun Filter(
    modifier: Modifier = Modifier,
    categoria: String,
    items: List<String>,
    onItemSelected: (Boolean) -> Unit,
    onItemChange: (String) -> Unit,
    clear: Boolean,
    onChangeClear: (Boolean) -> Unit,

) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(categoria) }
    val offsetY = (-4.dp)
    val maxHeight = 400.dp // Puedes ajustar la altura máxima según tus necesidades

    if(clear){
        Log.d("CATEGORIA",categoria)
        selectedItem=categoria
        onChangeClear(false)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = true },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = RoundedCornerShape(8.dp)
                ),
            content = {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable { expanded = true },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(selectedItem, fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
        )
    }

    Box(
        modifier = Modifier.offset(y = offsetY)
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .offset(y = offsetY)
                .heightIn(max = maxHeight)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemChange(item)
                        onItemSelected(true)
                        selectedItem = item
                        expanded = false
                    },
                    text = { Text(item, color = Color.Black) }
                )
            }
        }
    }
}

@Composable
fun Header(navController: NavController, username: String, paddingH: Dp, paddingV: Dp) {
    // Convertir el primer carácter a mayúscula y el resto a minúscula
    val formattedUsername = username.lowercase().replaceFirstChar { it.uppercase() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Agregar el botón de retroceso aquí
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Atrás"
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // Este Box ocupa la mitad del espacio del Row
                .padding(end = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = stringResource(id = R.string.welcome)+" "+formattedUsername,
                style = MaterialTheme.typography.headlineSmall,
                color = Turquesa4,
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ){
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null, // Proporciona una descripción adecuada
                modifier = Modifier.size(75.dp) // Tamaño de la imagen
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowHomePreview() {
    val navController = rememberNavController()
    HomeScreen(navController= navController, username="pedro")
}



fun buscarPorFiltro(
    listaProveedores: List<ProviderInfo>,
    profesion: String,
    puntaje: Float
): MutableList<ProviderInfo> {
    // Verificar si se debe aplicar el filtro de profesión
    val proveedoresFiltradosPorProfesion = if (profesion.isNotBlank()) {
        listaProveedores.filter { it.rol == profesion }
    } else {
        listaProveedores
    }

    // Verificar si se debe aplicar el filtro de puntaje
    val proveedoresFiltradosPorPuntaje = if (puntaje > -1) {
        proveedoresFiltradosPorProfesion.filter { it.puntaje >= puntaje && it.puntaje < puntaje + 1 }
    } else {
        proveedoresFiltradosPorProfesion
    }

    // Log de las profesiones de los proveedores filtrados
    Log.d("ProfesionPuntajeProveedorFiltrado", proveedoresFiltradosPorPuntaje.toString())

    // Devolver la lista de proveedores filtrados
    return proveedoresFiltradosPorPuntaje.toMutableList()
}


@Composable
fun EmptySearch(function: () -> Unit) {
    val text = "No hay resultados para esa búsqueda"

    AlertDialog(
        onDismissRequest = function,
        title = {
            Text(text)
        },
        confirmButton = {
            Button(
                onClick = function,
                enabled = true,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Turquesa3,
                    contentColor = Color.White,
                    disabledContainerColor = Turquesa2,
                    disabledContentColor = Turquesa3
                )
            ) {
                Text("Aceptar")
            }
        },
        modifier = Modifier
            .border(2.dp, Color.Black, RoundedCornerShape(23.dp))

    )
}
