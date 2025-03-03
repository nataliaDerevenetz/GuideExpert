package com.example.GuideExpert.presentation.ExcursionsScreen.DetailScreen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.example.GuideExpert.R
import com.example.GuideExpert.domain.models.ExcursionData
import com.example.GuideExpert.domain.models.Filter
import com.example.GuideExpert.domain.models.Image
import com.example.GuideExpert.presentation.ExcursionsScreen.HomeScreen.components.shimmerEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KFunction1

@Stable
interface ExcursionDetailScope {
    val excursionData: Flow<ExcursionData?>
    val excursionImages: Flow<List<Image>>
    val onNavigateToBack: () -> Boolean
    val getFiltersGroups: List<Filter>
    val stateView: StateFlow<UIState>
    val navigateToAlbum: (Int) -> Unit
    val navigateToImage: (Int,List<Image>,Int) -> Unit
    val handleEvent : (ExcursionDetailUiEvent) -> Unit
}

fun DefaultExcursionDetailScope(
    excursionData: Flow<ExcursionData?>,
    excursionImages: Flow<List<Image>>,
    onNavigateToBack: () -> Boolean,
    getFiltersGroups: List<Filter>,
    stateView: StateFlow<UIState>,
    navigateToAlbum: (Int) -> Unit,
    navigateToImage: (Int,List<Image>,Int) -> Unit,
    handleEvent : (ExcursionDetailUiEvent) -> Unit
): ExcursionDetailScope {
    return object : ExcursionDetailScope {
        override val excursionData: Flow<ExcursionData?>
            get() = excursionData
        override val excursionImages: Flow<List<Image>>
            get() = excursionImages
        override val onNavigateToBack: () -> Boolean
            get() = onNavigateToBack
        override val getFiltersGroups: List<Filter>
            get() = getFiltersGroups
        override val stateView: StateFlow<UIState>
            get() = stateView
        override val navigateToAlbum: (Int) -> Unit
            get() = navigateToAlbum
        override val navigateToImage: (Int,List<Image>,Int) -> Unit
            get() = navigateToImage
        override val handleEvent: (ExcursionDetailUiEvent) -> Unit
            get() = handleEvent
    }
}

@Composable
fun rememberDefaultExcursionDetailScope(
    excursionData: Flow<ExcursionData?>,
    excursionImages: Flow<List<Image>>,
    onNavigateToBack: () -> Boolean,
    getFiltersGroups: List<Filter>,
    stateView: StateFlow<UIState>,
    navigateToAlbum: (Int) -> Unit,
    navigateToImage: (Int,List<Image>,Int) -> Unit,
    handleEvent : KFunction1<ExcursionDetailUiEvent, Unit>
): ExcursionDetailScope = remember(excursionData,excursionImages,onNavigateToBack,getFiltersGroups,stateView,navigateToAlbum,navigateToImage,handleEvent) {
    DefaultExcursionDetailScope(excursionData,excursionImages,onNavigateToBack,getFiltersGroups,stateView,navigateToAlbum,navigateToImage,handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcursionDetailScreen(
    navigateToAlbum: (Int) -> Unit,
    navigateToImage: (Int,List<Image>,Int) -> Unit,
    onNavigateToBack:() -> Boolean,
    viewModel: ExcursionDetailViewModel = hiltViewModel(),
    scopeState:ExcursionDetailScope = rememberDefaultExcursionDetailScope(
        excursionData = viewModel.excursion,
        excursionImages = viewModel.images,
        onNavigateToBack = onNavigateToBack,
        getFiltersGroups = viewModel.getFiltersGroups(),
        stateView = viewModel.stateView,
        navigateToAlbum = navigateToAlbum,
        navigateToImage = navigateToImage,
        handleEvent=viewModel::handleEvent),
  //  dataContent: @Composable ExcursionDetailScope.() -> Unit ={},
) {

    val uiState by scopeState.stateView.collectAsStateWithLifecycle()


   Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon={ IconButton({ scopeState.onNavigateToBack()}) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")}},
                actions={
                    IconButton({ }) { Icon(Icons.Filled.FavoriteBorder, contentDescription = "featured")}
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.height(56.dp).shadow(6.dp),
                windowInsets = WindowInsets(0)
            )
        }
    ) { innerPadding ->
           when(uiState.contentState){
               is ExcursionInfoUIState.Data -> { scopeState.ExcursionDataContent(innerPadding) }
               is ExcursionInfoUIState.Error -> { scopeState.ExcursionDataError(innerPadding) }
               is ExcursionInfoUIState.Idle -> {}
               is ExcursionInfoUIState.Loading -> { LoadingExcursionDetail(innerPadding) }
           }
    }
}

@Composable
fun ExcursionDetailScope.ExcursionDataError(innerPadding: PaddingValues) {
    Column (Modifier.padding(innerPadding).fillMaxSize()){
        Row(
            Modifier.padding(start = 15.dp, end = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(id = R.string.failedload), color = Color.Gray)
            TextButton({handleEvent(ExcursionDetailUiEvent.OnLoadExcursionInfo) }) {
                Text(stringResource(id = R.string.update), fontSize = 15.sp, color = Color.Blue)
            }
        }
        Row {
            ExcursionDataContent(PaddingValues(0.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExcursionDetailScope.ExcursionDataContent(innerPadding: PaddingValues) {
    val excursionData by excursionData.collectAsStateWithLifecycle(null)
    val excursionImages by excursionImages.collectAsStateWithLifecycle(null)
    val scrollState = rememberScrollState()

    Column(Modifier.padding(innerPadding).fillMaxSize()
        .verticalScroll(scrollState)) {
        excursionImages?.let {
            HorizontalMultiBrowseCarousel(
                state = rememberCarouselState { excursionImages!!.count() },
                modifier = Modifier.padding(top = 5.dp).fillMaxWidth().height(250.dp),
                preferredItemWidth = 350.dp,
                itemSpacing = 1.dp,
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) { index ->
                val item = it[index]
                Card(
                    modifier = Modifier.height(250.dp).maskClip(MaterialTheme.shapes.extraLarge)
                ) {
                    NetworkImageCarousel(
                        item.url,
                        "",
                        500,
                        250,
                        navigateToImage,
                        item.id,
                        excursionImages!!,
                        index
                    )
                }
            }
            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = { excursionData?.let { navigateToAlbum(it.excursionId) } }) {
                Text(stringResource(id = R.string.showall), color = Color.Blue)
            }
        }
        excursionData?.let{
            Column(modifier = Modifier.padding(start = 10.dp, end=10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = it.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Text(
                    text = it.description,
                    modifier = Modifier.height(24.dp),
                    style = MaterialTheme.typography.titleMedium,
                )
                HorizontalDivider(modifier = Modifier.padding(top = 10.dp))
                if (getFiltersGroups.isNotEmpty()) {
                    val group = getFiltersGroups
                        .filter { idGroup -> it.group == idGroup.id }
                        .map { it.description }.first()
                    Text(
                        text = group,
                        color =Color.Gray,
                        modifier = Modifier.height(24.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 16.sp
                    )
                }
                Text(
                    text = it.text,
                    modifier = Modifier.height(24.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp
                )
                /*
                                Column {
                                    Text("Incr :: $count")
                                    Button(onClick = { onIcr() }) {
                                        Text(text = "Increase", fontSize = 25.sp)
                                    }
                                }*/
               // Spacer(Modifier.height((820.dp).coerceAtLeast(0.dp)))
            }
        }

    }
}

@Composable
fun NetworkImageCarousel(url: String, contentDescription: String?, width: Int, height: Int,
                         navigateToImage: (Int,List<Image>,Int) -> Unit, imageId:Int,
                         excursionImages: List<Image>,
                         indexImage:Int) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().height(height.dp).clickable { navigateToImage(imageId,excursionImages,indexImage) },
        loading = {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.requiredSize(48.dp)
            )
        },
        error = {
            Log.d("TAG", url)
            Log.d("TAG", "image load: Error!")
            Log.d("TAG", "something went wrong ${it.result.throwable.localizedMessage}")
        }
    )
}

@Composable
private fun LoadingExcursionDetail(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier.padding(innerPadding).fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(250.dp)
                .clip(shape= RoundedCornerShape(30.dp))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(20.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(20.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}