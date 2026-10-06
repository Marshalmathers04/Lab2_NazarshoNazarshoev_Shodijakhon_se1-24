package uz.ttpu.composearchlab

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import uz.ttpu.composearchlab.ui.signin.SignInRoute
import uz.ttpu.composearchlab.ui.taste.TastePickerRoute
import uz.ttpu.composearchlab.ui.theme.ComposeArchLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeArchLabTheme {
                ComposeArchLabApp()
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int
) {
    NAME("Name", R.drawable.ic_home),
    NEWS("News", R.drawable.ic_home),
    SIGN_IN("Sign In", R.drawable.ic_account_box),
    TASTE_PICKER("Taste Picker", R.drawable.ic_favorite)
}

@PreviewScreenSizes
@Composable
fun ComposeArchLabApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.NAME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            painter = painterResource(destination.icon),
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = destination == currentDestination,
                    onClick = { currentDestination = destination }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val modifier = Modifier.padding(innerPadding)
            when (currentDestination) {
                AppDestinations.NAME -> NameScreen(modifier = modifier)
                AppDestinations.NEWS -> NewsScreen(modifier = modifier)
                AppDestinations.SIGN_IN -> SignInRoute(modifier = modifier)
                AppDestinations.TASTE_PICKER -> TastePickerRoute(modifier = modifier)
            }
        }
    }
}

// --- Part A: Task 2 & Task 3 (NameField & NameScreen) ---

// Stateless composable: receives state and emits event upwards
@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") },
        modifier = modifier
    )
}

// Stateful composable: owns state with rememberSaveable
@Composable
fun NameScreen(modifier: Modifier = Modifier) {
    // Comment (Task 2 & 3): rememberSaveable persists state in a Bundle across configuration changes
    // (such as screen rotation) and process death, whereas remember state is discarded upon Activity recreation.
    var name by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier.padding(24.dp)) {
        NameField(
            name = name,
            onNameChange = { name = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Hello, $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun NameFieldPreview() {
    ComposeArchLabTheme {
        NameField(
            name = "Amin",
            onNameChange = {}
        )
    }
}

// --- Part A: Task 4 (News, Header overloads & NewsScreen) ---

data class News(
    val title: String,
    val subtitle: String,
    val views: Int
)

@Composable
fun Header(news: News) {
    SideEffect { Log.d("Recompose", "Header(news)") }
    Column {
        Text(news.title)
        Text(news.subtitle)
    }
}

@Composable
fun Header(title: String, subtitle: String) {
    SideEffect { Log.d("Recompose", "Header(title, subtitle)") }
    Column {
        Text(title)
        Text(subtitle)
    }
}

// Comment 1 (Task 4): Header(title, subtitle) is the better design because passing primitive parameters
// allows Compose to skip recomposition when unrelated properties in News (such as views) change.
// Header(news) recomposes every time views is incremented because the News object reference changes.
//
// Comment 2 (Task 4): Group parameters into a class instead of passing them individually when the parameters
// naturally form a single cohesive domain object or when passing individual parameters creates excessively long,
// unmaintainable composable signatures across multiple layers.
@Composable
fun NewsScreen(modifier: Modifier = Modifier) {
    var news by remember {
        mutableStateOf(News("Compose is declarative", "State in, UI out", views = 0))
    }
    Column(modifier = modifier.padding(24.dp)) {
        Header(news)
        Header(news.title, news.subtitle)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Views: ${news.views}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { news = news.copy(views = news.views + 1) }) {
            Text("Add view")
        }
    }
}
