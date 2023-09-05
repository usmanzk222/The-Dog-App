package com.usman.mvvmsample.presentation.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.themeadapter.material.MdcTheme
import com.usman.mvvmsample.R
import com.usman.mvvmsample.data.model.Measurement
import com.usman.mvvmsample.domain.model.DogBreed
import com.usman.mvvmsample.presentation.utils.LoadImageAsync

@Composable
fun DetailScreen(viewModel: DetailViewModel = hiltViewModel()) {
    Surface {
        val detailData by viewModel.breedDetail.collectAsStateWithLifecycle()

        detailData.let {
            when (it) {
                is DetailUiState.Success -> DetailViewContent(it.breedDetail)
                is DetailUiState.Error -> ErrorMessage(message = it.message)
                else -> AppProgressBar()
            }
        }
    }
}

@Composable
private fun DetailViewContent(detailData: DogBreed) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        LoadImageAsync(
            referenceImageId = detailData.referenceImageId,
            stringResource(R.string.dog_image),
            modifier = Modifier.fillMaxWidth()
        )
        BreedName(name = detailData.name)
        Label(label = stringResource(R.string.group), value = detailData.breedGroup)
        Label(label = stringResource(R.string.origin), value = detailData.origin)
        Label(label = stringResource(R.string.life_span), value = detailData.lifeSpan)
        Label(label = stringResource(R.string.height), value = detailData.height.toString())
        Label(label = stringResource(R.string.weight), value = detailData.weight.toString())
        Label(label = stringResource(R.string.temperament), value = detailData.temperament)
        Label(label = stringResource(R.string.description), value = detailData.description)
    }
}

@Composable
private fun BreedName(name: String) {
    Text(
        text = name,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun Label(label: String, value: String) {
    if (value.isNotEmpty()) {
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.margin_small))
        )
    }
}

@Composable
fun ErrorMessage(message: String?) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        androidx.compose.material3.Text(
            text = message ?: stringResource(R.string.error_message),
            fontWeight = FontWeight.Medium,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AppProgressBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator()
    }
}

@Preview
@Composable
private fun DetailContentPreview() {

    val detailData = DogBreed(
        76, "Water Retriever", "Sporting",
        Measurement("21 - 26", "53 - 66"),
        "10 - 13 years", "Chesapeake Bay Retriever", origin = "",
        temperament = "Affectionate, Intelligent, Quiet, Dominant, Happy, Protective",
        weight = Measurement("55 - 80", "25 - 36"), description = "",
        referenceImageId = "https://cdn2.thedogapi.com/images/9BXwUeCc2.jpg"
    )
    MdcTheme {
        Surface {
            DetailViewContent(detailData)
        }

    }
}